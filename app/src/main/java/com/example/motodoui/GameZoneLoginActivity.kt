package com.example.motodoui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motodoui.databinding.ActivityGameZoneLoginBinding
import com.example.motodoui.databinding.ActivityMainBinding

class GameZoneLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameZoneLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_zone_login)

        binding.memmaster.setOnClickListener {
            startActivity(Intent(this@GameZoneLoginActivity,MemoLogActivity::class.java))
        }

    }
}