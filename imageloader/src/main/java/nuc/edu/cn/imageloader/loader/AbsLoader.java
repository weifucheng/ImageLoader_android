package nuc.edu.cn.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import nuc.edu.cn.imageloader.cache.ImageCache;
import nuc.edu.cn.imageloader.config.DisplayConfig;
import nuc.edu.cn.imageloader.config.ImageLoaderConfig;
import nuc.edu.cn.imageloader.request.ImageRequest;
import nuc.edu.cn.imageloader.utils.LogUtils;

/**
 * Created by weifucheng on 2016/3/23.
 * Loader模板
 */
public abstract class AbsLoader implements Loader{
    ImageCache mImageCache;
    public final void loadImage(ImageRequest request) {
            mImageCache= ImageLoaderConfig.getInstance().imageCache;
            Bitmap resultbitmap=mImageCache.get(request);
            if(resultbitmap==null){
                showLoading(request);
                resultbitmap=onLoadImage(request);
                LogUtils.d("下载完成==================");
                cacheImage(request,resultbitmap);
            }
            updateUI(request, resultbitmap);

    }
    protected void updateUI(final ImageRequest request, final Bitmap resultbitmap) {
           final ImageView imageView=request.mImageView;
            if(imageView==null)return;
            imageView.post(new Runnable() {
                @Override
                public void run() {
                   if(request!=null&&request.isNoChangeImageView()){
                       imageView.setImageBitmap(resultbitmap);
                   }
                    if(request==null&&hasFaildPlaceholder(ImageLoaderConfig.getInstance().displayConfig)){
                        imageView.setImageResource(ImageLoaderConfig.getInstance().displayConfig.failedResId);
                    }

                }
            });

    }
    private void cacheImage(ImageRequest request, Bitmap resultbitmap) {
        if(mImageCache!=null&&resultbitmap!=null){
            synchronized (mImageCache){
                mImageCache.put(request.mUrl,resultbitmap);
            }
        }
    }

    protected abstract Bitmap onLoadImage(ImageRequest request);

    protected void showLoading(ImageRequest request) {
        final ImageView imageview=request.mImageView;
        if(request.isNoChangeImageView()&&hasLoadingPlaceholder(ImageLoaderConfig.getInstance().displayConfig)){
            imageview.post(new Runnable() {
                @Override
                public void run() {
                    imageview.setImageResource(ImageLoaderConfig.getInstance().displayConfig.loadingResId);
                }
            });
        }
    }
    private boolean hasLoadingPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.loadingResId > 0;
    }

    private boolean hasFaildPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.failedResId > 0;
    }
}
