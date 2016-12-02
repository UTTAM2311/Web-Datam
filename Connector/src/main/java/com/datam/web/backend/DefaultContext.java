package com.datam.web.backend;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datam.web.core.service.Service;
import com.datam.web.exception.ServiceException;
import com.tt.core.util.Verify;

public class DefaultContext implements Context {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultContext.class);

    private Map<String, Service> services = new HashMap<>();
    private final String name;

    public DefaultContext(String contextName) {
        this.name = contextName;
    }

    @Override
    public void addService(Service service) throws ServiceException {
        // TODO:do null check
        if (!services.containsKey(service.getName()))
            services.put(service.getName(), service);
        else {
            throwException(service + " already exists");
        }
    }

    @Override
    public void startAllServices() {
        for (Entry<String, Service> entry : services.entrySet()) {
            entry.getValue().startService();
        }
    }

    @Override
    public void stopAllServices() {
        Collection<String> serviceKeys = services.keySet();
        for (String serviceKey : serviceKeys) {
            try {
                stopService(serviceKey);
            } catch (ServiceException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    @Override
    public void removeService(Service service) throws ServiceException {
        String name = service.getName();
        removeService(name);
    }

    @Override
    public void removeService(String name) throws ServiceException {
        stopService(name);
        services.remove(name);
    }

    @Override
    public void addServices(List<Service> servicesList) {
        Verify.notNull(servicesList);
        servicesList.forEach(service -> {
            if (!services.containsKey(service.getName()))
                services.put(service.getName(), service);
        });
    }

    @Override
    public boolean hasService(Service service) {
        return services.containsKey(service.getName());
    }

    @Override
    public Service getService(String name) {
        Service service = null;
        if (services.containsKey(name)) {
            service = services.get(name);
        }
        return service;
    }

    @Override
    public Map<String, Service> getServices() {
        return services;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void stopService(Service service) throws ServiceException {
        String name = service.getName();
        stopService(name);
    }

    @Override
    public void stopService(String name) throws ServiceException {
        // will just stop the service but not remove from map
        if (services.containsKey(name)) {
            Service service = services.get(name);
            if (service.isServiceRunning()) {
                service.stopService();
            } else {
                throwException(name + " Service is not running");
            }

        } else {
            throwException("There is no service" + name);
        }

    }

    @Override
    public void startService(Service service) throws ServiceException {
        String name = service.getName();
        startService(name);
    }

    @Override
    public void startService(String name) throws ServiceException {
        if (services.containsKey(name)) {
            Service service = services.get(name);
            if (!service.isServiceRunning()) {
                service.startService();
            } else {
                throwException(name + " already running");
            }
        } else {
            throwException(name + " does not exist");
        }
    }

    private void throwException(String message) throws ServiceException {
        throw new ServiceException(message);
    }

}
