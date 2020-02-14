package backend.dto;

public class NewMessageDto {
    Long id;
    String text;

    public NewMessageDto(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public NewMessageDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
