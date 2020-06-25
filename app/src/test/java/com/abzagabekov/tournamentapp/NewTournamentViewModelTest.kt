package com.abzagabekov.tournamentapp

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.abzagabekov.tournamentapp.database.KnockoutNodeDao
import com.abzagabekov.tournamentapp.database.MatchDao
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.database.TournamentDao
import com.abzagabekov.tournamentapp.pojo.Tournament
import com.abzagabekov.tournamentapp.ui.newTournament.InputErrorCodes
import com.abzagabekov.tournamentapp.ui.newTournament.NewTournamentViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by abzagabekov on 24.06.2020.
 * email: abzagabekov@gmail.com
 */

class NewTournamentViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: NewTournamentViewModel

    @Mock
    private lateinit var tournamentDao: TournamentDao

    @Mock
    private lateinit var matchDao: MatchDao

    @Mock
    private lateinit var teamDao: TeamDao

    @Mock
    private lateinit var knockoutNodeDao: KnockoutNodeDao

    @Mock
    private lateinit var observer: Observer<Tournament>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = NewTournamentViewModel(
            tournamentDao,
            matchDao,
            teamDao,
            knockoutNodeDao
        ).apply {
            //eventCreateNewTournament.observeForever{}
            //eventShowErrorMessage.observeForever {  }
            navigateToTournamentMenu.observeForever(observer)
            initViewModel(Resources())
        }
    }

    @Test
    fun createNewTournament_emptyName_showErrorMessage() {
        // given
        viewModel.tournamentType = "LEAGUE"
        val name = ""
        val teamsCount = 10
        val isTwoLeg = false

        // when
        viewModel.createNewTournament(name, teamsCount, isTwoLeg)

        // then
        val result = viewModel.eventShowErrorMessage.value
        assertEquals(InputErrorCodes.EMPTY_FIELDS, result)
    }

    @Test
    fun createNewTournament_incorrectTeamsCount_showErrorMessage() {
        // given
        val tType = "LEAGUE"
        viewModel.tournamentType = tType
        val name = "EPL"
        val teamsCount = 100
        val isTwoLeg = false

        // when
        viewModel.createNewTournament(name, teamsCount, isTwoLeg)

        // then
        val result = viewModel.eventShowErrorMessage.value
        assertEquals(InputErrorCodes.INVALID_TEAMS_COUNT, result)
    }

    @Test
    fun createNewTournament_successCreateNewTournament_sameValueInLiveData() = testCoroutineRule.runBlockingTest {
        //given
        val tType = "LEAGUE"
        viewModel.tournamentType = tType
        val name = "EPL"
        val teamsCount = 10
        val isTwoLeg = false

        val tournament = Tournament(31, name, tType, teamsCount, isTwoLeg)
        whenever(tournamentDao.getLastTournament()).thenReturn(tournament)

        // when
        viewModel.createNewTournament(name, teamsCount, isTwoLeg)

        // then
        val result = viewModel.navigateToTournamentMenu.value
        //assertEquals(tournament, result)
        verify(observer).onChanged(tournament)
    }

}