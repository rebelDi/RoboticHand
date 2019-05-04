package Additions;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordSecurityTest {

    @Test
    public void hashPassword() {
        String a = PasswordSecurity.hashPassword("aaa");
        Assert.assertEquals(PasswordSecurity.hashPassword("aaa"), a);
    }
}