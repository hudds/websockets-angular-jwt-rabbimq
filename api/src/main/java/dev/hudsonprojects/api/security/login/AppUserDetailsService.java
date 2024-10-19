package dev.hudsonprojects.api.security.login;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.hudsonprojects.api.appuser.AppUser;
import dev.hudsonprojects.api.appuser.AppUserRepository;
import dev.hudsonprojects.api.common.lib.util.CPFUtil;
import dev.hudsonprojects.api.common.lib.util.EmailUtils;

@Service
public class AppUserDetailsService implements UserDetailsService {
	 
	
	private final AppUserRepository appUserRepository;

	
	public AppUserDetailsService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new AppUserDetails(loadAppUserByUsername(username));
	}

	public AppUser loadAppUserByUsername(String username) throws UsernameNotFoundException {
		return appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}


}
