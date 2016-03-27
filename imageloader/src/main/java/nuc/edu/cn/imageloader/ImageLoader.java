package nuc.edu.cn.imageloader;

import android.content.Context;
import android.widget.ImageView;

import nuc.edu.cn.imageloader.cache.ImageCache;
import nuc.edu.cn.imageloader.config.ImageLoaderConfig;
import nuc.edu.cn.imageloader.request.ImageRequest;
import nuc.edu.cn.imageloader.request.RequestQueue;

/**
 * Created by weifucheng on 2016/3/19.
 */
public class ImageLoader {
    private ImageLoader(){}

    /**
     * 图片加载配置
     */
    private ImageLoaderConfig mConfig;
    /**
     * 图片请求分发
     */
    private RequestQueue mImageQueue;
    /**
     * 判断是否已经开启处理图片请求的线程池
     */
    private Boolean mIsstart=false;

    /**
     * 显示图片
     * @param url
     * @param imageView
     */
    public void displayImage(final String url,final ImageView imageView){
        ImageRequest imageRequest=new ImageRequest(imageView,url);
        mImageQueue.addRequest(imageRequest);
    }

    /**
     * 应用图片加载的配置
     * @param imageLoaderConfig
     */
    private void applyConfig(ImageLoaderConfig imageLoaderConfig){
        this.mConfig=imageLoaderConfig;
        if(!mIsstart) start();
    }

    /**
     * 开启图片加载线程池
     */
    public void start(){
        if(mImageQueue==null){
            mImageQueue=new RequestQueue(mConfig.threadCount);
        }
        mImageQueue.start();
        mIsstart=true;
    }

    /**
     * 关闭图片加载线程池
     */
    public void stop(){
        if(mImageQueue!=null) {
            mImageQueue.stop();
            mIsstart=false;
        }
    }

    /**
     * ImageLoader的建造者
     */
    public static class Builder{
        private ImageLoaderConfig iConfig=ImageLoaderConfig.getInstance();
        private ImageLoader mLoader;
        private static Builder sBuilder;

        /**
         * 获取Builder单例对象，这里的单例不需要同步，因为只能从主线程调用
         * @param context
         * @return
         */
        public static Builder getInstance(Context context){
            if(sBuilder==null)
                sBuilder=new Builder();
            sBuilder.iConfig.context=context;
            return sBuilder;
        }
        private Builder(){
            mLoader=new ImageLoader();
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

        /**
         * 返回一个ImageLoader对象
         * @return
         */
        public ImageLoader create(){
            ImageLoader imageLoader=mLoader;
            imageLoader.applyConfig(iConfig);
            return imageLoader;
        }


    }
}
