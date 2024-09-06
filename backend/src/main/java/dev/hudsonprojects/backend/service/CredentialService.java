package dev.hudsonprojects.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hudsonprojects.backend.model.entity.Credentials;


@Service
@Transactional
public class CredentialService {

    private final PasswordEncoder passwordEncoder;

    public CredentialService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void encodeCredentials(Credentials credentials){
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
    }  
     
}
