package nuc.edu.cn.imageloader.loader;

import android.graphics.Bitmap;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nuc.edu.cn.imageloader.request.ImageRequest;
import nuc.edu.cn.imageloader.resizer.ImageResizer;

/**
 * Created by weifucheng on 2016/3/23.
 */
public class NetLoader extends AbsLoader {
    private NetLoader(){
        notifyObservers(this.getClass().getCanonicalName());
    }
    private static NetLoader sInstance=new NetLoader();
    public static NetLoader getInstance(){
        return sInstance;
    }

    @Override
    protected Bitmap onLoadImage(ImageRequest request) {
        InputStream is=null;
        Bitmap bitmap=null;
        try {
            URL url=new URL(request.mUrl);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            is=new BufferedInputStream(conn.getInputStream());
            bitmap=ImageResizer.deodeSampledBitmapFromStream(is,request.mImageView.getWidth(),request.mImageView.getHeight());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
