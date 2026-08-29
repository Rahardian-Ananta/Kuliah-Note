package com.rahardian.kuliahnote.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = Week::class,
            parentColumns = ["id"],
            childColumns = ["weekId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("weekId")]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val weekId: Long,
    val title: String,
    val isCompleted: Boolean = false,
    val deadline: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
