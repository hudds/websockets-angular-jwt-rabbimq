package dev.hudsonprojects.backend.common.lib;

import java.security.SecureRandom;
import java.util.List;

public final class RandomAlphaNumericalStringGenerator {

	private static final SecureRandom RANDOM = new SecureRandom();

	private static final List<Character> CHARACTERS = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '0');

	private RandomAlphaNumericalStringGenerator() {
	}

	public static String generate(int length) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i< length; i++) {
			stringBuilder.append(CHARACTERS.get(RANDOM.nextInt(0, CHARACTERS.size())));
		}
		return stringBuilder.toString();
	}
}
