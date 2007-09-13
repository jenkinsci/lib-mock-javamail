package org.jvnet.mock_javamail;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * In-memory mailbox that hosts messages.
 *
 * @author Kohsuke Kawaguchi
 */
public class Mailbox extends ArrayList<Message> {
    private final Address address;

    private boolean error;

    public Mailbox(Address address) {
        this.address = address;
    }

    /**
     * Gets the e-mail address of this mailbox.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns true if this mailbox is flagged as 'error'.
     *
     * @see #setError(boolean)
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets if this mailbox should be flagged as 'error'.
     *
     * Any sending/receiving operation with an error mailbox
     * will fail. This behavior can be used to test the error
     * handling behavior of the application.
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * All mailboxes.
     */
    private static final Map<Address,Mailbox> mailboxes = new HashMap<Address,Mailbox>();

    /**
     * Get the inbox for the given address.
     */
    public synchronized static Mailbox get(Address a) {
        Mailbox inbox = mailboxes.get(a);
        if(inbox==null)
            mailboxes.put(a,inbox=new Mailbox(a));
        return inbox;
    }

    public static Mailbox get(String address) throws AddressException {
        return get(new InternetAddress(address));
    }

    /**
     * Discards all the mailboxes and its data.
     */
    public static void clearAll() {
        mailboxes.clear();
    }
}
