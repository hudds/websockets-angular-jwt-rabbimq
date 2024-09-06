package dev.hudsonprojects.backend.lib;

import java.util.Locale;

public enum Locales {
	
	PT_BR(Locale.of("pt", "BR"));
	
	private final Locale locale;
	
	Locales(Locale locale){
		this.locale = locale;
	}
	
	public Locale getLocale() {
		return locale;
	}

}
