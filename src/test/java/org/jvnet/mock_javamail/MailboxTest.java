package org.jvnet.mock_javamail;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import junit.framework.TestCase;

/**
 * @author Réda Housni Alaoui
 */
public class MailboxTest extends TestCase {

	private static final String RECIPIENT = "john@doe.com";
	private static final String SUBJECT = "Test";
	private static final String TEXT = "Hello world";

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
