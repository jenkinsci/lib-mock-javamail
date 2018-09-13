package org.jvnet.mock_javamail.providers;

import org.jvnet.mock_javamail.MockTransport;

import javax.mail.Provider;

/**
 * Provider for the MockTransport
 */
public class SMTPMockTransportProvider extends Provider {
    public SMTPMockTransportProvider() {
        super(Provider.Type.TRANSPORT, "smtp", MockTransport.class.getName(),
                "java.net mock-javamail project", null);
    }
}
