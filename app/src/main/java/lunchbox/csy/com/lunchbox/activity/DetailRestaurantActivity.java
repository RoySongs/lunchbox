package lunchbox.csy.com.lunchbox.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.HttpURLConnection;
import java.net.URL;

import lunchbox.csy.com.lunchbox.R;
import lunchbox.csy.com.lunchbox.base.AbstBaseActivity;
import lunchbox.csy.com.lunchbox.commons.Common;
import lunchbox.csy.com.lunchbox.commons.Const;

//가람담당_식당상세화면
public class DetailRestaurantActivity extends AbstBaseActivity implements OnMapReadyCallback {

    TextView tvRestName,tvDistance;

    @Override
    protected void onCreateChild() {
        setContentView(R.layout.activity_detail_restaurant);
        Common.showLogD("test log debug");
        tvRestName=(TextView)findViewById(R.id.tvRestName);
        tvDistance=(TextView)findViewById(R.id.tvDistance);

        if(isOnline()) {
            String netState = getWhatKindOfNetwork();
            if(netState.equals(Const.NONE_STATE)) {
                //인터넷 연결이 안 되어 있으므로 intent로 상점정보 나타냄
                getIntentData();
            } else {
                Common.showLogD("제어옴");
                getIntentData();
            }

        } else {
            //인터넷 환경이므로 서버로부터 받은 상점정보 나타냄
            //org1
//            getServerData();
            //org2

            //test1
            getIntentData();
            //test2
        }

        //google map
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
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
        String restId = i.getStringExtra(Const.REST_ID);
        String restName = i.getStringExtra(Const.REST_NAME);
        int distance = i.getIntExtra("distance", -1);

        tvRestName.setText(restName);
        tvDistance.setText(distance + "m");

        //add1
//        boolean isException = false;
//        isException = restId == null || restName == null || distance == -1;
//
//        if (isException) {
//            Common.showLogD("화면이동 후 값 받을 때 값이 제대로 안 넘어 옴_isException=" + isException);
//        } else {
//            tvRestName.setText(restName);
//        }
        //add2
    }

    private void findLoad() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5,127.0"));
        startActivity(intent);
    }

    //인터넷 환경이 안 될 경우 intent로 넘긴 값으로 식당 상세보기 보여줌
    private String getWhatKindOfNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activityNetwork = connectivityManager.getActiveNetworkInfo();

        if (activityNetwork != null) {
            if(activityNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return Const.WIFI_STATE;
            } else if(activityNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return Const.MOBILE_STATE;
            }
        }

        return Const.NONE_STATE;
    }

    private class CheckConnect extends Thread{
        private boolean success;
        private String host;

        public CheckConnect(String host){
            this.host = host;
        }

        @Override
        public void run() {

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection)new URL(host).openConnection();
                conn.setRequestProperty("User-Agent","Android");
                conn.setConnectTimeout(1000);
                conn.connect();
                int responseCode = conn.getResponseCode();
                if(responseCode == 204) success = true;
                else success = false;
            }
            catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
            if(conn != null){
                conn.disconnect();
            }
        }

        public boolean isSuccess(){
            return success;
        }

    }

    private boolean isOnline() {
        CheckConnect cc = new CheckConnect(Const.CONNECTION_CONFIRM_CLIENT_URL);
        cc.start();
        try{
            cc.join();
            return cc.isSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}
