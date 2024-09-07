package dev.hudsonprojects.backend.appuser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class AppUserDTOTest {

	@Test
	void shouldCopyUserData() {
		AppUser appUser = new AppUser();
		LocalDate birthDate = LocalDate.of(2024, 9, 7);
		String cpf = "11111111111";
		String email = "test@test";
		String name = "Full Name";
		String username = "full.name";
		
		appUser.setBirthDate(birthDate);
		appUser.setCpf(cpf);
		appUser.setEmail(email);
		appUser.setName(name);
		appUser.setUsername(username);
		
		AppUserDTO appUserDTO = new AppUserDTO(appUser);
		assertThat(appUserDTO.getBirthDate()).isEqualTo(birthDate);
		assertThat(appUserDTO.getCpf()).isEqualTo(cpf);
		assertThat(appUserDTO.getEmail()).isEqualTo(email);
		assertThat(appUserDTO.getName()).isEqualTo(name);
		assertThat(appUserDTO.getUsername()).isEqualTo(username);
		
	}

}
