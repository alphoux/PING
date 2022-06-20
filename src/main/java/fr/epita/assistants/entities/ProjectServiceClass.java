package fr.epita.assistants.entities;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;

import java.nio.file.Path;

public class ProjectServiceClass implements ProjectService {

    private NodeServiceClass nodeService_ = new NodeServiceClass();
    public Project load(Path root) {
        Node rootNode = new NodeClass(root);
        ProjectClass project = new ProjectClass(rootNode);
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
