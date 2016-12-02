package com.datam.web.core;

import java.io.File;
import java.io.FileInputStream;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIResponse;

public class Voicetester {
    /**
     * Default exit code in case of error
     */
    private static final int ERROR_EXIT_CODE = 1;

    /**
     * @param args List of parameters:<br>
     *        First parameters should be valid api key<br>
     *        Second and the following args should be file names containing audio data.
     */
    public static void main(String[] args) {


        AIConfiguration configuration = new AIConfiguration("5833c21e551a4bf6bbf21906b77f3b21");

        AIDataService dataService = new AIDataService(configuration);

        for (int i = 1; i < 2; ++i) {
            File file = new File("/home/uttam/projects/apiai-java-sdk/examples/voice-client/audio/how_are_you.raw");
            try (FileInputStream inputStream = new FileInputStream(file)) {

                System.out.println(file);

                AIResponse response = dataService.voiceRequest(inputStream);

                if (response.getStatus().getCode() == 200) {
                    System.out.println(response.getResult());
                } else {
                    System.err.println(response.getStatus().getErrorDetails());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
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
        System.out.println("Usage: APIKEY FILE...");
        System.out.println();
        System.out.println("APIKEY  Your unique application key");
        System.out.println("        See https://docs.api.ai/docs/key-concepts for details");
        System.out.println();
        System.out.println("FILE    Path to file containing raw audio data");
        System.out.println("        See https://docs.api.ai/docs/query#post-query-multipart for details");
        System.out.println();

        System.exit(exitCode);
    }
}
