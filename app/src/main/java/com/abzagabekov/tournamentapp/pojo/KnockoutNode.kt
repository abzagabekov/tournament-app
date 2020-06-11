package com.abzagabekov.tournamentapp.pojo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by abzagabekov on 11.06.2020.
 * email: abzagabekov@gmail.com
 */

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Tournament::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tournament"),
        onDelete = ForeignKey.CASCADE)
), indices = arrayOf(
    Index(value = arrayOf("tournament"), name = "NodeTournamentIndex")
))
data class KnockoutNode(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var parent: Long? = 0,
    var name: String,
    val tournament: Long
)