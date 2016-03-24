package nuc.edu.cn.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import nuc.edu.cn.imageloader.request.ImageRequest;

/**
 * Created by weifucheng on 2016/3/20.
 */
public class DoubleCache implements ImageCache {
    MemoryCache mMemoryCache=null;
    DiskCache mDiskCache=null;
    public boolean isInit=false;
    private DoubleCache(){}
    @Override
    public void init(Context context) {
        mMemoryCache= (MemoryCache) CacheManager.getCache(MemoryCache.class);
        mDiskCache= (DiskCache) CacheManager.getCache(DiskCache.class);
        if(!mMemoryCache.isInit()){
            mMemoryCache.init(context);
        }
        if(!mDiskCache.isInit()){
            mDiskCache.init(context);
        }
        isInit=true;
    }

    @Override
    public boolean isInit() {
        return isInit;
    }

    @Override
    public void put(String url, Bitmap bmp) {
        mMemoryCache.put(url,bmp);
        mDiskCache.put(url,bmp);
    }

    @Override
    public Bitmap get(ImageRequest imageRequest) {
        Bitmap bitmap=mMemoryCache.get(imageRequest);
        if(bitmap==null){
            bitmap=mDiskCache.get(imageRequest);
        }
        return bitmap;
    }
}
