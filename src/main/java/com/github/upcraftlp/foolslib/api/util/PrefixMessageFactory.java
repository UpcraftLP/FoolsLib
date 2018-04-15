package com.github.upcraftlp.foolslib.api.util;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.apache.logging.log4j.spi.AbstractLogger;

public class PrefixMessageFactory extends AbstractMessageFactory {

    private final String prefix;

    public PrefixMessageFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Message newMessage(String message, Object... params) {
        return new ParameterizedMessage("[" + prefix + "]: " + message, params);
    }

}
