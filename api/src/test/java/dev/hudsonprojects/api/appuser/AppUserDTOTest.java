package dev.hudsonprojects.api.appuser;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class AppUserDTOTest {

	@Test
	void shouldCopyUserData() {
		AppUser appUser = new AppUser();
		LocalDate birthDate = LocalDate.of(2024, 9, 7);
		String username = "full.name";
		
		appUser.setUsername(username);
		
		AppUserDTO appUserDTO = new AppUserDTO(appUser);
		assertThat(appUserDTO.getUsername()).isEqualTo(username);
		
	}

}
