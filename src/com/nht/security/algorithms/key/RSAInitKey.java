package com.nht.security.algorithms.key;

import java.security.*;

public class RSAInitKey extends AReadAndSaveData {

    public RSAInitKey(){

    }

    public void initKey(int bits){

        try{
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(bits,secureRandom);
            KeyPair keyPair  = keyPairGenerator.genKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // save public_key
            saveData(publicKey.getEncoded(),"public_key.k");

            // save private_key
            saveData(privateKey.getEncoded(),"private_key.k");
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}
