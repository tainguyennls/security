package com.nht.security.algorithms.sha;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 {

	public SHA1() {

	}

	public String hashText(String text) {
		byte[] out = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			out = messageDigest.digest(text.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return byteTo2Hex(out);
	}

	public String hashFile(File source) {
		byte[] out = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			InputStream is = new BufferedInputStream(new FileInputStream(source));
			byte[] input = new byte[1024];
			int byteReads;

			while ((byteReads = is.read(input)) != -1) {
				messageDigest.update(input, 0, byteReads);
			}
			out = messageDigest.digest();
			is.close();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteTo2Hex(out);
	}

	private String byteTo2Hex(byte[] input) {
		StringBuilder stringBuilder = new StringBuilder();
		for (byte b : input) {
			String hex = Integer.toHexString(b & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			stringBuilder.append(hex);
		}
		return stringBuilder.toString();
	}

}
