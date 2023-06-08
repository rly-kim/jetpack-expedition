package com.example.jetpack_expedition.main.screen.contact.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.jetpack_expedition.domain.entity.contact.Contact

class ContactsListViewModel : ViewModel() {
    private var contacts = mutableStateListOf<Contact>()

    init {
        contacts.addAll(ContactSampleData.contacts)
    }
    fun getContacts(): List<Contact> {
        return contacts
    }
    fun getSize(): Int {
        return contacts.size
    }

    fun addContact(contact: Contact) {
        contacts.add(contact)
    }
    fun removeContact(contact: Contact) {
        contacts.remove(contact)
    }

}