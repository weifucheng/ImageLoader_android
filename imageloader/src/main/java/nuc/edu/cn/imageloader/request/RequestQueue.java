package nuc.edu.cn.imageloader.request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import nuc.edu.cn.imageloader.utils.LogUtils;

/**
 * Created by weifucheng on 2016/3/23.
 * 图片加载请求分发类
 */
public final class RequestQueue {
    /**
     * 请求队列
     */
    private BlockingQueue<ImageRequest> mRequestQueue=new PriorityBlockingQueue<>();
    /**
     * 序号生成器，为每个请求生成一个序号,确定加载顺序
     */
    private AtomicInteger mSerialNumGenerator=new AtomicInteger(0);
    /**
     * 默认处理请求的线程数量
     */
    public static int DEFAULT_CORE_NUMS=Runtime.getRuntime().availableProcessors()+1;
    /**
     * 处理请求的线程数量
     */
    private int mDispatcherNums=DEFAULT_CORE_NUMS;
    private RequestDispatcher[] mDispatchers=null;
    public RequestQueue(){
        this(DEFAULT_CORE_NUMS);
    }
    public RequestQueue(int coreNums) {
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

    /**
     * 清空请求队列
     */
    public void clear() {
        mRequestQueue.clear();
    }

    /**
     * 给请求队列增加一个请求
     * @param request
     */
    public void addRequest(ImageRequest request){
        if(!mRequestQueue.contains(request)){
            request.serialNum=this.generateSerialNumber();
            mRequestQueue.add(request);
            LogUtils.d(request.mUrl);
        }
    }

    public BlockingQueue<ImageRequest> getAllRequests() {
        return mRequestQueue;
    }
    private int generateSerialNumber(){
        return mSerialNumGenerator.incrementAndGet();
    }
}
