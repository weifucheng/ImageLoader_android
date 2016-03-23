package nuc.edu.cn.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import nuc.edu.cn.imageloader.request.ImageRequest;

/**
 * Created by weifucheng on 2016/3/20.
 * 该模块，还存在耦合问题，get操作中不应该负责处理图片的大小问题！
 * 但如果不去处理，又会造成大图加载，影响效率
 */
public interface ImageCache {
    void put(String url,Bitmap bmp);
    Bitmap get(ImageRequest imageRequest);
    void init(Context context);
    boolean isInit();
}
