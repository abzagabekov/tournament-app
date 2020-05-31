package com.abzagabekov.tournamentapp.pojo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

@Entity
@Parcelize
data class Tournament(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val type: String,
    val teamsCount: Int,
    val isTwoLeg: Boolean = false
) : Parcelable