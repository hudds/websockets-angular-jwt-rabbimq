package dev.hudsonprojects.backend.integration.protocol;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationHttpProtocolRepository extends CrudRepository<IntegrationHttpProtocol, Long> {
}
