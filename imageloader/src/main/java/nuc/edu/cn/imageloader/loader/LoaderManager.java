package nuc.edu.cn.imageloader.loader;

import java.util.HashMap;

/**
 * Created by weifucheng on 2016/3/23.
 * 现在如果自定义一个Loader需要在其构造方法中调用
 * LoaderManager.getInstance().registerLoader(协议名,this)
 * 如何能实现自动注册？
 */
public class LoaderManager {
        private HashMap<String,Loader> mLoaderMap=new HashMap<>();
        private LoaderManager(){
            mLoaderMap.put("HTTP",NetLoader.getInstance());
        }
        public static LoaderManager getInstance(){
            return LoaderManagerHolder.sInstance;
        }
        private static class LoaderManagerHolder{
            private static LoaderManager sInstance=new LoaderManager();
        }
        public void registerLoader(String protocol,Loader loader){
            mLoaderMap.put(protocol,loader);
        }
        public Loader getLoader(String protocol){
            return mLoaderMap.get(protocol);
        }

}
