package fr.epita.assistants.entities;

import fr.epita.assistants.myide.domain.entity.*;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class ProjectServiceClass implements ProjectService {

    private NodeServiceClass nodeService_ = new NodeServiceClass();
    public Project load(Path root) {
        // Set the project root
        Node rootNode = new NodeClass(root);

        // Set the project aspects
        Set<Aspect> aspects = new HashSet<Aspect>();
        aspects.add(new AspectClass(Mandatory.Aspects.ANY));

        //
        String path = root.toString();
        File pomCheck = new File(path + "/pom.xml");
        if (pomCheck.exists()) {
            aspects.add(new AspectClass(Mandatory.Aspects.MAVEN));
        }
        File gitCheck = new File(path + "/.git");
        if (gitCheck.exists()) {
            aspects.add(new AspectClass(Mandatory.Aspects.GIT));
        }

        // Create the project
        ProjectClass project = new ProjectClass(rootNode, aspects);
        return project;
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Feature.Type featureType, Object... params) {
        return new FeatureClass(featureType).execute(project, params);
    }

    @Override
    public NodeService getNodeService() {
        return nodeService_;
    }
}
