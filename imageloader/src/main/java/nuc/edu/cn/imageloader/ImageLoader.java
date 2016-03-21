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

/**
 * Created by weifucheng on 2016/3/19.
 */
public class ImageLoader {
    private ImageLoader(){}
    private ImageLoaderConfig mConfig;
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public void displayImage(final String url,final ImageView imageView){
        Bitmap bitmap=mConfig.imageCache.get(url);
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
                    return;
                }
                if(imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }
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
    public static ImageLoader getInstance(){
        return ImageLoaderHolder.sImageLoader;
    }
    private static class ImageLoaderHolder{
        private static final ImageLoader sImageLoader=new ImageLoader();
    }

    /**
     * 这个地方不使用单例模式，保持Builder的多样性,但如果一个地方想重复调用怎么办？
     * new 多个Builder显然不合适，所以提供选择性单例
     */
    public static class Builder{
        private static volatile Builder signBuilder=null;
        ImageLoaderConfig iConfig=new ImageLoaderConfig();
        public static Builder getBuilder(Context context){
            return new Builder(context);
        }
        public static Builder getsignBuilder(Context context){
            if(signBuilder==null){
                synchronized (Builder.class){
                    if(signBuilder==null){
                        signBuilder=new Builder(context);
                    }
                }
            }
            return signBuilder;
        }

        public Builder(Context context){
                iConfig.context=context;
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
