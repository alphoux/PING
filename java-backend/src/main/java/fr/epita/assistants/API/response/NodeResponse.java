package fr.epita.assistants.API.response;

import java.util.List;
import java.util.ArrayList;
import fr.epita.assistants.myide.domain.entity.Node;

public class NodeResponse {

    private String path;
    private boolean isFile;
    private List<NodeResponse> children;
    private int id;
    
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public boolean isFile() {
        return isFile;
    }
    public void setFile(boolean isFile) {
        this.isFile = isFile;
    }
    public List<NodeResponse> getChildren() {
        return children;
    }
    public void setChildren(List<NodeResponse> children) {
        this.children = children;
    }


    public NodeResponse(String path, boolean isFile, List<Node> children, int id) {
        this.id = id;
        this.path = path;
        this.isFile = isFile;
        this.children = new ArrayList<>();

        for (Node node : children)
        {
            this.children.add(new NodeResponse(node.getPath().toString(), node.isFile(), node.getChildren(), node.getId()));
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }    
    
}
