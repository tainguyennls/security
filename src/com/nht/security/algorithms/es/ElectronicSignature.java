package com.nht.security.algorithms.es;

import com.nht.security.algorithms.io.IO;
import com.nht.security.algorithms.key.RSAReadKey;

import java.io.*;
import java.security.*;

public class ElectronicSignature {

    private IO io;
    private RSAReadKey rsaReadKey;
    private PublicKey pk;
    private PrivateKey pv;

    public ElectronicSignature(){
        io = new IO();
        rsaReadKey = new RSAReadKey();
    }

    /**
     *
     * @param src
     * @param sig
     * @param key
     * @return
     */

    public boolean verifySignature(File src, String sig, PublicKey key){

        try{

            byte [] tmp = rsaReadKey.readData(sig);

            Signature signature = Signature.getInstance("SHA1withDSA","SUN");
            signature.initVerify(key);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
            byte [] buffer = new byte[1024];
            int length;

            while (bis.available()!=0){
                length = bis.read(buffer);
                signature.update(buffer,0, length);
            }

            bis.close();
            boolean verify = signature.verify(tmp);
            return verify;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     *
     * @param src
     * @param des
     * @param k
     * @return
     */

    public boolean signSignature(File src , String des, PrivateKey k){

        try{


            Signature sig = Signature.getInstance("SHA1withDSA","SUN");
            sig.initSign(k);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
            byte [] bufferSize = new byte[1024];
            int tmp;

            while ((tmp = bis.read(bufferSize)) >= 0){
                sig.update(bufferSize,0,tmp);
            }
            bis.close();

            byte [] signed = sig.sign();

            // save  sig
            io.saveData(signed,des);

            return true;

        }
        catch(Exception e){
            return false;
        }
    }

    public boolean gen(){
        try{
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA","SUN");
            SecureRandom sd = SecureRandom.getInstance("SHA1PRNG","SUN");
            kpg.initialize(1024,sd);
            KeyPair kp = kpg.genKeyPair();
            pv = kp.getPrivate();
            pk = kp.getPublic();

            io.saveData(pv.getEncoded(),"es_private_key.k");
            io.saveData(pk.getEncoded(),"es_public_key.k");
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
