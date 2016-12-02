package com.datum.userSide;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.datum.ui.core.Util.FileUtil;

public class UserDatumRoutes {

    private static UserDatumRoutes instance = new UserDatumRoutes();

    private static final String UPDATE_DATA = "http://localhost:9090/updateData";
    private static final String FILE_PATH_TO_UPDATE = "some path";

    private UserDatumRoutes() {}

    public static UserDatumRoutes getInstance() {
        return instance;
    }

    public void createRoutes() {
        try {
            URL url = new URL(UPDATE_DATA);
            InputStream in = url.openStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(in, writer, "UTF-8");
            String updatedData = writer.toString();
            updateDataFile(updatedData);
            closeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateDataFile(String updatedData) {
        try {
            FileUtil.writeContentToFile(FILE_PATH_TO_UPDATE, updatedData, false);
        } catch (IOException e) {
            // TODO: how do we know if the update is failed or not from the user side...
        }
    }

    private void closeStream(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
