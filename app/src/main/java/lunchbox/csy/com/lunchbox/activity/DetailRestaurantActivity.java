package lunchbox.csy.com.lunchbox.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;

import lunchbox.csy.com.lunchbox.R;
import lunchbox.csy.com.lunchbox.base.AbstBaseActivity;
import lunchbox.csy.com.lunchbox.commons.Common;
import lunchbox.csy.com.lunchbox.commons.Const;
import lunchbox.csy.com.lunchbox.googleMap.MarkerItem;

//가람담당_식당상세화면
public class DetailRestaurantActivity extends AbstBaseActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private TextView tvRestName, tvDistance;

    //====구글맵 관련----
    private Marker selectedMarker;
    private View marker_root_view;
    private TextView tvMarker;
    private GoogleMap mMap;

    @Override
    protected void onCreateChild() {
        setContentView(R.layout.activity_detail_restaurant);
        Common.showLogD("test log debug");
        tvRestName = (TextView) findViewById(R.id.tvRestName);
        tvDistance = (TextView) findViewById(R.id.tvDistance);

        if (isOnline()) {
            String netState = getWhatKindOfNetwork();
            if (netState.equals(Const.NONE_STATE)) {
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
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);

//        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
//        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onDestoryChild() {

    }

    @Override
    public void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.llBack:
                finish();
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activityNetwork = connectivityManager.getActiveNetworkInfo();

        if (activityNetwork != null) {
            if (activityNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return Const.WIFI_STATE;
            } else if (activityNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return Const.MOBILE_STATE;
            }
        }

        return Const.NONE_STATE;
    }

    private class CheckConnect extends Thread {
        private boolean success;
        private String host;

        public CheckConnect(String host) {
            this.host = host;
        }

        @Override
        public void run() {

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(host).openConnection();
                conn.setRequestProperty("User-Agent", "Android");
                conn.setConnectTimeout(1000);
                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode == 204) success = true;
                else success = false;
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        public boolean isSuccess() {
            return success;
        }

    }

    private boolean isOnline() {
        CheckConnect cc = new CheckConnect(Const.CONNECTION_CONFIRM_CLIENT_URL);
        cc.start();
        try {
            cc.join();
            return cc.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        LatLng location = new LatLng(37.56, 126.97);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(location);
//        markerOptions.title("서울");
//        markerOptions.snippet("한국의 수도");
//        googleMap.addMarker(markerOptions);
//
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        //=====
        mMap=googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.537523, 126.96558), 14));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        setCustumMarkerView();
        getSampleMarkerItems();
    }

    //====구글맵 관련----
    private void setCustumMarkerView() {
        marker_root_view = LayoutInflater.from(this).inflate(R.layout.googlemap_marker_layout, null);
        tvMarker = (TextView)marker_root_view.findViewById(R.id.tvMarker);
    }

    private void getSampleMarkerItems() {
        ArrayList<MarkerItem> sampleList = new ArrayList<MarkerItem>();

        sampleList.add(new MarkerItem(37.538523, 126.96568, 2500000));
        sampleList.add(new MarkerItem(37.527523, 126.96568, 100000));
        sampleList.add(new MarkerItem(37.549523, 126.96568, 15000));
        sampleList.add(new MarkerItem(37.538523, 126.95768, 5000));

        for(MarkerItem markerItem : sampleList) {
            addMarker(markerItem, false);
        }
    }

    private Marker addMarker(MarkerItem markerItem, boolean isSelectedMarker) {
        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLon());
        int price = markerItem.getPrice();
        String formatted = NumberFormat.getCurrencyInstance().format(price);

        tvMarker.setText(formatted);

        if(isSelectedMarker) {
            tvMarker.setBackgroundResource(R.drawable.ic_launcher_background);
            tvMarker.setTextColor(Color.WHITE);
        } else {
            tvMarker.setBackgroundResource(R.drawable.ic_kakao);
            tvMarker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(Integer.toString(price));
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));

        return mMap.addMarker(markerOptions);
    }

    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0,0,displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas=new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        int price = Integer.parseInt(marker.getTitle());
        MarkerItem temp = new MarkerItem(lat, lon, price);

        return addMarker(temp, isSelectedMarker);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        changeSelectedMarker(marker);

        return true;
    }

    private void changeSelectedMarker(Marker marker) {
        //selected marker return
        if(selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        //selected marker show
        if(marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }
}