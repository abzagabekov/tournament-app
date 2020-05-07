package com.abzagabekov.tournamentapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

@Entity
data class Tournament(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val type: String,
    val teamsCount: Int
)