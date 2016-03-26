package nuc.edu.cn.imageloader.request;

import java.util.concurrent.BlockingQueue;

import nuc.edu.cn.imageloader.loader.Loader;
import nuc.edu.cn.imageloader.loader.LoaderManager;

/**
 * Created by weifucheng on 2016/3/23.
 */
public class RequestDispatcher extends Thread {
    private BlockingQueue<ImageRequest> mBlckingQueue;

    public RequestDispatcher(BlockingQueue<ImageRequest> blockingQueue){
        this.mBlckingQueue=blockingQueue;
    }


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
        return uri.substring(0,uri.indexOf("://"));
    }
}
