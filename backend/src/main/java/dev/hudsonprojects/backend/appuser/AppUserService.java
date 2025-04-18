package dev.hudsonprojects.backend.appuser;

import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.hudsonprojects.backend.common.exception.NotFoundException;
import dev.hudsonprojects.backend.common.requestdata.RequestData;

@Service
public class AppUserService {
	
	private final AppUserRepository appUserRepository;
	private final RequestData requestData;

	@Autowired
	public AppUserService(AppUserRepository appUserRepository, RequestData requestData) {
		this.appUserRepository = appUserRepository;
		this.requestData = requestData;
	}
	
	
	public AppUserDTO getLoggedUser() {
		return appUserRepository.findDTOById(requestData.getUserOrUnauthorized().getUserId())
				.orElseThrow(() -> new NotFoundException(ErrorDetailsBuilder.buildWithMessage("error.user.notFound")));
	}
	
	
	

}
