package dev.hudsonprojects.backend.appuser.registration;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.appuser.AppUserDTO;
import dev.hudsonprojects.backend.appuser.AppUserRepository;
import dev.hudsonprojects.backend.appuser.AppUserValidationService;
import dev.hudsonprojects.backend.common.exception.ValidationException;
import dev.hudsonprojects.backend.common.messages.APIMessage;
import dev.hudsonprojects.backend.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.backend.security.credentials.CredentialService;
import dev.hudsonprojects.backend.security.credentials.Credentials;
import dev.hudsonprojects.backend.security.credentials.CredentialsType;

@ExtendWith(MockitoExtension.class)
class UserRegistrationServiceTest {
	
	@Mock
    private CredentialService credentialsService;
	@Mock
    private AppUserRepository appUserRepository;
	@Mock
    private AppUserValidationService userValidationService;
	
	@InjectMocks
	private UserRegistrationService userRegistrationService;

	@Test
	void shouldThrowExceptionIfNotValid() {
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
		
		
		APIFieldError error = new APIFieldError("fieldName", new APIMessage("errorCode"));
		when(userValidationService.validate(any(AppUser.class))).thenReturn(List.of(error));
		
		assertThrows(ValidationException.class, () -> userRegistrationService.registerUser(userRegistrationDTO));
		
	}
	
	@Test
	void shouldRegisterUser() {
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
		
		AppUserDTO registeredUser = userRegistrationService.registerUser(userRegistrationDTO);
		
		verify(credentialsService, times(1)).save(any(Credentials.class));
		verify(appUserRepository, times(1)).save(any(AppUser.class));
		
		assertThat(registeredUser).isNotNull();
		assertThat(registeredUser.getUsername()).isNotBlank();
		
	}

}
