package main.java.com.revature.app.models;

import java.util.Arrays;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// TODO Extract static logic into new PasswordService
// TODO Encode hash information into output and input string
// TODO Massively simplify logic (Optional type?)

public final class Password {

    private static final int SALT_LENGTH_BYTES = 64;
    private static final int HASH_LENGTH_BYTES = 64;
    private static final int HASH_ITERATIONS = 1024;
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA512";

    private static final int PASSWORD_PLAIN_MIN_LENGTH = 8;
    private static final int PASSWORD_PLAIN_MAX_LENGTH = 32;

    private static final byte[] NO_BYTES = {};
    private static final byte[] NULL_BYTES = null;

    private byte[] saltBytes = Password.NULL_BYTES;
    private byte[] hashBytes = Password.NULL_BYTES;

    public Password(String passwordText) {
        if ()
        Password(passwordText.toCharArray());
    }

    public Password(char[] passwordText) {
        try {
            Password.verifyPlainPasswordCriteria(passwordText);
            byte[] newSalt = Password.generateSalt();
            byte[] newHash = Password.generateHash(passwordText, newSalt);
            if (newHash != Password.NO_BYTES) {
                Password(newSalt, newHash);
            } else {
                Password(Password.NO_BYTES, Password.NO_BYTES);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            Password(Password.NO_BYTES, Password.NO_BYTES);
        } finally {
            Array.fill(passwordText, Character.MIN_VALUE);
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

    public final boolean validatePassword(String passwordText) {
        return this.validatePassword(passwordText.toCharArray());
    }

    public final boolean validatePassword(char[] passwordText) {
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

    public final String getHash() {
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

    public final String getSalt() {
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

    public final boolean isValid() {
        return Password.isValid(this.getSaltBytes(), this.getHashBytes());
    }

    public static final boolean isValid(byte[]... values) {
        for (byte[] v : values) {
            if (v == Password.NO_BYTES || v == Password.NULL_BYTES) {
                return false;
            }
        }
        return true;

        return !Arrays.asList(this.hashBytes, this.saltBytes).contains(Password.NO_BYTES, Password.NULL_BYTES);
    }

    public final String toString() {
        return (this.getSalt() + ":" + this.getHash());
    }

    private final byte[] getHashBytes() {
        if (this.hashBytes.length != Password.HASH_LENGTH_BYTES) {
            return Password.NO_BYTES;
        }
        return this.hashBytes;
    }

    private final byte[] getSaltBytes() {
        if (this.saltBytes.length != Password.Salt_LENGTH_BYTES) {
            return Password.NO_BYTES;
        }
        return this.saltBytes;
    }

    private static final void verifyPasswordCriteria(char[] passwordText) throws IllegalArgumentException {
        if (passwordText.length < Password.PASSWORD_PLAIN_MIN_LENGTH) {
            throw new IllegalArgumentException("Exception in verifyPasswordCriteria(): Minimum password length is "
                    + Password.PASSWORD_PLAIN_MIN_LENGTH);
        } else if (passwordText.length > Password.PASSWORD_PLAIN_MAX_LENGTH) {
            throw new IllegalArgumentException("Exception in verifyPasswordCriteria(): Maximum password length is "
                    + Password.PASSWORD_PLAIN_MAX_LENGTH);
        }
    }

    private static final byte[] generateHash(char[] passwordText, byte[] saltBytes) {
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

    private static final byte[] generateSalt() {
        SecureRandom generator = new SecureRandom();
        byte[] saltBytes = new saltBytes[Password.SALT_LENGTH];
        generator.nextBytes(saltBytes);
        return saltBytes;
    }
}
