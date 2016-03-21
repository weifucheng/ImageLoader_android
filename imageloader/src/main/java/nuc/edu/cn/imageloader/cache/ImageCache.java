package nuc.edu.cn.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by weifucheng on 2016/3/20.
 */
public interface ImageCache {
    void put(String url,Bitmap bmp);
    Bitmap get(String url);
    void init(Context context);
    boolean isInit();
}
