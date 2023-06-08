package com.example.jetpack_expedition.main.screen.contact.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetpack_expedition.domain.entity.contact.Contact

class ContactViewModel(
) : ViewModel() {
    private val _name = mutableStateOf("")
    val name = _name
    private val _number = mutableStateOf("")
    val number = _number
    private val _enabled = mutableStateOf(false)
    val enabled = _enabled



    fun validate() {
        _enabled.value = _name.value.length >= 2 && _number.value.length == 11
    }

    fun giveHint(): String {
        return if (_name.value.length < 2) {
            "이름은 2글자 이상이어야 합니다."
        } else {
            "전화번호 11자리를 정확히 입력해 주세요."
        }
    }

}