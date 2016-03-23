package nuc.edu.cn.imageloader.request;

import android.widget.ImageView;

/**
 * Created by weifucheng on 2016/3/21.
 */
public class ImageRequest implements Comparable<ImageRequest>{
    public ImageView mImageView=null;
    public String mUrl="";
    public int serialNum=0;
    public ImageRequest(ImageView imageView,String url){
        mImageView=imageView;
        mUrl=url;
        //为什么需要绑定，ListView以及RecyView的View重用机制
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
