package org.emsg.smart_connector.security.impl;

import org.emsg.smart_connector.security.IAuthenticator;

/**
 * Shuttle on 8/23/14.
 */
public class AcceptAllAuthenticator implements IAuthenticator {
    public boolean checkValid(String username, byte[] password) {
        return true;
    }
}
