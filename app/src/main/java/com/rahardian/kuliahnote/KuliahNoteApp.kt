package com.rahardian.kuliahnote

import android.app.Application
import android.util.Log
import com.rahardian.kuliahnote.data.db.KuliahDatabase

class KuliahNoteApp : Application() {
    val database: KuliahDatabase by lazy {
        try {
            KuliahDatabase.getDatabase(this)
        } catch (e: Exception) {
            Log.e("KuliahNote", "DB init failed", e)
            throw e
        }
    }

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("KuliahNote", "Uncaught exception in thread ${thread.name}", throwable)
            throw throwable
        }
    }
}
