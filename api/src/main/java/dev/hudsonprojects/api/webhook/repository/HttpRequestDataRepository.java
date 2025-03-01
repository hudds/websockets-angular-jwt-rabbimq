package dev.hudsonprojects.api.webhook.repository;

import dev.hudsonprojects.api.webhook.entity.HttpRequestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HttpRequestDataRepository extends JpaRepository<HttpRequestData, Long> {

}
