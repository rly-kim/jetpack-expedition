package com.example.jetpack_expedition.main.record.state

/// 녹음 파일 상태
///     ㄴ 초기 상태  (RecordingInitState)
///     ㄴ 진행 상태 (abstract RecordInProgressState)
///         ㄴ 재생 중 상태 (RecordingInTemporalPlayState)
///         ㄴ 일시 정지 상태 (RecordingInPauseState)

abstract class RecordProcessState {
    abstract val whoIsPlaying: Int
}

class RecordingInitState : RecordProcessState() {
    override val whoIsPlaying: Int
        get() = -1
}

abstract class RecordInProgressState : RecordProcessState() {
    abstract val elapsedTime: Float

    abstract fun copyState(elapsedTime: Float): RecordInProgressState
}

class RecordingInPauseState(override val whoIsPlaying: Int, override val elapsedTime: Float) :
    RecordInProgressState() {
    override fun copyState(elapsedTime: Float): RecordInProgressState {
        return RecordingInPauseState(whoIsPlaying, elapsedTime)
    }

}

class RecordingInTemporalPlayState(
    override val whoIsPlaying: Int,
    override val elapsedTime: Float = 0f
) : RecordInProgressState() {
    override fun copyState(elapsedTime: Float): RecordInProgressState {
        return RecordingInTemporalPlayState(whoIsPlaying, elapsedTime)
    }

}
