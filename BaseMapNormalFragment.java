package com.raddarapp.presentation.android.fragment.base;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.pkmmte.view.CircularImageView;
import com.raddarapp.BuildConfig;
import com.raddarapp.R;
import com.raddarapp.domain.model.TerritoryArea;
import com.raddarapp.presentation.android.activity.FootprintMainActivity;
import com.raddarapp.presentation.android.utils.DimenUtils;
import com.raddarapp.presentation.android.utils.MapUtils;
import com.raddarapp.presentation.android.utils.NumberUtils;
import com.raddarapp.presentation.android.utils.TutorialUtils;
import com.raddarapp.presentation.android.view.CountAnimationTextView;
import com.raddarapp.presentation.android.view.map.MapOverlayLayout;
import com.raddarapp.presentation.android.view.map.PulseOverlayLayout;
import com.raddarapp.presentation.general.viewmodel.TerritoryViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public abstract class BaseMapNormalFragment extends BaseNormalFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, MapOverlayLayout.OnAnimateCameraMapFinishListener { 
 
 @OnClick(R.id.my_territory_position_testing)
    public void myTerritoryPositionOnClickedTesting() {
        if (getActivity() != null) {
            ((FootprintMainActivity) getActivity()).resetAllTerritories();
            ((FootprintMainActivity) getActivity()).setOnShowTerritoryFromMap(true);

            onShowTerritoryMainClickedTesting("", true);
        }
    }

    @OnClick(R.id.my_territory_position_testing2)
    public void myTerritoryPositionOnClickedTesting2() {
        if (getActivity() != null) {
            ((FootprintMainActivity) getActivity()).resetAllTerritories();
            ((FootprintMainActivity) getActivity()).setOnShowTerritoryFromMap(true);

            onShowTerritoryMainClickedTesting2("", true);
        }
    }
}