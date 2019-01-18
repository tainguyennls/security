package com.nht.security.algorithms.key;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class ESReadKey extends AReadAndSaveData {

    public ESReadKey(){

    }

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
}
