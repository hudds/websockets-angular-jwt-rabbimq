package dev.hudsonprojects.backend.integration.coursesapi.client;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CoursesAPIHttpRequest {
    private final Object body;
    private final Map<String, Object> queryParameters;
    private final Map<String, Object> namedURIParameters;
    private final Map<String, Object> headers;
    private final String path;

    private CoursesAPIHttpRequest(Builder builder) {
        this.path = Objects.requireNonNullElse(builder.path, "");
        this.body = builder.body;
        this.queryParameters = builder.queryParameters;
        this.namedURIParameters = builder.namedURIParameters;
        this.headers = builder.headers;
    }

    public Object getBody() {
        return body;
    }

    public Map<String, Object> getQueryParameters() {
        return queryParameters;
    }

    public Map<String, Object> getNamedURIParameters() {
        return namedURIParameters;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CoursesAPIHttpRequest that = (CoursesAPIHttpRequest) object;
        return Objects.equals(body, that.body) && Objects.equals(queryParameters, that.queryParameters) && Objects.equals(namedURIParameters, that.namedURIParameters) && Objects.equals(headers, that.headers) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, queryParameters, namedURIParameters, headers, path);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String path;
        private final Map<String, Object> queryParameters = new HashMap<>();
        private final Map<String, Object> namedURIParameters = new HashMap<>();
        private final Map<String, Object> headers = new HashMap<>();
        private Object body;

        private Builder(){}

        public Builder setBody(Object body) {
            this.body = body;
            return this;
        }

        public Builder setQueryParameters(Map<String, Object> queryParameters) {
            this.queryParameters.clear();
            if(queryParameters != null) {
                this.queryParameters.putAll(queryParameters);
            }
            return this;
        }

        public Builder queryParameter(String name, Object value) {
            this.queryParameters.put(name, value);
            return this;
        }

        public Builder setNamedURIParameters(Map<String, Object> namedURIParameters) {
            this.namedURIParameters.clear();
            if(namedURIParameters != null) {
                this.namedURIParameters.putAll(namedURIParameters);
            }
            return this;
        }

        public Builder namedURIParameter(String parameterName, Object value) {
            this.namedURIParameters.put(parameterName, value);
            return this;
        }

        public Builder setHeaders(Map<String, Object> headers) {
            this.headers.clear();
            if(headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        public Builder header(String name, String value) {
            this.headers.put(name, value);
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder contentType(String contentType) {
            return header("Content-Type", contentType);
        }

        public CoursesAPIHttpRequest build(){
            return new CoursesAPIHttpRequest(this);
        }
    }

}
