package dev.hudsonprojects.api.common.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class StringToInstantConverter
		implements Converter<String, Instant> {

	@Override
	public Instant convert(@NonNull String source) {
		return OffsetDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant();
	}

}
