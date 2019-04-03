package lunchbox.csy.com.lunchbox.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;

import lunchbox.csy.com.lunchbox.R;
import lunchbox.csy.com.lunchbox.activity.DetailRestaurantActivity;
import lunchbox.csy.com.lunchbox.alarm.AlarmReceiver;
import lunchbox.csy.com.lunchbox.commons.Const;

//190403_가람만듬
public class LocationFragment extends Fragment {
    public static LocationFragment newInstance() {
        LocationFragment locationFragment = new LocationFragment();
        return locationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        Button bt01 = (Button)view.findViewById(R.id.bt01);
        bt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DetailRestaurantActivity.class);
                i.putExtra(Const.REST_ID, Const.TEMP_REST_VALUE);
                i.putExtra(Const.REST_NAME, "아웃백 서초점");
                i.putExtra("distance", 500);
                startActivity(i);
            }
        });

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void moveDetailRestaurant() {
        Intent i = new Intent(getActivity(), DetailRestaurantActivity.class);
        i.putExtra(Const.REST_ID, Const.TEMP_REST_VALUE);
        i.putExtra(Const.REST_NAME, "김밥천국 서초점");
        i.putExtra("distance", 550);
        startActivity(i);
    }
}
