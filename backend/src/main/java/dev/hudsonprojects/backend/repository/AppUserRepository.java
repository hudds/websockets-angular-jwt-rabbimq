package dev.hudsonprojects.backend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.hudsonprojects.backend.model.entity.AppUser;


@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmailOrCpf(String email, String cpf);
   
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

}
