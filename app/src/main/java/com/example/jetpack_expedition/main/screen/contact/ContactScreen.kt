package com.example.jetpack_expedition.main.screen.contact

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack_expedition.main.screen.contact.viewmodel.ContactsListViewModel
import com.example.jetpack_expedition.common.ui.DoubleCircleDivider
import com.example.jetpack_expedition.main.screen.contact.view.ContactHeaderView
import com.example.jetpack_expedition.main.screen.contact.view.ContactsListView

@Composable
fun ContactScreen(
    onAddContactCall: () -> Unit,
    onEditContactCall: () -> Unit,
    contactsListViewModel: ContactsListViewModel = viewModel(),
) {
    Log.d("CompositionTest", "ContactScreen")

    Column (
        modifier = Modifier
            .padding(all = 15.dp)
            .background(color = Color.White)
            .fillMaxHeight()
    ) {
        // 연락처 추가, 검색 헤더
        ContactHeaderView(onAddContactCall, onEditContactCall)

        Spacer(modifier = Modifier.height(12.dp))

        DoubleCircleDivider()
        
        ContactsListView(contacts = contactsListViewModel.getContacts())

    }
}
