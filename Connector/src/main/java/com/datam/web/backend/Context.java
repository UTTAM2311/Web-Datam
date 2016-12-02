package com.datam.web.backend;

import java.util.List;
import java.util.Map;

import com.datam.web.core.service.Service;

public interface Context {

    void addService(Service service) throws Exception;

    void addServices(List<Service> service);

    void startService(Service service) throws Exception;

    void startService(String name) throws Exception;

    void startAllServices();

    void removeService(Service service) throws Exception;

    void removeService(String name) throws Exception;

    void stopAllServices();

    void stopService(Service service) throws Exception;

    void stopService(String name) throws Exception;

    boolean hasService(Service ser);

    Service getService(String name);

    Map<String, Service> getServices();

    public String getName();

}
