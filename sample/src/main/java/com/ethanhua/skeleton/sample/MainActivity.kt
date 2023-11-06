package com.ethanhua.skeleton.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ethanhua.skeleton.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnList.setOnClickListener {
            RecyclerViewActivity.start(this, RecyclerViewActivity.TYPE_LINEAR)
        }
        binding.btnGrid.setOnClickListener {
            RecyclerViewActivity.start(this, RecyclerViewActivity.TYPE_GRID)
        }
        binding.btnView.setOnClickListener {
            ViewActivity.start(this, ViewActivity.TYPE_VIEW)
        }
        binding.btnImgloading.setOnClickListener {
            ViewActivity.start(this, ViewActivity.TYPE_IMG_LOADING)
        }
        binding.btnStatus.setOnClickListener {
            startActivity(Intent(this, StatusViewActivity::class.java))
        }
    }
}