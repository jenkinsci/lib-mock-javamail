package org.jvnet.mock_javamail;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.*;

/**
 * @author Kohsuke Kawaguchi
 */
public class MockFolder extends Folder {
    private final Mailbox mailbox;

    public MockFolder(MockStore store, Mailbox mailbox) {
        super(store);
        this.mailbox = mailbox;
    }

    public String getName() {
        return "INBOX";
    }

    public String getFullName() {
        return "INBOX";
    }

    public Folder getParent() throws MessagingException {
        return null;
    }

    public boolean exists() throws MessagingException {
        return true;
    }

    public Folder[] list(String pattern) throws MessagingException {
        return new Folder[0];
    }

    public char getSeparator() throws MessagingException {
        return '/';
    }

    public int getType() throws MessagingException {
        return HOLDS_MESSAGES;
    }

    public boolean create(int type) throws MessagingException {
        return false;
    }

    public boolean hasNewMessages() throws MessagingException {
        return mailbox.getNewMessageCount()>0;
    }

    public Folder getFolder(String name) throws MessagingException {
        // just use the same folder no matter which folder the caller asks for.
        return this;
    }

    public boolean delete(boolean recurse) throws MessagingException {
        throw new UnsupportedOperationException();
    }

    public boolean renameTo(Folder f) throws MessagingException {
        throw new UnsupportedOperationException();
    }

    public void open(int mode) throws MessagingException {
        // always succeed
    }

    public void close(boolean expunge) throws MessagingException {
        if(expunge)
            expunge();
    }

    public boolean isOpen() {
        return true;
    }

    public Flags getPermanentFlags() {
        return null;
    }

    public int getMessageCount() throws MessagingException {
        return mailbox.size();
    }

    @Override
    public int getNewMessageCount() throws MessagingException {
        return mailbox.getNewMessageCount();
    }

    public Message getMessage(int msgnum) throws MessagingException {
        return mailbox.get(msgnum-1);   // 1-origin!? please.
    }

    @Override
     public Message[] getMessages(int low, int high) throws MessagingException {
        List<Message> messages = new ArrayList<Message>();
        for(int i=low; i<=high; i++){
            Message m = mailbox.get(i);
            messages.add(m);
        }
        return messages.toArray( new Message[messages.size()]);
    }

    public void appendMessages(Message[] msgs) throws MessagingException {
        mailbox.addAll(Arrays.asList(msgs));
    }

    public Message[] expunge() throws MessagingException {
        List<Message> expunged = new ArrayList<Message>();
        for (Message msg : mailbox) {
            if(msg.getFlags().contains(Flag.DELETED))
                expunged.add(msg);
        }
        mailbox.removeAll(expunged);
        return expunged.toArray(new Message[expunged.size()]);
    }
}
