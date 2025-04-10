package dev.hudsonprojects.backend.common.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.hudsonprojects.backend.common.lib.instance.InstanceId;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import dev.hudsonprojects.backend.common.config.converter.OffsetDateTimeToStringConverter;
import dev.hudsonprojects.backend.common.config.converter.StringToOffsetDateTimeConverter;
import dev.hudsonprojects.backend.common.lib.Locales;
import dev.hudsonprojects.backend.security.login.AppUserDetailsService;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig {

	@Bean
	public InstanceId instanceId(){
		return InstanceId.get();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(AppUserDetailsService userDetailsService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
	
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "DELETE", "OPTIONS");
//			}
			@Override
			public void addInterceptors(@NonNull InterceptorRegistry registry) {
				registry.addInterceptor(localeChangeInterceptor());
			}
			@Override
			public void addFormatters(@NonNull FormatterRegistry registry) {
				registry.addConverter(new StringToOffsetDateTimeConverter());
				registry.addConverter(new OffsetDateTimeToStringConverter());
			}
		};
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}


	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locales.PT_BR.getLocale());
	    return slr;
	}
	
	@Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

	@Bean
	@Scope("prototype")
	public ObjectMapper objectMapper(){
		return JsonMapper.builder()
				.findAndAddModules()
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.build();
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(10000);
		executor.setThreadNamePrefix("wajr-backend-");
		executor.initialize();
		return executor;
	}


}
