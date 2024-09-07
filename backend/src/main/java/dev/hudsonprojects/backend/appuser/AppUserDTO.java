package dev.hudsonprojects.backend.appuser;

import java.time.LocalDate;

public class AppUserDTO {

	private String username;
	private String email;
	private String name;
	private LocalDate birthDate;
	private String cpf;
	
	public AppUserDTO(AppUser appUser) {
		this.username = appUser.getUsername();
		this.email = appUser.getEmail();
		this.name = appUser.getName();
		this.birthDate = appUser.getBirthDate();
		this.cpf = appUser.getCpf();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	

}
