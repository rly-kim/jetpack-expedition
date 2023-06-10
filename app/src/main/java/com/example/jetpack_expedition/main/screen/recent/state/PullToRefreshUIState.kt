package com.example.jetpack_expedition.main.screen.recent.state

sealed class PullToRefreshUIState

// 상태변수가 있거나 equals를 override할 때만 class로 sub-class를 만들라고 함..
object PullToRefreshIdle : PullToRefreshUIState()

object PullToRefreshInProgress : PullToRefreshUIState()

object PullToRefreshFetched: PullToRefreshUIState()
