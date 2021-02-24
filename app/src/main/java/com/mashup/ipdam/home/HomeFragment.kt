package com.mashup.ipdam.home

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.graphics.PointF
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.activity.result.contract.ActivityResultContracts.*
import com.mashup.base.BaseFragment
import com.mashup.base.ext.checkSelfPermissionCompat
import com.mashup.base.ext.shouldShowRequestPermissionRationaleCompat
import com.mashup.base.ext.toast
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentHomeBinding
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), OnMapReadyCallback {

    override var logTag: String = "HomeFragment"

    private lateinit var map: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val viewModel by activityViewModels<HomeViewModel>()

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
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        binding.map.getMapAsync(this)
    }

    override fun onMapReady(map: NaverMap) {
        this.map = map.apply {
            minZoom = MIN_MAX_ZOOM
            maxZoom = MAP_MAX_ZOOM
            locationSource = locationSource
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

    private fun getMapBoundsOnScreen() {
        val locationOnScreen = getMapLocation()
        val topLeftPointF = PointF(0f, 0f)
        val bottomLeftPointF = PointF(0f, locationOnScreen[1].toFloat())
        val topRightPointF = PointF(locationOnScreen[0].toFloat(), 0f)
        val bottomRightPointF = PointF(locationOnScreen[0].toFloat(), locationOnScreen[1].toFloat())

        val projection = map.projection
        val topLeftCoord = projection.fromScreenLocation(topLeftPointF)
        val topRightCoord = projection.fromScreenLocation(topRightPointF)
        val bottomLeftCoord = projection.fromScreenLocation(bottomLeftPointF)
        val bottomRightCoord = projection.fromScreenLocation(bottomRightPointF)

    }

    private fun getMapLocation() = intArrayOf(0, 0).apply {
        binding.map.getLocationOnScreen(this)
        this[0] += binding.map.width
        this[1] += binding.map.height
    }

    private fun initMapListener() {
        map.setOnSymbolClickListener { symbol ->
            viewModel.showIpdamInfo(symbol.position)
            false
        }

        map.addOnCameraIdleListener {
            viewModel.getIpdamList()
        }
    }

    private fun initMapUi() {
        map.uiSettings.apply {
            isZoomControlEnabled = false
            isScaleBarEnabled = false
            isLocationButtonEnabled = false
        }

        binding.locationView.map = map
    }

    private fun hideLocationButton() {
        binding.locationView.visibility = View.GONE
        binding.locationView.map = null
        map.locationTrackingMode = LocationTrackingMode.None
    }

    private fun showLocationButton() {
        locationSource.onRequestPermissionsResult(
            LOCATION_PERMISSION_REQUEST_CODE,
            arrayOf(LOCATION_MAP_PERMISSION), intArrayOf(PackageManager.PERMISSION_GRANTED)
        )
        binding.locationView.visibility = View.VISIBLE
        binding.locationView.map = map
        map.locationTrackingMode = LOCATION_TRACKING_MODE
    }

    companion object {
        private const val LOCATION_MAP_PERMISSION = ACCESS_FINE_LOCATION
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private val LOCATION_TRACKING_MODE = LocationTrackingMode.Follow
        private const val MAP_MAX_ZOOM = 18.0
        private const val MIN_MAX_ZOOM = 5.0
    }
}