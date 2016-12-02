package com.datum.ui.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import spark.Route;
import spark.route.HttpMethod;

/**
 * Created by haridas on 17/7/15.`
 */
public abstract class MtsRoute implements Route {
    private HttpMethod httpMethod;
    private String path;
    private String acceptType;
    private String version;
    private String cacheKeyParams;

    public MtsRoute() {}

    public MtsRoute(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }


    // Non-static builder for this instance.
    public MtsRoute build() {
        return this;
    }


    public MtsRoute setPath(String path) {
        this.path = path;
        return this;
    }

    public MtsRoute setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    // Setters and Getters.

    public String getAcceptType() {
        return acceptType;
    }

    public MtsRoute setAcceptType(String acceptType) {
        this.acceptType = acceptType;
        return this;
    }

    @JsonInclude(value = Include.ALWAYS)
    public String getVersion() {
        return version;
    }

    @JsonProperty("method")
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @JsonInclude(value = Include.ALWAYS)
    public String getCacheKeyParams() {
        return cacheKeyParams;
    }

    public MtsRoute setCacheKeyParams(String cacheKeyParams) {
        this.cacheKeyParams = cacheKeyParams;
        return this;
    }

    public String getPath() {
        return path;
    }

    public MtsRoute setVersion(String version) {
        this.version = version;
        return this;
    }

    public void setHttpValues(String path, HttpMethod httpMethod, String acceptType) {
        this.acceptType = acceptType;
        this.path = path;
        this.httpMethod = httpMethod;
    }

}
