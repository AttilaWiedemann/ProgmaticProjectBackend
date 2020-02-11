package backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessageDto {

    @NotNull
    @NotBlank
    String text;

    public MessageDto() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
