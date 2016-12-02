package com.datam.web.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.RequestExtras;
import ai.api.model.AIContext;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Entity;
import ai.api.model.Location;

public class Test {
    private static final String INPUT_PROMPT = "> ";

    /**
     * @param args List of parameters:<br>
     *        First parameters should be valid api key<br>
     *        Second and the following args should be file names containing audio data.
     */
    public static void main(String[] args) {


        AIConfiguration configuration = new AIConfiguration("5833c21e551a4bf6bbf21906b77f3b21");
        configuration.setProtocolVersion("20150910");
        AIDataService dataService = new AIDataService(configuration);

        AIContext con = new AIContext();
        con.setName("europe");
        con.setLifespan(4);

        List<AIContext> contexts = new ArrayList<>();
        contexts.add(con);
        contexts.add(new AIContext("weather"));

        RequestExtras ext = new RequestExtras();
        ext.setContexts(contexts);
        ext.setLocation(new Location(37.4256293, -122.20539));

        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print(INPUT_PROMPT);
            while (null != (line = reader.readLine())) {

                try {
                    AIRequest request = new AIRequest(line);
                    float[] conf = {0.3f};
                    request.setConfidence(conf);

                    request.setLanguage("en");
                    request.setTimezone("Europe/Paris");
                    request.setSessionId("1234567890");

                    AIResponse response = dataService.request(request, ext);
                    dataService.uploadUserEntity(new Entity());

                    if (response.getStatus().getCode() == 200) {
                        System.out.println(response);
                    } else {
                        System.err.println(response.getStatus().getErrorDetails());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                System.out.print(INPUT_PROMPT);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("See ya!");
    }


    /**
     * Output application usage information to stdout and exit. No return from function.
     * 
     * @param errorMessage Extra error message. Would be printed to stderr if not null and not
     *        empty.
     * 
     */
    private static void showHelp(String errorMessage, int exitCode) {
        if (errorMessage != null && errorMessage.length() > 0) {
            System.err.println(errorMessage);
            System.err.println();
        }

        System.out.println("Usage: APIKEY");
        System.out.println();
        System.out.println("APIKEY  Your unique application key");
        System.out.println("        See https://docs.api.ai/docs/key-concepts for details");
        System.out.println();
        System.exit(exitCode);
    }

}
