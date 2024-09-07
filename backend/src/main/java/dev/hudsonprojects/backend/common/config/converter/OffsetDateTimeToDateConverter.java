package dev.hudsonprojects.backend.common.config.converter;

import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
@WritingConverter
public class OffsetDateTimeToDateConverter implements Converter<OffsetDateTime, Date> {

	@Override
	public Date convert(@NonNull OffsetDateTime source) {
		return Date.from(source.toInstant());
	}

}
