package ru.kostenko.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {
    lateinit var stopwatch: Chronometer
    var runnig = false
    var offset: Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatch = findViewById<Chronometer>(R.id.stopwatch)

        if(savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            runnig = savedInstanceState.getBoolean(RUNNING_KEY)
            if (runnig){
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }
            else{
             setBaseTime()
            }
        }

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{
            if(!runnig) {
                setBaseTime()
                stopwatch.start()
                runnig = true
            }
        }

        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener{
            if(runnig){
                saveOffset()
                stopwatch.stop()
                runnig = false
            }
        }

        val reset = findViewById<Button>(R.id.reset_button)
        reset.setOnClickListener{
            offset = 0
            setBaseTime()
        }

    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, runnig)
        savedInstanceState.putLong(BASE_KEY, stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    fun setBaseTime(){
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    fun saveOffset(){
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }


}