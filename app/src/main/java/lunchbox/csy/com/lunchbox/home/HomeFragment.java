package lunchbox.csy.com.lunchbox.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import lunchbox.csy.com.lunchbox.R;
import lunchbox.csy.com.lunchbox.roulette.FoodRoulette;

public class HomeFragment extends Fragment {
    private static String TAG = "HomeFragment";

    public static HomeFragment newInstance() {
        // TODO Parameters
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView food_image = view.findViewById(R.id.food_image);
        ImageView restaurant_image = view.findViewById(R.id.restaurant_image);

        food_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FoodRoulette.class);
                startActivity(intent);
            }
        });



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }
}
