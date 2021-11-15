package main.java.com.revature.app.models;

import java.util.Arrays;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Password {

    private static int SALT_LENGTH_BYTES = 64;
    private static int HASH_LENGTH_BYTES = 64;
    private static int HASH_ITERATIONS = 1024;
    private static String HASH_ALGORITHM = "PBKDF2WithHmacSHA512";

    private static int PASSWORD_PLAIN_MIN_LENGTH = 8;
    private static int PASSWORD_PLAIN_MAX_LENGTH = 32;

    private static byte[] NO_BYTES = {};
    private static byte[] NULL_BYTES = null;

    private byte[] saltBytes = Password.NULL_BYTES;
    private byte[] hashBytes = Password.NULL_BYTES;

    public Password(String passwordText) {
        Password(passwordText.toCharArray();
    }

    public Password(char[] passwordChars) {
        try {
            Password.verifyPlainPasswordCriteria(passwordChars);
            byte[] newSalt = Password.generateSalt();
            byte[] newHash = Password.generateHash(passwordChars, newSalt);
            if (newHash != Password.NO_BYTES) {
                Password(newSalt, newHash);
            } else {
                Password(Password.NO_BYTES, Password.NO_BYTES);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            Password(Password.NO_BYTES, Password.NO_BYTES);
        } finally {
            Array.fill(passwordChars, Character.MIN_VALUE);
        }
    }

    public Password(byte[] newSalt, byte[] newHash) {
        if (this.hashBytes == Password.NULL_BYTES && this.saltBytes == Password.NULL_BYTES) {
            if (Password.isValid(newSalt, newHash)) {
                this.hashBytes = newHash;
                this.saltBytes = newSalt;
            }
        }
    }

    public boolean validatePassword(String passwordText) {
        return this.validatePassword(passwordText.toCharArray());
    }

    public boolean validatePassword(char[] passwordText) {
        try {
            Password.verifyPasswordCriteria(passwordText);
            if (this.isValid()) {
                byte[] currentSalt = this.getSaltBytes();
                byte[] currentHash = this.getHashBytes();
                byte[] comparisonHash = Password.generateHash(currentSalt, passwordText);
                return Array.equals(currentHash, comparisonHash);
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        } finally {
            Arrays.fill(passwordText, Character.MIN_VALUE);
        }
    }

    public String getHash() {
        byte[] currentHash = this.getHashBytes();
        switch (currentHash) {
        case NULL_BYTES:
            return "NULL_BYTES";
            break;
        case NO_BYTES:
            return "NO_BYTES";
            break;
        default:
            return Base64.getEncoder().encodeToString(currentHash);
        }
    }

    public String getSalt() {
        byte[] currentSalt = this.getSaltBytes();
        switch (currentSalt) {
        case NULL_BYTES:
            return "NULL_BYTES";
            break;
        case NO_BYTES:
            return "NO_BYTES";
            break;
        default:
            return Base64.getEncoder().encodeToString(currentSalt);
        }
    }

    public boolean isNotEmpty() {
        return Password.isNotEmpty(this.getSaltBytes(), this.getHashBytes());
    }

    private boolean isNotEmpty(byte[]... values) {
        return !values.contains(Password.NO_BYTES)
    }

    public boolean isNotNull() {
        return Password.isNotNull(this.getSaltBytes(), this.getHashBytes());
    }

    private static boolean isNotNull(byte[]... values) {
        return !values.contains(Password.NULL_BYTES);
    }

    /**public String toString() {
    *    return String.format("%s:%s", this.getSalt(), this.getHash());
    * }
    */

    private byte[] getHashBytes() {
        if (this.hashBytes.length != Password.HASH_LENGTH_BYTES) {
            return new byte[0];
        }
        return this.hashBytes;
    }

    private byte[] getSaltBytes() {
        if (this.saltBytes.length != Password.SALT_LENGTH_BYTES) {
            return new byte[0];
        }
        return this.saltBytes;
    }

    private static void verifyPasswordCriteria(char[] passwordText) throws IllegalArgumentException {
        if (passwordText.length < Password.PASSWORD_PLAIN_MIN_LENGTH) {
            throw new IllegalArgumentException("Exception in verifyPasswordCriteria(): Minimum password length is "
                    + Password.PASSWORD_PLAIN_MIN_LENGTH);
        } else if (passwordText.length > Password.PASSWORD_PLAIN_MAX_LENGTH) {
            throw new IllegalArgumentException("Exception in verifyPasswordCriteria(): Maximum password length is "
                    + Password.PASSWORD_PLAIN_MAX_LENGTH);
        }
    }

    private static byte[] generateHash(char[] passwordText, byte[] saltBytes) {
        int hashLengthBits = (Password.HASH_LENGTH_BYTES * 8);
        PBEKeySpec keySpec = new PBEKeySpec(passwordText, saltBytes, Password.HASH_ITERATIONS, hashLengthBits);
        try {
            SecretKeyFactory secret = new SecretKeyFactory.getInstance(Password.HASH_ALGORITHM);
            return secret.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.err.println("Exception encountered in generateHash()");
            return Password.NO_BYTES;
        } finally {
            keySpec.clearPassword();
        }
    }

    private static byte[] generateSalt() {
        SecureRandom generator = new SecureRandom();
        byte[] saltBytes = new saltBytes[Password.SALT_LENGTH_BYTES];
        generator.nextBytes(saltBytes);
        return saltBytes;
    }
}
