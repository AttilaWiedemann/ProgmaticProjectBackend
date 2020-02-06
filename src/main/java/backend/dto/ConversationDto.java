package backend.dto;

public class ConversationDto {
    private String convStarter;
    private String convPartner;


    public ConversationDto() {
    }

    public String getConvStarter() {
        return convStarter;
    }

    public void setConvStarter(String convStarter) {
        this.convStarter = convStarter;
    }

    public String getConvPartner() {
        return convPartner;
    }

    public void setConvPartner(String convPartner) {
        this.convPartner = convPartner;
    }
}
