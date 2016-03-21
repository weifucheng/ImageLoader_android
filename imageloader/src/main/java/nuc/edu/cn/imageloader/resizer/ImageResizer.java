package nuc.edu.cn.imageloader.resizer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * Created by weifucheng on 2016/3/21.
 * 图片压缩的类
 */
public class ImageResizer {
    private static final String TAG="ImageResizer";
    public ImageResizer(){}
    public Bitmap deodeSampledBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res,resId,options);
        options.inSampleSize=caculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeResource(res,resId,options);
    }
    public Bitmap deodeSampledBitmapFromFileDescriptor(FileDescriptor fd,int reqWidth,int reqHeight){
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);
        options.inSampleSize=caculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }
    public int caculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        if(reqWidth==0||reqHeight==0){
            return  1;
        }
        final int height=options.outHeight;
        final int width=options.outWidth;
        int inSampleSize=1;
        if(height>reqHeight||width>reqWidth){
            final int halfHeight=height/2;
            final int halfWidth=width/2;
            while ((halfHeight/inSampleSize)>=reqHeight&&(halfWidth/inSampleSize)>reqWidth){
                inSampleSize*=2;
            }
        }
        return inSampleSize;


    }

}
