package nuc.edu.cn.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by weifucheng on 2016/3/19.
 * 这么多的AOP,是否需要使用代理呢？
 */
public class MemoryCache implements ImageCache {
    private static final String TAG="MemoryCache";
    LruCache<String,Bitmap> mImageCache;
    public boolean isInit=false;
    @Override
    public void init(Context context){
        final int maxMemory= (int) (Runtime.getRuntime().maxMemory()/1024);
        final int CacheSize=maxMemory/4;
        mImageCache=new LruCache<String,Bitmap>(CacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
        isInit=true;
    }

    @Override
    public boolean isInit() {
        return isInit;
    }

    @Override
    public void put(String url,Bitmap bitmap){
        mImageCache.put(url, bitmap);
    }
    @Override
    public Bitmap get(String url){
        Log.d(TAG,url+"come from memory");
        return mImageCache.get(url);
    }

}
