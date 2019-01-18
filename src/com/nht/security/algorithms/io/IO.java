package com.nht.security.algorithms.io;

import com.nht.security.algorithms.key.AReadAndSaveData;

import java.io.*;

public class IO extends AReadAndSaveData {

    public IO(){

    }

    public boolean save(File src, File des){
        int data ;
        try{
            InputStream is = new FileInputStream(src);
            OutputStream os = new FileOutputStream(des);
            data = is.read();
            while (data != -1){
                os.write(data);
                data = is.read();
            }
            os.close();
            is.close();
        }
        catch(IOException e){
            return false;
        }
        return true;
    }


}
