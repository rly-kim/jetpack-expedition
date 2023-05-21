package com.example.jetpack_expedition.main.recent.ui.state

abstract class PullToRefreshUIState

class PullToRefreshIdle : PullToRefreshUIState() {}

class PullToRefreshInProgress: PullToRefreshUIState() {}
