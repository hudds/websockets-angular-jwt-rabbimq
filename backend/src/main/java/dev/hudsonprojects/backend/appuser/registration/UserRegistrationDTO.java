package dev.hudsonprojects.backend.appuser.registration;

import java.time.LocalDate;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.common.lib.util.StringUtils;
import dev.hudsonprojects.backend.common.validation.constraint.cpf.CPF;
import dev.hudsonprojects.backend.common.validation.constraint.notfuture.NotFuture;
import dev.hudsonprojects.backend.common.validation.constraint.strongpassword.StrongPassword;
import dev.hudsonprojects.backend.security.credentials.Credentials;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotBlank
    @Size(max = 255)
    private String name;
    @NotBlank
    @CPF
    @Size(max = 11)
    private String cpf;
    @NotFuture
    private LocalDate birthDate;
    @Email
    private String email;
    @NotBlank
    @StrongPassword
    @Size(max = 255, min = 8)
    private String password;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = StringUtils.removeNonDigits(cpf);
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRegistrationDTO other = (UserRegistrationDTO) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (birthDate == null) {
            if (other.birthDate != null)
                return false;
        } else if (!birthDate.equals(other.birthDate))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        return true;
    }

    public AppUser toAppUser(){
        AppUser appUser = new AppUser();
        appUser.setBirthDate(birthDate);
        appUser.setCpf(cpf);
        appUser.setEmail(email);
        appUser.setName(name);
        Credentials credentials = new Credentials();
        credentials.setPassword(password);
        appUser.setCredentials(credentials);
        return appUser;
    }

    
}
