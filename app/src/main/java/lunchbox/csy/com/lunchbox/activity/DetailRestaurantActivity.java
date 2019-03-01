package lunchbox.csy.com.lunchbox.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import lunchbox.csy.com.lunchbox.R;
import lunchbox.csy.com.lunchbox.base.AbstBaseActivity;
import lunchbox.csy.com.lunchbox.commons.Common;

//가람담당_식당상세화면
public class DetailRestaurantActivity extends AbstBaseActivity {

    TextView tvMenuName;

    @Override
    protected void onCreateChild() {
        setContentView(R.layout.activity_detail_restaurant);
        Common.showLogD("test log debug");
        tvMenuName=(TextView)findViewById(R.id.tvMenuName);

        //음식점 목록 중 하나를 누르면 목록의 데이터를 intent에 담아 이 화면으로 이동_이 화면에서 해당 intent 값 적용
//        getIntentData();
    }

    @Override
    protected void onDestoryChild() {

    }

    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.llBack:
                showToastShort("test toast");
                break;
                //길찾기
            case R.id.btFindLoad:
                findLoad();
                break;
                //네비게이션
            case R.id.btNavi:
                showToastShort("test toast");
                break;
                //택시부르기
            case R.id.btCallTaxi:
                showToastShort("test toast");
                break;
                //주소 복사
            case R.id.btCopyLink:
                showToastShort("test toast");
                break;
            default:
                break;
        }
    }

    private void getIntentData() {
        Intent i = getIntent();
        String menuName = i.getStringExtra("menuName");
        int distance = i.getIntExtra("distance", -1);

        boolean isException = false;
        isException = menuName == null || distance == -1;

        if (isException) {
            Common.showLogD("화면이동 후 값 받을 때 값이 제대로 안 넘어 옴_isException=" + isException);
        } else {
            tvMenuName.setText(menuName);
        }
    }

    private void findLoad() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5,127.0"));
        startActivity(intent);
    }
}
