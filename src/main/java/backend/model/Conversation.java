package backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Message> conversationMessages;

    private String convStarter;
    private String convPartner;

    public Conversation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Message> getConversationMessages() {
        return conversationMessages;
    }

    public void setConversationMessages(List<Message> conversationMessages) {
        this.conversationMessages = conversationMessages;
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
