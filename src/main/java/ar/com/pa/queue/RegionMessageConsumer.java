package ar.com.pa.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class RegionMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionMessageConsumer.class);

    @JmsListener(destination = "region")
    public void messageListener(SystemMessage systemMessage) {
        LOGGER.info("Message received! {}, {} <- thread", systemMessage, Thread.currentThread().getName());
    }
}