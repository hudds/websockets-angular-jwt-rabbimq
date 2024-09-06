package dev.hudsonprojects.backend.lib;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonUsernameGeneratorTest {

	@Test
	void test() {
		System.out.println(new PersonUsernameGenerator("Hudson Rodrigues da Silva").getUsernames());
		System.out.println(new PersonUsernameGenerator("João").getUsernames());
		System.out.println(new PersonUsernameGenerator("João da Silva").getUsernames());
		System.out.println(new PersonUsernameGenerator("Ana Carolina Borba Gato").getUsernames());
		
		
		System.out.println(new PersonUsernameGenerator("Hudson Rodrigues da Silva").generatingWithTimestamps().getUsernames());
		
		System.out.println(new PersonUsernameGenerator("Hudson Rodrigues da Silva Pereira da Costa Conceição Tavares Menezes Costa").generatingWithTimestamps().getUsernames());
		System.out.println(new PersonUsernameGenerator("João").generatingWithTimestamps().getUsernames());
		System.out.println(new PersonUsernameGenerator("João da Silva").generatingWithTimestamps().getUsernames());
		System.out.println(new PersonUsernameGenerator("Ana Carolina Borba Gato").generatingWithTimestamps().getUsernames());
	}

}
