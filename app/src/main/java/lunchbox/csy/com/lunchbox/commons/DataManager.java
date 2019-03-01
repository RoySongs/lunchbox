package lunchbox.csy.com.lunchbox.commons;

import android.content.Context;
import android.content.SharedPreferences;

public class DataManager {
    private final static String APP_PREFERENCES = "Preferences";
    private SharedPreferences pref = null;

    // 생성자
    public DataManager(Context context) {
        this.pref = context.getSharedPreferences(APP_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    //===getter & setter===
    public boolean setName(String name) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("name", name);
        return edit.commit();
    }

    public String getName() {
        return pref.getString("name", "");
    }

    //===프레퍼런스 값 1개를 삭제 ---
    public boolean deleteKey(String key) {
        SharedPreferences.Editor edit = pref.edit();
        edit.remove(key);
        return edit.commit();
    }

    //===로그아웃(프레퍼런스의 모든 값 다 삭제)---
    public boolean clearData() {
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        return edit.commit();
    }
}
