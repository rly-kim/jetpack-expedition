package com.example.jetpack_expedition.main.screen.contact.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.example.jetpack_expedition.common.ui.DoubleCircleDivider
import com.example.jetpack_expedition.domain.entity.contact.Contact

@Composable
fun ContactsListView(
    contacts: List<Contact>
) {
    LazyColumn {
        itemsIndexed(contacts) {index, contact ->
            Column {
                ContactItemView(contact = contact)
                if (index != contacts.size - 1) {
                    DoubleCircleDivider()
                }
            }
        }

    }
}