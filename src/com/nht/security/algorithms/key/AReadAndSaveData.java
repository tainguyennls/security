package com.nht.security.algorithms.key;

import java.io.*;

public abstract class AReadAndSaveData {

    public byte [] readData(String src){
        File fSource = new File(src);
        if(!fSource.exists()){
            return null;
        }
        byte [] output = null;
        try{
            InputStream is = new BufferedInputStream(new FileInputStream(fSource));
            output = new byte[is.available()];
            is.read(output);
            is.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return output;
    }

    public void saveData(byte [] data,String des){
        File fDes = new File(des);
        OutputStream os = null;
        try{
            os = new BufferedOutputStream(new FileOutputStream(fDes));
            os.write(data);
            os.flush();
            os.close();
        }
        catch (IOException io){
            io.printStackTrace();
        }
    }
}
