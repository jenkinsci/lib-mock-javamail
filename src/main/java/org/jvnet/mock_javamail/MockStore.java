package org.jvnet.mock_javamail;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.URLName;

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

    @Override
    public void connect() throws MessagingException {
        connect(url.getHost(), url.getPort(), url.getUsername(), url.getPassword());
    }

    @Override
    protected boolean protocolConnect(String host, int port, String user, String password) throws MessagingException {
        address = user+'@'+host;
        Mailbox mailbox = Mailbox.get(Aliases.getInstance().resolve(address));
        folder = new MockFolder(this, mailbox);
        if(mailbox.isError())
            throw new MessagingException("Simulated error connecting to "+address);
        return true;
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "TODO needs triage")
    @Override
    public Folder getDefaultFolder() throws MessagingException {
        return folder;
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "TODO needs triage")
    @Override
    public Folder getFolder(String name) throws MessagingException {
        return folder;
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "TODO needs triage")
    @Override
    public Folder getFolder(URLName url) throws MessagingException {
        return folder;
    }
}
