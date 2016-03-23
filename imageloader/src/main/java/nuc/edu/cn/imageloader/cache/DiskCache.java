package nuc.edu.cn.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import nuc.edu.cn.imageloader.disklrucache.DiskLruCache;
import nuc.edu.cn.imageloader.request.ImageRequest;
import nuc.edu.cn.imageloader.resizer.ImageResizer;
import nuc.edu.cn.imageloader.utils.CloseUtils;
import nuc.edu.cn.imageloader.utils.MD5Helper;

/**
 * Created by weifucheng on 2016/3/20.
 */
public class DiskCache implements ImageCache {
    private static final String TAG="DiskCache";
    private static String cacheDir=null;
    private DiskLruCache mDiskLruCache;
    private static final int MB=1024*1024;
    public boolean isInit=false;
    private ImageResizer mImageResiz=new ImageResizer();
    private static final String IMAGE_DISK_CACHE = "bitmap";

    @Override
    public void init(Context context) {
        if(isInit) return;
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
            mDiskLruCache=DiskLruCache.open(file,1,1,200*MB);
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
    public  void put(String url, Bitmap bmp) {
        DiskLruCache.Editor editor=null;
        try {
            DiskLruCache.Snapshot snapshot=mDiskLruCache.get(MD5Helper.StringtoMD5(url));
            if(snapshot!=null) return;
            editor=mDiskLruCache.edit(MD5Helper.StringtoMD5(url));
            OutputStream outputStream=editor.newOutputStream(0);
            if(writeBitmapToDisk(bmp,outputStream)){
                editor.commit();
                Log.d(TAG,url+"have cache disk");
            }else {
                editor.abort();
            }
            mDiskLruCache.flush();
            CloseUtils.closeQuietly(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Boolean writeBitmapToDisk(Bitmap bitmap,OutputStream outputStream){
        BufferedOutputStream bos=new BufferedOutputStream(outputStream,8*1024);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        boolean result=true;
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            result=false;
        }
            return result;
    }


    @Override
    public synchronized Bitmap get(ImageRequest imageRequest) {
        Bitmap bitmap=null;
        try {
            DiskLruCache.Snapshot snapshot=mDiskLruCache.get(MD5Helper.StringtoMD5(imageRequest.mUrl));
            if(snapshot!=null){
                FileInputStream fileInputStream= (FileInputStream) snapshot.getInputStream(0);
                FileDescriptor fileDescriptor=fileInputStream.getFD();
                bitmap=mImageResiz.deodeSampledBitmapFromFileDescriptor(fileDescriptor,
                        imageRequest.mImageView.getWidth(),imageRequest.mImageView.getHeight());
                if(bitmap!=null){
                    CacheManager.getCache(MemoryCache.class).put(imageRequest.mUrl,bitmap);
                    Log.d(TAG,"have cache DISK");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
