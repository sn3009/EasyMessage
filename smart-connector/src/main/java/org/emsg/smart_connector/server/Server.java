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
package org.emsg.smart_connector.server;

import org.emsg.smart_connector.commons.config.FilesystemConfig;
import org.emsg.smart_connector.commons.config.IConfig;
import org.emsg.smart_connector.commons.config.MemoryConfig;
import org.emsg.smart_connector.commons.constants.BrokerConstants;
import org.emsg.smart_connector.interception.InterceptHandler;
import org.emsg.smart_connector.message.ProtocolProcessor;
import org.emsg.smart_connector.message.SimpleMessaging;
import org.emsg.smart_connector.message.mqtt.PublishMessage;
import org.emsg.smart_connector.netty.ServerAcceptor;
import org.emsg.smart_connector.netty.acceptor.NettyAcceptor;
import org.emsg.smart_connector.security.IAuthenticator;
import org.emsg.smart_connector.security.IAuthorizator;
import org.emsg.smart_connector.security.ISslContextCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Launch a  configured version of the server.
 * @author shuttle
 */
public class Server {
    
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    
    private ServerAcceptor m_acceptor;

    private volatile boolean m_initialized;

    private ProtocolProcessor m_processor;

    public static void main(String[] args) throws IOException {
        final Server server = new Server();
        server.startServer();
        System.out.println("Server started, version 0.9-SNAPSHOT");
        //Bind  a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.stopServer();
            }
        });
    }
    
    /**
     * Starts Moquette bringing the configuration from the file 
     * located at m_config/moquette.conf
     */
    public void startServer() throws IOException {
        final IConfig config = new FilesystemConfig();
        startServer(config);
    }

    /**
     * Starts Moquette bringing the configuration from the given file
     */
    public void startServer(File configFile) throws IOException {
        LOG.info("Using m_config file: " + configFile.getAbsolutePath());
        final IConfig config = new FilesystemConfig(configFile);
        startServer(config);
    }
    
    /**
     * Starts the server with the given properties.
     * 
     * Its suggested to at least have the following properties:
     * <ul>
     *  <li>port</li>
     *  <li>password_file</li>
     * </ul>
     */
    public void startServer(Properties configProps) throws IOException {
        final IConfig config = new MemoryConfig(configProps);
        startServer(config);
    }

    /**
     * Starts Moquette bringing the configuration files from the given Config implementation.
     */
    public void startServer(IConfig config) throws IOException {
        startServer(config, null);
    }

    /**
     * Starts Moquette with config provided by an implementation of IConfig class and with the
     * set of InterceptHandler.
     * */
    public void startServer(IConfig config, List<? extends InterceptHandler> handlers) throws IOException {
        startServer(config, handlers, null, null, null);
    }

    public void startServer(IConfig config, List<? extends InterceptHandler> handlers,
                            ISslContextCreator sslCtxCreator, IAuthenticator authenticator,
                            IAuthorizator authorizator) throws IOException {
        if (handlers == null) {
            handlers = Collections.emptyList();
        }

        final String handlerProp = System.getProperty("intercept.handler");
        if (handlerProp != null) {
            config.setProperty("intercept.handler", handlerProp);
        }
        LOG.info("Persistent store file: " + config.getProperty(BrokerConstants.PERSISTENT_STORE_PROPERTY_NAME));
        final ProtocolProcessor processor = SimpleMessaging.getInstance().init(config, handlers, authenticator, authorizator);

        if (sslCtxCreator == null) {
            sslCtxCreator = new DefaultSmartConnectorSslContextCreator(config);
        }

        m_acceptor = new NettyAcceptor();
        m_acceptor.initialize(processor, config, sslCtxCreator);
        m_processor = processor;
        m_initialized = true;
    }

    /**
     * Use the broker to publish a message. It's intended for embedding applications.
     * It can be used only after the server is correctly started with startServer.
     *
     * @param msg the message to forward.
     * @throws IllegalStateException if the server is not yet started
     * */
    public void internalPublish(PublishMessage msg) {
        if (!m_initialized) {
            throw new IllegalStateException("Can't publish on a server is not yet started");
        }
        m_processor.internalPublish(msg);
    }
    
    public void stopServer() {
    	LOG.info("Server stopping...");
        m_acceptor.close();
        SimpleMessaging.getInstance().shutdown();
        m_initialized = false;
        LOG.info("Server stopped");
    }
}
