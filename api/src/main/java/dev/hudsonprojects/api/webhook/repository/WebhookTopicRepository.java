package dev.hudsonprojects.api.webhook.repository;

import dev.hudsonprojects.api.common.repository.GenericJpaRepository;
import dev.hudsonprojects.api.webhook.entity.WebhookTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookTopicRepository extends JpaRepository<WebhookTopic, Long>, GenericJpaRepository<WebhookTopic> {

    @Modifying
    @Query(value = "DELETE FROM WebhookTopic topic WHERE topic.id.webhookId = :webhookId")
    void deleteByWebhookId(@Param("webhookId") Long webhookId);

    @Modifying
    @Query(value = """
        DELETE FROM WebhookTopic topic
        WHERE topic.id.webhookId != :webhookId
            AND topic.webhook.ownerId = :ownerId
            AND topic.id.topic = :topic
    """)
    void deleteByTopicAndOwnerIdAndNotWebhookId(@Param("topic") String topic, @Param("ownerId") Long ownerId, @Param("webhookId") Long webhookId);
}
