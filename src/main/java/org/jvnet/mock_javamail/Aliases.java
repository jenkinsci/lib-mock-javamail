package org.jvnet.mock_javamail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Reads <tt>/META-INF/mock-javamail.aliases</tt> file so tha you can
 * have different names for SMTP and POP/IMAP servers. This is useful
 * for writing a test that can work with both real servers and mock up servers.
 *
 * <p>
 * Alias table is essentially just a map from one e-mail address to another.
 */
public class Aliases {
    private static Aliases instance;
    
    private final Map<Address,Address> aliasMap;
    
    private Aliases(Map<Address,Address> aliasMap) {
        this.aliasMap = aliasMap;
    }
    
    public synchronized static Aliases getInstance() throws MessagingException {
        if (instance == null) {
            Map<Address,Address> aliasMap = new HashMap<Address,Address>();
            InputStream in = Aliases.class.getResourceAsStream("/META-INF/mock-javamail.aliases");
            if (in == null) {
                
            } else {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int index = line.indexOf('=');
                        if (index == -1) {
                            throw new MessagingException("Invalid entry in alias file");
                        } else {
                            aliasMap.put(new InternetAddress(line.substring(0, index)),
                                         new InternetAddress(line.substring(index+1)));
                        }
                    }
                }
                catch (IOException ex) {
                    throw new MessagingException("Unable to read alias file", ex);
                }
                finally {
                    try {
                        in.close();
                    }
                    catch (IOException ex) {
                        // Close silently
                    }
                }
            }
            instance = new Aliases(aliasMap);
        }
        return instance;
    }
    
    public Address resolve(Address address) {
        Address alias = aliasMap.get(address);
        return alias != null ? alias : address;
    }
    
    public String resolve(String address) {
        try {
            return resolve(new InternetAddress(address)).toString();
        }
        catch (AddressException ex) {
            return address;
        }
    }
}
