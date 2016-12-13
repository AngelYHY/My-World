package freestar.myapp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freestar on 2016/12/13.
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

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
