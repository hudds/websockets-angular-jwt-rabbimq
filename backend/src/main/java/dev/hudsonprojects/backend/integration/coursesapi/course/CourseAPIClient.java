package dev.hudsonprojects.backend.integration.coursesapi.course;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIAuthorizedHttpClient;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIHttpRequest;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseAPIClient {

    private final CoursesAPIAuthorizedHttpClient apiClient;

    @Autowired
    public CourseAPIClient(CoursesAPIAuthorizedHttpClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<CourseInfoDTO> getCourses(Integer pageNumber, Integer pageSize, String inRelationToCpf) throws CoursesAPIHttpException {
        var requestBuilder = CoursesAPIHttpRequest.builder()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .setPath("course");

        if(pageNumber != null){
            requestBuilder.queryParameter("pageNumber", pageNumber);
        }
        if(pageSize != null){
            requestBuilder.queryParameter("pageSize", pageSize);
        }
        if(inRelationToCpf != null){
            requestBuilder.queryParameter("inRelationToCpf", inRelationToCpf);
        }
        return apiClient.doGet(new TypeReference<List<CourseInfoDTO>>() {}, requestBuilder.build());
    }

}
