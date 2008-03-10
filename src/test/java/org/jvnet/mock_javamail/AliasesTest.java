package org.jvnet.mock_javamail;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import junit.framework.TestCase;

public class AliasesTest extends TestCase {
    public void testResolve() throws MessagingException {
        Aliases aliases = Aliases.getInstance();
        assertEquals(new InternetAddress("alias1@example.com"), aliases.resolve(new InternetAddress("address1@example.com")));
        assertEquals(new InternetAddress("alias2@example.com"), aliases.resolve(new InternetAddress("address2@example.com")));
    }
    
    public void testResolveNoAlias() throws MessagingException {
        Aliases aliases = Aliases.getInstance();
        assertEquals(new InternetAddress("test@example.com"), aliases.resolve(new InternetAddress("test@example.com")));
    }
}
