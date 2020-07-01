package org.apache.syncope.core.spring.security;

import org.apache.syncope.common.lib.types.CipherAlgorithm;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EncryptorTest{

    private static final Encryptor encryptor = Encryptor.getInstance("secretKeyLongEnough");
    private static final String payload = "string to encode";
    private static final String encriptedPayload = "wfvb5asUCaVke2twkQwb8KQmHDhdmm/xFHoOeLO+qzM=";

    @Test
    public void smallKey() throws Exception {
        String encripted = encryptor.encode(payload, CipherAlgorithm.AES);
        assertEquals(encriptedPayload, encripted);
    }

}