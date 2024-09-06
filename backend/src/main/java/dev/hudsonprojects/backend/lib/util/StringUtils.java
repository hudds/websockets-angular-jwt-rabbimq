package dev.hudsonprojects.backend.lib.util;

import java.text.Normalizer;

public class StringUtils {

	public static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}


	public static boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}


	public static String removeNonDigits(String string) {
		return string == null ? null : string.replaceAll("\\D", "");
	}


	public static boolean isNotBlank(String string) {
		return !isBlank(string);
	}

	public static boolean isLettersOnly(String string) {
		return !isBlank(string) && stripAccents(string).matches("[A-Za-z\\s]");
	}

	public static int wordCount(String string) {
		if(isBlank(string)){
			return 0;
		}
		return string.split("\\s+").length;
	}
}
