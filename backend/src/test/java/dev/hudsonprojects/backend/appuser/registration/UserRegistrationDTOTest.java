package dev.hudsonprojects.backend.appuser.registration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import dev.hudsonprojects.backend.appuser.AppUser;

class UserRegistrationDTOTest {

	@Test
	void shouldCreateAppUserWithAllTheData() {
		LocalDate birthDate = LocalDate.of(2024, 9, 7);
		String cpf = "11111111111";
		String email = "test@test";
		String name = "Full Name";
		String password = "Password123!";
		
		UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
		userRegistrationDTO.setBirthDate(birthDate);
		userRegistrationDTO.setCpf(cpf);
		userRegistrationDTO.setEmail(email);
		userRegistrationDTO.setName(name);
		userRegistrationDTO.setPassword(password);
		
		AppUser appUser = userRegistrationDTO.toAppUser();
		
		assertThat(appUser.getBirthDate()).isEqualTo(birthDate);
		assertThat(appUser.getCpf()).isEqualTo(cpf);
		assertThat(appUser.getEmail()).isEqualTo(email);
		assertThat(appUser.getName()).isEqualTo(name);
		assertThat(appUser.getCredentials().getPassword()).isEqualTo(password);
	}

}
