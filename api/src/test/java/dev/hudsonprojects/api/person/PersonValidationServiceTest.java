package dev.hudsonprojects.api.person;


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

import dev.hudsonprojects.api.common.messages.APIMessage;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;

@ExtendWith(MockitoExtension.class) 
class PersonValidationServiceTest {
	

    @Mock
	private PersonRepository personRepository;
	
	@InjectMocks
	private PersonValidationService PersonValidationService;

	@Test
	void shouldReturnErrorsForRequiredFields() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("name", Collections.singletonList(new APIMessage("validation.person.name.required"))),
				new APIFieldError("cpf", Collections.singletonList(new APIMessage("validation.person.cpf.required")))
		);
		List<APIFieldError> errors = PersonValidationService.validate(new Person());
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForEmailInvalid() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("email", Collections.singletonList(new APIMessage("validation.person.email.invalid")))
		);
		Person Person = new Person();
		Person.setEmail("");
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForInvalidCpf() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("cpf", Collections.singletonList(new APIMessage("validation.CPF.invalid")))
		);
		Person Person = new Person();
		Person.setCpf("123");
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForFutureBirthDate() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("birthDate", Collections.singletonList(new APIMessage("validation.NotFuture")))
		);
		Person Person = new Person();
		Person.setBirthDate(LocalDate.now().plusDays(1));
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	

	
	@Test
	void shouldReturnErrorForOnlyFirstName() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("name", Collections.singletonList(new APIMessage("validation.person.name.fullName")))
		);
		Person Person = new Person();
		Person.setName("name");
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorForNameContainingNonAlphabeticCharacters() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("name", Collections.singletonList(new APIMessage("validation.person.name.invalid")))
		);
		Person Person = new Person();
		Person.setName("name12 %!");
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	
	@Test
	void shouldReturnErrorIfCpfExists() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("cpf", Collections.singletonList(new APIMessage("validation.person.cpf.exists")))
		);
		Person Person = getValidPerson();
		when(personRepository.existsByCpf(Person.getCpf())).thenReturn(true);
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	
	@Test
	void shouldReturnErrorIfCpfWithAnotherIdExists() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("cpf", Collections.singletonList(new APIMessage("validation.person.cpf.exists")))
		);
		Person Person = getValidPerson();
		long personId = 1601L;
		Person.setPersonId(personId);
		when(personRepository.existsByCpfAndPersonIdNot(Person.getCpf(), personId)).thenReturn(true);
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}

	
	@Test
	void shouldReturnErrorIfEmailExists() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("email", Collections.singletonList(new APIMessage("validation.person.email.exists")))
		);
		Person Person = getValidPerson();
		String email = "teste@teste";
		Person.setEmail(email);
		when(personRepository.existsByEmail(email)).thenReturn(true);
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}
	
	@Test
	void shouldReturnErrorIfEmailWithAnotherIdExists() {
		List<APIFieldError> expected = List.of(
				new APIFieldError("email", Collections.singletonList(new APIMessage("validation.person.email.exists")))
		);
		Person Person = getValidPerson();
		String email = "teste@teste";
		long personId = 1602L;
		Person.setPersonId(personId);
		Person.setEmail(email);
		when(personRepository.existsByEmailAndPersonIdNot(email, personId)).thenReturn(true);
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isNotEmpty().containsAll(expected);
	}


	@Test
	void shouldNotReturnError() {
		Person Person = getValidPerson();
		List<APIFieldError> errors = PersonValidationService.validate(Person);
		assertThat(errors).isEmpty();
	}
	
	private Person getValidPerson() {
		Person Person = new Person();
		Person.setCpf("90139287060");
		Person.setName("Full Name");
		return Person;
	}
}
