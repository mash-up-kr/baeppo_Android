package com.mashup.ipdam.ui.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.doOnLayout
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mashup.base.BaseFragment
import com.mashup.base.ext.checkSelfPermissionCompat
import com.mashup.base.ext.hideSoftKeyBoard
import com.mashup.base.ext.shouldShowRequestPermissionRationaleCompat
import com.mashup.base.ext.toast
import com.mashup.ipdam.R
import com.mashup.ipdam.data.ReviewMarker
import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.data.map.MapConstants.DEFAULT_LATITUDE
import com.mashup.ipdam.data.map.MapConstants.DEFAULT_LONGITUDE
import com.mashup.ipdam.data.map.MapConstants.LOCATION_MAP_PERMISSION
import com.mashup.ipdam.data.map.MapConstants.LOCATION_PERMISSION_REQUEST_CODE
import com.mashup.ipdam.data.map.MapConstants.LOCATION_TRACKING_MODE
import com.mashup.ipdam.data.map.MapConstants.MAP_MAX_ZOOM
import com.mashup.ipdam.data.map.MapConstants.MIN_MAX_ZOOM
import com.mashup.ipdam.databinding.FragmentHomeBinding
import com.mashup.ipdam.ui.home.adapter.review.HomeReviewAdapter
import com.mashup.ipdam.ui.home.adapter.roomimagebymarker.RoomImageByMarkerAdapter
import com.mashup.ipdam.ui.search.SearchActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import dagger.hilt.android.AndroidEntryPoint
import ted.gun0912.clustering.naver.TedNaverClustering
import kotlin.math.min


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), OnMapReadyCallback {

    override var logTag: String = "HomeFragment"

    private lateinit var myMap: NaverMap
    private lateinit var mapLocationSource: FusedLocationSource
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val reviewAdapter by lazy { HomeReviewAdapter(homeViewModel) }
    private val roomImageByMarkerAdapter by lazy { RoomImageByMarkerAdapter() }
    private val clusteringMarkers: TedNaverClustering<ReviewMarker> by lazy {
        initTedCluster()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showLocationButton()
            } else {
                requireContext().toast(requireContext().getString(R.string.location_access_denied))
                hideLocationButton()
            }
        }

    private val searchActivityResultLauncher =
        registerForActivityResult(
            StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.run {
                    homeViewModel.setMapCameraPosition(
                        LatLng(
                            getDoubleExtra("latitude", DEFAULT_LATITUDE),
                            getDoubleExtra("longitude", DEFAULT_LONGITUDE)
                        )
                    )
                }
            }
        }

    override fun initLayout() {
        mapLocationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        binding.viewModel = homeViewModel
        binding.bottomSheet.loBottomSheetByMarker.viewModel = homeViewModel
        binding.map.getMapAsync(this)
        initBottomSheet()
        initSpinner()
        initSearchLayout()
    }

    private fun initTedCluster(): TedNaverClustering<ReviewMarker> {
        val markerImage = OverlayImage.fromResource(R.drawable.ic_marker)

        return TedNaverClustering.with<ReviewMarker>(requireContext(), myMap)
            .customMarker { clusterItem ->
                Marker(LatLng(clusterItem.latitude, clusterItem.longitude)).apply {
                    icon = markerImage
                    width = resources.getDimension(R.dimen.width_marker).toInt()
                    height = resources.getDimension(R.dimen.height_marker).toInt()
                }
            }
            .customCluster {
                TextView(requireContext()).apply {
                    setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.primary_color
                        )
                    )
                    setTextColor(Color.WHITE)
                    text = getString(R.string.cluster_marker_text, it.size)
                    setPadding(10, 10, 10, 10)
                }
            }.markerClickListener {
                homeViewModel.getReviewByMarker(it.id)
                homeViewModel.getAddressByLatLng(LatLng(it.latitude, it.longitude))
                homeViewModel.sortReviewByTime()
            }
            .make()
    }

    private fun initSearchLayout() {
        binding.searchView.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    homeViewModel.getResultBySearchAddress()
                    true
                }
                else -> false
            }
        }
    }

    private fun initBottomSheet() {
        binding.root.doOnLayout {
            setIpdamBottomSheetHeight()
        }
        BottomSheetBehavior.from(binding.bottomSheet.root)
            .addBottomSheetCallback(
                object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            setMapLocationButtonOnMapBottomSheet()
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        val changedAlpha = min(
                            ALPHA_MAX,
                            ALPHA_MAX / ALPHA_CHANGED_VALUE * slideOffset
                        )
                        binding.bottomSheet.apply {
                            tvBottomSheetHeader.alpha = ALPHA_MAX - changedAlpha
                            tvPlaceName.alpha = changedAlpha
                            tvPlaceNameAndCount.alpha = changedAlpha
                            ivMarker.alpha = changedAlpha
                            spSort.alpha = changedAlpha
                        }
                        binding.bottomSheet.loBottomSheetByMarker.apply {
                            tvAddress.alpha = ALPHA_MAX - changedAlpha
                            tvName.alpha = ALPHA_MAX - changedAlpha
                            tvReviewCounts.alpha = ALPHA_MAX - changedAlpha
                            rvThumbnail.alpha = ALPHA_MAX - changedAlpha
                        }
                    }
                })

        binding.bottomSheet.rvReviews.adapter = reviewAdapter
        binding.bottomSheet.loBottomSheetByMarker.rvThumbnail.adapter = roomImageByMarkerAdapter
        ViewCompat.setNestedScrollingEnabled(
            binding.bottomSheet.loBottomSheetByMarker.rvThumbnail,
            false
        )
    }

    private fun initSpinner() {
        val spinner = binding.bottomSheet.spSort
        context?.let {
            spinner.adapter = ArrayAdapter.createFromResource(
                it,
                R.array.review_sort,
                R.layout.item_spinner,
            )
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> homeViewModel.sortReviewByTime()
                    else -> homeViewModel.sortReviewByStar()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                homeViewModel.sortReviewByTime()
            }
        }
    }

    override fun observeViewModel() {
        observeBottomSheet()
        observeMapLiveData()
        observeSearchLiveData()
    }

    private fun showSearchActivity(searchingAddress: String) {
        val intent = Intent(requireActivity(), SearchActivity::class.java).apply {
            putExtra("keyword", searchingAddress)
        }
        searchActivityResultLauncher.launch(intent)
    }

    private fun observeBottomSheet() {
        homeViewModel.bottomSheetState.observe(this, { state ->
            state?.let {
                when (it) {
                    BottomSheetState.MAP_MOVED -> {
                        showIpdamBottomSheetByMap()
                        setMapLocationButtonOnMapBottomSheet()
                    }
                    BottomSheetState.MARKER_CLICKED -> {
                        showIpdamBottomSheetByMarker()
                        setMapLocationButtonOnMarkerBottomSheet()
                    }
                }
            }
        })
    }

    private fun observeMapLiveData() {
        homeViewModel.mapCameraPosition.observe(this) {
            val cameraUpdate = CameraUpdate.scrollTo(it)
            myMap.moveCamera(cameraUpdate)
        }
        homeViewModel.reviewMarkersOnMap.observe(this) {
            createClusteringMarker(it)
        }
    }

    private fun createClusteringMarker(item: List<ReviewMarker>) {
        clusteringMarkers.addItems(item)
    }

    private fun hideClusteringMarker() {
        clusteringMarkers.clearItems()
    }

    private fun observeSearchLiveData() {
        homeViewModel.showSearchResultEvent.observe(this) {
            showSearchActivity(homeViewModel.searchAddress.value ?: "")
        }
        homeViewModel.isSearchingPlace.observe(this) {
            requireActivity().hideSoftKeyBoard()
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        myMap = naverMap.apply {
            minZoom = MIN_MAX_ZOOM
            maxZoom = MAP_MAX_ZOOM
            locationSource = mapLocationSource
        }
        requestMapLocationPermission()
        initMapListener()
        initMapUi()
    }

    private fun requestMapLocationPermission() {
        when {
            checkSelfPermissionCompat(
                LOCATION_MAP_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED -> {
                showLocationButton()
            }
            shouldShowRequestPermissionRationaleCompat(LOCATION_MAP_PERMISSION) -> {
                requireContext().toast(requireContext().getString(R.string.location_access_required))
                requestPermissionLauncher.launch(
                    LOCATION_MAP_PERMISSION
                )
            }
            else -> {
                requestPermissionLauncher.launch(
                    LOCATION_MAP_PERMISSION
                )
            }
        }
    }

    private fun getMapBoundaryOnScreen(): MapBoundary {
        val locationOnScreen = getMapLocation()
        val topLeftPointF = PointF(0f, 0f)
        val bottomRightPointF =
            PointF(locationOnScreen[0].toFloat(), locationOnScreen[1].toFloat())

        val projection = myMap.projection
        val topLeftLatLng = projection.fromScreenLocation(topLeftPointF)
        val bottomRightLatLng = projection.fromScreenLocation(bottomRightPointF)

        return MapBoundary(topLeftLatLng, bottomRightLatLng)
    }

    private fun getMapLocation() = intArrayOf(0, 0).apply {
        binding.map.getLocationOnScreen(this)
        this[0] += binding.map.width
        this[1] += binding.map.height
    }

    private fun initMapListener() {
        myMap.addOnCameraIdleListener {
            hideClusteringMarker()
            homeViewModel.getReviewInBoundary(getMapBoundaryOnScreen())
            homeViewModel.getAddressByLatLng(myMap.cameraPosition.target)
            homeViewModel.sortReviewByTime()
        }
    }

    private fun initMapUi() {
        myMap.uiSettings.apply {
            isZoomControlEnabled = false
            isScaleBarEnabled = false
            isLocationButtonEnabled = false
        }
        binding.locationView.map = myMap
    }

    private fun hideLocationButton() {
        binding.locationView.visibility = View.GONE
        binding.locationView.map = null
        myMap.locationTrackingMode = LocationTrackingMode.None
    }

    private fun showLocationButton() {
        mapLocationSource.onRequestPermissionsResult(
            LOCATION_PERMISSION_REQUEST_CODE,
            arrayOf(LOCATION_MAP_PERMISSION), intArrayOf(PackageManager.PERMISSION_GRANTED)
        )
        binding.locationView.visibility = View.VISIBLE
        binding.locationView.map = myMap
        myMap.locationTrackingMode = LOCATION_TRACKING_MODE
    }

    private fun showIpdamBottomSheetByMarker() {
        BottomSheetBehavior.from(binding.bottomSheet.root).run {
            peekHeight = resources.getDimensionPixelSize(R.dimen.peek_height_bottom_sheet_by_marker)
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun showIpdamBottomSheetByMap() {
        BottomSheetBehavior.from(binding.bottomSheet.root).run {
            peekHeight = resources.getDimensionPixelSize(R.dimen.peek_height_bottom_sheet_by_map)
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setMapLocationButtonOnMapBottomSheet() {
        binding.locationView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin =
                resources.getDimensionPixelSize(R.dimen.margin_bottom_location_button_by_map)
        }
    }

    private fun setMapLocationButtonOnMarkerBottomSheet() {
        binding.locationView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin =
                resources.getDimensionPixelSize(R.dimen.margin_bottom_location_button_by_marker)
        }
    }

    private fun setIpdamBottomSheetHeight() {
        binding.bottomSheet.root.updateLayoutParams {
            val bottomSheetHeight =
                binding.root.height -
                        binding.searchView.height -
                        binding.searchView.marginTop -
                        resources.getDimensionPixelSize(R.dimen.bottom_sheet_margin_top)
            height = bottomSheetHeight
        }
    }

    companion object {
        private const val ALPHA_MAX = 1F
        private const val ALPHA_CHANGED_VALUE = 0.15F
        fun getInstance() = HomeFragment()
    }
}
