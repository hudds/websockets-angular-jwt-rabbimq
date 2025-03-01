package dev.hudsonprojects.api.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmailOrCpf(String email, String cpf);

    Optional<Person> findByCpf(String cpf);
    
    boolean existsByCpf(String cpf);

    boolean existsByCpfAndPersonIdNot(String cpf, Long userId);

    boolean existsByEmail(String email);

    boolean existsByEmailAndPersonIdNot(String email, Long userId);

    Optional<Person> findByEmail(String usernameOrPassword);

    Optional<Long> findPersonIdByCpf(String cpf);

    Optional<Person> findPersonByCpf(String cpf);

    Person getReferenceByCpf(String cpf);
}
