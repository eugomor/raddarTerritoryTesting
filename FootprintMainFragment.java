package com.raddarapp.presentation.android.fragment;

import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.karumi.rosie.repository.PaginatedCollection;
import com.karumi.rosie.view.Presenter;
import com.pkmmte.view.CircularImageView;
import com.raddarapp.BuildConfig;
import com.raddarapp.R;
import com.raddarapp.domain.model.FootprintMain;
import com.raddarapp.domain.model.TerritoryArea;
import com.raddarapp.domain.model.enums.VotedType;
import com.raddarapp.presentation.android.RaddarApplication;
import com.raddarapp.presentation.android.activity.FootprintMainActivity;
import com.raddarapp.presentation.android.activity.FootprintMainDetailsActivity;
import com.raddarapp.presentation.android.activity.UserProfileActivity;
import com.raddarapp.presentation.android.dialog.DialogInfo;
import com.raddarapp.presentation.android.fragment.base.BaseMapNormalFragment;
import com.raddarapp.presentation.android.renderer.FootprintsMainAdapteeCollection;
import com.raddarapp.presentation.android.renderer.FootprintsMainRendererBuilder;
import com.raddarapp.presentation.android.utils.DimenUtils;
import com.raddarapp.presentation.android.utils.FileUtils;
import com.raddarapp.presentation.android.utils.FontUtils;
import com.raddarapp.presentation.android.utils.PreferencesUtils;
import com.raddarapp.presentation.android.view.CountAnimationTextView;
import com.raddarapp.presentation.android.view.FloatingAddCaptureFootprint;
import com.raddarapp.presentation.android.view.FloatingAddVote;
import com.raddarapp.presentation.android.view.map.HorizontalRecyclerViewScrollListenerRosie;
import com.raddarapp.presentation.android.view.radarscan.RadarScanView;
import com.raddarapp.presentation.android.view.radarscan.RandomPointsView;
import com.raddarapp.presentation.android.view.renderer.RendererBuilderWithIndex;
import com.raddarapp.presentation.android.view.swipe.RVRendererAdapterSwipeable;
import com.raddarapp.presentation.android.view.swipe.SimpleItemTouchHelperCallback;
import com.raddarapp.presentation.general.presenter.FootprintsMainPresenter;
import com.raddarapp.presentation.general.presenter.FootprintsMainTerritoryPresenter;
import com.raddarapp.presentation.general.viewmodel.FootprintMainViewModel;
import com.raddarapp.presentation.general.viewmodel.MyUserProfileViewModel;
import com.raddarapp.presentation.general.viewmodel.TerritoryViewModel;
import com.raddarapp.presentation.general.viewmodel.contract.FootprintMainViewModelContract;
import com.ufreedom.floatingview.FloatingBuilder;
import com.ufreedom.floatingview.FloatingElement;
import com.ufreedom.floatingview.effect.TranslateFloatingTransition;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class FootprintMainFragment extends BaseMapNormalFragment implements
        FootprintsMainTerritoryPresenter.View, FootprintsMainPresenter.View, SimpleItemTouchHelperCallback.OnSwipedListener {
		 @Inject @Presenter
    FootprintsMainPresenter footprintsMainPresenter;
		 @Inject @Presenter
    FootprintsMainTerritoryPresenter territoryPresenter;
	
	
	public static FootprintMainFragment newInstance() {
        FootprintMainFragment fragment = new FootprintMainFragment();
        return fragment;
    }
	@Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }
	 @Override
    protected void onPrepareFragment(View view) {
        super.onPrepareFragment(view);
        preferencesUtils = new PreferencesUtils(getActivity());
        initializeFonts();
        initializeMap();
        initializeViews();
        initializeAnimations();

        if(BuildConfig.VERSION_CODE > preferencesUtils.getVersionCode()) {
            preferencesUtils.setVersionCode(BuildConfig.VERSION_CODE);
            showDialogNews();
        }

        showTutorialMap = new PreferencesUtils(getActivity()).showTutorialMap();

        sizeScreenX = new DimenUtils().calculateScreenWitdh(getActivity().getWindowManager());
    }

	
		
		 @Override
    public void onShowTerritoryMainClickedTesting(String zoneKey, boolean fromMap) {




        if (checkGpsEnabledToRefresh()) {
            // TODO: Change to Map footprintsMainPresenter
            if (lastLocation != null && loadingTerritoryAreaView.getVisibility() == View.GONE) {

                if (getActivity() != null) {
                    ((FootprintMainActivity) getActivity()).resetMyUsersRankingAndTerritoryFromMap();
                }

                if (zoneKey.isEmpty()) {
                    if (!cleanedMap) {
                        cleanMap();
                    }

                    if (fromMap) {
                        removeTerritoryAreaPolygon();
                    }

                   
                    territoryPresenter.onShowTerritoryMainTesting(coordsTestingEditText.getText().toString());
                    

                } else {
                    territoryPresenter.onShowTerritoryMainByZone(zoneKey);
                }

            } else {

                if (checkGpsPermission()) {
                    if (loadingTerritoryAreaView.getVisibility() == View.GONE) {
                        showLocationError();
                    }
                } else {
                    getGpsPermission();
                }

                hideLoading();
            }
        }
    }
    @Override
    public void onShowTerritoryMainClickedTesting2(String zoneKey, boolean fromMap) {




        if (checkGpsEnabledToRefresh()) {
            // TODO: Change to Map footprintsMainPresenter
            if (lastLocation != null && loadingTerritoryAreaView.getVisibility() == View.GONE) {

                if (getActivity() != null) {
                    ((FootprintMainActivity) getActivity()).resetMyUsersRankingAndTerritoryFromMap();
                }

                if (zoneKey.isEmpty()) {
                    if (!cleanedMap) {
                        cleanMap();
                    }

                    if (fromMap) {
                        removeTerritoryAreaPolygon();
                    }

                    
                    territoryPresenter.onShowTerritoryMainByZoneTesting(coordsTestingEditText.getText().toString());
                   

                } else {
                    territoryPresenter.onShowTerritoryMainByZone(zoneKey);
                }

            } else {

                if (checkGpsPermission()) {
                    if (loadingTerritoryAreaView.getVisibility() == View.GONE) {
                        showLocationError();
                    }
                } else {
                    getGpsPermission();
                }

                hideLoading();
            }
        }
    }
		
		
		
}