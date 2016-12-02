package com.datum.ui.core;

import java.util.ArrayList;
import java.util.List;

import spark.Filter;
import spark.Spark;
import spark.route.HttpMethod;


/**
 * The main building block of an MTS application is a set of routes. A route is
 * made up of three simple pieces:
 * <ul>
 * <li>A verb (get, post, put, delete, head, trace, connect, options)</li>
 * <li>A path (/hello, /users/:name)</li>
 * <li>A callback (request, response)</li>
 * </ul>
 * Example:
 * get("/hello", (request, response) -&#62; {
 * return "Hello World!";
 * });
 * Created by haridas on 17/7/15.
 */
public class MtsSpark {

    public static final String DEFAULT_ACCEPT_TYPE = "*/*";

    static List<MtsRoute> routeEntries = new ArrayList<MtsRoute>();
    

    /**
     * Map the route for HTTP GET requests
     * HTTP METHODS compatible to the SPARK APIs.
     * @param path  the path
     * @param route The route
     */
    public static synchronized void get(final String path, final MtsRoute route) {
        route.setHttpValues(path, HttpMethod.get, null);
        routeEntries.add(route);
        Spark.get(path, route);
    }

    /**
     * Map the route for HTTP POST requests
     * HTTP METHODS compatible to the SPARK APIs.
     * @param path  the path
     * @param route The route
     */
    
    public static synchronized void post(final String path, final MtsRoute route) {
        route.setHttpValues(path, HttpMethod.post, null);
        routeEntries.add(route);
        Spark.post(path, route);
    }

    /**
     * Map the MtsRoute for HTTP PUT requests
     *
     * @param path  the path
     * @param MtsRoute The MtsRoute
     */
    public static synchronized void put(String path, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.put, null);
        routeEntries.add(route);
        Spark.put(path, route);
    }

    /**
     * Map the MtsRoute for HTTP PATCH requests
     *
     * @param path  the path
     * @param MtsRoute The MtsRoute
     */
    public static synchronized void patch(String path, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.patch, null);
        routeEntries.add(route);
        Spark.patch(path, route);
    }

    /**
     * Map the MtsRoute for HTTP DELETE requests
     *
     * @param path  the path
     * @param MtsRoute The MtsRoute
     */
    public static synchronized void delete(String path, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.delete, null);
        routeEntries.add(route);
        Spark.delete(path, route);
    }

    /**
     * Map the MtsRoute for HTTP HEAD requests
     *
     * @param path  the path
     * @param MtsRoute The MtsRoute
     */
    public static synchronized void head(String path, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.head, null);
        routeEntries.add(route);
        Spark.head(path, route);
    }

    /**
     * Map the MtsRoute for HTTP TRACE requests
     *
     * @param path  the path
     * @param MtsRoute The MtsRoute
     */
    public static synchronized void trace(String path, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.trace, null);
        routeEntries.add(route);
        Spark.trace(path, route);
    }

    /**
     * Map the MtsRoute for HTTP CONNECT requests
     *
     * @param path  the path
     * @param MtsRoute The MtsRoute
     */
    public static synchronized void connect(String path, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.connect, null);
        routeEntries.add(route);
        Spark.connect(path, route);
    }

    /**
     * Map the MtsRoute for HTTP OPTIONS requests
     *
     * @param path  the path
     * @param MtsRoute The MtsRoute
     */
    public static synchronized void options(String path, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.options, null);
        routeEntries.add(route);
        Spark.options(path, route);
    }

    /**
     * Maps a filter to be executed before any matching routes
     *
     * @param path   the path
     * @param filter The filter
     */
    public static synchronized void before(String path, Filter filter) {
        Spark.before(path, filter);
    }

    /**
     * Maps a filter to be executed after any matching routes
     *
     * @param path   the path
     * @param filter The filter
     */
    public static synchronized void after(String path, Filter filter) {
        Spark.after(path, filter);
    }


    //////////////////////////////////////////////////
    // BEGIN MtsRoute/filter mapping with accept type
    //////////////////////////////////////////////////

    /**
     * Map the MtsRoute for HTTP GET requests
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param MtsRoute      The MtsRoute
     */
    public static synchronized void get(String path, String acceptType, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.get, acceptType);
        routeEntries.add(route);
        Spark.get(path, acceptType, route);
    }

    /**
     * Map the MtsRoute for HTTP POST requests
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param MtsRoute      The MtsRoute
     */
    public static synchronized void post(String path, String acceptType, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.post, acceptType);
        routeEntries.add(route);
        Spark.post(path, acceptType, route);
    }

    /**
     * Map the MtsRoute for HTTP PUT requests
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param MtsRoute      The MtsRoute
     */
    public static synchronized void put(String path, String acceptType, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.put, acceptType);
        routeEntries.add(route);
        Spark.put(path, acceptType, route);
    }


    /**
     * Map the MtsRoute for HTTP PATCH requests
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param MtsRoute      The MtsRoute
     */
    public static synchronized void patch(String path, String acceptType, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.patch, acceptType);
        routeEntries.add(route);
        Spark.patch(path, acceptType, route);
    }

    /**
     * Map the MtsRoute for HTTP DELETE requests
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param MtsRoute      The MtsRoute
     */
    public static synchronized void delete(String path, String acceptType, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.delete, acceptType);
        routeEntries.add(route);
        Spark.delete(path, acceptType, route);
    }

    /**
     * Map the MtsRoute for HTTP HEAD requests
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param MtsRoute      The MtsRoute
     */
    public static synchronized void head(String path, String acceptType, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.head, acceptType);
        routeEntries.add(route);
        Spark.head(path, acceptType, route);
    }

    /**
     * Map the MtsRoute for HTTP TRACE requests
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param MtsRoute      The MtsRoute
     */
    public static synchronized void trace(String path, String acceptType, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.trace, acceptType);
        routeEntries.add(route);
        Spark.trace(path, acceptType, route);
    }


    /**
     * Map the MtsRoute for HTTP CONNECT requests
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param MtsRoute      The MtsRoute
     */
    public static synchronized void connect(String path, String acceptType, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.connect, acceptType);
        routeEntries.add(route);
        Spark.connect(path, acceptType, route);
    }

    /**
     * Map the MtsRoute for HTTP OPTIONS requests
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param MtsRoute      The MtsRoute
     */
    public static synchronized void options(String path, String acceptType, MtsRoute route) {
        route.setHttpValues(path, HttpMethod.options, acceptType);
        routeEntries.add(route);
        Spark.options(path, acceptType, route);
    }


    /**
     * Maps a filter to be executed before any matching routes
     *
     * @param filter The filter
     */
    public static synchronized void before(Filter filter) {
        Spark.before(filter);
    }

    /**
     * Maps a filter to be executed after any matching routes
     *
     * @param filter The filter
     */
    public static synchronized void after(Filter filter) {
        Spark.after(filter);
    }

    /**
     * Maps a filter to be executed before any matching routes
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param filter     The filter
     */
    public static synchronized void before(String path, String acceptType, Filter filter) {
        Spark.before(path, acceptType, filter);
    }

    /**
     * Maps a filter to be executed after any matching routes
     *
     * @param path       the path
     * @param acceptType the accept type
     * @param filter     The filter
     */
    public static synchronized void after(String path, String acceptType, Filter filter) {
        Spark.after(path, acceptType, filter);
    }

    //////////////////////////////////////////////////
    // END MtsRoute/filter mapping with accept type
    //////////////////////////////////////////////////
    
    public static List<MtsRoute> getRoutes() {
        return routeEntries;
    }
   
}