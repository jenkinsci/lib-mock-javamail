package org.jvnet.mock_javamail;

import static org.junit.Assert.assertEquals;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import org.junit.Test;

public class AliasesTest {
    @Test
    public void testResolve() throws MessagingException {
        Aliases aliases = Aliases.getInstance();
        assertEquals(new InternetAddress("alias1@example.com"), aliases.resolve(new InternetAddress("address1@example.com")));
        assertEquals(new InternetAddress("alias2@example.com"), aliases.resolve(new InternetAddress("address2@example.com")));
    }

    @Test
    public void testResolveNoAlias() throws MessagingException {
        Aliases aliases = Aliases.getInstance();
        assertEquals(new InternetAddress("test@example.com"), aliases.resolve(new InternetAddress("test@example.com")));
    }
}
