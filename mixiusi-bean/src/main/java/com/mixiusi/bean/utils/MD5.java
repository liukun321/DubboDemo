package com.mixiusi.bean.utils;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5 {
	
    public static String md5(String string) {
        if (string == null || string.trim().length() < 1) {
            return null;
        }
        try {
            return getMD5(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static String getMD5(byte[] source) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            StringBuffer result = new StringBuffer();
            for (byte b : md5.digest(source)) {
                result.append(Integer.toHexString((b & 0xf0) >>> 4));
                result.append(Integer.toHexString(b & 0x0f));
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', 
    	'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static String md5sum(String filename) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try{
            fis = new FileInputStream(filename);
            md5 = MessageDigest.getInstance("MD5");
            while((numRead=fis.read(buffer)) > 0) {
                md5.update(buffer,0,numRead);
            }
            fis.close();
            return toHexString(md5.digest());   
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }
    
    /*
    public static String getFileMD5(File file) {
    	if (!file.isFile()) {
    		return null;
    	}
    	
    	MessageDigest digest = null;
    	FileInputStream in = null;
    	byte buffer[] = new byte[1024];
    	int len;
    	try {
    		digest = MessageDigest.getInstance("MD5");
    		in = new FileInputStream(file);
    		while ((len = in.read(buffer, 0, 1024)) != -1) {
    			digest.update(buffer, 0, len);
    		}
    		in.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    	BigInteger bigInt = new BigInteger(1, digest.digest());
    	return bigInt.toString(16);
	}*/
}
