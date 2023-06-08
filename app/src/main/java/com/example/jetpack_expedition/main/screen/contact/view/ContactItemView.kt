package com.example.jetpack_expedition.main.screen.contact.view

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jetpack_expedition.domain.entity.contact.Contact

@Composable
fun ContactItemView(
    contact: Contact
) {
//    val context = LocalContext.current
//    val permissionsLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            Log.d("PermissionTest", "PERMISSION GRANTED")
//            val intentCall = Intent(Intent.ACTION_CALL)
//            intentCall.data = Uri.parse("tel:${contact.number}")
//            context.startActivity(intentCall)
//        }
//        else {
//            Log.d("PermissionTest", "PERMISSION DENIED")
//        }
//    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp),
            imageVector = Icons.Default.Person,
            contentDescription = ""
        )

        Column() {
            Text(text = contact.name)
            Text(text = contact.number)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(
                // 전화
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = ""
                )
            }
            IconButton(
                // 수정
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = ""
                )
            }
        }

    }

}
