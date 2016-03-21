package nuc.edu.cn.imageloader.request;

import android.widget.ImageView;

import nuc.edu.cn.imageloader.utils.MD5Helper;

/**
 * Created by weifucheng on 2016/3/21.
 */
public class ImageRequest implements Comparable<ImageRequest>{
    public ImageView mImageView=null;
    public String mUrl="";
    public String mUrlMD5="";
    public int serialNum=0;
    public ImageRequest(ImageView imageView,String url){
        mImageView=imageView;
        mUrl=url;
        mUrlMD5= MD5Helper.StringtoMD5(url);
        mImageView.setTag(url);
    }
    @Override
    public int compareTo(ImageRequest another) {
        return this.serialNum-another.serialNum;
    }
}
