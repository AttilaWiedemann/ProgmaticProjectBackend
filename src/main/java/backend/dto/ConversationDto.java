package backend.dto;

public class ConversationDto {
    private String convPartner;
    private String firstMessage;


    public ConversationDto() {
    }

    public String getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(String firstMessage) {
        this.firstMessage = firstMessage;
    }

    public String getConvPartner() {
        return convPartner;
    }

    public void setConvPartner(String convPartner) {
        this.convPartner = convPartner;
    }
}
