package nuc.edu.cn.imageloader.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by weifucheng on 2016/3/21.
 */
public class MD5Helper {
    public static String StringtoMD5(String url){
        String cacheKey=null;
        try {
            final MessageDigest mDigest=MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey=String.valueOf(url.hashCode());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cacheKey;
    }
    public String bytesToHexString(byte[] bytes){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            //bytes[i]&0xFF是为了将其高24为清零
            String hex=Integer.toHexString(0xFF&bytes[i]);
            if (hex.length()==1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
