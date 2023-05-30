package com.example.jetpack_expedition.data.recent

import com.example.jetpack_expedition.domain.entity.recent.Call

object RecentHistorySampleData {
    var calls = listOf(
        Call("오란다", "01012122020", "12:20"),
        Call("미란다", "01029292939", "12:20"),
        Call("김란다", "01012122020", "12:20"),
        Call(phoneNumber = "01012122020", callTime = "12:20"),
        Call("최란다", "01089891212", "12:20"),
        Call("이란다", "01012122020", "12:20"),
        Call(phoneNumber = "01012122020", callTime = "12:20"),
        Call("최란다", "01089891212", "12:20"),
        Call("란다", "01012122020", "12:20"),
        Call("차란다", "01012122020", "12:20"),
        Call("구란다", "01039385822", "12:20"),
    )
}
