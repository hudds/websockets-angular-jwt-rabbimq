package dev.hudsonprojects.api.course;

import dev.hudsonprojects.api.common.exception.ValidationException;
import dev.hudsonprojects.api.common.lib.util.StringUtils;
import dev.hudsonprojects.api.common.messages.APIMessage;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.common.validation.GenericValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseValidationService {

    public static final int MAX_LENGTH_COURSE_NAME = 200;


    public List<APIFieldError> validateUpdate(Course course) {
        List<APIFieldError> errors = new ArrayList<>();
        validateCourseId(course).ifPresent(errors::add);
        errors.addAll(validate(course));
        return errors;
    }

    public Optional<APIFieldError> validateCourseId(Course course) {
        if(course == null){
            return Optional.empty();
        }
        return validateCourseId(course.getCourseId());
    }


    public void validateCourseIdAndThrowException(Long courseId) {
        Optional<APIFieldError> errorCourseId = validateCourseId(courseId);
        if(errorCourseId.isPresent()){
            throw new ValidationException(ErrorDetailsBuilder.withAPIFieldErrors(errorCourseId.get()).build());
        }
    }

    public Optional<APIFieldError> validateCourseId(Long courseId) {
        if(courseId == null){
            return Optional.of(new APIFieldError("courseId", new APIMessage("validation.notNull")));
        }
        return Optional.empty();
    }

    public List<APIFieldError> validate(Course course) {
        return getCourseValidator().applyValidations(course);
    }

    private GenericValidator<Course> getCourseValidator(){
        return GenericValidator.builder(Course.class)
                .addValidation(
                        "name",
                        new APIMessage("validation.course.name.invalid", MAX_LENGTH_COURSE_NAME),
                        this::nameValid
                ).addValidation(
                        "slots",
                        "validation.course.slots.invalid",
                        this::slotsValid
                ).build();
    }

    private boolean slotsValid(Course course){
        return course.getSlots() != null && course.getSlots() >= 0;
    }

    private boolean nameValid(Course course){
        return StringUtils.isNotBlank(course.getName()) && course.getName().length() <= MAX_LENGTH_COURSE_NAME;
    }


}
