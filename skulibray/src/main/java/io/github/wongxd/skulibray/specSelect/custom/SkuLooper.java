package io.github.wongxd.skulibray.specSelect.custom;

import android.os.Handler;
import android.os.Looper;

public class SkuLooper extends Handler {

    static SkuLooper instance = new SkuLooper(Looper.getMainLooper());

    private SkuLooper(Looper looper) {
        super(looper);
    }

    public SkuLooper getInstance() {
        return instance;
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            runnable.run();
        } else {
            instance.post(runnable);
        }
    }
}