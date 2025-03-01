package dev.hudsonprojects.api.subscription;

import dev.hudsonprojects.api.common.lib.util.StringUtils;
import dev.hudsonprojects.api.course.CourseService;
import dev.hudsonprojects.api.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionAdapter {

    private final CourseService courseService;
    private final PersonService personService;

    @Autowired
    public SubscriptionAdapter(CourseService courseService, PersonService personService) {
        this.courseService = courseService;
        this.personService = personService;
    }

    public Subscription createSubscription(CreateSubscriptionDTO createSubscriptionDTO) {
        Subscription subscription = new Subscription();
        if(createSubscriptionDTO.getCourseId() != null) {
            subscription.setCourse(courseService.getCourseById(createSubscriptionDTO.getCourseId()));
        }
        if(StringUtils.isNotBlank(createSubscriptionDTO.getCpf())) {
            subscription.setPerson(personService.getPersonByCpf(createSubscriptionDTO.getCpf()));
        }
        return subscription;
    }

}
