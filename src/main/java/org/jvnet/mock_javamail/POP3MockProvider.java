package org.jvnet.mock_javamail;

import javax.mail.Provider;

/**
 * @author Réda Housni Alaoui
 */
public class POP3MockProvider extends Provider {

	public POP3MockProvider() {
		super(Provider.Type.STORE, "pop3", MockStore.class.getName(),
				"java.net mock-javamail project", null);
	}
}
