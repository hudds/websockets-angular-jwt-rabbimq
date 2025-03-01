package dev.hudsonprojects.api.course.registration;

import dev.hudsonprojects.api.common.exception.NotFoundException;
import dev.hudsonprojects.api.common.exception.ValidationException;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.course.Course;
import dev.hudsonprojects.api.course.CourseDTO;
import dev.hudsonprojects.api.course.CourseValidationService;
import dev.hudsonprojects.api.course.event.CourseEvent;
import dev.hudsonprojects.api.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseRegistrationService {

    private final CourseRepository courseRepository;
    private final CourseValidationService courseValidationService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CourseRegistrationService(CourseRepository courseRepository, CourseValidationService courseValidationService, ApplicationEventPublisher applicationEventPublisher) {
        this.courseRepository = courseRepository;
        this.courseValidationService = courseValidationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public static NotFoundException getCourseNotFoundException() {
        return new NotFoundException(ErrorDetailsBuilder.withMessage("validation.course.notFound").build());
    }

    @Transactional
    public CourseDTO createCourse(CourseRegistrationDTO courseRegistration){
        Course course = courseRegistration.toCourse();
        List<APIFieldError> errors = courseValidationService.validate(course);
        if(!errors.isEmpty()) {
            throw new ValidationException(ErrorDetailsBuilder.withAPIFieldErrors(errors).build());
        }
        courseRepository.save(course);
        applicationEventPublisher.publishEvent(new CourseEvent(course.getCourseId()));
        return new CourseDTO(course);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CourseDTO updateCourse(CourseUpdateDTO courseRegistration) {
        courseValidationService.validateCourseIdAndThrowException(courseRegistration.getCourseId());
        Course course = courseRepository.findById(courseRegistration.getCourseId())
                .orElseThrow(CourseRegistrationService::getCourseNotFoundException);
        courseRegistration.copyValuesTo(course);
        List<APIFieldError> errors = courseValidationService.validateUpdate(course);
        if(!errors.isEmpty()) {
            throw new ValidationException(ErrorDetailsBuilder.withAPIFieldErrors(errors).build());
        }
        courseRepository.save(course);
        applicationEventPublisher.publishEvent(new CourseEvent(course.getCourseId()));
        return new CourseDTO(course);
    }
}
