package nuc.edu.cn.imageloader_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import nuc.edu.cn.imageloader.ImageLoader;
import nuc.edu.cn.imageloader.cache.DoubleCache;

/**
 * Created by weifucheng on 2016/3/19.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mData;
    private Context mContext;
    public RecyclerAdapter(List<String> data,Context context){
        mData=data;
        mContext=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder myholder= (ViewHolder) holder;
        ImageLoader.Builder.getInstance(mContext).
                setLoadingPlaceholder(R.drawable.loading).
                setImageCache(DoubleCache.class).create().
                displayImage(mData.get(position), myholder.imageView);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public OnItemClickListener itemClickListener;
    public void setOnItemClicListener(OnItemClickListener onItemClicListener){
        this.itemClickListener=onItemClicListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public ViewHolder(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.iv_item);
            imageView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (itemClickListener!=null){
                itemClickListener.onItemClick(v,getPosition());
            }
        }
    }
}
