package com.abzagabekov.tournamentapp.pojo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Tournament::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tournament"),
        onDelete = ForeignKey.CASCADE)
), indices = arrayOf(
    Index(value = arrayOf("tournament"), name = "FirstIndex")
))
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    var goalsScored: Int = 0,
    var goalsConceded: Int = 0,
    val tournament: Long,
    val totalPoints: Int = 0
) {
    override fun toString(): String {
        return name
    }
}