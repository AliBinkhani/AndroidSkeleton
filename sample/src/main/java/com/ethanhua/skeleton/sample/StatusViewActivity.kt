package com.ethanhua.skeleton.sample

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ethanhua.skeleton.ViewReplacer
import com.ethanhua.skeleton.sample.databinding.ActivityStatusViewBinding

class StatusViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatusViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewReplacer = ViewReplacer(binding.tvContent)
        binding.btnLoading.setOnClickListener {
            viewReplacer.replace(R.layout.layout_progress)
        }
        binding.btnError.setOnClickListener {
            viewReplacer.replace(R.layout.layout_error)
        }
        binding.btnEmpty.setOnClickListener {
            viewReplacer.replace(R.layout.layout_empty_view)
        }
        binding.btnContent.setOnClickListener {
            viewReplacer.restore()
        }
    }

    fun gotoSet(view: View) {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }
}