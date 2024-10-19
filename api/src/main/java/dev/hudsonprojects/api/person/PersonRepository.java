package dev.hudsonprojects.api.person;

import dev.hudsonprojects.api.appuser.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<AppUser> findByEmailOrCpf(String email, String cpf);

    Optional<AppUser> findByCpf(String cpf);
    
    boolean existsByCpf(String cpf);

    boolean existsByCpfAndPersonIdNot(String cpf, Long userId);

    boolean existsByEmail(String email);

    boolean existsByEmailAndPersonIdNot(String email, Long userId);

    Optional<AppUser> findByEmail(String usernameOrPassword);

    Optional<Long> findPersonIdByCpf(String cpf);

    Optional<Person> findPersonByCpf(String cpf);
}
