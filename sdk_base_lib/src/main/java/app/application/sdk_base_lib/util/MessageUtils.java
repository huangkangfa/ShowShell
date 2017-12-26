package app.application.sdk_base_lib.util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by huangkangfa on 2016/11/16 0016.
 */
public class MessageUtils {
    public static void sendMessage(Handler handler,int what,Object obj){
        Message msg=handler.obtainMessage();
        msg.what=what;
        msg.obj=obj;
        handler.sendMessage(msg);
    }

    public static void sendMessage(Handler handler,int what){
        Message msg=handler.obtainMessage();
        msg.what=what;
        handler.sendMessage(msg);
    }
}
