package dev.hudsonprojects.api.appuser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AppUserTest {

	@Test
	void shouldRemoveNonNumericCharacters() {
		AppUser appUser = new AppUser();
		appUser.setCpf("  111.111.111-11 abcde");
		assertThat(appUser.getCpf()).isEqualTo("11111111111");
	}
	
	
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
		appUser.setCpf("111111111111");
		AppUser anotherAppUser = new AppUser();
		anotherAppUser.setUserId(1607L);
		anotherAppUser.setCpf("22222222222");
		assertThat(appUser).isEqualTo(anotherAppUser);
	}

}
