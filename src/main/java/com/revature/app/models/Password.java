package com.revature.app.models;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

public class Password {

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
        this(passwordText.toCharArray());
    }

    public Password(char[] passwordChars) {
        byte[] newSalt = Password.generateSalt();
        byte[] newHash = Password.generateHash(passwordChars, newSalt);
        Arrays.fill(passwordChars, Character.MIN_VALUE);
        if (newHash != Password.NO_BYTES) {
            this.initializeNewCredentialsOnce(newSalt, newHash);
        } else {
            this.initializeNewCredentialsOnce(Password.NO_BYTES, Password.NO_BYTES);
        }
    }

    public Password(byte[] newSalt, byte[] newHash) {
        this.initializeNewCredentialsOnce(newSalt, newHash);
    }

    public boolean validatePassword(String passwordText) {
        return this.validatePassword(passwordText.toCharArray());
    }

    public boolean validatePassword(char[] passwordChars) {
        try {
            if (Password.isValidPasswordText(passwordChars)) {
                return false;
            }
            if (this.isValid()) {
                byte[] currentSalt = this.getSaltBytes();
                byte[] currentHash = this.getHashBytes();
                byte[] comparisonHash = Password.generateHash(passwordChars, currentSalt);
                return Arrays.equals(currentHash, comparisonHash);
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        } finally {
            Arrays.fill(passwordChars, Character.MIN_VALUE);
        }
    }

    public String getHash() {
        byte[] currentHash = this.getHashBytes();
        if (currentHash == Password.NULL_BYTES) {
            return "NULL_BYTES";
        }
        if (currentHash == Password.NO_BYTES) {
            return "NO_BYTES";
        }
        return Base64.getEncoder().encodeToString(currentHash);
    }

    public String getSalt() {
        byte[] currentSalt = this.getSaltBytes();
        if (currentSalt == Password.NULL_BYTES) {
            return "NULL_BYTES";
        }
        if (currentSalt == Password.NO_BYTES) {
            return "NO_BYTES";
        }
        return Base64.getEncoder().encodeToString(currentSalt);
    }
    private void initializeNewCredentialsOnce(byte[] newSalt, byte[] newHash) {
        if ((this.getSaltBytes() == Password.NULL_BYTES && this.getHashBytes() == Password.NULL_BYTES) &&
                Password.isValid(newSalt, newHash)){
            this.saltBytes = newSalt;
            this.hashBytes = newHash;
        } else {
            this.saltBytes = Password.NO_BYTES;
            this.hashBytes = Password.NO_BYTES;
        }
    }

    public boolean isValid() {
        return Password.isValid(this.getSaltBytes(), this.getHashBytes());
    }

    private static boolean isValid(byte[]... values) {
        for (byte[] v : values) {
            if (v == Password.NULL_BYTES || v == Password.NO_BYTES) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return String.format("%s:%s", this.getSalt(), this.getHash());
    }

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

    public static boolean isValidPassword(Password credentials) {
        return Password.isValid(credentials.getSaltBytes(), credentials.getHashBytes());
    }

    public static boolean isValidPasswordText(String passwordText) {
        return Password.isValidPasswordText(passwordText.toCharArray());
    }

    public static boolean isValidPasswordText(char[] passwordText) {
        if (passwordText.length < Password.PASSWORD_PLAIN_MIN_LENGTH) {
            return false;
        }
        return passwordText.length <= Password.PASSWORD_PLAIN_MAX_LENGTH;
    }

    private static byte[] generateHash(char[] passwordText, byte[] saltBytes) {
        int hashLengthBits = (Password.HASH_LENGTH_BYTES * 8);
        PBEKeySpec keySpec = new PBEKeySpec(passwordText, saltBytes, Password.HASH_ITERATIONS, hashLengthBits);
        try {
            SecretKeyFactory secret = SecretKeyFactory.getInstance(Password.HASH_ALGORITHM);
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
        byte[] saltBytes = new byte[Password.SALT_LENGTH_BYTES];
        generator.nextBytes(saltBytes);
        return saltBytes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Password credentials = (Password) obj;
        return Objects.equals(this.getSaltBytes(), credentials.getSaltBytes()) && Objects.equals(this.getHashBytes(), credentials.getHashBytes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSaltBytes(), getHashBytes());
    }
}
