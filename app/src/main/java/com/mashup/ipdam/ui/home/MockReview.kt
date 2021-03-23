package com.mashup.ipdam.ui.home

import com.mashup.ipdam.data.Review

object MockReview {
    fun getMockReviewList() =
        listOf(
            Review(
                "서울빌딩",
                "2020.01.20",
                "dahyun",
                listOf(
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAyMTlfMTM4%2FMDAxNjEzNjYzMDgyMzUz.UmYCoopR8Q8rTZTztLy9dHtZ9ZPZCIhHc8F7aUeQn5og.KfjcQvyb-xKR1G_sb28Mv7E8qZ4yBDz-Rf65j55Qhw0g.JPEG.jskdms007%2FIMG_6114.jpg&type=a340",
                    "dd"
                ),
                false,
                "ss",
                1.5,
                "sss"
            ),
            Review(
                "서울빌딩",
                "2020.01.20",
                "dahyun",
                listOf(
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAxMDhfMTY2%2FMDAxNjEwMTE0ODU5OTA0.AQHbOYKQYzAqXjSEmCXjSlKSPe5D87aRv1fr_opYeOIg.AJbs2vOn5bhicqiHogYAF7MvY4rrwvK8FpOHEPJ7tgog.JPEG.psaa980%2F20210105%25A3%25DF114311.jpg&type=a340",
                    "dd"
                ),
                false,
                "ss",
                4.4,
                "sss"
            ),
            Review(
                "서울빌딩",
                "2020.01.20",
                "dahyun",
                listOf(
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDEyMDFfNTYg%2FMDAxNjA2Nzk5MDE2MDUw.U3qreKXfTgkNnzaPzcCMGjJXv59kacodEEn374dX8sgg.6SCh_iWN6Xiq4trm4A2A0TZraZGZP9lzA3N-oOXigbgg.PNG.monk0531%2Fimage.png&type=a340",
                    "dd"
                ),
                true,
                "ss",
                5.0,
                "sss"
            ),
            Review(
                "보빌딩",
                "2020.01.20",
                "dahyun",
                listOf(
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTAyMDRfMTYx%2FMDAxNjEyNDQzMzkxNzg4.U_ov3cutDLr5SOKQCuqvIWnjhtY1xnyHb6gn2-D6rgEg.hGHrcNgvx9hRTHGpNqLg2j2KBIwufGfv0biR4XCXmsMg.JPEG.vit4566%2FIMG_0387.jpg&type=a340",
                    "dd"
                ),
                false,
                "ss",
                4.4,
                "sss"
            ),
        )
}
