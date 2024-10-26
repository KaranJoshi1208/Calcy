package com.karan.calcy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karan.calcy.screens.Calcy
import com.karan.calcy.ui.theme.CalcyTheme
import com.karan.calcy.ui.theme.DarkGray
import com.karan.calcy.ui.theme.MediumGray
import com.karan.calcy.viewmodel.CalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        window.navigationBarColor = getColor(R.color.dark_grey)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CalcyTheme {
                val viewModel = viewModel<CalViewModel>()
                val btnSpacing = 8.dp

                Calcy(
                    expState = viewModel.exp,
                    ansState = viewModel.ans,
                    onAction = viewModel::onAction,
                    buttonSpacing = btnSpacing,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkGray)
                )
            }
        }
    }
}
