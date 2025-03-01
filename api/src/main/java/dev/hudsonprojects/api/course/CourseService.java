package dev.hudsonprojects.api.course;

import dev.hudsonprojects.api.common.exception.NotFoundException;
import dev.hudsonprojects.api.common.lib.util.StringUtils;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.lang.Math.max;
import static java.util.Objects.requireNonNullElse;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseValidationService courseValidationService;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseValidationService courseValidationService) {
        this.courseRepository = courseRepository;
        this.courseValidationService = courseValidationService;
    }

    public CourseInfoDTO getCourseInfoById(Long courseId){
        courseValidationService.validateCourseIdAndThrowException(courseId);
        return courseRepository.getCourseInfoById(courseId)
                .orElseThrow(() -> new NotFoundException(ErrorDetailsBuilder.withMessage("validation.course.notFound").build()));
    }

    public List<CourseInfoDTO> getAllCourseInfo(Integer pageNumber, Integer pageSize, String inRelationToCpf) {
        Pageable pageable = Pageable.unpaged();
        if(pageSize != null && pageSize > 0) {
            pageable = PageRequest.of(max(requireNonNullElse(pageNumber, 0), 0), pageSize);
        }
        inRelationToCpf = StringUtils.removeNonDigits(inRelationToCpf);
        if(StringUtils.isNotBlank(inRelationToCpf)){
            return courseRepository.getAllCourseInfoInRelationToCpf(inRelationToCpf, pageable);
        }
        return courseRepository.getAllCourseInfo(pageable);
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(ErrorDetailsBuilder.withMessage("validation.course.notFound").build()));
    }
}
