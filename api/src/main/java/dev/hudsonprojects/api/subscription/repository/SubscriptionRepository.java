package dev.hudsonprojects.api.subscription.repository;

import dev.hudsonprojects.api.subscription.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {


    @Query(value = """
             SELECT count(*) > 0
                FROM Subscription subscription
             WHERE subscription.course.courseId = :courseId AND subscription.person.personId = :personId
             """)
    boolean existsByCourseIdAndPersonId(@Param("courseId") Long courseId, @Param("personId") Long personId);


    @Query(value = " SELECT COUNT(*) FROM Subscription subscription WHERE subscription.course.courseId = :courseId ")
    long countSubscriptionByCourseId(@Param("courseId") Long courseId);
}
