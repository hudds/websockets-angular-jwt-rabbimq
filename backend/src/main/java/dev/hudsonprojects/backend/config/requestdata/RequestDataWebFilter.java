package dev.hudsonprojects.backend.config.requestdata;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import dev.hudsonprojects.backend.lib.Locales;
import dev.hudsonprojects.backend.model.entity.AppUser;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(1)
public class RequestDataWebFilter implements Filter {

	private static final Locales DEFAULT_LOCALE = Locales.PT_BR;

	private final RequestData requestData;

	public RequestDataWebFilter(RequestData requestData) {
		this.requestData = requestData;
	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.map(Authentication::getPrincipal)
				.filter(AppUser.class::isInstance)
				.map(AppUser.class::cast)
				.ifPresent(requestData::setUser);
		
		Locale locale = req.getLocale();
		locale = locale == null ? DEFAULT_LOCALE.getLocale() : locale;
		requestData.setLocale(locale);
		chain.doFilter(request, response);
	}

}
