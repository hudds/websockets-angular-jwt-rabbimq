package dev.hudsonprojects.backend.repository;

import org.springframework.data.repository.CrudRepository;

import dev.hudsonprojects.backend.model.entity.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long>{

}
