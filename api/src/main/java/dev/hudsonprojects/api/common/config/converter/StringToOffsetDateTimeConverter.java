package dev.hudsonprojects.api.common.config.converter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StringToOffsetDateTimeConverter
		implements Converter<String, OffsetDateTime> {

	@Override
	public OffsetDateTime convert(@NonNull String source) {
		return OffsetDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

}
