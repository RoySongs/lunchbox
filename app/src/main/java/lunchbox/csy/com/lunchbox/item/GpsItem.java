package lunchbox.csy.com.lunchbox.item;

//<가람생성_위도,경도를 레트로핏 요청값으로 보낼 때 활용하려고 생성>
public class GpsItem {
    private double myLatitude;
    private double myLongitude;

    public GpsItem() {

    }

    public GpsItem(double myLatitude, double myLongitude) {
        this.myLatitude=myLatitude;
        this.myLongitude=myLongitude;
    }

    public double getMyLatitude() {
        return myLatitude;
    }

    public void setMyLatitude(double myLatitude) {
        this.myLatitude = myLatitude;
    }

    public double getMyLongitude() {
        return myLongitude;
    }

    public void setMyLongitude(double myLongitude) {
        this.myLongitude = myLongitude;
    }
}
