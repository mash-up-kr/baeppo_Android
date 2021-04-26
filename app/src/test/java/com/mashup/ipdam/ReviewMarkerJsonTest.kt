package com.mashup.ipdam

import com.google.gson.Gson
import com.mashup.ipdam.data.ReviewMarkersByMap
import org.junit.Assert.assertEquals
import org.junit.Test

class ReviewMarkerJsonTest {

    @Test
    fun test_reviewMarker_json() {
        val json = """
            [
            	{
            		"id": 0,
            		"latitude": 132.1,
            		"longitude": 128.1,
            		"count": 0
            	}
            ]
        """.trimIndent()

        val gson = Gson()
        val reviewMarkers = gson.fromJson(json, ReviewMarkersByMap::class.java)

        assertEquals(reviewMarkers.isEmpty(), false)
        assertEquals(reviewMarkers[0].count, 0)
        assertEquals(reviewMarkers[0].latitude, 132.1, 0.0)
    }
}