package org.emsg.smart_connector.netty;

import java.io.IOException;

import org.emsg.smart_connector.commons.config.IConfig;
import org.emsg.smart_connector.message.ProtocolProcessor;
import org.emsg.smart_connector.security.ISslContextCreator;

public interface ServerAcceptor {

	void initialize(ProtocolProcessor processor, IConfig props, ISslContextCreator sslCtxCreator) throws IOException;

	void close();
}
