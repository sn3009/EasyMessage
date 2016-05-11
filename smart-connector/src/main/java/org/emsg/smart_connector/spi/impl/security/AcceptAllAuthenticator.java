package org.emsg.smart_connector.spi.impl.security;

import org.emsg.smart_connector.spi.security.IAuthenticator;

/**
 * Created by andrea on 8/23/14.
 */
public class AcceptAllAuthenticator implements IAuthenticator {
    public boolean checkValid(String username, byte[] password) {
        return true;
    }
}
