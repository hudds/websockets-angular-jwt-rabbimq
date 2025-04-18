package dev.hudsonprojects.api.common.requestdata;

import java.util.*;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import dev.hudsonprojects.api.common.exception.UnauthorizedException;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.security.login.AppUserDetails;


@Component
@RequestScope
public class RequestData {

	private Locale locale;
	private AppUserDetails user;
	private Map<String, Object> properties = new HashMap<>();

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setUser(AppUserDetails user) {
		this.user = user;
	}

	public Optional<AppUserDetails> getUser() {
		return Optional.ofNullable(user);
	}

	public AppUserDetails getUserOrUnauthorized() {
		return this.getUser().orElseThrow(() -> new UnauthorizedException(
				ErrorDetailsBuilder.unauthorized().setMessage("user.notAuthenticated").build()));
	}

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
