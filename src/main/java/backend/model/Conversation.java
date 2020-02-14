package backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany (mappedBy = "conversation", fetch = FetchType.LAZY)
    private List<ConversationMessage> conversationMessages;

    private String convStarter;
    private String convPartner;

    public void addMessage(ConversationMessage convMess) {
        this.conversationMessages.add(convMess);
    }

    public Conversation() {
        conversationMessages = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ConversationMessage> getConversationMessages() {
        return conversationMessages;
    }

    public void setConversationMessages(List<ConversationMessage> conversationMessages) {
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
