package dev.hudsonprojects.backend.integration.coursesapi;

import dev.hudsonprojects.backend.common.lib.URLBuilder;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIHttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CoursesAPIParameters {

    @Value("${integration.courses_api.url}")
    private String apiUrl;


    public URLBuilder getApiUrlBuilder(){
        return new URLBuilder(apiUrl);
    }

    public String buildUrl(CoursesAPIHttpRequest httpRequest) {
        URLBuilder builder = getApiUrlBuilder();
        builder.path(httpRequest.getPath());
        httpRequest.getNamedURIParameters().forEach(builder::namedParameter);
        httpRequest.getQueryParameters().forEach(builder::queryParameter);
        return builder.build();
    }
}
