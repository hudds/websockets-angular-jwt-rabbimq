package dev.hudsonprojects.api.security.credentials;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CredentialService {

    private final PasswordEncoder passwordEncoder;
    private final CredentialsRepository credentialsRepository;

    public CredentialService(PasswordEncoder passwordEncoder, CredentialsRepository credentialsRepository) {
        this.passwordEncoder = passwordEncoder;
		this.credentialsRepository = credentialsRepository;
    }

    public void save(Credentials credentials){
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        credentialsRepository.save(credentials);
    }  
     
}
