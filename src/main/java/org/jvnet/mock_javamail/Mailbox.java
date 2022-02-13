package org.jvnet.mock_javamail;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * In-memory mailbox that hosts messages.
 *
 * <p>
 * This class also maintains the 'unread' flag for messages that are newly added.
 * This flag is automatically removed when the message is retrieved, much like
 * how MUA behaves. This flag affects {@link MockFolder#getNewMessageCount()}.
 *
 * @author Kohsuke Kawaguchi
 */
public class Mailbox extends ArrayList<Message> {
    private final Address address;
    /**
     * Of the mails in the {@link ArrayList}, these are considered unread.
     *
     * <p>
     * Because we can't intercept every mutation of {@link ArrayList},
     * this set may contain messages that are no longer in them.
     */
    private final Set<Message> unread = new HashSet<>();

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
    private static final Map<Address,Mailbox> mailboxes = new HashMap<>();

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

    public int getNewMessageCount() {
        // to compute the real size, we need to trim off all the e-mails that are no longer in the base set
        unread.retainAll(this);
        return unread.size();
    }

    @Override
    public Message get(int msgnum){
        Message m = super.get(msgnum);
        unread.remove(m);
        return m;
    }

    @Override
    public boolean addAll(Collection<? extends Message> messages){
        unread.addAll(messages);
        return super.addAll(messages);
    }

    @Override
    public boolean add(Message message) {
        unread.add(message);
        return super.add(message); 
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Removes the 'new' status from all the e-mails.
     * Akin to "mark all e-mails as read" in the MUA.
     */
    public void clearNewStatus() {
        unread.clear();
    }

    /**
     * Discards all the mailboxes and its data.
     */
    public static void clearAll() {
        mailboxes.clear();
    }
}
