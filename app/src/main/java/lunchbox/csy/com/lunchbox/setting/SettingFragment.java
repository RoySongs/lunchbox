package lunchbox.csy.com.lunchbox.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import lunchbox.csy.com.lunchbox.R;

public class SettingFragment extends Fragment implements View.OnClickListener{
    private static String TAG = "HomeFragment";

    View viewNoticeLayout, viewServiceLayout, viewPersonalLayout, viewLocationLayout, viewRequestLayout;

    public static SettingFragment newInstance() {
        // TODO Parameters
        SettingFragment settingFragment = new SettingFragment();
        return settingFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        viewNoticeLayout = view.findViewById(R.id.setting_sub_notice);
        viewServiceLayout = view.findViewById(R.id.setting_sub_service);
        viewPersonalLayout = view.findViewById(R.id.setting_sub_personal);
        viewLocationLayout = view.findViewById(R.id.setting_sub_gpsInfo);
        viewRequestLayout = view.findViewById(R.id.setting_sub_requestion);

        viewNoticeLayout.setOnClickListener(this);
        viewServiceLayout.setOnClickListener(this);
        viewPersonalLayout.setOnClickListener(this);
        viewLocationLayout.setOnClickListener(this);
        viewRequestLayout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.setting_sub_notice :
                intent = new Intent(getActivity(),SettingFragmentNotice.class);
                startActivity(intent);break;
            case R.id.setting_sub_service :
                intent = new Intent(getActivity(),SettingFragmentService.class);
                startActivity(intent);break;
            case R.id.setting_sub_personal :
                intent = new Intent(getActivity(),SettingFragmentPersonal.class);
                startActivity(intent);break;
            case R.id.setting_sub_gpsInfo :
                intent = new Intent(getActivity(),SettingFragmentGps.class);
                startActivity(intent);break;
            case R.id.setting_sub_requestion :
                intent = new Intent(Intent.ACTION_SEND);
                try {
                intent.setType("message/rfc822");
                intent.setPackage("com.google.android.gm");
                if(intent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(intent);
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"sgsn2015@email.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                intent.putExtra(Intent.EXTRA_TEXT   , "body of email");
                startActivity(Intent.createChooser(intent, "Send mail..."));
                } catch (Exception e) {
                    e.printStackTrace();
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sgsn2020@gmail.com"});
                    startActivity(Intent.createChooser(intent, "Send Email"));
                }

        }

    }
}
