package com.mashup.ipdam.ui.home

import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.activityViewModels
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.view.ViewCompat
import androidx.core.view.doOnLayout
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mashup.base.BaseFragment
import com.mashup.base.BaseRecyclerView
import com.mashup.base.ext.checkSelfPermissionCompat
import com.mashup.base.ext.shouldShowRequestPermissionRationaleCompat
import com.mashup.base.ext.toast
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.R
import com.mashup.ipdam.data.Review
import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.data.map.MapConstants.LOCATION_MAP_PERMISSION
import com.mashup.ipdam.data.map.MapConstants.LOCATION_PERMISSION_REQUEST_CODE
import com.mashup.ipdam.data.map.MapConstants.LOCATION_TRACKING_MODE
import com.mashup.ipdam.data.map.MapConstants.MAP_MAX_ZOOM
import com.mashup.ipdam.data.map.MapConstants.MIN_MAX_ZOOM
import com.mashup.ipdam.databinding.FragmentHomeBinding
import com.mashup.ipdam.databinding.ItemBottomsheetBinding
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.mashup.ipdam.databinding.ItemBottomsheetByMarkerBinding
import kotlin.math.min
import kotlin.math.log

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), OnMapReadyCallback {

    override var logTag: String = "HomeFragment"

    private lateinit var myMap: NaverMap
    private lateinit var mapLocationSource: FusedLocationSource
    private val homeViewModel by activityViewModels<HomeViewModel>()

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

    override fun initLayout() {
        mapLocationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        binding.viewModel = homeViewModel
        binding.bottomSheet.loBottomSheetByMarker.viewModel = homeViewModel
        binding.map.getMapAsync(this)
        initBottomSheet()
        binding.button2.setOnClickListener {
            homeViewModel.getReviewByMarker()
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
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        val changedAlpha = min(
                            ALPHA_MAX,
                            ALPHA_MAX / ALPHA_CHANGED_VALUE * slideOffset
                        )
                        binding.bottomSheet.apply {
                            ipdamHeader.alpha = ALPHA_MAX - changedAlpha
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

        binding.bottomSheet.rvReviews.adapter =
            object : BaseRecyclerView.Adapter<Review, ItemBottomsheetBinding>(
                R.layout.item_bottomsheet,
                BR.review
            ) {}
        binding.bottomSheet.loBottomSheetByMarker.rvThumbnail.adapter = object :
            BaseRecyclerView.Adapter<Review, ItemBottomsheetByMarkerBinding>(
                R.layout.item_bottomsheet_by_marker,
                BR.review
            ) {}
        ViewCompat.setNestedScrollingEnabled(binding.bottomSheet.loBottomSheetByMarker.rvThumbnail, false)
    }

    override fun observeViewModel() {
        homeViewModel.bottomSheetState.observe(this, Observer {
            when (it!!) {
                BottomSheetState.MAP_MOVED -> showIpdamBottomSheetByMap()
                BottomSheetState.MARKER_CLICKED -> showIpdamBottomSheetByMarker()
            }
        })
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
        myMap.setOnSymbolClickListener { symbol ->
            homeViewModel.getIpdamBySymbol(symbol.position)
            false
        }
        myMap.addOnCameraIdleListener {
            homeViewModel.getReviewInBoundary(getMapBoundaryOnScreen())
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

    private fun hideIpdamBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet.root).run {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun setIpdamBottomSheetHeight() {
        binding.bottomSheet.root.updateLayoutParams {
            val bottomSheetHeight = binding.root.height - binding.searchView.height -
                    binding.searchView.marginTop - resources.getDimensionPixelSize(R.dimen.bottom_sheet_marigin_top)
            height = bottomSheetHeight.toInt()
        }
    }

    companion object {
        private const val ALPHA_MAX = 1F
        private const val ALPHA_CHANGED_VALUE = 0.15F
        fun getInstance() = HomeFragment()
    }
}
