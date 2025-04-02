package dev.hudsonprojects.backend.integration.coursesapi.subscription.status;

import dev.hudsonprojects.backend.integration.coursesapi.course.CourseInfoDTO;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.SubscriptionDTO;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.status.repository.SubscriptionStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class SubscriptionStatusService {

    private final SubscriptionStatusRepository repository;

    @Autowired
    public SubscriptionStatusService(SubscriptionStatusRepository repository) {
        this.repository = repository;
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SubscriptionStatus updateStatus(Long userId, SubscriptionStatus.Status status, SubscriptionDTO subscriptionDTO) {
        if(userId == null || subscriptionDTO == null || subscriptionDTO.getCourse() == null || subscriptionDTO.getCourse().getCourseId() == null || status == null){
            return null;
        }
        Long courseId = subscriptionDTO.getCourse().getCourseId();
        SubscriptionStatus subscriptionStatus = repository.findByUserIdAndCourseId(userId, courseId).orElseGet(SubscriptionStatus::new);
        subscriptionStatus.setStatus(status);
        subscriptionStatus.setUserId(userId);
        subscriptionStatus.setCourseId(courseId);
        subscriptionStatus.setSubscriptionId(subscriptionDTO.getSubscriptionId());
        repository.save(subscriptionStatus);
        return subscriptionStatus;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SubscriptionStatus updateStatus(Long userId, Long courseId, SubscriptionStatus.Status status) {
        if(userId == null || courseId == null || status == null){
            return null;
        }
        SubscriptionStatus subscriptionStatus = repository.findByUserIdAndCourseId(userId, courseId).orElseGet(SubscriptionStatus::new);
        subscriptionStatus.setStatus(status);
        subscriptionStatus.setUserId(userId);
        subscriptionStatus.setCourseId(courseId);
        repository.save(subscriptionStatus);
        return subscriptionStatus;
    }


    public Map<Long, SubscriptionStatus.Status> getStatusByCourses(Long userId, Collection<Long> courseId) {
        if(userId == null || courseId == null || courseId.isEmpty()){
            return new HashMap<>();
        }
        return repository.findByUserIdAndCourseIdIn(userId, courseId)
                .stream()
                .collect(Collectors.toMap(SubscriptionStatus::getCourseId, SubscriptionStatus::getStatus, (existing, newStatus) -> existing));
    }

    public void fillCourseStatus(Long userId, List<CourseInfoDTO> courses) {
        if (userId == null || courses == null || courses.isEmpty()) {
            return;
        }
        Set<Long> coursesId = courses.stream()
                .map(CourseInfoDTO::getCourseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (coursesId.isEmpty()) {
            return;
        }
        Map<Long, SubscriptionStatus.Status> statusByCourseId = this.getStatusByCourses(userId, coursesId);
        for (var course : courses) {
            if (course.getCourseId() != null) {
                course.setSubscriptionStatus(statusByCourseId.get(course.getCourseId()));
            }
        }
    }
}
