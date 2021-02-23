package com.mashup.ipdam.map

import android.graphics.PointF
import android.util.Log
import com.mashup.base.BaseFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentMapBinding
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    override var logTag: String = "MapFragment"

    private lateinit var map: NaverMap

    override fun initLayout() {
        binding.map.getMapAsync(this)
        getMapBoundsOnScreen()
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

        Log.d(logTag, "topLeft: $topLeftCoord topRight: $topRightCoord" +
                "bottomLeft: $bottomLeftCoord bottomRight: $bottomRightCoord")
    }

    /**
     * @return IntArray : 절대 좌표 x, y
     */
    private fun getMapLocation() = intArrayOf(2).apply {
        binding.map.getLocationOnScreen(this)
    }

    override fun onMapReady(map: NaverMap) {
        this.map = map
    }
}