package com.tifuseini.timefighter

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var gameScoreTextView : TextView
    private lateinit var timeLeftTextView: TextView
    private lateinit var tapMeButton : Button

    private var score = 0

    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    private  var initialCountDown: Long = 60000
    private  var countDownInterval : Long = 1000
    private var timeleft = 60




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)

        tapMeButton.setOnClickListener{ view ->
            val bounceAnimation = AnimationUtils.loadAnimation(
                this,R.anim.bounce
            )
            view.startAnimation(bounceAnimation)
            incrementScore()
        }

        resetGame()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_item) {
            showInfo()
        }

        return true
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title,
            BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }

    private fun incrementScore(){

        if (!gameStarted){
            startGame()
        }
        score++

        val newScore = getString(R.string.your_score,score)
        gameScoreTextView.text =newScore

    }

    private fun resetGame() {
        score= 0

        val initialScore = getString(R.string.your_score,score)
        gameScoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left,60)
        timeLeftTextView.text = initialTimeLeft

        countDownTimer = object : CountDownTimer(initialCountDown,countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeleft = millisUntilFinished.toInt()/1000

                val timeLeftString = getString(R.string.time_left,timeleft)
                timeLeftTextView.text = timeLeftString

            }

            override fun onFinish() {
                endGame()
            }
        }

        gameStarted = false

    }
    private fun startGame() {

        countDownTimer.start()
        gameStarted = true

    }
    private fun endGame() {
        Toast.makeText(
            this,getString(R.string.game_over_message,score),
            Toast.LENGTH_LONG).show()
        resetGame()
    }
}