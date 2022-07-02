package fr.epita.assistants.API.response;

public class ContentResponse {

    private String path;
    private String content;

    public ContentResponse (String path, String content) {
        this.path = path;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    
    
}
