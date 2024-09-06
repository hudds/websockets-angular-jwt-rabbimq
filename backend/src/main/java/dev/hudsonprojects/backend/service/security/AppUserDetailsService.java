package dev.hudsonprojects.backend.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.hudsonprojects.backend.model.AppUserDetails;
import dev.hudsonprojects.backend.repository.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
	 
	
	private final AppUserRepository appUserRepository;

	
	public AppUserDetailsService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String usernameOrPassword) throws UsernameNotFoundException {
		return appUserRepository.findByEmailOrCpf(usernameOrPassword, usernameOrPassword)
				.map(AppUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(usernameOrPassword));
	}
	
	
	

}
