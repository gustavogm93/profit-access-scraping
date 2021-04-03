package ar.com.pa.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import ar.com.pa.model.queue.SystemMessage;

/**
 *
 * @author AJ Catambay of Bridging Code
 *
 */
@Component
public class RegionMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionMessageConsumer.class);

    @JmsListener(destination = "region")
    public void messageListener(SystemMessage systemMessage) {
        LOGGER.info("Message received! {}", systemMessage);
    }
}