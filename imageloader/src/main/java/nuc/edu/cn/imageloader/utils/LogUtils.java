package nuc.edu.cn.imageloader.utils;

import android.util.Log;

/**
 * 打印Log的工具类，TAG确定调用者的为类名
 * Created by weifucheng on 2016/3/26.
 */
public class LogUtils {
        private static final int VERBOSE=1;
        private static final int DEBUG=2;
        private static final int INFO=3;
        private static final int WARN=4;
        private static final int ERROR=5;
        public static final int NOTHING=6;
        public static final int LEVEL=VERBOSE;
        public static void v(String msg){
            if(LEVEL<VERBOSE){
                Log.v(getClassName(),msg);
            }
        }
        public static void d(String msg){
            if(LEVEL<DEBUG){
                Log.d(getClassName(),msg);
            }
        }
        public static void i(String msg){
            if(LEVEL<INFO){
                Log.i(getClassName(),msg);
            }
        }
        public static void w(String msg){
            if(LEVEL<WARN){
                Log.w(getClassName(),msg);
            }
        }
        public static void e(String msg){
            if(LEVEL<ERROR){
                Log.e(getClassName(),msg);
            }
        }
        public static String getClassName(){
            Throwable throwable=new Throwable();
            return throwable.getStackTrace()[2].getClassName();
        }
}
