package dev.hudsonprojects.backend.appuser;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.hudsonprojects.backend.common.messages.APIMessage;
import dev.hudsonprojects.backend.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.backend.security.credentials.Credentials;

@ExtendWith(MockitoExtension.class) 
class AppUserValidationServiceTest {
	

    @Mock
	private AppUserRepository appUserRepository;
	
	@InjectMocks
	private AppUserValidationService appUserValidationService;

	@Test
	void shouldReturnErrorsForRequiredFields() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("name", Collections.singletonList(new APIMessage("validation.user.name.required"))),
				new APIFieldError("cpf", Collections.singletonList(new APIMessage("validation.user.cpf.required"))),
				new APIFieldError("credentials.password", Collections.singletonList(new APIMessage("validations.user.credentials.password.required")))
		);
		List<APIFieldError> errors = appUserValidationService.validate(new AppUser());
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForEmailInvalid() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("email", Collections.singletonList(new APIMessage("validation.user.email.invalid")))
		);
		AppUser appUser = new AppUser();
		appUser.setEmail("");
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForInvalidCpf() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("cpf", Collections.singletonList(new APIMessage("validation.CPF.invalid")))
		);
		AppUser appUser = new AppUser();
		appUser.setCpf("123");
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForFutureBirthDate() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("birthDate", Collections.singletonList(new APIMessage("validation.NotFuture")))
		);
		AppUser appUser = new AppUser();
		appUser.setBirthDate(LocalDate.now().plusDays(1));
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForWeakPassword() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("credentials.password", Collections.singletonList(new APIMessage("validation.StrongPassword")))
		);
		AppUser appUser = new AppUser();
		appUser.setCredentials(new Credentials());
		appUser.getCredentials().setPassword("a");
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForOnlyFirstName() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("name", Collections.singletonList(new APIMessage("validation.user.name.fullName")))
		);
		AppUser appUser = new AppUser();
		appUser.setName("name");
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForNameContainingNonAlphabeticCharacters() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("name", Collections.singletonList(new APIMessage("validation.user.name.invalid")))
		);
		AppUser appUser = new AppUser();
		appUser.setName("name12 %!");
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	
	@Test
	void shouldReturnErrorIfCpfExists() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("cpf", Collections.singletonList(new APIMessage("validation.user.cpf.exists")))
		);
		AppUser appUser = getValidUser();
		when(appUserRepository.existsByCpf(appUser.getCpf())).thenReturn(true);
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	
	@Test
	void shouldReturnErrorIfCpfWithAnotherIdExists() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("cpf", Collections.singletonList(new APIMessage("validation.user.cpf.exists")))
		);
		AppUser appUser = getValidUser();
		long userId = 1601L;
		appUser.setUserId(userId);
		when(appUserRepository.existsByCpfAndUserIdNot(appUser.getCpf(), userId)).thenReturn(true);
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}

	
	@Test
	void shouldReturnErrorIfEmailExists() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("email", Collections.singletonList(new APIMessage("validation.user.email.exists")))
		);
		AppUser appUser = getValidUser();
		String email = "teste@teste";
		appUser.setEmail(email);
		when(appUserRepository.existsByEmail(email)).thenReturn(true);
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorIfEmailWithAnotherIdExists() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("email", Collections.singletonList(new APIMessage("validation.user.email.exists")))
		);
		AppUser appUser = getValidUser();
		String email = "teste@teste";
		long userId = 1602L;
		appUser.setUserId(userId);
		appUser.setEmail(email);
		when(appUserRepository.existsByEmailAndUserIdNot(email, userId)).thenReturn(true);
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}


	@Test
	void shouldNotReturnError() {
		AppUser appUser = getValidUser();
		List<APIFieldError> errors = appUserValidationService.validate(appUser);
		assertThat(errors).isEmpty();
	}
	
	private AppUser getValidUser() {
		AppUser appUser = new AppUser();
		appUser.setCpf("90139287060");
		appUser.setName("Full Name");
		appUser.setCredentials(new Credentials());
		appUser.getCredentials().setPassword("Abcdef123!");
		return appUser;
	}
}
