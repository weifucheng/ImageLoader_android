package nuc.edu.cn.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import nuc.edu.cn.imageloader.disklrucache.DiskLruCache;
import nuc.edu.cn.imageloader.utils.CloseUtils;

/**
 * Created by weifucheng on 2016/3/20.
 */
public class DiskCache implements ImageCache {
    private static final String TAG="DiskCache";
    private static String cacheDir=null;
    private DiskLruCache mDiskLruCache;
    private static final int MB=1024*1024;
    public boolean isInit=false;
    private static final String IMAGE_DISK_CACHE = "bitmap";

    @Override
    public void init(Context context) {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            cacheDir=context.getExternalCacheDir().getPath();
        }else {
            cacheDir=context.getCacheDir().getPath();
        }
        File file=new File(cacheDir+File.separator+IMAGE_DISK_CACHE);
        if(!file.exists()) {
            file.mkdirs();
        }
        try {
            mDiskLruCache=DiskLruCache.open(file,1,1,50*MB);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isInit=true;
    }

    @Override
    public boolean isInit() {
        return isInit;
    }


    @Override
    public void put(String url, Bitmap bmp) {
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(cacheDir+"/"+url);
            bmp.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            Log.d(TAG,url+"pic diskcache success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  finally {
            CloseUtils.closeQuietly(fileOutputStream);
        }
    }

    @Override
    public Bitmap get(String url) {
        Log.d(TAG, cacheDir+url+"come from Disk");
        return BitmapFactory.decodeFile(cacheDir+"/"+url);
    }

}
