package com.abzagabekov.tournamentapp.pojo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

@Entity(foreignKeys = arrayOf(
    ForeignKey(entity = Team::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("homeTeam"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Team::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("awayTeam"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Tournament::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tournament"),
        onDelete = ForeignKey.CASCADE)
), indices = arrayOf(
    Index(value = arrayOf("homeTeam"), name = "HomeTeamIndex"),
    Index(value = arrayOf("awayTeam"), name = "AwayTeamIndex"),
    Index(value = arrayOf("tournament"), name = "TournamentIndex")
))
@Parcelize
data class Match(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val homeTeam: Long,
    val awayTeam: Long,
    var homeTeamGoals: Int? = null,
    var awayTeamGoals: Int? = null,
    val tournament: Long
) : Parcelable {
}