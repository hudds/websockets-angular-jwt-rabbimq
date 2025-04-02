package dev.hudsonprojects.backend.integration.coursesapi.subscription.status.repository;


import dev.hudsonprojects.backend.integration.coursesapi.subscription.status.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionStatusRepository extends JpaRepository<SubscriptionStatus, Long> {
    Optional<SubscriptionStatus> findByUserIdAndCourseId(Long appUserId, Long courseId);

    List<SubscriptionStatus> findByUserIdAndCourseIdIn(Long appUserId, Collection<Long> coursesIds);
}
