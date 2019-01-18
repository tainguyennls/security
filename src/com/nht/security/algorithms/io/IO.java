package com.nht.security.algorithms.io;

import java.io.*;

public class IO {

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
