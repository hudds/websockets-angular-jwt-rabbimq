package dev.hudsonprojects.api.webhook.repository;

import dev.hudsonprojects.api.common.repository.GenericJpaRepository;
import dev.hudsonprojects.api.webhook.WebhookDTO;
import dev.hudsonprojects.api.webhook.entity.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebhookRepository extends JpaRepository<Webhook, Long>, GenericJpaRepository<Webhook> {

    @Query("SELECT DISTINCT new dev.hudsonprojects.api.webhook.WebhookDTO(webhook) " +
            " FROM WebhookTopic topic " +
            " JOIN topic.webhook webhook " +
            " JOIN FETCH webhook.httpRequestData requestData " +
            " LEFT JOIN FETCH httpRequestData.headers header " +
            " WHERE topic.id.topic = :topic AND topic.webhook.active ")
    List<WebhookDTO> getWebhookDTOByTopic(@Param("topic") String topic);

    Optional<Webhook> findByWebhookIdAndOwnerId(Long webhookId, Long ownerId);

    @Query("""
        SELECT webhook
        FROM Webhook webhook
        WHERE webhook.ownerId = :ownerId
            AND webhook.httpRequestData.url = :url
            AND webhook.httpRequestData.method = :method
    """)
    Optional<Webhook> findByOwnerIdAndUrlAndMethod(@Param("ownerId") Long ownerId, @Param("url") String url, @Param("method") String method);

    @Query("""
        SELECT count(1) > 0
        FROM Webhook webhook
        WHERE webhook.ownerId = :ownerId
            AND webhook.httpRequestData.url = :url
            AND webhook.httpRequestData.method = :method
            AND webhook.webhookId != :webhookId
    """)
    boolean existsByOwnerIdAndUrlAndMethodAndNotWebhookId(@Param("ownerId") Long ownerId, @Param("url") String url, @Param("method") String method, @Param("webhookId") Long webhookId);

    @Query("""
        SELECT count(*) > 0
        FROM Webhook webhook
        WHERE webhook.ownerId = :ownerId
            AND webhook.httpRequestData.url = :url
            AND webhook.httpRequestData.method = :method
    """)
    boolean existsByOwnerIdAndUrlAndMethod(@Param("ownerId") Long ownerId, @Param("url") String url, @Param("method") String method);
}
