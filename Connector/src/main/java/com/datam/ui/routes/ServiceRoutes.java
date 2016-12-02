
package com.datam.ui.routes;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datam.ui.core.MtsRoute;
import static com.datam.ui.core.MtsSpark.*;
import com.datam.ui.util.JsonUtil;

import spark.Request;
import spark.Response;

import com.datam.web.core.StandaloneContext;
import com.datam.web.core.service.Service;
import com.datam.web.exception.ServiceException;

public class ServiceRoutes {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRoutes.class);

    private static final String SERVICELIST = "/service_list";
    private static final String START_SERVICE = "/startService/:serviceName";
    private static final String STOP_SERVICE = "/stopService/:serviceName";

    private static ServiceRoutes instance = new ServiceRoutes();
    private Map<String, Service> serviceMap = new HashMap<>();
    Map<String, Boolean> tmpServicesMap = new HashMap<>();
    private StandaloneContext standaloneContext = StandaloneContext.getInstance();

    private ServiceRoutes() {
        serviceMap = standaloneContext.getServices();
    }

    public static ServiceRoutes getInstance() {
        return instance;
    }

    public void createRoutes() {
        get(SERVICELIST, new MtsRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Collection<Service> serviceSet = serviceMap.values();
                for (Service service : serviceSet) {
                    tmpServicesMap.put(service.getName(), service.isServiceRunning());
                }
                String json = JsonUtil.getJsonString(tmpServicesMap);
                return json;
            }
        });

        get(START_SERVICE, new MtsRoute() {
            @Override
            public Object handle(Request request, Response response) {
                String responseString = "started";
                String service = request.params(":serviceName");
                try {
                    standaloneContext.startService(service);
                    tmpServicesMap.put(service, true);
                } catch (ServiceException e) {
                    LOGGER.error(e.getMessage());
                    responseString = e.getMessage();
                }
                return responseString;
            }
        });

        get(STOP_SERVICE, new MtsRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String responseString = "stopped";
                String service = request.params(":serviceName");
                try {
                    standaloneContext.stopService(service);
                    tmpServicesMap.put(service, false);
                } catch (ServiceException e) {
                    LOGGER.error(e.getMessage());
                    responseString = e.getMessage();
                }
                return responseString;
            }
        });

    }

}
