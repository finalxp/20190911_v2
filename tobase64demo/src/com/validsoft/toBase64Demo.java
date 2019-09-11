package com.validsoft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
public class toBase64Demo {
    /**
    * <p>将文件转成base64 字符串</p>
    * @param path 文件路径
    * @return
    * @throws Exception
    */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        
        FileInputStream inputFile = new FileInputStream(file);
        System.out.println(inputFile);
        byte[] buffer = new byte[(int)file.length()];       
        inputFile.read(buffer);
        //System.out.println(buffer);
        inputFile.close();
        //System.out.println(new BASE64Encoder().encode(buffer));
        return new BASE64Encoder().encode(buffer);
    }
    /**
    * <p>将base64字符解码保存文件</p>
    * @param base64Code
    * @param targetPath
    * @throws Exception
    */
    public static void decoderBase64File(String base64Code,String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
    /**
    * <p>将base64字符保存文本文件</p>
    * @param base64Code
    * @param targetPath
    * @throws Exception
    */
    public static void toFile(String base64Code,String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
    public static void main(String[] args) {
        try {
            String base64Code =encodeBase64File("W:\\company\\BoCOM_交行总部_卡中心_松涛路80号\\zoo\\audio_20190226145519.wav");
            //System.out.println(base64Code);
            decoderBase64File(base64Code, "W:\\company\\BoCOM_交行总部_卡中心_松涛路80号\\zoo\\audio_20190226145519.png");
            toFile(base64Code, "W:\\company\\BoCOM_交行总部_卡中心_松涛路80号\\zoo\\audio_20190226145519.ddd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}