package dev.hudsonprojects.backend.integration.coursesapi.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.common.exception.APIErrorType;
import dev.hudsonprojects.backend.common.lib.util.StringUtils;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ErrorDetailsAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorDetailsAdapter.class);
    private final ObjectMapper objectMapper;
    private final Set<String> supportedMessageCodes;
    private final Map<String, String> messageMap;

    public ErrorDetailsAdapter(ObjectMapper objectMapper, Set<String> supportedMessageCodes) {
        this(objectMapper, supportedMessageCodes, new HashMap<>());
    }

    public ErrorDetailsAdapter(ObjectMapper objectMapper, Map<String, String> messageMap) {
        this(objectMapper, new HashSet<>(), messageMap);
    }

    public ErrorDetailsAdapter(ObjectMapper objectMapper, Set<String> supportedMessageCodes, Map<String, String> messageMap) {
        this.objectMapper = objectMapper;
        this.supportedMessageCodes = new HashSet<>(supportedMessageCodes);
        this.messageMap = new HashMap<>(messageMap);
    }

    public Optional<ErrorDetails> adapt(APIErrorType apiErrorType, String responseBody) {
        if (StringUtils.isBlank(responseBody)) {
            return Optional.empty();
        }
        try {
            CourseAPIError courseAPIError = objectMapper.readValue(responseBody, CourseAPIError.class);
            ErrorDetailsBuilder builder = ErrorDetailsBuilder.builder()
                    .setType(apiErrorType);
            Optional<String> messageCode = Optional.ofNullable(courseAPIError.getMessage())
                    .map(CourseAPIMessage::getCode)
                    .flatMap(this::translateMessageCode);
            if(messageCode.isPresent()){
                return Optional.of(builder.setMessage(messageCode.get()).build());
            }
            if(courseAPIError.getFieldErrors() != null){
                return courseAPIError.getFieldErrors().stream()
                        .filter(Objects::nonNull)
                        .map(CourseAPIFieldError::getMessages)
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .map(CourseAPIMessage::getCode)
                        .findFirst()
                        .flatMap(this::translateMessageCode)
                        .map(fieldMessageCode -> builder.setMessage(fieldMessageCode).build());
            }
        } catch (JsonProcessingException e) {
            LOGGER.warn("Failed to translate API Error", e);
            return Optional.empty();
        }
        return Optional.empty();
    }

    private Optional<String> translateMessageCode(String code) {
        if (this.supportedMessageCodes.contains(code)) {
            return Optional.ofNullable(code);
        }
        return Optional.ofNullable(this.messageMap.get(code));
    }
}
