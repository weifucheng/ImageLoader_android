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
    private int mDispatcherNums=DEFAULT_CORE_NUMS;
    private RequestDispatcher[] mDispatchers=null;
    protected RequestQueue(){
        this(DEFAULT_CORE_NUMS);
    }
    protected RequestQueue(int coreNums) {
        mDispatcherNums = coreNums;
    }
    private final void startDispatchers(){
        mDispatchers=new RequestDispatcher[mDispatcherNums];
        for(int i=0;i<mDispatcherNums;i++){
            mDispatchers[i]=new RequestDispatcher(mRequestQueue);
            mDispatchers[i].start();
        }
    }
    public void stop(){
        if(mDispatchers!=null&&mDispatchers.length>0){
            for (int i=0;i<mDispatchers.length;i++){
                mDispatchers[i].interrupt();
            }
        }
    }
    public void start(){
        stop();
        startDispatchers();
    }
    public void clear() {
        mRequestQueue.clear();
    }
    public void addRequest(ImageRequest request){
        if(!mRequestQueue.contains(request)){
            request.serialNum=this.generateSerialNumber();
            mRequestQueue.add(request);
        }
    }

    public BlockingQueue<ImageRequest> getAllRequests() {
        return mRequestQueue;
    }
    private int generateSerialNumber(){
        return mSerialNumGenerator.incrementAndGet();
    }
}
