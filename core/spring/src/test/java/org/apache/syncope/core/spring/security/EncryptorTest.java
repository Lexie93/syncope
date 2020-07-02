package org.apache.syncope.core.spring.security;

import org.apache.syncope.common.lib.types.CipherAlgorithm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class EncryptorTest{

    private static final Encryptor encryptor = Encryptor.getInstance("secretKeyLongEnough");

    @RunWith(Parameterized.class)
    public static class parameterizedTests{

        private final String message;
        private final String encryptedMessage;

        @Parameterized.Parameters
        public static Collection<Object[]> getParameters(){
            return Arrays.asList(new Object[][]{
                    {"string to encode", "wfvb5asUCaVke2twkQwb8KQmHDhdmm/xFHoOeLO+qzM="},
                    {null, null}
            });
        }

        public parameterizedTests(String message, String encryptedMessage){
            this.message = message;
            this.encryptedMessage = encryptedMessage;
        }

        @Test
        public void testEncode() {
            try {
                String encrypted = encryptor.encode(message, CipherAlgorithm.AES);
                assertEquals("bad encoding", encryptedMessage, encrypted);
            } catch (Exception e){
                Assert.fail("Should not throw Exception " + e);
            }
        }

        @Test
        public void testDecode(){
            try {
                String decrypted = encryptor.decode(encryptedMessage, CipherAlgorithm.AES);
                assertEquals("bad decoding", message, decrypted);
            } catch (Exception e){
                Assert.fail("Should not throw Exception " + e);
            }
        }

        @Test
        public void testVerify(){
            if (message != null) {
                assertTrue("false negative", encryptor.verify(message, CipherAlgorithm.AES, encryptedMessage));
            } else {
                //makes pitest throw an exception after mutation, but coverage is good
                assertFalse("false positive", encryptor.verify(null, CipherAlgorithm.AES, encryptedMessage));
            }
        }
    }

    public static class notParameterizedTests{

        //dubious but maybe still ok
        @Test
        public void testEncodeDecodeVoid() {
            try {
                String encrypted = encryptor.encode("", CipherAlgorithm.AES);
                String decrypted = encryptor.decode(encrypted, CipherAlgorithm.AES);
                assertEquals("bad encoding", "", decrypted);
            } catch (Exception e){
                Assert.fail("Should not throw Exception " + e);
            }
        }

        //added to increase line and condition coverages
        @Test
        public void testShortKeyAndNullCipherAlgorithm(){
            Encryptor crypto = Encryptor.getInstance("shortKey");
            try{
                String encrypted = crypto.encode("message", null);
                crypto.verify("message", null, encrypted);
            } catch (Exception e){
                Assert.fail("Should not throw Exception " + e);
            }
        }

    }

}