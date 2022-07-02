package fr.epita.assistants.API.response;

public class ContentResponse {

    private String content;

    public ContentResponse (String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
    
}
