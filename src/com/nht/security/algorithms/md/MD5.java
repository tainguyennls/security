/**
 * 
 */
package com.nht.security.algorithms.md;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author NguyenHuuTai
 *
 */
public class MD5 {

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

	public String hashText(String text) {
		byte[] out = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(text.getBytes(StandardCharsets.UTF_8));
			out = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return byteTo2Hex(out);
	}

	public String hashFile(File src) {
		byte[] out = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			InputStream is = new BufferedInputStream(new FileInputStream(src));
			byte[] tmp = new byte[1024];
			int byteReads;
			while ((byteReads = is.read(tmp)) != -1) {
				messageDigest.update(tmp, 0, byteReads);
			}
			out = messageDigest.digest();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return byteTo2Hex(out);
	}
}
