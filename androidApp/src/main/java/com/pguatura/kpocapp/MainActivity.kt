package com.pguatura.kpocapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.pguatura.kpoc.api.ApplicationApi
import com.pguatura.kpoc.api.createApplicationScreenMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val api = ApplicationApi()

        api.about {
            GlobalScope.apply {
                launch(Dispatchers.Main) {
                    findViewById<TextView>(R.id.teste).text = it
                }
            }
        }
        findViewById<TextView>(R.id.teste).text = createApplicationScreenMessage()
    }
}
