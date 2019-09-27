package com.pguatura.kpocapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pguatura.kpoc.api.ApplicationApi
import com.pguatura.kpoc.api.createApplicationScreenMessage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val api = ApplicationApi()

        api.jobs {
            findViewById<TextView>(R.id.teste).text = it
        }
        findViewById<TextView>(R.id.teste).text = createApplicationScreenMessage()
    }
}
