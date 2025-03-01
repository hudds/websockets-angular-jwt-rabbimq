package dev.hudsonprojects.api.common.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class InstantToStringConverter implements Converter<Instant, String> {

	@Override
	public String convert(@NonNull Instant source) {
		return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(OffsetDateTime.from(source));
	}

}
