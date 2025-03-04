package dev.hudsonprojects.api.common.config.converter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class DateToOffsetDateTimeConverter implements Converter<Date, OffsetDateTime> {

	@Override
	public OffsetDateTime convert(@NonNull Date source) {
		return source.toInstant().atOffset(ZoneOffset.UTC);
	}

}
