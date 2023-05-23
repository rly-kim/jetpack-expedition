package com.example.jetpack_expedition

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack_expedition.main.MainScreen2
import com.example.jetpack_expedition.main.MainViewModel
import com.example.jetpack_expedition.main.record.viewmodel.RecordDataViewModel
import com.example.jetpack_expedition.ui.theme.JetpackexpeditionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = MainViewModel()
        val recordDataViewModel = RecordDataViewModel(this.application, Environment.getExternalStorageDirectory().absolutePath)
        setContent {
            JetpackexpeditionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen2(mainViewModel = mainViewModel, recordDataViewModel = recordDataViewModel)
                }
            }
        }
    }
}
