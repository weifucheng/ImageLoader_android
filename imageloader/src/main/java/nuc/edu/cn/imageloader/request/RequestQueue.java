package nuc.edu.cn.imageloader.request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by weifucheng on 2016/3/23.
 * 图片加载请求队列
 */
public final class RequestQueue {
    /**
     * 生产者-消费者队列
     */
    private BlockingQueue<ImageRequest> mRequestQueue=new PriorityBlockingQueue<>();
    private AtomicInteger mSerialNumGenerator=new AtomicInteger(0);
    public static int DEFAULT_CORE_NUMS=Runtime.getRuntime().availableProcessors()+1;
    private int mDispatherNums=DEFAULT_CORE_NUMS;

}
