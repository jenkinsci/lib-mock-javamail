package org.jvnet.mock_javamail;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

/**
 * {@link Store} backed by {@link Mailbox}.
 *
 * @author Kohsuke Kawaguchi
 */
public class MockStore extends Store {
    private MockFolder folder;
    private String address;

    public MockStore(Session session, URLName urlname) {
        super(session, urlname);
    }

    public void connect(String host, int port, String user, String password) throws MessagingException {
        address = user+'@'+host;
        folder = new MockFolder(this,Mailbox.get(address));
        super.connect(host, port, user, password);
    }

    public Folder getDefaultFolder() throws MessagingException {
        return folder;
    }

    public Folder getFolder(String name) throws MessagingException {
        return folder;
    }

    public Folder getFolder(URLName url) throws MessagingException {
        return folder;
    }
}
