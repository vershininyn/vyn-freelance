package dev.projects.sspsoft.service.mq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

@Service
public class ArtemisMQImpl implements MessageQueue {

    @Value("${apiship.queue}")
    String queue;

    private final JmsTemplate jmsTemplate;

    public ArtemisMQImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void send(String message) {
        final UUID uuid = UUID.randomUUID();
        jmsTemplate.send(queue, session -> createMessage(session, message, uuid.toString()));
    }

    private Message createMessage(final Session session,
                                  final String messageBodyStr,
                                  final String messageId) throws JMSException {
        final TextMessage message = session.createTextMessage(messageBodyStr);
        message.setJMSMessageID("ID:" + messageId);

        return message;
    }
}