package org.jvnet.mock_javamail.providers;

import org.jvnet.mock_javamail.MockStore;

import javax.mail.Provider;

/**
 * Provider for the MockStore
 */
public class IMAPMockStoreProvider extends Provider {
    public IMAPMockStoreProvider() {
        super(Type.STORE, "imap", MockStore.class.getName(),
                "java.net mock-javamail project", null);
    }
}

