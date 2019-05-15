package lunchbox.csy.com.lunchbox.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

import lunchbox.csy.com.lunchbox.MainActivity;
import lunchbox.csy.com.lunchbox.R;
import lunchbox.csy.com.lunchbox.activity.DetailRestaurantActivity;
import lunchbox.csy.com.lunchbox.alarm.AlarmReceiver;
import lunchbox.csy.com.lunchbox.commons.Common;
import lunchbox.csy.com.lunchbox.commons.Const;
import lunchbox.csy.com.lunchbox.googleMap.MarkerItem;
import lunchbox.csy.com.lunchbox.item.GpsItem;
import lunchbox.csy.com.lunchbox.item.MemberItem;
import lunchbox.csy.com.lunchbox.lib.GpsInfo;
import lunchbox.csy.com.lunchbox.remote.RemoteService;
import lunchbox.csy.com.lunchbox.remote.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//190403_가람만듬
public class LocationFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    private MapView mapView = null;

    private Marker selectedMarker;
    private View marker_root_view;
    private TextView tvMarker;

    //permission
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;

    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    //GPSTracker class
    private GpsInfo gps;

    private double latitude, longitude;

    public static LocationFragment newInstance() {
        LocationFragment locationFragment = new LocationFragment();
        return locationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.getMapAsync(this);

        Button bt01 = (Button) view.findViewById(R.id.bt01);
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

        Button btGps = (Button) view.findViewById(R.id.btGps);
        btGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //권한요청 해야 함
                if (!isPermission) {
                    callPermission();
                    return;
                }

                gps = new GpsInfo(getActivity());
                //gps 사용여부 가져오기
                if (gps.isGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    Toast.makeText(getContext(), "위도=" + latitude + ", 경도=" + longitude, Toast.LENGTH_LONG).show();

                    selectLocationInfo(latitude, longitude);
                } else {
                    //gps를 사용할 수 없으므로
                    gps.showSettingAlert();
                }
            }
        });

        //권한요청 해야 함
        callPermission();

        return view;
    }

    //permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    private void callPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    private void moveDetailRestaurant() {
        Intent i = new Intent(getActivity(), DetailRestaurantActivity.class);
        i.putExtra(Const.REST_ID, Const.TEMP_REST_VALUE);
        i.putExtra(Const.REST_NAME, "김밥천국 서초점");
        i.putExtra("distance", 550);
        startActivity(i);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //activity가 처음 생성될 때 실행되는 함수
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }

        //안드로이드 서비스 개발
//        selectLocationInfo();
    }

    private void selectLocationInfo(double myLatitude, double myLongitude) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

//        latitude = 37.537523;
//        longitude = 126.96558;
        Call<JsonObject> call = remoteService.selectLocationInfo(myLatitude, myLongitude);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                ArrayList<GpsItem> list = response.body();
//                ArrayList<JsonObject> list = response.body();

//                if(response.isSuccessful() && list != null) {
//                    infoList.addItemList(list);

//                    if(infoListAdapter.getItemCount() == 0) {
//                        noDataText.setVisibility(View.VISIBLE);
//                    } else {
//                        noDataText.setVisibility(View.GONE);
//                    }

//                }

                //==
                if(response.isSuccessful()) {
                    JsonObject object = response.body();
                    if(object != null) {
                        Common.showLogD(object.toString());
                        Toast.makeText(getContext(), "responce=" + object.toString(), Toast.LENGTH_LONG);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Common.showLogD("no internet connectivity");
                Common.showLogD("throwable=" + t.toString());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.537523, 126.96558);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        setCustumMarkerView();
        getSampleMarkerItems(googleMap);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.537523, 126.96558), 14));

    }

    //====구글맵 관련----
    private void setCustumMarkerView() {
        marker_root_view = LayoutInflater.from(getContext()).inflate(R.layout.googlemap_marker_layout, null);
        tvMarker = (TextView) marker_root_view.findViewById(R.id.tvMarker);
    }

    private void getSampleMarkerItems(GoogleMap googleMap) {
        ArrayList<MarkerItem> sampleList = new ArrayList<MarkerItem>();

        sampleList.add(new MarkerItem(37.538523, 126.96568, 2500000));
        sampleList.add(new MarkerItem(37.527523, 126.96568, 100000));
        sampleList.add(new MarkerItem(37.549523, 126.96568, 15000));
        sampleList.add(new MarkerItem(37.538523, 126.95768, 5000));

        for (MarkerItem markerItem : sampleList) {
            addMarker(markerItem, false, googleMap);
        }
    }

    private Marker addMarker(MarkerItem markerItem, boolean isSelectedMarker, GoogleMap googleMap) {
        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLon());
        int price = markerItem.getPrice();
        String formatted = NumberFormat.getCurrencyInstance().format(price);

        tvMarker.setText(formatted);

        if (isSelectedMarker) {
            tvMarker.setBackgroundResource(R.drawable.ic_launcher_background);
            tvMarker.setTextColor(Color.WHITE);
        } else {
            tvMarker.setBackgroundResource(R.drawable.ic_kakao);
            tvMarker.setTextColor(Color.BLACK);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(Integer.toString(price));
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getContext(), marker_root_view)));

        return googleMap.addMarker(markerOptions);
    }

    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        int price = Integer.parseInt(marker.getTitle());
        MarkerItem temp = new MarkerItem(lat, lon, price);

        return addMarker(temp, isSelectedMarker, null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
//        mapView.animateCamera(center);

        changeSelectedMarker(marker);

        return true;
    }

    private void changeSelectedMarker(Marker marker) {
        //selected marker return
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        //selected marker show
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }
}
