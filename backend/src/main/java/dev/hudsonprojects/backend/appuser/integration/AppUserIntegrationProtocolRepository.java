package dev.hudsonprojects.backend.appuser.integration;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppUserIntegrationProtocolRepository extends CrudRepository<AppUserIntegrationProtocol, Long> {
}
