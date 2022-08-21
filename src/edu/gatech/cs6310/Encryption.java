package edu.gatech.cs6310;

import java.util.Base64;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	private String secret;

	public Encryption(String secret){
		this.secret = secret;
	}

	private byte[] getSecret(){
		byte[] secretByte = Base64.getDecoder().decode(this.secret);
		return Arrays.copyOf(secretByte, 16);
	}
	public String Encrypt(String pInput) {

		try {

			String Input = pInput;

			SecretKeySpec aesKey = new SecretKeySpec(getSecret(), "AES");
			Cipher cipher = Cipher.getInstance("AES");

			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(Input.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(encrypted);

		} catch (Exception e) {
			return pInput;
		}
	}

	public String Decrypt(String pInput) {

		try {

			String Input = pInput;

			SecretKeySpec aesKey = new SecretKeySpec(getSecret(), "AES");
			Cipher cipher = Cipher.getInstance("AES");

			cipher.init(Cipher.DECRYPT_MODE, aesKey);
//			encrypted = DatatypeConverter.parseBase64Binary(encryptedtext);
			String decrypted = new String(cipher.doFinal(
					Base64.getDecoder().decode(Input)
			));
			//System.err.println("decrypted: " + decrypted);
			return decrypted;

		} catch (Exception e) {
			return pInput;
		}
	}

	public static void main(String[] ag){
		Encryption test = new Encryption("IDUDSIDUDSIDUDSIDUDS");
		String a1 = test.Encrypt("aapple2");
		System.out.println(a1);
		String a2 = test.Encrypt("ccherry4");
		System.out.println(a2);
		String a3 = test.Encrypt("kroger1");
		System.out.println(a3);
		String a4 = test.Encrypt("fly3");
		System.out.println(a4);
		String a5 = test.Encrypt("admin");
		System.out.println(a5);
		Encryption test2 = new Encryption("QWERTQWERTQWERTQWERT");
		String b = test2.Decrypt(a1);
		String c = "111";
	}
}
