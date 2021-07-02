package com.dzungvu.testvowao.utils

import com.dzungvu.testvowao.models.EPGGeneral

object DataUtils {

    fun createListData(): List<EPGGeneral> {
        return arrayListOf<EPGGeneral>().apply {
            for (count in 0..10) {
                add(
                    EPGGeneral(
                        "Thời sự buổi sáng",
                        "6:00 - 7:30",
                        "Kênh 01",
                        "TIẾP THEO  9:00  Sức khoẻ - công việc",
                        "TIẾP THEO  9:15  Thời gian 17s"
                    )
                )
            }
        }
    }
}