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


	public Optional<AppUser> findAppUserByEmailOrCpf(String cpfOrEmail) {
		if(EmailUtils.isEmailValid(cpfOrEmail)) {
			return appUserRepository.findByEmail(cpfOrEmail);
		}
		if(CPFUtil.isCPF(cpfOrEmail)) {
			return appUserRepository.findByCpf(cpfOrEmail.replaceAll("\\D", ""));
		}
		
		throw new UsernameNotFoundException(cpfOrEmail);
	}
	
	
	

}
