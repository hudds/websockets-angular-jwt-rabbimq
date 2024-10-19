package dev.hudsonprojects.api.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import dev.hudsonprojects.api.common.requestdata.RequestData;

@Service
public class AppUserService {
	
	private final AppUserRepository appUserRepository;
	private final RequestData requestData;

	@Autowired
	public AppUserService(AppUserRepository appUserRepository, RequestData requestData) {
		this.appUserRepository = appUserRepository;
		this.requestData = requestData;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {

	}



}
