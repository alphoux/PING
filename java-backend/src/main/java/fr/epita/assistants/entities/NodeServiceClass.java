package fr.epita.assistants.entities;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.utils.Exceptions;
import fr.epita.assistants.utils.ThrowingRunnable;
import fr.epita.assistants.utils.ThrowingSupplier;

import java.io.Console;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


public class NodeServiceClass implements NodeService {

    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent) {
        if (!node.isFile())
            return node;

        Exceptions.mayThrow(new ThrowingRunnable<Exception>() {
            @Override
            public void run() throws Exception {
                String content = Files.readString(node.getPath());
                content = content.substring(0, from) + new String(insertedContent) + content.substring(to);
                Files.write(node.getPath(), content.getBytes());
            }
        });
        return node;
    }

    @Override
    public boolean delete(Node node) {
        if (!node.isFile())
        {
            for (Node child : node.getChildren())
            {
                if (!delete(child))
                {
                    return false;
                }
            }
        }
       try {
              Files.delete(node.getPath());
         } catch (Exception e) {
              System.out.println(e.getMessage());
              return false;
       }
         return true;
    }

    @Override
    public Node create(Node folder, String name, Node.Type type) {
        Node newNode = Exceptions.mayThrow(new ThrowingSupplier<Node, Exception>() {

            @Override
            public Node get() throws Exception {
                NodeClass newNode = null;
                if (type == Node.Types.FILE)
                {
                    File newFile = new File(folder.getPath().toString(), name);
                    if (newFile.createNewFile())
                    {
                        newNode = new NodeClass(newFile.toPath());
                    }
                }
                else if (type == Node.Types.FOLDER)
                {
                    Path newPath = folder.getPath().resolve(name);
                    Files.createDirectory(newPath);
                    newNode = new NodeClass(newPath);
                }
                return newNode;
            }
        });
        if (newNode != null)
        {
            folder.getChildren().add(newNode);
        }
        return newNode;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        Node movedNode = Exceptions.mayThrow(new ThrowingSupplier<Node, Exception>() {
                @Override
                public Node get() throws Exception {
                    Path newPath = destinationFolder.getPath().resolve(nodeToMove.getPath().getFileName());
                    Files.move(nodeToMove.getPath(), newPath);
                    return new NodeClass(newPath);
                }
            });
        if (movedNode != null)
            destinationFolder.getChildren().add(movedNode);
        return movedNode;
    }

    @Override
    public String read(Node node) {
        if (!node.isFile())
            return "";
        try {
            String content = Files.readString(node.getPath());
            return content;
        }
        catch (Exception e)
        {
            return "";
        }
       
    }
    @Override
    public Node findNode(final Node node, Path path) {
        if (node.getPath().compareTo(path) == 0)
            return node;

        for (Node child : node.getChildren())
        {
            if (child.getPath().compareTo(path) == 0)
                return child;
            Node rec = findNode(child, path);
            if (rec != null)
                return rec;
        }
        return null;
    }


}
