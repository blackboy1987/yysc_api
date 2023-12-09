package com.bootx.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class ImageUtils {

    public static String imageToBase64(String imgPath) {
        InputStream in = null;
        byte[] data;
        String encode = null;
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            encode = Base64.encodeBase64String(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in!=null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encode;
    }

    public static Boolean base64ToImage(String imgData,File file){
        if (StringUtils.isBlank(imgData)) {
            return false;
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {

            byte[] bfile = Base64.decodeBase64(imgData);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}