package com.datum.ui.core.routes;


import com.datum.ui.core.MtsRoute;
import com.datum.ui.core.Util.UserUtil;

import static com.datum.ui.core.MtsSpark.post;
import static com.datum.ui.core.MtsSpark.get;

import spark.Request;
import spark.Response;

public class DatumRoute {

    private static final String CREATEACCOUNT = "/createAccount";
    private static final String UPDATE_PROVIDED_INFO = "/:user/updateUserDataInfo";
    private static final String PROVIDEINFO_RESPONSE = "/dataInfoResponse";
    private static final String PROVIDE_INFO = "/:userKey/dataInfo";

    private static DatumRoute instance = new DatumRoute();

    public static DatumRoute getInstance() {
        return instance;
    }

    private DatumRoute() {}

    public void createRoutes() {

        get("/test", new MtsRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "success";
            }
        });
        
        post(CREATEACCOUNT, new MtsRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String requestBody = request.body();
                String responseString = UserUtil.createUserAccount(requestBody);
                return responseString;
            }
        });

        post(PROVIDEINFO_RESPONSE, new MtsRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String requestBody = request.body();
                UserUtil.collectUserResponse(requestBody);
                return "success";
            }
        });

        get(UPDATE_PROVIDED_INFO, new MtsRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // TODO: here we update the local cache of user frequently and we provide user's
                // data from this local cache to avoid network delay
                return "";
            }
        });

        get(PROVIDE_INFO, new MtsRoute() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String userKey = request.params(":userKey");
                String data = UserUtil.getDataForUser(userKey);
                return data;
            }
        });
    }

}
