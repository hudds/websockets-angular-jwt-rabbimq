package dev.hudsonprojects.backend.common.validation.constraint.notfuture;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotFutureValidator implements ConstraintValidator<NotFuture, Object>{

    private final Instant currentInstant;

    public NotFutureValidator(){
        this(Instant.now());
    }

    public NotFutureValidator(Instant currentDate) {
        this.currentInstant = currentDate;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if(value == null){
            return true;
        }

        if (value instanceof Timestamp timestamp){
            return !timestamp.after(Date.from(this.currentInstant));
        }

        if (value instanceof Date date){
            return !date.after(Date.from(this.currentInstant));
        }

        if (value instanceof Instant instant){
            return !instant.isAfter(this.currentInstant);
        }

        if (value instanceof LocalDate date){
            return !date.isAfter(LocalDate.ofInstant(this.currentInstant, ZoneId.systemDefault()));
        }

        if (value instanceof LocalDateTime date){
            return !date.isAfter(LocalDateTime.ofInstant(currentInstant, ZoneId.systemDefault()));
        }

        if (value instanceof ZonedDateTime date){
            return !date.isAfter(ZonedDateTime.from(this.currentInstant));
        }

        if (value instanceof OffsetDateTime date){
            return !date.isAfter(OffsetDateTime.from(this.currentInstant));
        }

        if (value instanceof Year year){
            return !year.isAfter(Year.from(this.currentInstant));
        }

        if (value instanceof YearMonth yearMonth){
            return !yearMonth.isAfter(YearMonth.from(this.currentInstant));
        }

        if (value instanceof LocalTime time){
            return !time.isAfter(LocalTime.from(this.currentInstant));
        }
    
        return true;
    }

}
