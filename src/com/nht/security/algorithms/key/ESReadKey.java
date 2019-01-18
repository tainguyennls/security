package com.nht.security.algorithms.key;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ESReadKey extends AReadAndSaveData {

    public ESReadKey(){

    }

    /**
     *
     * @param src
     * @return
     */

    public PrivateKey readPrivateKey(String src){

        byte [] in = readData(src);
        if(null == in){
            return null;
        }

        PrivateKey privateKey = null;
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(in);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA","SUN");
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return privateKey;
    }

    /**
     *
     * @param src
     * @return
     */

    public PublicKey readPublicKey(String src){
        byte [] in = readData(src);
        if(null == in){
            return null;
        }

        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(in);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA","SUN");
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return publicKey;
    }
}
