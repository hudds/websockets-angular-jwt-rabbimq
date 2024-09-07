package dev.hudsonprojects.backend.common.requestdata;

import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.common.exception.UnauthorizedException;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;


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
