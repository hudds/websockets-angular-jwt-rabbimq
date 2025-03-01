package dev.hudsonprojects.api.webhook.repository;


import dev.hudsonprojects.api.webhook.entity.WebhookNotificationHttpProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookNotificationHttpProtocolRepository extends JpaRepository<WebhookNotificationHttpProtocol, Long> {
}
