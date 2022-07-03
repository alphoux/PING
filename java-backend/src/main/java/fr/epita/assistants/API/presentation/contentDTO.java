package fr.epita.assistants.API.presentation;



public class contentDTO {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public contentDTO(String content) {
        this.content = content;
    }

    public contentDTO() {
        this.content = "";
    }
    
}
