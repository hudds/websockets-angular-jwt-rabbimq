package dev.hudsonprojects.backend.integration.coursesapi.course;

import dev.hudsonprojects.backend.appuser.AppUserService;
import dev.hudsonprojects.backend.common.exception.InternalErrorException;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.common.requestdata.RequestData;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseAPIService {

    private final CourseAPIClient courseAPIClient;
    private final AppUserService appUserService;

    @Autowired
    public CourseAPIService(CourseAPIClient courseAPIClient, AppUserService appUserService) {
        this.courseAPIClient = courseAPIClient;
        this.appUserService = appUserService;
    }

    public List<CourseInfoDTO> getCourses(Integer pageNumber, Integer pageSize) {
        try {
            return courseAPIClient.getCourses(pageNumber, pageSize, appUserService.getLoggedUser().getCpf());
        } catch (CoursesAPIHttpException coursesAPIHttpException) {
            throw new InternalErrorException(ErrorDetailsBuilder.withMessage("error.unknown").build(), coursesAPIHttpException);
        }
    }
}
