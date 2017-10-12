package com.vladikavkaz.taxi.taxivladikavkaz;

import android.app.Activity;
import android.content.res.Resources;;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.map.GeoCode;
import ru.yandex.yandexmapkit.map.GeoCodeListener;
import ru.yandex.yandexmapkit.map.MapEvent;
import ru.yandex.yandexmapkit.map.OnMapListener;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.utils.GeoPoint;
import ru.yandex.yandexmapkit.utils.ScreenPoint;

public class MainActivity extends Activity implements OnMapListener, View.OnClickListener {
    BottomSheetBehavior behavior;
    @BindView(R.id.edit_placefrom_bs)
    EditText editFromText;
    @BindView(R.id.edit_placeto_bs)
    EditText editToText;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.bottom_sheet)
    View bottomSheet;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.map_yandex)
    MapView mapView;
    MapController mapController;
    OverlayManager overlayManager;
    Overlay overlay;
    @BindAnim(R.anim.fade_in)
    Animation fadeIn;
    @BindAnim(R.anim.fade_out)
    Animation fadeOut;
    private void initMapYandex(){
        mapController = mapView.getMapController();
        overlayManager = new OverlayManager(mapController);
        overlayManager.getMyLocation().setEnabled(true);
        overlay = new Overlay(mapController);
        mapController.setPositionNoAnimationTo(new GeoPoint(43.0222284, 44.678499), 91);
    }
    private ScreenPoint getCenterScreen(){
        Resources res = getResources();
        float displayHeight = res.getDisplayMetrics().heightPixels;
        float displayWidth = res.getDisplayMetrics().widthPixels;
        ScreenPoint screenPoint = new ScreenPoint(displayWidth / 2, displayHeight / 2);
        return screenPoint;
    }

    private void getTitleOnMap(){
        GeoPoint geoPoint;
        geoPoint = overlay.getMapController().getGeoPoint(getCenterScreen());
        mapController.getDownloader().getGeoCode(new GeoCodeListener() {
            @Override
            public boolean onFinishGeoCode(GeoCode geoCode) {
                if (geoCode != null)
                    textTitle.setText(geoCode.getTitle());
                return true;
            }
        }, geoPoint);
    }

    private void serchLocationByStreet(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMapYandex();
        initBottomSheet();
        mapController.addMapListener(this);
        nextButton.setOnClickListener(this);
    }

    private void initBottomSheet() {
        behavior = BottomSheetBehavior.from(bottomSheet);
    }

    @Override
    public void onMapActionEvent(MapEvent mapEvent) {
        switch (mapEvent.getMsg()){
            case MapEvent.MSG_SCALE_END:
            case MapEvent.MSG_SCROLL_END:
            case MapEvent.MSG_ZOOM_END:
                getTitleOnMap();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_button:
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                editFromText.setText(textTitle.getText());
                break;
        }
    }
}
