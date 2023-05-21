package com.example.jetpack_expedition.main.record.state

/// 음성녹음 idle 화면 -(녹음버튼 클릭)-> 녹음화면 push
/// 녹음화면(idle화면 composable 재사용)
///     ㄴ 녹음 상태
///     ㄴ 일시정지 상태
///     ㄴ 일시 듣기 상태
/// 저장 시 녹음화면 replace 로 녹음 목록 화면

abstract class RecordProcessState

class RecordingState: RecordProcessState() {

}

class RecordingInPauseState: RecordProcessState() {

}

class RecordingInTemporalPlayState: RecordProcessState() {

}
