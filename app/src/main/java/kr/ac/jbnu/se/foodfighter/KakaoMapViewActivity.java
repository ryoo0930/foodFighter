package kr.ac.jbnu.se.foodfighter;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class KakaoMapViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_navermap);

        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 줌 레벨 변경
        mapView.setZoomLevel(4, true);

        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        //데이터 가져오는 부분 : 데이터는 장소 이름, 위도, 경도로 이루어져있다.
        TestApiData apiData = new TestApiData();
        ArrayList<TestData> dataArr = apiData.getData();

        ArrayList<MapPOIItem> markerArr = new ArrayList<MapPOIItem>();
        for (TestData data : dataArr) {
            MapPOIItem marker = new MapPOIItem();
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(data.getLatitude(), data.getLongitude()));
            marker.setItemName(data.getName());
            markerArr.add(marker);
        }
        mapView.addPOIItems(markerArr.toArray(new MapPOIItem[markerArr.size()]));

    }
}

class TestData {
    String name;
    Double latitude;
    Double longitude;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

class TestApiData {
    String apiUrl = "https://api.odcloud.kr/api/15076735/v1/uddi:73ae39f6-7aa9-49ee-915e-9018d738f882";
    String apiKey = "%2FpknOIU5O2D%2BjRlySLDqBtPAGnuJdXWIVLL3Gb8RGrxq1BOYcCanV1MwNLpWZFKTIwLoVZdgaxwLGObJheHQvw%3D%3D";

    public ArrayList<TestData> getData() {
        //return data와 관련된 부분
        ArrayList<TestData> dataArr = new ArrayList<TestData>();

        //네트워킹 작업은 메인스레드에서 처리하면 안된다. 따로 스레드를 만들어 처리하자
        Thread t = new Thread() {
            @Override
            public void run() {
                try {

                    //url과 관련된 부분
                    String fullurl = apiUrl + "?serviceKey=" + apiKey + "&returnType=XML";
                    URL url = new URL(fullurl);
                    InputStream is = url.openStream();

                    //xmlParser 생성
                    XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = xmlFactory.newPullParser();
                    parser.setInput(is,"utf-8");

                    //xml과 관련된 변수들
                    boolean bName = false, bLat = false, bLong = false;
                    String name = "", latitude = "", longitude = "";

                    //본격적으로 파싱
                    while(parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                        int type = parser.getEventType();
                        TestData data = new TestData();

                        //태그 확인
                        if(type == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("col")) {
                                if (parser.getAttributeValue(0).equals("식당명"))
                                    bName = true;
                                else if (parser.getAttributeValue(0).equals("식당위도"))
                                    bLat = true;
                                else if (parser.getAttributeValue(0).equals("식당경도"))
                                    bLong = true;
                            }
                        }
                        //내용 확인
                        else if(type == XmlPullParser.TEXT) {
                            if (bName) {
                                name = parser.getText();
                                bName = false;
                            } else if (bLat) {
                                latitude = parser.getText();
                                bLat = false;
                            } else if (bLong) {
                                longitude = parser.getText();
                                bLong = false;
                            }
                        }
                        //내용 다 읽었으면 데이터 추가
                        else if (type == XmlPullParser.END_TAG && parser.getName().equals("item")) {
                            data.setName(name);
                            data.setLatitude(Double.valueOf(latitude));
                            data.setLongitude(Double.valueOf(longitude));

                            dataArr.add(data);
                        }

                        type = parser.next();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        };
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return dataArr;
    }
}
