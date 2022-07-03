package fr.epita.assistants.API.response;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.API.response.NodeResponse;
import java.util.List;

public class ProjectResponse {
    private String path;
    private NodeResponse nodes;
    private List<String> aspects;

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public NodeResponse getNodes() {
        return nodes;
    }
    public void setNodes(Node rootNode) {
        this.nodes = new NodeResponse(rootNode.getPath().toString(), rootNode.isFile(), rootNode.getChildren());;
    }

    

    public ProjectResponse(String path, Node rootNode, List<String> aspects) {
        this.path = path;
        this.nodes = new NodeResponse(rootNode.getPath().toString(), rootNode.isFile(), rootNode.getChildren());
        this.aspects = aspects;
    }
    public void setNodes(NodeResponse nodes) {
        this.nodes = nodes;
    }
    public List<String> getAspects() {
        return aspects;
    }
    public void setAspects(List<String> aspects) {
        this.aspects = aspects;
    }    
}
