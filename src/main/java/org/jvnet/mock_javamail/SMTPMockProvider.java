package org.jvnet.mock_javamail;

import javax.mail.Provider;

/**
 * @author Réda Housni Alaoui
 */
public class SMTPMockProvider extends Provider {

	public SMTPMockProvider() {
		super(Provider.Type.TRANSPORT, "smtp", MockTransport.class.getName(),
				"java.net mock-javamail project", null);
	}

}
