package fr.epita.assistants;

import java.lang.reflect.Executable;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import fr.epita.assistants.entities.NodeClass;
import fr.epita.assistants.entities.NodeServiceClass;
import fr.epita.assistants.entities.ProjectClass;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature.ExecutionReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.LSP.LspClient;

public final class Singleton {

    private static Singleton instance;

    private ProjectService ps;
    private FileSystem fs;
    private Project project;
    private NodeService ns;
    private Node currentNode;
    private LspClient lspClient;

    private Singleton(String pathToLoad) {
        System.out.println("Instantiate Singleton");
        this.ps = MyIde.init(null);
        this.fs = FileSystems.getDefault();
        Path path = fs.getPath(pathToLoad);
        this.project = ps.load(path);
        this.ns = ps.getNodeService();

        for (Node node : this.project.getRootNode().getChildren())
        {
            System.out.println(node.getPath().toString());
        }
        /*
        //Start Server
        Runnable launchScript = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                String filePath = new File("").getAbsolutePath();
                filePath = filePath.concat("/LSP/startServer.sh");
                System.out.println("Launching");
                System.out.println(filePath);
                try {
                    
                    Runtime.getRuntime().exec(filePath);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        };

        Thread threadScript = new Thread(launchScript);
        threadScript.start();

        try {
            this.lspClient = new LspClient(pathToLoad);
        }

        catch (Exception e)
		{
			System.out.println(e);
		}
        
        Runnable lspServer = new Runnable() {
			@Override
			public void run() {
				try {
					lspClient.processMessages();
					lspClient.close();
					}
					catch (Exception e)
					{
						System.out.println(e);
					}
			}
		};
		Thread newThread = new Thread(lspServer);
        newThread.start();
        */
    }

    public static boolean alreadyInstantiated() {
        return instance != null;
    }

    public static Singleton getInstance(String pathToLoad) {
        if (instance == null)
        {
            instance = new Singleton(pathToLoad);
        }
        return instance;
    }

    public Node getRootNode() {
        return this.project.getRootNode();
    }

    public void LoadPath(String pathToLoad) {
        Path path = fs.getPath(pathToLoad);
        this.project = ps.load(path);
        this.ns = ps.getNodeService();

        for (Node node : this.project.getRootNode().getChildren())
        {
            System.out.println(node.getPath().toString());
        }
    }

    public String makeActive(String pathToLoad) {
        Path path = fs.getPath(pathToLoad);
        System.out.println("Looking for path " + path.toString());
        Node node = this.ns.findNode(this.project.getRootNode(), path);
        if (node != null)
        {
            currentNode = node;
            return this.ns.read(currentNode);
        }
        return "Cannot find the file requested";
    }
    
    public String getContent() {
        if (currentNode == null)
            return "";
        return this.ns.read(currentNode);
    }

    public String updateContent(String newContent) {

        this.ns.insertContent(this.currentNode, newContent.getBytes());
        return newContent;
    }

    public List<String> getAspects() {
        ArrayList<String> res = new ArrayList<>();
        for (Aspect aspect : this.project.getAspects())
        {
            res.add(aspect.getType().toString());
        }

        return res;
    }

    public Path getPath()
    {
        return this.currentNode.getPath();
    }

    public ExecutionReport mavenBuild() {
        return this.project.getFeature(Mandatory.Features.Maven.COMPILE).get().execute(this.project);
    }

    public ExecutionReport mavenRun() {
        return this.project.getFeature(Mandatory.Features.Maven.EXEC).get().execute(this.project);
    }
}
