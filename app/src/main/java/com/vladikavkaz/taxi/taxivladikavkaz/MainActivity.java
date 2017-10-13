package com.vladikavkaz.taxi.taxivladikavkaz;

import android.app.Activity;
import android.content.res.Resources;;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.vladikavkaz.taxi.taxivladikavkaz.models.GeoLocationModel;
import com.vladikavkaz.taxi.taxivladikavkaz.rest.RestService;

import java.util.List;

import butterknife.BindAnim;
import butterknife.BindDrawable;
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
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity implements OnMapListener, View.OnClickListener, View.OnTouchListener {
    Observable<GeoLocationModel> geoLocationModelsObserver;
    GeoLocationModel geoLocationModels;
    BottomSheetBehavior behavior;
    @BindView(R.id.relative_from)
    View relative_from;
    @BindView(R.id.relative_to)
    View relative_to;
    @BindView(R.id.text_placefrom_bs)
    TextView textFromText;
    @BindView(R.id.text_placeto_bs)
    TextView textToText;
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
    @BindView(R.id.image_center)
    ImageView imageCenterScreen;
    @BindDrawable(R.drawable.ic_place_green_24dp)
    Drawable greenFlag;
    @BindDrawable(R.drawable.ic_place_black_24dp)
    Drawable blackFlag;
    private void initMapYandex(){
        mapController = mapView.getMapController();
        overlayManager = new OverlayManager(mapController);
        overlayManager.getMyLocation().setEnabled(true);
        overlay = new Overlay(mapController);
        mapController.setPositionNoAnimationTo(new GeoPoint(43.0222284, 44.678499), 91);
    }
    private GeoPoint getCenterScreenLocation(){
        Resources res = getResources();
        float displayHeight = res.getDisplayMetrics().heightPixels;
        float displayWidth = res.getDisplayMetrics().widthPixels;
        ScreenPoint screenPoint = new ScreenPoint(displayWidth / 2, displayHeight / 2);
        return overlay.getMapController().getGeoPoint(screenPoint);
    }

    private String formatedRequest(String request){
        String[] parse = request.split(",");
        if (parse.length > 2)
            return parse[parse.length - 2] + "," + parse[parse.length - 1];
        else return request;
    }

    private void getTitleOnMap(String requestText){
        RestService restService = new RestService();
        geoLocationModelsObserver = restService.getGeoInfo(requestText, "json", "1");
        geoLocationModelsObserver.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GeoLocationModel>() {
                    @Override
                    public void onCompleted() {
                        if (geoLocationModels != null)
                            if (!geoLocationModels.getResponse().getGeoObjectCollection().getMetaDataProperty().getGeocoderResponseMetaData().getFound().equals("0")) {
                                textTitle.setText(formatedRequest(geoLocationModels.getResponse()
                                        .getGeoObjectCollection()
                                        .getFeatureMember()
                                        .get(0)
                                        .getGeoObject()
                                        .getMetaDataProperty()
                                        .getGeocoderMetaData()
                                        .getText()));
                                if (imageCenterScreen.getDrawable() != greenFlag) {
                                    textFromText.setText(textTitle.getText());
                                }else
                                    textToText.setText(textTitle.getText());
                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(GeoLocationModel geoLocationModelsOn) {
                        geoLocationModels = geoLocationModelsOn;
                    }
                });
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
        relative_from.setOnTouchListener(this);
        relative_to.setOnTouchListener(this);
    }


    private void initBottomSheet() {
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_EXPANDED:
                        nextButton.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        nextButton.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onMapActionEvent(MapEvent mapEvent) {
        switch (mapEvent.getMsg()){
            case MapEvent.MSG_SCALE_END:
            case MapEvent.MSG_SCROLL_END:
            case MapEvent.MSG_ZOOM_END:
                getTitleOnMap(getCenterScreenLocation().getLon() + "," + getCenterScreenLocation().getLat());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_button:
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.relative_from:
                imageCenterScreen.setImageDrawable(blackFlag);
                break;
            case R.id.relative_to:
                imageCenterScreen.setImageDrawable(greenFlag);
                break;
        }
        return false;
    }
}
