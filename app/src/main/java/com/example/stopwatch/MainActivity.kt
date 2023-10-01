package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.R.id
import com.example.stopwatch.R.layout

class MainActivity : AppCompatActivity() {
    private lateinit var startbtn: Button
    private lateinit var time: TextView
    private lateinit var runnable: Runnable

    private var minutes = 0
    private var seconds = 0
    private var milliseconds = 0
    private var isRunning = false
    private lateinit var pausebtn: Button
    private lateinit var resetbtn: Button
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        time = findViewById(id.textView8)
        startbtn = findViewById(id.startbtn)
        pausebtn = findViewById(id.pausebtn)
        resetbtn = findViewById(id.resetbtn)

        startbtn.setOnClickListener {
            if (!isRunning) {
                startTimer()
                isRunning = true
            }
        }

        resetbtn.setOnClickListener {
            resetTimer()
        }

        pausebtn.setOnClickListener {
            if (isRunning) {
                pauseTimer()
                pausebtn.text = "Resume"
            } else {
                pausebtn.text = "Pause"
                resumeTimer()
            }
        }
    }

    private fun startTimer() {
        runnable = Runnable {
            milliseconds++
            if (milliseconds == 100) {
                milliseconds = 0
                seconds++
            }
            if (seconds == 60) {
                seconds = 0
                minutes++
            }
            val final_time = String.format("%02d:%02d.%02d", minutes, seconds, milliseconds)
            time.text = final_time
            handler.postDelayed(runnable, 100)
        }
        handler.post(runnable)
    }

    private fun resumeTimer() {
        isRunning = true
        startTimer()
    }

    private fun pauseTimer() {
        handler.removeCallbacks(runnable)
        isRunning = false
    }

    private fun resetTimer() {
        milliseconds = 0
        seconds = 0
        minutes = 0
        handler.removeCallbacks(runnable)
        time.text = "00:00.00"
        startbtn.text = "Start"
        isRunning = false
    }
}
