package dev.hudsonprojects.api.security.refreshtoken.refreshtokenfamily;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenFamilyRepository extends CrudRepository<RefreshTokenFamily, Long>{
	

}
