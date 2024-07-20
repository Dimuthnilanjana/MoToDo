package com.example.motodoui

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.IOException

class MeditationActivity : AppCompatActivity() {
    private lateinit var mediaPlayer1: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private var isPlaying1 = false
    private var isPlaying2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)

        val playButton1: Button = findViewById(R.id.playButton1)
        val pauseButton1: Button = findViewById(R.id.pauseButton1)
        val seekBar1: SeekBar = findViewById(R.id.seekBar1)

        val playButton2: Button = findViewById(R.id.playButton2)
        val pauseButton2: Button = findViewById(R.id.pauseButton2)
        val seekBar2: SeekBar = findViewById(R.id.seekBar2)

        mediaPlayer1 = MediaPlayer()
        mediaPlayer2 = MediaPlayer()

        val storage = Firebase.storage
        val song1Ref = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/motodoui.appspot.com/o/meditation-blue-138131.mp3?alt=media&token=a01175e1-6477-4a93-a4bb-2e2c2df5a0ed")
        val song2Ref = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/motodoui.appspot.com/o/medimusic.mp3?alt=media&token=9a1a9f69-603b-48aa-8925-b93779b56de9")

        // Load and play the first song
        song1Ref.downloadUrl.addOnSuccessListener { uri ->
            try {
                Log.d("MeditationActivity", "Song 1 URL: $uri")
                mediaPlayer1.setDataSource(uri.toString())
                mediaPlayer1.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load song 1", Toast.LENGTH_SHORT).show()
                Log.e("MeditationActivity", "Error loading song 1: ${e.message}")
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load song 1", Toast.LENGTH_SHORT).show()
            Log.e("MeditationActivity", "Error loading song 1 from Firebase: ${it.message}")
        }

        // Load and play the second song
        song2Ref.downloadUrl.addOnSuccessListener { uri ->
            try {
                Log.d("MeditationActivity", "Song 2 URL: $uri")
                mediaPlayer2.setDataSource(uri.toString())
                mediaPlayer2.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load song 2", Toast.LENGTH_SHORT).show()
                Log.e("MeditationActivity", "Error loading song 2: ${e.message}")
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load song 2", Toast.LENGTH_SHORT).show()
            Log.e("MeditationActivity", "Error loading song 2 from Firebase: ${it.message}")
        }

        playButton1.setOnClickListener {
            if (!isPlaying1) {
                mediaPlayer1.start()
                isPlaying1 = true
            }
        }

        pauseButton1.setOnClickListener {
            if (isPlaying1) {
                mediaPlayer1.pause()
                isPlaying1 = false
            }
        }

        playButton2.setOnClickListener {
            if (!isPlaying2) {
                mediaPlayer2.start()
                isPlaying2 = true
            }
        }

        pauseButton2.setOnClickListener {
            if (isPlaying2) {
                mediaPlayer2.pause()
                isPlaying2 = false
            }
        }

        seekBar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer1.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer2.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer1.release()
        mediaPlayer2.release()
    }
}
