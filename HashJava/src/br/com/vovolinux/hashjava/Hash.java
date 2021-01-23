/*
 *  License   : MIT - Copyright 2021 Viniciusalopes (Vovolinux) <suporte@vovolinux.com.br>
 *  Author    : Vinicius Lopes
 *  Date      : 23/01/2021 14:10:25 
 *  Project   : HashJava
 *  Version   : 0.0.1
 *  Purpose   :
 *  Changelog : 2020-01-00 v.0.0.2 (Vinicius Lopes)
 */
package br.com.vovolinux.hashjava;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Vovolinux<br>
 * SOURCE: https://codare.aurelio.net/2007/02/02/java-gerando-codigos-hash-md5-sha/
 */
public class Hash {

    private static String getHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int up = ((bytes[i] >> 4) & 0xf) << 4;
            int down = bytes[i] & 0xf;
            if (up == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(up | down));
        }
        return s.toString();
    }

    private static byte[] getHash(String phrase, String algorithm) throws Exception {
        try {
            if (algorithm.trim().length() == 0) {
                throw new Exception("Enter the algorithm!");
            }

            String[] algorithms = new String[]{"MD5", "SHA-1", "SHA-256"};
            List<String> list = Arrays.asList(algorithms);
            if (!list.contains(algorithm)) {
                throw new Exception("Invalid algorithm!");
            }

            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(phrase.getBytes());
            return md.digest();

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String encrypt(String text, String algorithm) throws Exception {
        try {
            return getHexa(getHash(text, algorithm));
        } catch (Exception e) {
            throw new Exception("Encrypt error: " + getErrorMessage(e));
        }
    }

    public static boolean areIdentical(String strHash, String text, String algorithm) throws Exception {
        return strHash.equals(encrypt(text, algorithm));
    }

    /**
     * Gets a text about the reason for the Exception.
     *
     * @param e Exception to get the message text.
     * @return
     */
    private static String getErrorMessage(Exception e) {
        return (e.getMessage() != null) ? e.getMessage()
                : ((e.getCause() != null) ? e.getCause().getMessage()
                : ((e.getLocalizedMessage() != null) ? e.getLocalizedMessage() : e.getStackTrace().toString()));
    }

}
