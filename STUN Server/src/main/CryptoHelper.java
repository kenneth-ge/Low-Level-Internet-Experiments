package main;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {

	public static void main(String[] args) throws Exception {

		CryptoHelper crypto = new CryptoHelper("password", "salt");
		
		String plaintext = "This is a good secret.";
		System.out.println(plaintext);

		String ciphertext = crypto.encrypt(plaintext);
		System.out.println(ciphertext);

		String decrypted = crypto.decrypt(ciphertext);
		System.out.println(decrypted);

	}

	public String encrypt(String plaintext) throws Exception {
		return encrypt(generateIV(), plaintext);
	}

	public String encrypt(byte[] iv, String plaintext) throws Exception {

		byte[] unencrypted = plaintext.getBytes();
		byte[] encrypted = encrypt(iv, unencrypted);

		StringBuilder ciphertext = new StringBuilder();

		ciphertext.append(new String(Base64.getEncoder().encode(iv)));
		ciphertext.append(":");
		ciphertext.append(new String(Base64.getEncoder().encode(encrypted)));

		return ciphertext.toString();

	}

	public String decrypt(String ciphertext) throws Exception {
		String[] parts = ciphertext.split(":");
		byte[] iv = Base64.getDecoder().decode(parts[0]);
		byte[] encrypted = Base64.getDecoder().decode(parts[1]);
		byte[] decrypted = decrypt(iv, encrypted);
		return new String(decrypted);
	}

	private Key key;

	public CryptoHelper(Key key) {
		this.key = key;
	}

	public CryptoHelper(String password, String salt) throws Exception {
		this(generateSymmetricKey(password, salt));
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public static byte[] generateIV() {
		SecureRandom random = new SecureRandom();
		byte[] iv = new byte[16];
		random.nextBytes(iv);
		return iv;
	}

	public static Key generateSymmetricKey(String password, String salt) throws Exception {
		/* Derive the key, given password and salt. */
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		
		return secret;
	}

	public byte[] encrypt(byte[] iv, byte[] plaintext) throws Exception {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm() + "/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
		return cipher.doFinal(plaintext);
	}

	public byte[] decrypt(byte[] iv, byte[] ciphertext) throws Exception {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm() + "/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
		return cipher.doFinal(ciphertext);
	}

}