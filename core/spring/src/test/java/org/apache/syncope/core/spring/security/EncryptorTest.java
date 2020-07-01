package org.apache.syncope.core.spring.security;

import org.apache.syncope.common.lib.types.CipherAlgorithm;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EncryptorTest{

    private static final Encryptor encryptor = Encryptor.getInstance("secretKeyLongEnough");
    private static final String payload = "string to encode";
    private static final String encryptedPayload = "wfvb5asUCaVke2twkQwb8KQmHDhdmm/xFHoOeLO+qzM=";

    @Test
    public void testEncode() {
        try {
            String encrypted = encryptor.encode(payload, CipherAlgorithm.AES);
            assertEquals("bad encoding", encryptedPayload, encrypted);
        } catch (Exception e){
            Assert.fail("Should not throw Exception " + e);
        }
    }

    @Test
    public void testEncodeNull() {
        try {
            String encrypted = encryptor.encode(null, CipherAlgorithm.AES);
            assertNull("bad encoding", encrypted);
        } catch (Exception e){
            Assert.fail("Should not throw Exception " + e);
        }
    }

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
    
    @Test
    public void testDecode(){
        try {
            String decrypted = encryptor.decode(encryptedPayload, CipherAlgorithm.AES);
            assertEquals("bad decoding", payload, decrypted);
        } catch (Exception e){
            Assert.fail("Should not throw Exception " + e);
        }
    }

    @Test
    public void testDecodeNull(){
        try {
            String decrypted = encryptor.decode(null, CipherAlgorithm.AES);
            assertNull("bad decoding", decrypted);
        } catch (Exception e){
            Assert.fail("Should not throw Exception " + e);
        }
    }

    @Test
    public void testVerify(){
        assertTrue("false negative", encryptor.verify(payload, CipherAlgorithm.AES, encryptedPayload));
        assertFalse("false positive", encryptor.verify(payload, CipherAlgorithm.AES, payload));
    }

}