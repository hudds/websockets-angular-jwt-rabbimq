package dev.hudsonprojects.backend.appuser;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmailOrCpf(String email, String cpf);
    
    Optional<AppUser> findByCpf(String cpf);
    
    Optional<AppUser> findByUsername(String username);
   
    boolean existsByCpf(String cpf);

    boolean existsByCpfAndUserIdNot(String cpf, Long userId);

    boolean existsByEmail(String email);

    boolean existsByEmailAndUserIdNot(String email, Long userId);

    @Query("select appUser.username from AppUser appUser where appUser.username in :usernames")
    List<String> getExistingUsernames(@Param("usernames") Collection<String> usernames);

	Optional<AppUser> findByEmail(String usernameOrPassword);
	
	@Query("select new dev.hudsonprojects.backend.appuser.AppUserDTO(user) from AppUser user where user.userId = :userId")
	Optional<AppUserDTO> findDTOById(@Param("userId") Long userId);

}
