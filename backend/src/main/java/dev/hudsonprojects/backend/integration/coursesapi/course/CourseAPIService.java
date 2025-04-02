package dev.hudsonprojects.backend.integration.coursesapi.course;

import dev.hudsonprojects.backend.appuser.AppUserDTO;
import dev.hudsonprojects.backend.appuser.AppUserService;
import dev.hudsonprojects.backend.common.exception.InternalErrorException;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.common.requestdata.RequestData;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.status.SubscriptionStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseAPIService {

    private final CourseAPIClient courseAPIClient;
    private final AppUserService appUserService;
    private final SubscriptionStatusService subscriptionStatusService;

    @Autowired
    public CourseAPIService(CourseAPIClient courseAPIClient, AppUserService appUserService, SubscriptionStatusService subscriptionStatusService) {
        this.courseAPIClient = courseAPIClient;
        this.appUserService = appUserService;
        this.subscriptionStatusService = subscriptionStatusService;
    }

    public List<CourseInfoDTO> getCourses(Integer pageNumber, Integer pageSize) {
        try {
            AppUserDTO loggedUser = appUserService.getLoggedUser();
            List<CourseInfoDTO> courses = courseAPIClient.getCourses(pageNumber, pageSize, loggedUser.getCpf());
            subscriptionStatusService.fillCourseStatus(loggedUser.getUserId(),  courses);
            return courses;
        } catch (CoursesAPIHttpException coursesAPIHttpException) {
            throw new InternalErrorException(ErrorDetailsBuilder.withMessage("error.unknown").build(), coursesAPIHttpException);
        }
    }
}
