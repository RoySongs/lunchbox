package lunchbox.csy.com.lunchbox.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lunchbox.csy.com.lunchbox.R;
import lunchbox.csy.com.lunchbox.activity.DetailRestaurantActivity;
import lunchbox.csy.com.lunchbox.commons.Const;

public class ProfileFragment extends Fragment {
    private static String TAG = "HomeFragment";

    public static ProfileFragment newInstance() {
        // TODO Parameters
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    //<가람작성>
    private void moveDetailRestaurant() {
        Intent i = new Intent(getActivity(), DetailRestaurantActivity.class);
        i.putExtra(Const.REST_ID, Const.TEMP_REST_VALUE);
        i.putExtra(Const.REST_NAME, "김밥천국 서초점");
        i.putExtra("distance", 550);
        startActivity(i);
    }
    //</가람작성>

}
