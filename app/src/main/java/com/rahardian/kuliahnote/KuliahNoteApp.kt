package com.rahardian.kuliahnote

import android.app.Application
import com.rahardian.kuliahnote.data.db.KuliahDatabase

class KuliahNoteApp : Application() {
    val database: KuliahDatabase by lazy { KuliahDatabase.getDatabase(this) }
}
