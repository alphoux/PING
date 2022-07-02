package fr.epita.assistants;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import fr.epita.assistants.entities.NodeClass;
import fr.epita.assistants.entities.NodeServiceClass;
import fr.epita.assistants.entities.ProjectClass;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;

public final class Singleton {

    private static Singleton instance;

    private ProjectService ps;
    private FileSystem fs;
    private Project project;
    private NodeService ns;
    private Node currentNode;

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
}
