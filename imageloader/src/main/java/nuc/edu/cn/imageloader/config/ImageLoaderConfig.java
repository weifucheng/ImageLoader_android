package nuc.edu.cn.imageloader.config;

import android.content.Context;

import nuc.edu.cn.imageloader.cache.CacheManager;
import nuc.edu.cn.imageloader.cache.ImageCache;
import nuc.edu.cn.imageloader.cache.MemoryCache;

/**
 * Created by weifucheng on 2016/3/20.
 * 图片加载配置类
 */
public class ImageLoaderConfig {
    public ImageCache imageCache;
    public DisplayConfig displayConfig;
    public int threadCount;
    public Context context;
    private ImageLoaderConfig(){
        setImageCache(MemoryCache.class);
        displayConfig=new DisplayConfig();
        threadCount=Runtime.getRuntime().availableProcessors()+1;
    }
    public void setImageCache(Class<? extends ImageCache> imageCache) {
        ImageCache IC= CacheManager.getCache(imageCache);
        if(!IC.isInit()) IC.init(context);
        this.imageCache=IC;
    }
    private static class ImageLoaderConfigHolder{
        private static final ImageLoaderConfig sImageLoaderConfig=new ImageLoaderConfig();
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
    public void clear(Context context){
        setImageCache(MemoryCache.class);
        displayConfig=new DisplayConfig();
        threadCount=Runtime.getRuntime().availableProcessors()+1;
        this.context=context;
    }
    public static ImageLoaderConfig getInstance(){
        return ImageLoaderConfigHolder.sImageLoaderConfig;
    }

}
