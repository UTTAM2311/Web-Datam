package com.datam.web.core.service.qbyte;

import com.datam.web.core.service.AbstractService;
import com.tt.web.Site;

public class QbyteService extends AbstractService {
    
    private static boolean isRunning = false;

    public QbyteService(String name, Site site, String inDir, String outDir) {
        super(name, site, inDir, outDir);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void implementer() {
        // TODO Auto-generated method stub

    }

    @Override
    public void preprocessor(String inDir, String outDir) {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopService() {
        isRunning = true;
        
    }

    @Override
    public boolean isServiceRunning() {
        return isRunning;
    }

}
