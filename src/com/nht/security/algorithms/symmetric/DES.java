package com.nht.security.algorithms.symmetric;

import java.io.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author NguyenHuuTai
 * @create 08/11/2018
 *
 */
public class DES {

	private byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };

	public DES() {

	}

	public void encrypt(File src, File des, String pad) throws Exception {

		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init(56);
		SecretKey secretKey = keyGenerator.generateKey();
		saveKey(secretKey.getEncoded(), "secret_key.sk");

		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance(pad);
		if("DES/CBC/PKCS5Padding".equals(pad)){
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, aps);
		}
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		InputStream is = new BufferedInputStream(new FileInputStream(src));
		OutputStream os = new CipherOutputStream(new FileOutputStream(des), cipher);

		writeData(is, os);
	}

	public void decryptInputFile(File src, File des, String pad) throws Exception {

		SecretKey secretKey = readKey("secret_key.sk");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance(pad);
		if("DES/CBC/PKCS5Padding".equals(pad)) {
			cipher.init(Cipher.DECRYPT_MODE, secretKey,aps);
		}
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		

		InputStream is = new CipherInputStream(new FileInputStream(src), cipher);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(des));

		writeData(is, os);

	}

	public void saveKey(byte[] date, String des) {
		try {
			OutputStream osKey = new BufferedOutputStream(new FileOutputStream(new File(des)));
			osKey.write(date);
			osKey.flush();
			osKey.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SecretKey readKey(String src) {
		
		KeySpec keySpec;
		SecretKey secretKey = null;
		SecretKeyFactory secretKeyFactory;
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(new File(src)));
			byte[] out = new byte[is.available()];
			is.read(out);
			is.close();
			keySpec = new DESKeySpec(out);
			secretKeyFactory = SecretKeyFactory.getInstance("DES");
			secretKey = secretKeyFactory.generateSecret(keySpec);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secretKey;
	}

	private void writeData(InputStream input, OutputStream output) throws IOException {
		byte[] writeBuffer = new byte[1024];
		int readBytes = 0;
		while ((readBytes = input.read(writeBuffer)) >= 0) {
			output.write(writeBuffer, 0, readBytes);
		}
		output.close();
		input.close();
	}
}
