package com.mashup.ipdam.home

import android.graphics.PointF
import androidx.fragment.app.activityViewModels
import com.mashup.base.BaseFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentHomeBinding
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), OnMapReadyCallback {

    override var logTag: String = "HomeFragment"

    private lateinit var map: NaverMap
    private val viewModel by activityViewModels<HomeViewModel>()

    override fun initLayout() {
        binding.map.getMapAsync(this)
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

    override fun onMapReady(map: NaverMap) {
        this.map = map.apply {
            minZoom = MIN_MAX_ZOOM
            maxZoom = MAP_MAX_ZOOM
        }
        initMapListener()
        initMapUi()
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
            isLocationButtonEnabled = true
            isScaleBarEnabled = false
        }
    }

    companion object {
        const val MAP_MAX_ZOOM = 18.0
        const val MIN_MAX_ZOOM = 5.0
    }
}