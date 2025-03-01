package dev.hudsonprojects.backend.integration.error;

import java.util.*;

public class CourseAPIFieldError {

	private String field;
	private Set<CourseAPIMessage> messages;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Set<CourseAPIMessage> getMessages() {
		return messages;
	}

	public void setMessages(Set<CourseAPIMessage> messages) {
		this.messages = messages;
	}
}
