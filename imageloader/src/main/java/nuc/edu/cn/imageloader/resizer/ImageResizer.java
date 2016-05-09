package nuc.edu.cn.imageloader.resizer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

import nuc.edu.cn.imageloader.utils.LogUtils;

/**
 * Created by weifucheng on 2016/3/21.
 * 图片压缩的类
 */
public class ImageResizer {
    public ImageResizer(){}

    /**
     * 从资源中获得图片并压缩
     * @param res       资源类
     * @param resId     资源ID
     * @param reqWidth  需要图片的宽度
     * @param reqHeight 需要图片的高度
     * @return          压缩后的图片
     */
    public  Bitmap deodeSampledBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize=caculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 从文件修饰符中获取压缩文件，避免对文件流的二次加载
     * @param fd
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public  Bitmap deodeSampledBitmapFromFileDescriptor(FileDescriptor fd,int reqWidth,int reqHeight){
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize=caculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 从输入流中获取压缩图片，网络请求使用
     * 问题：mark的大小不知道设置为多少合适？
     * 这里不应该使用这种方法，应该先把流全部加载，然后再进行处理，注意修改
     * @param is
     * @param reqWidth
     * @param reqHeight
     * @return
     * @throws IOException
     */
    public  Bitmap deodeSampledBitmapFromStream(InputStream is,int reqWidth,int reqHeight) throws IOException {
            is.mark(1024*1024*5);
            final BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeStream(is, null, options);
            if(options.inJustDecodeBounds){
                is.reset();
                LogUtils.d("复位===========");
            }
            options.inSampleSize=caculateInSampleSize(options,reqWidth,reqHeight);
            options.inJustDecodeBounds=false;
            return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * 计算缩放率
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public  int caculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
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
