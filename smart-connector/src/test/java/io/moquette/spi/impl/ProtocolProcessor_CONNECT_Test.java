/*
 * Copyright (c) 2012-2015 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package io.moquette.spi.impl;

import io.moquette.proto.messages.AbstractMessage;
import io.moquette.proto.messages.ConnectMessage;
import io.moquette.proto.messages.SubscribeMessage;
import io.moquette.server.netty.NettyUtils;
import io.moquette.spi.IMessagesStore;
import io.moquette.spi.ISessionsStore;
import io.moquette.spi.impl.subscriptions.SubscriptionsStore;
import io.moquette.spi.impl.security.PermitAllAuthorizator;
import io.moquette.spi.impl.subscriptions.Subscription;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.moquette.parser.netty.Utils.VERSION_3_1_1;
import static io.moquette.proto.messages.ConnAckMessage.BAD_USERNAME_OR_PASSWORD;
import static io.moquette.proto.messages.ConnAckMessage.CONNECTION_ACCEPTED;
import static io.moquette.proto.messages.ConnAckMessage.UNNACEPTABLE_PROTOCOL_VERSION;
import static io.moquette.spi.impl.NettyChannelAssertions.assertEqualsConnAck;
import static io.moquette.spi.impl.NettyChannelAssertions.assertEqualsSubAck;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author shuttle
 */
public class ProtocolProcessor_CONNECT_Test {

    EmbeddedChannel m_session;
    ConnectMessage connMsg;
    ProtocolProcessor m_processor;

    IMessagesStore m_messagesStore;
    ISessionsStore m_sessionStore;
    SubscriptionsStore subscriptions;
    MockAuthenticator m_mockAuthenticator;

    @Before
    public void setUp() throws InterruptedException {
        connMsg = new ConnectMessage();
        connMsg.setProtocolVersion((byte) 0x03);

        m_session = new EmbeddedChannel();

        //sleep to let the messaging batch processor to process the initEvent
        Thread.sleep(300);
        MemoryStorageService memStorage = new MemoryStorageService();
        memStorage.initStore();
        m_messagesStore = memStorage.messagesStore();
        m_sessionStore = memStorage.sessionsStore();
        //m_messagesStore.initStore();

        Map<String, byte[]> users = new HashMap<>();
        users.put(ProtocolProcessorTest.TEST_USER, ProtocolProcessorTest.TEST_PWD);
        m_mockAuthenticator = new MockAuthenticator(users);

        subscriptions = new SubscriptionsStore();
        subscriptions.init(m_sessionStore);
        m_processor = new ProtocolProcessor();
        m_processor.init(subscriptions, m_messagesStore, m_sessionStore, m_mockAuthenticator, true,
                new PermitAllAuthorizator(), ProtocolProcessorTest.NO_OBSERVERS_INTERCEPTOR);
    }

    @Test
    public void testHandleConnect_BadProtocol() {
        connMsg.setProtocolVersion((byte) 0x02);

        //Exercise
        m_processor.processConnect(m_session, connMsg);

        //Verify
        assertEqualsConnAck(UNNACEPTABLE_PROTOCOL_VERSION, m_session.readOutbound());
    }

    @Test
    public void testConnect_badClientID() {
        connMsg.setClientID("extremely_long_clientID_greater_than_23");

        //Exercise
        m_processor.processConnect(m_session, connMsg);

        //Verify
        assertEqualsConnAck(CONNECTION_ACCEPTED, m_session.readOutbound());
    }

    @Test
    public void testWill() {
        connMsg.setClientID("123");
        connMsg.setWillFlag(true);
        connMsg.setWillTopic("topic");
        connMsg.setWillMessage("Topic message".getBytes());


        //Exercise
        //m_handler.setMessaging(mockedMessaging);
        m_processor.processConnect(m_session, connMsg);

        //Verify
        assertEqualsConnAck(CONNECTION_ACCEPTED, m_session.readOutbound());
        //TODO verify the call
        /*verify(mockedMessaging).publish(eq("topic"), eq("Topic message".getBytes()),
                any(AbstractMessage.QOSType.class), anyBoolean(), eq("123"), any(IoSession.class));*/
    }

    @Test
    public void validAuthentication() {
        connMsg.setClientID("123");
        connMsg.setUserFlag(true);
        connMsg.setPasswordFlag(true);
        connMsg.setUsername(ProtocolProcessorTest.TEST_USER);
        connMsg.setPassword(ProtocolProcessorTest.TEST_PWD);

        //Exercise
        m_processor.processConnect(m_session, connMsg);

        //Verify
        assertEqualsConnAck(CONNECTION_ACCEPTED, m_session.readOutbound());
    }

    @Test
    public void noPasswdAuthentication() {
        connMsg.setClientID("123");
        connMsg.setUserFlag(true);
        connMsg.setPasswordFlag(false);
        connMsg.setUsername(ProtocolProcessorTest.TEST_USER);

        //Exercise
        m_processor.processConnect(m_session, connMsg);

        //Verify
        assertEqualsConnAck(BAD_USERNAME_OR_PASSWORD, m_session.readOutbound());
    }

    @Test
    public void invalidAuthentication() {
        connMsg.setClientID("123");
        connMsg.setUserFlag(true);
        connMsg.setPasswordFlag(true);
        connMsg.setUsername(ProtocolProcessorTest.TEST_USER + "_fake");
        connMsg.setPassword(ProtocolProcessorTest.TEST_PWD);

        //Exercise
        m_processor.processConnect(m_session, connMsg);

        //Verify
        assertEqualsConnAck(BAD_USERNAME_OR_PASSWORD, m_session.readOutbound());
    }

    @Test
    public void prohibitAnonymousClient() {
        connMsg.setClientID("123");
        m_processor.init(subscriptions, m_messagesStore, m_sessionStore, m_mockAuthenticator, false,
                new PermitAllAuthorizator(), ProtocolProcessorTest.NO_OBSERVERS_INTERCEPTOR);

        //Exercise
        m_processor.processConnect(m_session, connMsg);

        //Verify
        assertEqualsConnAck(BAD_USERNAME_OR_PASSWORD, m_session.readOutbound());
    }

    @Test
    public void prohibitAnonymousClient_providingUsername() {
        connMsg.setClientID("123");
        connMsg.setUserFlag(true);
        connMsg.setUsername(ProtocolProcessorTest.TEST_USER + "_fake");
        m_processor.init(subscriptions, m_messagesStore, m_sessionStore, m_mockAuthenticator, false,
                new PermitAllAuthorizator(), ProtocolProcessorTest.NO_OBSERVERS_INTERCEPTOR);

        //Exercise
        m_processor.processConnect(m_session, connMsg);

        //Verify
        assertEqualsConnAck(BAD_USERNAME_OR_PASSWORD, m_session.readOutbound());
    }

    @Test
    public void acceptAnonymousClient() {
        connMsg.setClientID("123");
        m_processor.init(subscriptions, m_messagesStore, m_sessionStore, m_mockAuthenticator, true,
                new PermitAllAuthorizator(), ProtocolProcessorTest.NO_OBSERVERS_INTERCEPTOR);

        //Exercise
        m_processor.processConnect(m_session, connMsg);

        //Verify
        assertEqualsConnAck(CONNECTION_ACCEPTED, m_session.readOutbound());
    }

    @Test
    public void connectWithSameClientIDBadCredentialsDoesntDropExistingClient() {
        //Connect a client1
        connMsg.setClientID("Client1");
        connMsg.setUserFlag(true);
        connMsg.setPasswordFlag(true);
        connMsg.setUsername(ProtocolProcessorTest.TEST_USER);
        connMsg.setPassword(ProtocolProcessorTest.TEST_PWD);
        m_processor.processConnect(m_session, connMsg);
        assertEqualsConnAck(CONNECTION_ACCEPTED, m_session.readOutbound());

        //create another connect same clientID but with bad credentials
        ConnectMessage evilClientConnMsg = new ConnectMessage();
        evilClientConnMsg.setProtocolVersion((byte) 0x03);
        evilClientConnMsg.setClientID("Client1");
        evilClientConnMsg.setUserFlag(true);
        evilClientConnMsg.setPasswordFlag(true);
        evilClientConnMsg.setUsername(ProtocolProcessorTest.EVIL_TEST_USER);
        evilClientConnMsg.setPassword(ProtocolProcessorTest.EVIL_TEST_PWD);
        EmbeddedChannel evilSession = new EmbeddedChannel();

        //Exercise
        m_processor.processConnect(evilSession, evilClientConnMsg);

        //Verify
        //the evil client gets a not auth notification
        assertEqualsConnAck(BAD_USERNAME_OR_PASSWORD, evilSession.readOutbound());
        //the good client remains connected
        assertTrue(m_session.isOpen());
        assertFalse(evilSession.isOpen());
    }


    @Test
    public void testConnAckContainsSessionPresentFlag() throws InterruptedException {
        connMsg = new ConnectMessage();
        connMsg.setProtocolVersion(VERSION_3_1_1);
        connMsg.setClientID("CliID");
        connMsg.setCleanSession(false);
        NettyUtils.clientID(m_session, "CliID");
        NettyUtils.cleanSession(m_session, false);

        //Connect a first time
        m_processor.processConnect(m_session, connMsg);
        //disconnect
        m_processor.processDisconnect(m_session);

        //Exercise, reconnect
        EmbeddedChannel firstReceiverSession = new EmbeddedChannel();
        m_processor.processConnect(firstReceiverSession, connMsg);

        //Verify
        assertEqualsConnAck(CONNECTION_ACCEPTED, firstReceiverSession.readOutbound());
    }


    @Test
    public void testMultipleReconnection() throws InterruptedException {
        //connect with clean a false and subscribe to a topic
        connMsg = new ConnectMessage();
        connMsg.setProtocolVersion(VERSION_3_1_1);
        connMsg.setClientID("CliID");
        connMsg.setCleanSession(false);
        m_processor.processConnect(m_session, connMsg);
        assertEqualsConnAck(CONNECTION_ACCEPTED, m_session.readOutbound());

        //subscribe
        SubscribeMessage subscribeMsg = new SubscribeMessage();
        subscribeMsg.addSubscription(new SubscribeMessage.Couple((byte) AbstractMessage.QOSType.MOST_ONE.ordinal(),
                ProtocolProcessorTest.FAKE_TOPIC));
        NettyUtils.clientID(m_session, "CliID");
        NettyUtils.cleanSession(m_session, false);
        m_processor.processSubscribe(m_session, subscribeMsg);
        Subscription expectedSubscription = new Subscription("CliID", ProtocolProcessorTest.FAKE_TOPIC,
                AbstractMessage.QOSType.MOST_ONE);
        assertTrue(subscriptions.contains(expectedSubscription));
        assertEqualsSubAck(m_session.readOutbound());

        //disconnect
        m_processor.processDisconnect(m_session);
        assertFalse(m_session.isOpen());

        //reconnect clean session a false
        m_session = new EmbeddedChannel();
        m_processor.processConnect(m_session, connMsg);
        assertEqualsConnAck(CONNECTION_ACCEPTED, m_session.readOutbound());

        //verify that the first subscription is still preserved
        assertTrue(subscriptions.contains(expectedSubscription));
    }
}
