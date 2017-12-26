package app.application.sdk_base_lib;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import app.application.sdk_base_lib.util.ScreenUtils;
import app.application.sdk_base_lib.util.bitmap.BitmapManager;

/**
 * Created by huangkangfa on 2017/11/21.
 */

public class InitService extends IntentService{
    private static final String TAG="InitService";
    private static final String ACTION_INIT_WHEN_APP_ONCREATE="app.application.sdk_base_lib.InitService";

    public InitService() {
        super(TAG);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_ONCREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_ONCREATE.equals(action)) {
                performInit();
            }
        }
    }

    /**
     * 初始化操作在这里进行
     */
    private void performInit() {
        ScreenUtils.init(getApplicationContext());
        BitmapManager.init(BaseApplication.getContext());
    }
}
