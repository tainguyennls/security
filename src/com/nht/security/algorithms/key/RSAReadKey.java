package com.nht.security.algorithms.key;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAReadKey extends AReadAndSaveData {

    public RSAReadKey(){

    }

    /***
     *
     * @param src
     * @return public_key from file
     */
    public PublicKey readPublicKey(String src){
        byte [] in = readData(src);
        if(null == in){
            return null;
        }

        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(in);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch (InvalidKeySpecException e){
            e.printStackTrace();
        }
        return publicKey;
    }

    /***
     *
     * @param src
     * @return private_key from file
     */
    public PrivateKey readPrivateKey(String src){
        byte [] in = readData(src);
        if(null == in){
            return null;
        }

        PrivateKey privateKey = null;
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(in);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch (InvalidKeySpecException e){
            e.printStackTrace();
        }
        return privateKey;
    }
}
