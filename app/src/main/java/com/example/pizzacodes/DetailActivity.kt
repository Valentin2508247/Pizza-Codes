 package com.example.pizzacodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.pizzacodes.database.AppDatabase
import com.example.pizzacodes.database.Code

 class DetailActivity : AppCompatActivity() {
    private val TAG = "DetailActivity2"

    private var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        id = intent.getIntExtra("id", -1)

        if (id != -1){
            val code = AppDatabase.getDatabase(this).codeDao().getCode(id)
            initViews(code)
        }

        findViewById<Button>(R.id.button_detail_ok).setOnClickListener { finish() }
    }

    fun initViews(code: Code){
        findViewById<TextView>(R.id.tv_detail_code).text = code.code
        findViewById<TextView>(R.id.tv_detail_description).text = code.description
        if (code.cost % 100 == 0)
            findViewById<TextView>(R.id.tv_detail_cost).text = "${code.cost / 100}.00"
        else
            findViewById<TextView>(R.id.tv_detail_cost).text = "${code.cost / 100}.${code.cost % 100}"
        findViewById<TextView>(R.id.tv_detail_cities).text = code.cities
    }
}