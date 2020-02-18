package backend.repos;

import backend.model.messageModels.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Conversation findConversationByConvPartnerAndConvStarter(String userOne, String userTwo);
    Conversation findConversationById(Long id);
}
