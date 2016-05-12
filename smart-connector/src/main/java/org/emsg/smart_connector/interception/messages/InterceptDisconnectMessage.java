package org.emsg.smart_connector.interception.messages;

/**
 * @author Shuttle
 */
public class InterceptDisconnectMessage {
    private final String clientID;

    public InterceptDisconnectMessage(String clientID) {
        this.clientID = clientID;
    }

    public String getClientID() {
        return clientID;
    }
}
