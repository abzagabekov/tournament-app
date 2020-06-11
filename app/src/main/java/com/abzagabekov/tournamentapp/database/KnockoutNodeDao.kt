package com.abzagabekov.tournamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abzagabekov.tournamentapp.pojo.KnockoutNode

/**
 * Created by abzagabekov on 11.06.2020.
 * email: abzagabekov@gmail.com
 */

@Dao
interface KnockoutNodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNodes(nodes: List<KnockoutNode>)

    @Update
    fun update(node: KnockoutNode)

    @Query("SELECT * FROM `KnockoutNode` WHERE tournament = :id ORDER BY id")
    fun getNodesOfTournament(id: Long): List<KnockoutNode>
}