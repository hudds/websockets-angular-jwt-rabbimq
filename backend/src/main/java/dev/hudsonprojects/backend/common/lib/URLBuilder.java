package dev.hudsonprojects.backend.common.lib;

import dev.hudsonprojects.backend.common.lib.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class URLBuilder {

    private final String target;
    private String path;
    private final Map<String, List<Object>> queryParameters = new LinkedHashMap<>();
    private final Map<String, Object> namedParameters = new LinkedHashMap<>();
    private QueryParameterBuilder queryParameterBuilder = new CommaSeparatedListQueryParameterBuilder();

    public URLBuilder(String target) {
        validateNotBlank(target);
        this.target = target;
    }

    public URLBuilder(String target, String path) {
        validateNotBlank(target);
        this.target = target;
        this.path = path;
    }

    private static void validateNotBlank(String target) {
        if(StringUtils.isBlank(target)){
            throw new IllegalArgumentException("target cannot be empty or null");
        }
    }

    public URLBuilder path(String path) {
        this.path = path;
        return this;
    }

    public URLBuilder queryParameter(String name, Object ... value){
        queryParameters.put(name, new ArrayList<>(List.of(value)));
        return this;
    }

    public URLBuilder queryParameter(String name, Collection<?> values){
        queryParameters.put(name, new ArrayList<>(values));
        return this;
    }

    public URLBuilder namedParameter(String name, Object value) {
        namedParameters.put(name, value);
        return this;
    }

    public URLBuilder queryParameterBuilder(QueryParameterBuilder queryParameterBuilder) {
        this.queryParameterBuilder = Objects.requireNonNull(queryParameterBuilder);
        return this;
    }

    public String build() {
        StringBuilder url = new StringBuilder(getTargetWithoutLastSeparator());
        if(StringUtils.isNotBlank(path)){
            url.append("/").append(getPathWithoutSeparators());
        }
        String urlQueryParameters = queryParameterBuilder.build(this.queryParameters);
        if(StringUtils.isNotBlank(urlQueryParameters)){
            url.append("?").append(urlQueryParameters);
        }

        String urlString = url.toString();
        for (var namedParameter : namedParameters.entrySet()) {
            urlString = urlString.replace(namedParameter.getKey(), String.valueOf(namedParameter.getValue()));
        }
        return urlString;
    }


    private String getTargetWithoutLastSeparator() {
        return removeLastSeparator(target);
    }

    private String getPathWithoutSeparators() {
        return removeFirstSeparator(removeLastSeparator(path));
    }

    private String removeLastSeparator(String string) {
        while (string.endsWith("/")){
            string = StringUtils.removeLastCharacter(string);
        }
        return string;
    }

    private String removeFirstSeparator(String string) {
        while (string.startsWith("/")){
            string = StringUtils.removeFirstCharacter(string);
        }
        return string;
    }

    public interface QueryParameterBuilder{
        String build(Map<String, List<Object>> queryParameters);
    }

    public static class CommaSeparatedListQueryParameterBuilder implements QueryParameterBuilder {
        @Override
        public String build(Map<String, List<Object>> queryParameters) {
            StringBuilder urlQueryParameters = new StringBuilder();
            for(var parameter : queryParameters.entrySet()){
                String name = parameter.getKey();
                List<Object> value = parameter.getValue();
                if(!urlQueryParameters.isEmpty()){
                    urlQueryParameters.append("&");
                }
                urlQueryParameters.append(name);
                urlQueryParameters.append("=");
                urlQueryParameters.append(value.stream().map(String::valueOf).collect(Collectors.joining(",")));
            }

            return urlQueryParameters.toString();
        }
    }

    public static class RepeatedQueryParameterListBuilder implements QueryParameterBuilder {
        @Override
        public String build(Map<String, List<Object>> queryParameters) {
            StringBuilder urlQueryParameters = new StringBuilder();
            for(var parameter : queryParameters.entrySet()){
                String name = parameter.getKey();
                List<Object> values = parameter.getValue();
                for(var value : values) {
                    if (!urlQueryParameters.isEmpty()) {
                        urlQueryParameters.append("&");
                    }
                    urlQueryParameters.append(name);
                    urlQueryParameters.append("=");
                    urlQueryParameters.append(value);
                }
            }

            return urlQueryParameters.toString();
        }
    }
}
