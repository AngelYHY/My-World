package freestar.myapp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freestar on 2016/12/13.
 * 用来记录 开启的 activity 可在任意 activity中 销毁有记录的 activity
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    //添加 activity
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //移除 activity
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //销毁添加的 activity
    public static void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

}
