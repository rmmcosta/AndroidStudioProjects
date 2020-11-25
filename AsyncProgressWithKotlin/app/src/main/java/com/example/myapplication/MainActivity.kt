package com.example.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    private val numFiles = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        progressBar?.max = numFiles
    }

    fun Integer.format(digits: Int) = "%.${digits}f".format(this)

    @RequiresApi(Build.VERSION_CODES.N)
    fun download(view: View) {
        val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            // here the exception is gracefully handled
        }
        GlobalScope.launch(errorHandler) {
            var result: String;
            withContext(Dispatchers.IO) {
                for (x in 1..numFiles) {
                    delay(100)
                    updateProgress(x);
                }
                result = "Download %d files with success!".format(numFiles)
                downloadFinish(result)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateProgress(progress: Int) {
        progressBar?.setProgress(progress, false)
    }

    suspend fun downloadFinish(msg: String) {
        withContext(Dispatchers.Main) {
            val toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG)
            toast.show()
        }
    }
}