package dev.hudsonprojects.api.webhook;

import dev.hudsonprojects.api.webhook.entity.HttpHeader;
import dev.hudsonprojects.api.webhook.entity.HttpRequestData;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class HttpHeaderDTO {
    @NotBlank
    private String name;
    private String value;

    public HttpHeaderDTO() {
    }

    public HttpHeaderDTO(HttpHeader httpHeader) {
        this.name = httpHeader.getName();
        this.value = httpHeader.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HttpHeader toHttpHeader(HttpRequestData httpRequestData) {
        return new HttpHeader(httpRequestData, name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHeaderDTO that = (HttpHeaderDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
