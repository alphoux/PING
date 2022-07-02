package fr.epita.assistants;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
/*
public class Main {

    public static void main(String[] args)
    {
        ProjectService ps = MyIde.init(null);
        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath("/Users/theoripoll/Documents/Epita/ING1/prog/PING/PING");
        Project project = ps.load(path);
        NodeService ns = ps.getNodeService();
        Node dir = ns.create(project.getRootNode(), "TestDir", Node.Types.FOLDER);
        Node file = ns.create(project.getRootNode(), "TestFile", Node.Types.FILE);
        file = ns.move(file, dir);
        ns.update(file, 0, 0, new String("This is a test file").getBytes());
        /*
        for (Node node : project.getRootNode().getChildren())
        {
            System.out.println(node.getPath().toString());
            if (node.getPath().toString().contains("TestDir"))
            {
                ns.delete(node);
            }
        }
        System.out.println("After delete");
        for (Node node : project.getRootNode().getChildren())
        {
            System.out.println(node.getPath().toString());
        }
        
    }
}
*/