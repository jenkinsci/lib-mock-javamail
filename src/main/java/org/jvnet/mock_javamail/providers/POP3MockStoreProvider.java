package org.jvnet.mock_javamail.providers;

import org.jvnet.mock_javamail.MockStore;

import javax.mail.Provider;

/**
 * Provider for the MockStore
 */
public class POP3MockStoreProvider extends Provider {
    public POP3MockStoreProvider() {
        super(Provider.Type.STORE, "pop3", MockStore.class.getName(),
                "java.net mock-javamail project", null);
    }
}

