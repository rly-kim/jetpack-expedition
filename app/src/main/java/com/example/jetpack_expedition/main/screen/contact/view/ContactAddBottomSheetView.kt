package com.example.jetpack_expedition.main.screen.contact.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack_expedition.main.screen.contact.viewmodel.ContactViewModel

@Composable
fun ContactAddBottomSheetView(
    popBack: () -> Unit,
    contactViewModel: ContactViewModel = viewModel(),
) {
    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    // pop
                    popBack()
                }
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "뒤로가기",
                )
            }

            Text(
                modifier = Modifier.weight(1f),
                text = "연락처 추가"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = contactViewModel.name.value,
                label = { Text(text = "이름") },
                singleLine = true,
                onValueChange = {
                    contactViewModel.name.value = it
                    contactViewModel.validate()
                }
            )

            Spacer(Modifier.height(15.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                value = contactViewModel.number.value,
                label = { Text(text = "전화번호") },
                singleLine = true,
                onValueChange = {
                    contactViewModel.number.value = it
                    contactViewModel.validate()
                }
            )

        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        Button(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth(),
            onClick = {
                if(contactViewModel.enabled.value) {
                    // 저장 후 pop
                    // TODO : ContactsListViewModel.add(?) 저장
                    popBack()
                }
                else {
                    openDialog.value = !openDialog.value
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (contactViewModel.enabled.value) MaterialTheme.colors.primary else Color.LightGray,
                contentColor = if (contactViewModel.enabled.value) Color.White else Color.Gray
            )
        ) {
            Text(
                text = "저장",
                fontSize = 23.sp,
            )
        }

        Column() {
            val dialogWidth = 220.dp
            val dialogHeight = 70.dp

            if (openDialog.value) {
                Dialog(
                    onDismissRequest = { openDialog.value = false }
                ) {
                    Box(
                        modifier = Modifier
                            .size(dialogWidth, dialogHeight)
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding()
                        ) {
                            Text(text = contactViewModel.giveHint())
                        }
                    }
                }
            }
        }

    }
}

