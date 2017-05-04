package com.seven.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.manager.R;
import com.seven.manager.model.http.OrderModel;

import java.io.Serializable;

/**
 * 百度地图
 *
 * @author seven
 */

public class MapActivity extends BaseTitleActivity implements OnGetPoiSearchResultListener {

    private OrderModel model;

    private MapView mMapView;

    private BaiduMap mBaiduMap;

    private PoiSearch mPoiSearch;
    private PoiCitySearchOption mOption;

    //业主名字、面积、楼盘、地址
    private TextView mName;
    private TextView mArea;
    private TextView mAddress;
    private TextView mLocation;

    private RelativeLayout mInfoLayout;
    private ImageView mLine;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, Serializable serializable) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.SERIALIZABLE, serializable);
        ActivityStack.getInstance().startActivity(MapActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitContentView(Bundle savedInstanceState) {

        initView();

        initData(null);

    }

    @Override
    public int getContentLayoutId() {

        isLeftBtn = true;

        return R.layout.activity_map;
    }

    @Override
    public void onLeftButtonClicked() {

        finish();

    }

    @Override
    public void onRightButtonClicked() {

    }

    @Override
    public void onRightTextClicked() {

    }

    @Override
    public void initView() {

        mMapView = getView(mMapView, R.id.map_view);

        mName = getView(mName, R.id.map_name_tv);
        mArea = getView(mArea, R.id.map_area_tv);
        mAddress = getView(mAddress, R.id.map_zone_tv);
        mLocation = getView(mLocation, R.id.map_location_tv);

        mInfoLayout = getView(mInfoLayout, R.id.map_info_rl);
        mLine = getView(mLine, R.id.map_line);

        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        mInfoLayout.measure(w, h);
        int height =mInfoLayout.getMeasuredHeight();

        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) mLine.getLayoutParams();
        params.height=height;
        mLine.setLayoutParams(params);

    }

    @Override
    public void initData(Intent intent) {

        setTitle(ResourceUtils.getInstance().getText(R.string.map_location));

        if (intent == null)
            intent = getIntent();

        model = (OrderModel) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.SERIALIZABLE);

        if (model != null) {
            mName.setText(model.getOwnerName());
            mArea.setText(model.getArea() + "m²");
            mAddress.setText(model.getAddress());
        }

        mBaiduMap = mMapView.getMap();
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

//        setMapInfo(RunTimeConfig.MapConfig.TYPE_NORMAL);
//
//        setLocation(30.67,104.07);

        retrieval();
    }

    /**
     * 基础地图
     */
    private void setMapInfo(int type) {

        switch (type) {

            case RunTimeConfig.MapConfig.TYPE_NORMAL:

                //普通地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

                break;
            case RunTimeConfig.MapConfig.TYPE_MOON:

                //卫星地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

                break;
            case RunTimeConfig.MapConfig.TYPE_TRAFFIC:

                //开启交通图

                if (mBaiduMap.isTrafficEnabled())
                    mBaiduMap.setTrafficEnabled(false);
                else
                    mBaiduMap.setTrafficEnabled(true);

                break;
            case RunTimeConfig.MapConfig.TYPE_HEATING:

                //开启热力图

                if (mBaiduMap.isBaiduHeatMapEnabled())
                    mBaiduMap.setBaiduHeatMapEnabled(false);
                else
                    mBaiduMap.setBaiduHeatMapEnabled(true);

                break;

        }

//        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

    }

    /**
     * 检索位置
     */
    private void retrieval() {

        if (model == null)
            return;

        mOption = new PoiCitySearchOption()
                .city(ResourceUtils.getInstance().getText(R.string.city))
                .keyword(model.getAddress());
        mPoiSearch.searchInCity(mOption);
    }

    /**
     * 地图展示
     */
    private void setLocation(double lat, double lon) {

        mMapView.showZoomControls(false);

//        LatLng ll = new LatLng(lat, lon);
//        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
//        mBaiduMap.animateMapStatus(msu);
//        msu = MapStatusUpdateFactory.zoomTo(19);
//        mBaiduMap.animateMapStatus(msu);

        LatLng ll = new LatLng(lat, lon);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(ll)
                .zoom(19)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        displayInfoWindow(new LatLng(lat, lon));
    }

    /**
     * 显示弹出窗口覆盖物
     */
    private void displayInfoWindow(final LatLng latLng) {
        // 创建infowindow展示的view
        ImageView img = new ImageView(getApplicationContext());
        img.setBackgroundResource(R.drawable.weizhi);
//        btn.setText(zone);
//        btn.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.colorPrimary));
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromView(img);
        // infowindow点击事件
        InfoWindow.OnInfoWindowClickListener infoWindowClickListener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
//                reverseGeoCode(latLng);
                //隐藏InfoWindow
                mBaiduMap.hideInfoWindow();
            }
        };
        // 创建infowindow
        InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng, -47,
                infoWindowClickListener);

        // 显示InfoWindow
        mBaiduMap.showInfoWindow(infoWindow);
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

        mLocation.setText(poiResult.getAllPoi().get(0).address);

        setLocation(poiResult.getAllPoi().get(0).location.latitude, poiResult.getAllPoi().get(0).location.longitude);

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
    }
}
