package nuc.edu.cn.imageloader.request;

import android.widget.ImageView;

/**
 * Created by weifucheng on 2016/3/21.
 * 图片请求封装类，实现Comparable接口是因为需要请求队列需要优先级
 */
public class ImageRequest implements Comparable<ImageRequest>{
    /**
     * 请求的ImageView
     */
    public ImageView mImageView=null;
    /**
     * 请求的URL
     */
    public String mUrl="";
    /**
     * 请求序列号
     */
    public int serialNum=0;
    public ImageRequest(ImageView imageView,String url){
        mImageView=imageView;
        mUrl=url;
        //需要绑定，ListView以及RecyView的View重用机制
        mImageView.setTag(url);
    }
    @Override
    public int compareTo(ImageRequest another) {
        return this.serialNum-another.serialNum;
    }
    public boolean isNoChangeImageView(){
        return mImageView.getTag().equals(mUrl);
    }
}
