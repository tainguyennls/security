package com.nht.security.algorithms.asymmetric;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

/**
 * @author NguyenHuuTai
 * @create 08/11/2018
 */
public class RSA {

	public RSA() {

	}

	public byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] output = cipher.doFinal(data);
		return output;
	}

	public byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] output = cipher.doFinal(Base64.getDecoder().decode(data));
		return output;
	}
}
