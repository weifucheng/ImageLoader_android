package nuc.edu.cn.imageloader.cache;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import nuc.edu.cn.imageloader.utils.LogUtils;

/**
 * Created by weifucheng on 2016/3/20.
 * 为了保证每个缓存模式，只有一个，使用通过CacheManager
 * 来存取Cache
 */
public class CacheManager {
    //不需要volatile，因为指令重排序不会在2条语句直接发生。
    private static  Map<Class<? extends ImageCache>,ImageCache> CacheMap=new HashMap<>();
    static {
        CacheMap.put(null,CacheManager.getCache(MemoryCache.class));
    }
    public static ImageCache getCache(Class<? extends ImageCache> key) {
        if (!CacheMap.containsKey(key)) {
            synchronized (CacheManager.class) {
                if (!CacheMap.containsKey(key)) {
                    try {
                        LogUtils.d(key.getName());
                        Constructor c=key.getDeclaredConstructor();
                        c.setAccessible(true);
                        ImageCache imageCache= (ImageCache) c.newInstance();
                        CacheMap.put(key, imageCache);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return CacheMap.get(key);
    }

}
