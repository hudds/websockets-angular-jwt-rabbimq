package dev.hudsonprojects.api.common.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dev.hudsonprojects.api.common.messages.error.APIMessageResolved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolvedBuilder;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldErrorResolved;
import dev.hudsonprojects.api.common.requestdata.RequestData;



@Component
public class MessageResolver {

	private final MessageSource messageSource;
	private final RequestData requestData;

	@Autowired
	public MessageResolver(MessageSource messageSource, RequestData requestData) {
		this.messageSource = messageSource;
		this.requestData = requestData;
	}

	public APIMessageResolved getResolvedMessage(APIMessage message) {
		if(message == null) {
			return null;
		}
		String code = message.getCode();
		try {
			String messageResolved = messageSource.getMessage(code, message.getArgs(), requestData.getLocale());
			return new APIMessageResolved(messageResolved, code);
		} catch (NoSuchMessageException e) {
			return new APIMessageResolved(null, code);
		}
	}
	
	
	public List<APIMessageResolved> getResolvedAPIMessages(Collection<APIMessage> messages) {
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
