package org.jvnet.mock_javamail;

import jakarta.mail.Flags;
import jakarta.mail.Flags.Flag;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kohsuke Kawaguchi
 */
public class MockFolder extends Folder {
    private final Mailbox mailbox;

    public MockFolder(MockStore store, Mailbox mailbox) {
        super(store);
        this.mailbox = mailbox;
    }

    @Override
    public String getName() {
        return "INBOX";
    }

    @Override
    public String getFullName() {
        return "INBOX";
    }

    @Override
    public Folder getParent() throws MessagingException {
        return null;
    }

    @Override
    public boolean exists() throws MessagingException {
        return true;
    }

    @Override
    public Folder[] list(String pattern) throws MessagingException {
        return new Folder[0];
    }

    @Override
    public char getSeparator() throws MessagingException {
        return '/';
    }

    @Override
    public int getType() throws MessagingException {
        return HOLDS_MESSAGES;
    }

    @Override
    public boolean create(int type) throws MessagingException {
        return false;
    }

    @Override
    public boolean hasNewMessages() throws MessagingException {
        return mailbox.getNewMessageCount() > 0;
    }

    @Override
    public Folder getFolder(String name) throws MessagingException {
        // just use the same folder no matter which folder the caller asks for.
        return this;
    }

    @Override
    public boolean delete(boolean recurse) throws MessagingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean renameTo(Folder f) throws MessagingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void open(int mode) throws MessagingException {
        // always succeed
    }

    @Override
    public void close(boolean expunge) throws MessagingException {
        if (expunge) {
            expunge();
        }
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public Flags getPermanentFlags() {
        return null;
    }

    @Override
    public int getMessageCount() throws MessagingException {
        return mailbox.size();
    }

    @Override
    public int getNewMessageCount() throws MessagingException {
        return mailbox.getNewMessageCount();
    }

    @Override
    public Message getMessage(int msgnum) throws MessagingException {
        return mailbox.get(msgnum - 1); // 1-origin!? please.
    }

    @Override
    public Message[] getMessages(int low, int high) throws MessagingException {
        List<Message> messages = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            Message m = mailbox.get(i);
            messages.add(m);
        }
        return messages.toArray(new Message[0]);
    }

    @Override
    public void appendMessages(Message[] msgs) throws MessagingException {
        mailbox.addAll(Arrays.asList(msgs));
    }

    @Override
    public Message[] expunge() throws MessagingException {
        List<Message> expunged = new ArrayList<>();
        for (Message msg : mailbox) {
            if (msg.getFlags().contains(Flag.DELETED)) {
                expunged.add(msg);
            }
        }
        mailbox.removeAll(expunged);
        return expunged.toArray(new Message[0]);
    }
}
