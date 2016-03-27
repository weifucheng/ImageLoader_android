package nuc.edu.cn.imageloader.request;

import java.util.concurrent.BlockingQueue;

import nuc.edu.cn.imageloader.loader.Loader;
import nuc.edu.cn.imageloader.loader.LoaderManager;
import nuc.edu.cn.imageloader.utils.LogUtils;

/**
 * 请求的处理线程
 * Created by weifucheng on 2016/3/23.
 */
public class RequestDispatcher extends Thread {
    private BlockingQueue<ImageRequest> mBlckingQueue;

    public RequestDispatcher(BlockingQueue<ImageRequest> blockingQueue){
        this.mBlckingQueue=blockingQueue;
    }

    /**
     * 死循环，不断处理请求，直到中断
     */
    @Override
    public void run() {
            while (!this.isInterrupted()){
                try {
                    final ImageRequest request=mBlckingQueue.take();
                    Loader imageLoader= LoaderManager.getInstance().getLoader(protocol(request.mUrl));
                    imageLoader.loadImage(request);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
    protected String protocol(String uri){
        LogUtils.d(uri.substring(0,uri.indexOf("://")).toUpperCase());
        return uri.substring(0,uri.indexOf("://")).toUpperCase();
    }
}
