package nuc.edu.cn.imageloader.config;

import android.content.Context;

import nuc.edu.cn.imageloader.cache.CacheManager;
import nuc.edu.cn.imageloader.cache.ImageCache;
import nuc.edu.cn.imageloader.cache.MemoryCache;

/**
 * Created by weifucheng on 2016/3/20.
 * 难点：context应该什么时候传，在什么地方传，但init的时候就需要context
 */
public class ImageLoaderConfig {
    private static final String TAG="ImageLoaderConfig";
    public ImageCache imageCache;
    public DisplayConfig displayConfig;
    public int threadCount;
    public Context context;
    public ImageLoaderConfig(){
        setImageCache(MemoryCache.class);
        displayConfig=new DisplayConfig();
        threadCount=Runtime.getRuntime().availableProcessors()+1;
    }
    public void setImageCache(Class<? extends ImageCache> imageCache) {
        ImageCache IC= CacheManager.getCache(imageCache);
        if(!IC.isInit()) IC.init(context);
        this.imageCache=IC;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = Math.max(threadCount,1);
    }

    public void setLoadingPlaceholder(int ResId){
        displayConfig.loadingResId=ResId;
    }
    public void setNotFoundPlaceholder(int ResId){
        displayConfig.failedResId=ResId;
    }
}
