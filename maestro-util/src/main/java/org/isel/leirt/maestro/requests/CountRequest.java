package org.isel.leirt.maestro.requests;

import java.io.Reader;

/**
 * TO COMPLETE
 */
public class CountRequest implements Request {

    public CountRequest(HttpRequest req) {

    }

    @Override
    public Reader get(String path) {
       return null;
    }

    public int getCount() {
        return 0;
    }
}
