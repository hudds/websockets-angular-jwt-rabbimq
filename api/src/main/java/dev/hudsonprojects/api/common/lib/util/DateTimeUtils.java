package dev.hudsonprojects.api.common.lib.util;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

public final class DateTimeUtils {

    private DateTimeUtils(){}


    public static LocalDateTime max(LocalDateTime ... localDateTime){
        return Stream.of(localDateTime).filter(Objects::nonNull).max(LocalDateTime::compareTo).orElse(null);
    }




}
