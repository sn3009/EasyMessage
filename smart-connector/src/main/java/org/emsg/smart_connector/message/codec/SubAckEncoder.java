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
package org.emsg.smart_connector.message.codec;

import org.emsg.smart_connector.message.mqtt.AbstractMessage;
import org.emsg.smart_connector.message.mqtt.SubAckMessage;
import org.emsg.smart_connector.message.mqtt.AbstractMessage.QOSType;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author andrea
 */
class SubAckEncoder extends DemuxEncoder<SubAckMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, SubAckMessage message, ByteBuf out) {
        if (message.types().isEmpty()) {
            throw new IllegalArgumentException("Found a suback message with empty topics");
        }

        int variableHeaderSize = 2 + message.types().size();
        ByteBuf buff = chc.alloc().buffer(6 + variableHeaderSize);
        try {
            buff.writeByte(AbstractMessage.SUBACK << 4 );
            buff.writeBytes(Utils.encodeRemainingLength(variableHeaderSize));
            buff.writeShort(message.getMessageID());
            for (QOSType c : message.types()) {
                buff.writeByte(c.byteValue());
            }

            out.writeBytes(buff);
        } finally {
            buff.release();
        }
    }
    
}
