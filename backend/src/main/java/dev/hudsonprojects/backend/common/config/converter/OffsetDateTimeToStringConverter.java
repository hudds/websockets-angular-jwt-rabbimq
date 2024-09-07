package dev.hudsonprojects.backend.common.config.converter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class OffsetDateTimeToStringConverter implements Converter<OffsetDateTime, String> {

	@Override
	public String convert(@NonNull OffsetDateTime source) {
		return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(source);
	}

}
