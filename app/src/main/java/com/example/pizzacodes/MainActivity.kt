package com.example.pizzacodes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pizzacodes.database.AppDatabase
import com.example.pizzacodes.database.Code
import java.lang.Exception
import java.lang.NullPointerException
import java.net.URL

class MainActivity : AppCompatActivity(), IListItemClickListener {
    private val TAG: String = "MainActivity_2"
    
    private lateinit var mButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: CodeListAdapter
    private lateinit var mDatabase: AppDatabase
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        mDatabase = AppDatabase.getDatabase(this)
        mainViewModel = ViewModelProvider(this,
                MainViewModelFactory(mDatabase))
                .get(MainViewModel::class.java)

        mAdapter = CodeListAdapter(ArrayList<Code>(), this)
        recyclerView = findViewById(R.id.rv_codes)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        mainViewModel.allCodes.observe(this, Observer { codes ->
            Log.d(TAG, "Observe method of viewModel")
            //for (code: Code in codes)
            //    Log.d(TAG, code.toString())
            mAdapter.setData(codes)
        })

        mButton = findViewById(R.id.button)
        mButton.setOnClickListener {
            try {
                LoadCodesAsyncTask(mDatabase).execute("https://www.papajohns.by/api/stock/codes")
            }
            catch (exception: Exception)
            {
                Toast.makeText(this, "Error happened while loading codes. =(", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_settings -> {
                openSettings()
                return true
            }
        }
        return false
    }

    private fun openSettings(){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun onListItemClick(code: Code) {
        intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", code.id)
        startActivity(intent)
    }
}

