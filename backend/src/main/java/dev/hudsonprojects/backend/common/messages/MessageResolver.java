package dev.hudsonprojects.backend.common.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsResolvedBuilder;
import dev.hudsonprojects.backend.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.backend.common.messages.error.fielderror.APIFieldErrorResolved;
import dev.hudsonprojects.backend.common.requestdata.RequestData;



@Component
public class MessageResolver {

	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private RequestData requestData;
	
	public String getResolvedMessage(APIMessage message) {
		if(message == null) {
			return null;
		}
		
		try {
			return messageSource.getMessage(message.getCode(), message.getArgs(), requestData.getLocale());
		} catch (NoSuchMessageException e) {
			return message.getCode();
		}
	}
	
	
	public List<String> getResolvedAPIMessages(Collection<APIMessage> messages) {
		if(messages == null) {
			return Collections.emptyList();
		}
		return messages.stream().map(this::getResolvedMessage).toList();
	}
	

	public List<APIFieldErrorResolved> getResolvedMessages(Collection<APIFieldError> messages) {
		if(messages == null) {
			return new ArrayList<>();
		}
		List<APIFieldErrorResolved> fieldErrors = new ArrayList<>();
		messages.forEach(v -> fieldErrors.add(new APIFieldErrorResolved(v.getFieldName(), this.getResolvedAPIMessages(v.getMessages()))));
		return fieldErrors;
	}
	
	
	public ErrorDetailsResolved getResolved(ErrorDetails errorDetails) {
		return ErrorDetailsResolvedBuilder.builder()
			.setMessage(this.getResolvedMessage(errorDetails.getMessage()))
			.setStatus(errorDetails.getStatus())
			.setType(errorDetails.getType())
			.setFieldErrors(this.getResolvedMessages(errorDetails.getFieldErrors()))
			.build();
	}

}
