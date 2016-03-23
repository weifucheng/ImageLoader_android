package nuc.edu.cn.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nuc.edu.cn.imageloader.cache.ImageCache;
import nuc.edu.cn.imageloader.config.ImageLoaderConfig;
import nuc.edu.cn.imageloader.request.ImageRequest;

/**
 * Created by weifucheng on 2016/3/19.
 */
public class ImageLoader {
    private ImageLoader(){}
    private ImageLoaderConfig mConfig;
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public void displayImage(final String url,final ImageView imageView){
        ImageRequest imageRequest=new ImageRequest(imageView,url);
        Bitmap bitmap=mConfig.imageCache.get(imageRequest);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        imageView.setImageBitmap(BitmapFactory.decodeResource(mConfig.context.getResources(),mConfig.displayConfig.loadingResId));
        subitmLoadRequest(url, imageView);
    }
    private void subitmLoadRequest(final String url, final ImageView imageView) {
        mExecutorService.submit(new Runnable() {

            @Override
            public void run() {
                Bitmap bitmap=downloadImage(url);
                if (bitmap==null){
                    Log.d("weifucheng",url+"下载为空");
                    return;
                }
                Log.d("weifucheng",url+"下载完成");
                if(imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }
                Log.d("weifucheng",url+"加入缓存");
                mConfig.imageCache.put(url, bitmap);
            }
        });
    }

    public Bitmap downloadImage(String imageUrl){
        Log.d("download", imageUrl + "come from download");
        Bitmap bitmap=null;
        try {
            URL url=new URL(imageUrl);
            final HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            bitmap= BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public void applyConfig(ImageLoaderConfig imageLoaderConfig){
        this.mConfig=imageLoaderConfig;
    }
    protected static ImageLoader getInstance(){
        return ImageLoaderHolder.sImageLoader;
    }
    private static class ImageLoaderHolder{
        private static final ImageLoader sImageLoader=new ImageLoader();
    }


    public static class Builder{
        private static volatile Builder signBuilder=null;
        ImageLoaderConfig iConfig=ImageLoaderConfig.getInstance();
        public static Builder getInstance(Context context){
            if(signBuilder==null){
                synchronized (Builder.class){
                    if(signBuilder==null){
                        signBuilder=new Builder();
                    }
                }
            }
            signBuilder.iConfig.clear(context);
            return signBuilder;
        }

        private Builder(){

        }
        public ImageLoader.Builder setImageCache(Class<? extends ImageCache> imageCache){
                iConfig.setImageCache(imageCache);
                return this;
        }
        public Builder setThreadCount(int threadCount) {
            iConfig.setThreadCount(threadCount);
            return this;
        }

        public Builder setLoadingPlaceholder(int ResId){
           iConfig.setLoadingPlaceholder(ResId);
            return this;
        }
        public Builder setNotFoundPlaceholder(int ResId){
            iConfig.setNotFoundPlaceholder(ResId);
            return this;
        }
        public ImageLoader create(){
            ImageLoader imageLoader=ImageLoader.getInstance();
            imageLoader.applyConfig(iConfig);
            return imageLoader;
        }


    }
}
