package ca.uqac.etu.jcid.chadal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ca.uqac.etu.jcid.chadal.ui.theme.ChaDalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChaDalTheme {
                ChadalApp()
            }
        }
    }
}