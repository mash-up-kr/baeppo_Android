package com.mashup.ipdam.ui.home

import android.content.pm.PackageManager
import android.graphics.PointF
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.view.marginTop
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mashup.base.BaseFragment
import com.mashup.base.ext.checkSelfPermissionCompat
import com.mashup.base.ext.shouldShowRequestPermissionRationaleCompat
import com.mashup.base.ext.toast
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.R
import com.mashup.ipdam.data.map.MapBoundary
import com.mashup.ipdam.data.map.MapConstants.LOCATION_MAP_PERMISSION
import com.mashup.ipdam.data.map.MapConstants.LOCATION_PERMISSION_REQUEST_CODE
import com.mashup.ipdam.data.map.MapConstants.LOCATION_TRACKING_MODE
import com.mashup.ipdam.data.map.MapConstants.MAP_MAX_ZOOM
import com.mashup.ipdam.data.map.MapConstants.MIN_MAX_ZOOM
import com.mashup.ipdam.databinding.FragmentHomeBinding
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

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
        binding.map.getMapAsync(this)
        initBottomSheet()
    }

    private fun initBottomSheet() {

        BottomSheetBehavior.from(binding.bottomSheet.root)
            .addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    //TODO: 입담 리뷰 세세한 리스트 화면에 보여주기
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
    }

    override fun observeViewModel() {
        homeViewModel.ipdamDialogEvent
            .observeOn(SchedulerProvider.ui())
            .subscribe { event ->
                if (event) {
                    showIpdamBottomSheet()
                } else {
                    hideIpdamBottomSheet()
                }
            }.addToDisposable()
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
        val bottomRightPointF = PointF(locationOnScreen[0].toFloat(), locationOnScreen[1].toFloat())

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
        myMap.addOnCameraIdleListener { homeViewModel.getIpdamInBoundary(getMapBoundaryOnScreen()) }
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

    private fun showIpdamBottomSheet() {
        setIpdamBottomSheetHeight()
        BottomSheetBehavior.from(binding.bottomSheet.root).run {
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setIpdamBottomSheetHeight() {
        val bottomSheetLayoutParams = binding.bottomSheet.root.layoutParams.apply {
            val bottomSheetHeight = binding.root.height - binding.searchView.height -
                    binding.searchView.marginTop -
                    resources.getDimension(R.dimen.margin_bottom_search)
            height = bottomSheetHeight.toInt()
        }
        binding.bottomSheet.root.layoutParams = bottomSheetLayoutParams
    }

    private fun hideIpdamBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet.root).run {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    companion object {
        fun getInstance() = HomeFragment()
    }
}