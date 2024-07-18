package com.example.motodoui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motodoui.databinding.ActivityGameZoneLoginBinding
import com.example.motodoui.databinding.ActivityMainBinding
import com.example.motodoui.databinding.ActivitySignUpBinding

class GameZoneLoginActivity : AppCompatActivity() {

private lateinit var binding: ActivityGameZoneLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_zone_login)

        binding = ActivityGameZoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.memmaster.setOnClickListener {
            val intent = Intent(this, MemoLogActivity::class.java)
            startActivity(intent)
        }


    }
}