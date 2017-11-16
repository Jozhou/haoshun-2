package com.corelibrary.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.corelibrary.utils.LogcatUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {
    public static final String TAG = ForegroundCallbacks.class.getName();

    public interface Listener {
        public void onBecameForeground(Activity activity);
        public void onBecameBackground();
    }
    private static ForegroundCallbacks instance;
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private volatile int count = 0;
    public static ForegroundCallbacks init(Application application){
        if (instance == null) {
            instance = new ForegroundCallbacks();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }
    public static ForegroundCallbacks get(Application application){
        if (instance == null) {
            init(application);
        }
        return instance;
    }
    public static ForegroundCallbacks get(Context ctx){
        if (instance == null) {
            Context appCtx = ctx.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application)appCtx);
            }
            throw new IllegalStateException(
                    "Foreground is not initialised and " +
                            "cannot obtain the Application object");
        }
        return instance;
    }
    public static ForegroundCallbacks get(){
        if (instance == null) {
            throw new IllegalStateException(
                    "Foreground is not initialised - invoke " +
                            "at least once with parameterised init/get");
        }
        return instance;
    }
    public boolean isForeground(){
        return count > 0;
    }
    public boolean isBackground(){
        return count <1;
    }
    public void addListener(Listener listener){
        listeners.add(listener);
    }
    public void removeListener(Listener listener){
        listeners.remove(listener);
    }
    @Override
    public void onActivityResumed(Activity activity) {}
    @Override
    public void onActivityPaused(Activity activity) {}
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
    @Override
    public void onActivityStarted(Activity activity) {
        boolean wasBackground = isBackground();
        if (count < 0) {//即时纠正count的值
            count = 0;
        }
        count++;
        if (wasBackground){
            LogcatUtils.d (TAG, "went foreground - " + count);
            for (Listener l : listeners) {
                try {
                    l.onBecameForeground(activity);
                } catch (Exception exc) {
                    LogcatUtils.d (TAG, "Listener threw exception!:"+exc.toString());
                }
            }
        } else {
            LogcatUtils.d (TAG, "still foreground - " + count);
        }
    }
    @Override
    public void onActivityStopped(Activity activity) {
        boolean wasForeground = isForeground();
        count --;
        if (wasForeground && isBackground()) {
            LogcatUtils.d (TAG, "went background - " + count);
            for (Listener l : listeners) {
                try {
                    l.onBecameBackground();
                } catch (Exception exc) {
                    LogcatUtils.d (TAG, "Listener threw exception!:"+exc.toString());
                }
            }
        } else {
            LogcatUtils.d (TAG, "still foreground - " + count);
        }
    }
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
    @Override
    public void onActivityDestroyed(Activity activity) {}
}