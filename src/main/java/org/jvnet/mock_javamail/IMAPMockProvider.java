package org.jvnet.mock_javamail;

import javax.mail.Provider;

/**
 * @author Réda Housni Alaoui
 */
public class IMAPMockProvider extends Provider {

	public IMAPMockProvider() {
		super(Provider.Type.STORE, "imap", MockStore.class.getName(),
				"java.net mock-javamail project", null);
	}

}
