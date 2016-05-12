package org.emsg.smart_connector.interception.messages;

/**
 * @author Shuttle
 */
public class InterceptUnsubscribeMessage {
    private final String topicFilter;
    private final String clientID;

    public InterceptUnsubscribeMessage(String topicFilter, String clientID) {
        this.topicFilter = topicFilter;
        this.clientID = clientID;
    }

    public String getTopicFilter() {
        return topicFilter;
    }

    public String getClientID() {
        return clientID;
    }
}
