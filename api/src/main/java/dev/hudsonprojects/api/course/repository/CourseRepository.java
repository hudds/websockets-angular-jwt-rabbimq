package dev.hudsonprojects.api.course.repository;

import dev.hudsonprojects.api.course.Course;
import dev.hudsonprojects.api.course.CourseInfoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = """
              SELECT new dev.hudsonprojects.api.course.CourseInfoDTO(
                course,
                (SELECT count(*) FROM Subscription subscription WHERE subscription.course.courseId = course.courseId),
                (SELECT max(subscription.registryData.createdAt) FROM Subscription subscription WHERE subscription.course.courseId = course.courseId)
              )
              FROM Course course
              WHERE course.courseId = :courseId
              """)
    Optional<CourseInfoDTO> getCourseInfoById(@Param("courseId") Long courseId);

    @Query(value = """
            SELECT new dev.hudsonprojects.api.course.CourseInfoDTO(
               course,
               (SELECT count(*) FROM Subscription subscription WHERE subscription.course.courseId = course.courseId),
               (SELECT max(subscription.registryData.createdAt) FROM Subscription subscription WHERE subscription.course.courseId = course.courseId)
            )
            FROM Course course
            """,
            countQuery = "SELECT count(*) FROM Course course")
    List<CourseInfoDTO> getAllCourseInfo(Pageable pageable);

    @Query(value = "SELECT new dev.hudsonprojects.api.course.CourseInfoDTO( " +
            "   course, " +
            "   (SELECT count(*) FROM Subscription subscription WHERE subscription.course.courseId = course.courseId), " +
            "   (SELECT max(subscription.registryData.createdAt) FROM Subscription subscription WHERE subscription.course.courseId = course.courseId), " +
            "   (SELECT count(*) > 0 FROM Subscription subscription WHERE subscription.course.courseId = course.courseId AND subscription.person.cpf = :cpf) " +
            "  ) " +
            "  FROM Course course ",
            countQuery = "SELECT count(*) FROM Course course ")
    List<CourseInfoDTO> getAllCourseInfoInRelationToCpf(@Param("cpf") String cpf, Pageable pageable);

}
