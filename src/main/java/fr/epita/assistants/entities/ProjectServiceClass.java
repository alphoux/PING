package fr.epita.assistants.entities;

import fr.epita.assistants.myide.domain.entity.*;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class ProjectServiceClass implements ProjectService {

    private NodeServiceClass nodeService_ = new NodeServiceClass();
    public Project load(Path root) {
        // Set the project root
        Node rootNode = new NodeClass(root.toString());

        // Set the project aspects
        Set<Aspect> aspects = new HashSet<Aspect>();
        aspects.add(new AspectClass(Mandatory.Aspects.ANY));

        // Create the project
        ProjectClass project = new ProjectClass(rootNode, aspects);
        return project;
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Feature.Type featureType, Object... params) {
        return null;
    }

    @Override
    public NodeService getNodeService() {
        return null;
    }
}
