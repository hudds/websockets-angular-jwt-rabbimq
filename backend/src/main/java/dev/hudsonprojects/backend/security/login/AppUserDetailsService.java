package dev.hudsonprojects.backend.security.login;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.appuser.AppUserRepository;
import dev.hudsonprojects.backend.common.lib.util.CPFUtil;
import dev.hudsonprojects.backend.common.lib.util.EmailUtils;

@Service
public class AppUserDetailsService implements UserDetailsService {
	 
	
	private final AppUserRepository appUserRepository;

	
	public AppUserDetailsService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String cpfOrEmail) throws UsernameNotFoundException {
		
		return findAppUserByEmailOrCpf(cpfOrEmail).map(AppUserDetails::new)
					.orElseThrow(() -> new UsernameNotFoundException(cpfOrEmail));
		
	}


	public Optional<AppUser> findAppUserByEmailOrCpf(String cpfOrEmailOrUsername) {
		Optional<AppUser> byUsername = appUserRepository.findByUsername(cpfOrEmailOrUsername);
		
		if(byUsername.isPresent()) {
			return byUsername;
		}
		
		if(EmailUtils.isEmailValid(cpfOrEmailOrUsername)) {
			return appUserRepository.findByEmail(cpfOrEmailOrUsername);
		}
		if(CPFUtil.isCPF(cpfOrEmailOrUsername)) {
			return appUserRepository.findByCpf(cpfOrEmailOrUsername.replaceAll("\\D", ""));
		}
		
		return Optional.empty();
	}
	
	
	

}
