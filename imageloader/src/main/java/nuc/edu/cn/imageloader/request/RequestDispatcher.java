package nuc.edu.cn.imageloader.request;

import java.util.concurrent.BlockingQueue;

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

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}
