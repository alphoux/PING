package fr.epita.assistants.LSP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LspClient {
  BufferedReader in;
  PrintWriter out;
  ServerSocket serverSocket;
  Socket clientSocket;
  String rootUri;

  public LspClient(String rootUri) throws Exception {
    this.rootUri = rootUri;
    serverSocket = new ServerSocket(6666);

    System.out.println("Waiting for server to connect");
    clientSocket = serverSocket.accept();
    System.out.println("Server connected");

    in = new BufferedReader(
      new InputStreamReader(clientSocket.getInputStream())
    );
    out = new PrintWriter(clientSocket.getOutputStream(), true);
  }

  /**
   * Processes incoming messages from the server
   */
  public void processMessages() throws Exception {
    // All messages start with a header
    String header;
    while ((header = in.readLine()) != null) {
      // Read all the headers and extract the message content from them
      int contentLength = -1;
      while (!header.equals("")) {
        System.out.println("Header: " + header);
        if (isContentLengthHeader(header)) {
          contentLength = getContentLength(header);
        }
        header = in.readLine();
      }

      System.out.println("Reading body");
      // Read the body
      if (contentLength == -1) {
        throw new RuntimeException("Unexpected content length in message");
      }
      char[] messageChars = new char[contentLength];
      in.read(messageChars, 0, contentLength);
      handleMessage(String.valueOf(messageChars));
    }
  }

  public void handleMessage(String message) {
    System.out.println("Message: " + message);

    if (message.contains("Main thread is waiting")) {
      System.out.println("Server is ready. Initializing");
      sendIntialize(this.rootUri);
    }
  }


  public void sendIntialize(String rootUri) {
    System.out.println("Sending initialize message");

    String initializeMessage = "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"initialize\",\"params\":{\"clientInfo\":{\"name\":\"PING\",\"version\":\"1\"}, \"workspaceFolders\": [{uri : \"file://"+ rootUri + "\", \"name\": \"main\"}]}}";
    sendMessage(initializeMessage);
  }

  public void sendMessage(String body) {
    String header = "Content-Length: " + body.getBytes().length + "\r\n";
    final String message = header + "\r\n" + body;
    out.println(message);
  }

  public boolean isContentLengthHeader(String header) {
    return header.toLowerCase().contains("content-length");
  }

  public int getContentLength(String header) {
    return Integer.parseInt(header.split(" ")[1]);
  }

  public void close() throws Exception {
    in.close();
    out.close();
    clientSocket.close();
    serverSocket.close();
  }
}