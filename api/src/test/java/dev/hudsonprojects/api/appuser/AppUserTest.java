package dev.hudsonprojects.api.appuser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AppUserTest {

	
	@Test
	void shouldNotBeEqualIfIdsAreDifferent() {
		AppUser appUser = new AppUser();
		appUser.setUserId(1607L);
		AppUser anotherAppUser = new AppUser();
		anotherAppUser.setUserId(1608L);
		assertThat(appUser).isNotEqualTo(anotherAppUser);
	}
	
	
	@Test
	void shouldBeEqualIfIdsAreTheSame() {
		AppUser appUser = new AppUser();
		appUser.setUserId(1607L);
		AppUser anotherAppUser = new AppUser();
		anotherAppUser.setUserId(1607L);
		assertThat(appUser).isEqualTo(anotherAppUser);
	}

}
