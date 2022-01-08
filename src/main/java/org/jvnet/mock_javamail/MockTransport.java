package org.jvnet.mock_javamail;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.URLName;
import jakarta.mail.internet.MimeMessage;

/**
 * Mock {@link Transport} to deliver to {@link Mailbox}.
 *
 * @author Kohsuke Kawaguchi
 */
public class MockTransport extends Transport {
    public MockTransport(Session session, URLName urlname) {
        super(session, urlname);
    }

    @Override
    public void connect(String host, int port, String user, String password) throws MessagingException {
        // noop 
    }

    @Override
    public void sendMessage(Message msg, Address[] addresses) throws MessagingException {
        for (Address a : addresses) {
            // create a copy to isolate the sender and the receiver
            Mailbox mailbox = Mailbox.get(Aliases.getInstance().resolve(a));
            if(mailbox.isError())
                throw new MessagingException("Simulated error sending message to "+a);
            mailbox.add(new MimeMessage((MimeMessage)msg));
        }
    }
}
