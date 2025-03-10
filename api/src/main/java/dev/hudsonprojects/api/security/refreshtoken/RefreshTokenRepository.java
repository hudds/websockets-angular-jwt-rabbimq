package dev.hudsonprojects.api.security.refreshtoken;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
	
	Optional<RefreshToken> findByToken(String token);

}
