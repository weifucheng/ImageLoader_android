package nuc.edu.cn.imageloader.loader;

import java.util.HashMap;

/**
 * Created by weifucheng on 2016/3/23.
 * Loader的管理类
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

    /**
     * 自定义的Loader，需要在这里进行注册
     * @param protocol 协议，注意全部大写 eg:HTTP
     * @param loader    自定义的Loader
     */
        public void registerLoader(String protocol,Loader loader){
            mLoaderMap.put(protocol,loader);
        }
        public Loader getLoader(String protocol){
            if (mLoaderMap.containsKey(protocol))
            return mLoaderMap.get(protocol);
            return null;
        }

}
