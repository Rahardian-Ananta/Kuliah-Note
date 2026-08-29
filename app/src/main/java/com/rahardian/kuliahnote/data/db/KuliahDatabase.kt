package com.rahardian.kuliahnote.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rahardian.kuliahnote.data.db.dao.*
import com.rahardian.kuliahnote.data.db.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Semester::class, Course::class, Week::class, Note::class, Task::class, CalendarEvent::class],
    version = 1,
    exportSchema = false
)
abstract class KuliahDatabase : RoomDatabase() {
    abstract fun semesterDao(): SemesterDao
    abstract fun courseDao(): CourseDao
    abstract fun weekDao(): WeekDao
    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
    abstract fun calendarEventDao(): CalendarEventDao

    companion object {
        @Volatile
        private var INSTANCE: KuliahDatabase? = null

        fun getDatabase(context: Context): KuliahDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KuliahDatabase::class.java,
                    "kuliah_note_db"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    seedData(database)
                }
            }
        }

        private suspend fun seedData(database: KuliahDatabase) {
            val semesterDao = database.semesterDao()
            val courseDao = database.courseDao()
            val weekDao = database.weekDao()
            val noteDao = database.noteDao()
            val taskDao = database.taskDao()
            val eventDao = database.calendarEventDao()

            val semesterId = semesterDao.insert(
                Semester(name = "Semester 5", startDate = "2024-08-19", endDate = "2025-01-15")
            )

            val mlId = courseDao.insert(
                Course(semesterId = semesterId, name = "Machine Learning", color = "#4D96FF", description = "Fondasi dan penerapan Machine Learning")
            )
            val psdId = courseDao.insert(
                Course(semesterId = semesterId, name = "Proyek Sains Data", color = "#6BCB77", description = "Proyek analisis data")
            )
            val techId = courseDao.insert(
                Course(semesterId = semesterId, name = "Technopreneurship", color = "#FF9F45", description = "Kewirausahaan teknologi")
            )
            val mprId = courseDao.insert(
                Course(semesterId = semesterId, name = "Metode Penelitian", color = "#B08BFF", description = "Metodologi penelitian TIF")
            )
            val srpId = courseDao.insert(
                Course(semesterId = semesterId, name = "Sistem Rekomendasi & Personalisasi", color = "#FF6B9D", description = "Sistem rekomendasi berbasis konten dan kolaboratif")
            )
            val stdId = courseDao.insert(
                Course(semesterId = semesterId, name = "Sistem Terdistribusi", color = "#FFD93D", description = "Arsitektur dan implementasi sistem terdistribusi")
            )
            val pplId = courseDao.insert(
                Course(semesterId = semesterId, name = "Proyek Perangkat Lunak", color = "#FF4444", description = "Manajemen proyek perangkat lunak")
            )

            val mlWeek1 = weekDao.insert(Week(courseId = mlId, weekNumber = 1, title = "Fondasi ML"))
            val mlWeek2 = weekDao.insert(Week(courseId = mlId, weekNumber = 2, title = "Linear Regression"))
            val psdWeek1 = weekDao.insert(Week(courseId = psdId, weekNumber = 1, title = "Proyek Awal"))
            val techWeek1 = weekDao.insert(Week(courseId = techId, weekNumber = 1, title = "Kontrak Kuliah"))
            val mprWeek1 = weekDao.insert(Week(courseId = mprId, weekNumber = 1, title = "Metodologi Dasar"))
            val srpWeek1 = weekDao.insert(Week(courseId = srpId, weekNumber = 1, title = "User Based Filtering"))
            val stdWeek1 = weekDao.insert(Week(courseId = stdId, weekNumber = 1, title = "Pengantar"))
            val pplWeek1 = weekDao.insert(Week(courseId = pplId, weekNumber = 1, title = "Sprint Planning"))

            noteDao.insert(Note(weekId = mlWeek1, title = "Fondasi Machine Learning", content = "## Fondasi ML\n\n- **Supervised Learning**: belajar dari data berlabel\n- **Unsupervised Learning**: menemukan pola tanpa label\n- **Training**: proses model belajar dari data\n- **Overfitting**: model terlalu cocok dengan data training\n\n> Workflow ML: Problem → Data Preparation → Training → Validation → Evaluate"))
            noteDao.insert(Note(weekId = psdWeek1, title = "Proyek Sains Data", content = "## Proyek Sains Data\n\nTarget: Analisis data polutan\n\n⚠️ **Kesalahan**: Analisis hanya 4 polutan, seharusnya 6 polutan\n\n### Polutan yang harus dianalisis:\n1. PM2.5\n2. PM10\n3. SO2\n4. NO2\n5. CO\n6. O3"))
            noteDao.insert(Note(weekId = techWeek1, title = "Kontrak Kuliah", content = "## Technopreneurship\n\n📌 **Target bisnis: Rp2 juta**\n\n- Business Understanding harus dipisah dari Data Understanding\n- Fokus pada validasi ide bisnis"))
            noteDao.insert(Note(weekId = mprWeek1, title = "Metodologi Penelitian", content = "## Metode Penelitian\n\n### 8 Karakteristik Penelitian\n1. Sistematis\n2. Terencana\n3. Berk濂lanjutan\n4. Berulang\n5. Dapat diverifikasi\n6. Objektif\n7. Dapat direplikasi\n8. Memiliki manfaat\n\n### 6 Bidang TIF\n- Artificial Intelligence\n- Software Engineering\n- Computer Network\n- Database\n- Graphics\n- Security"))
            noteDao.insert(Note(weekId = srpWeek1, title = "User Based Filtering", content = "## Sistem Rekomendasi\n\n### User Based Collaborative Filtering\n- Menggunakan **PCC (Pearson Correlation Coefficient)**\n- Mengukur kesamaan antar user\n\n### PCC Formula\nr = Σ(xi - x̄)(yi - ȳ) / √Σ(xi - x̄)² × √Σ(yi - ȳ)²"))

            taskDao.insert(Task(weekId = mprWeek1, title = "Cari 3 paper", deadline = "2024-09-03"))
            taskDao.insert(Task(weekId = psdWeek1, title = "Revisi analisis 6 polutan", deadline = "2024-09-02"))
            taskDao.insert(Task(weekId = techWeek1, title = "Siapkan proposal bisnis", deadline = "2024-09-05"))

            eventDao.insert(CalendarEvent(date = "2024-08-25", title = "Libur Maulid Nabi", isHoliday = true))
            eventDao.insert(CalendarEvent(date = "2024-08-26", title = "Kuliah ML & PPL", isHoliday = false))
            eventDao.insert(CalendarEvent(date = "2024-08-27", title = "Kuliah MPR, SRP, STD", isHoliday = false))
            eventDao.insert(CalendarEvent(date = "2024-08-28", title = "Kuliah PSD & Tech", isHoliday = false))
        }
    }
}
