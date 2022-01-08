package org.jvnet.mock_javamail;

import static org.junit.Assert.assertEquals;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import org.junit.Test;

/**
 * @author Réda Housni Alaoui
 */
public class MailboxTest {

    private static final String RECIPIENT = "john@doe.com";
    private static final String SUBJECT = "Test";
    private static final String TEXT = "Hello world";

    @Test
    public void test() throws MessagingException, IOException {
        Message message = new MimeMessage(Session.getInstance(new Properties()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(RECIPIENT));
        message.setSubject(SUBJECT);
        message.setText(TEXT);

        Transport.send(message);

        Mailbox mailbox = Mailbox.get(RECIPIENT);
        assertEquals(1, mailbox.size());

        Message receivedMessage = mailbox.get(0);
        assertEquals(SUBJECT, receivedMessage.getSubject());
        assertEquals(TEXT, receivedMessage.getContent());
    }
}
