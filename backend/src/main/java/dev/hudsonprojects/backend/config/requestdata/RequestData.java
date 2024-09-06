package dev.hudsonprojects.backend.config.requestdata;

import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import dev.hudsonprojects.backend.exception.UnauthorizedException;
import dev.hudsonprojects.backend.lib.messages.builder.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.model.entity.AppUser;


@Component
@RequestScope
public class RequestData {

	private Locale locale;
	private AppUser user;

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Optional<AppUser> getUser() {
		return Optional.ofNullable(user);
	}

	public AppUser getUserOrUnauthorized() {
		return this.getUser().orElseThrow(() -> new UnauthorizedException(
				ErrorDetailsBuilder.unauthorized().setMessage("user.notAuthenticated").build()));
	}
}
