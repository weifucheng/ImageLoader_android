package nuc.edu.cn.imageloader.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5生成类
 * Created by weifucheng on 2016/3/21.
 */
public class MD5Helper {
    /**
     * 生成一个String的MD5串
     * @param url
     * @return
     */
    public static String StringtoMD5(String url){
        String cacheKey=null;
        try {
            final MessageDigest mDigest=MessageDigest.getInstance("MD5");
            if ( mDigest == null ) {
                return String.valueOf(url.hashCode());
            }
            mDigest.update(url.getBytes());
            cacheKey=bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cacheKey;
    }

    /**
     * 将一个byte数组转换成一个16进制的字符串
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes){
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
