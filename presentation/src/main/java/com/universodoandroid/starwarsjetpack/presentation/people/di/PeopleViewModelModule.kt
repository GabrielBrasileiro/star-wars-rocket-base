package com.universodoandroid.starwarsjetpack.presentation.people.di

import com.mvvmredux.core.livedata.SingleLiveEvent
import com.universodoandroid.starwarsjetpack.presentation.people.mapper.PeoplePresentationMapper
import com.universodoandroid.starwarsjetpack.presentation.people.mapper.PersonDetailsPresentationMapper
import com.universodoandroid.starwarsjetpack.presentation.people.viewmodels.people.PeopleListViewModel
import com.universodoandroid.starwarsjetpack.presentation.people.viewmodels.people.reducer.PeopleReducer
import com.universodoandroid.starwarsjetpack.presentation.people.viewmodels.person.PersonDetailsViewModel
import com.universodoandroid.starwarsjetpack.presentation.rx.DefaultRXExecutor
import com.universodoandroid.starwarsjetpack.shared.extensions.getMapper
import com.universodoandroid.starwarsjetpack.shared.extensions.mapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getPeopleViewModelModules() = module {
    mapper { PeoplePresentationMapper() }
    mapper { PersonDetailsPresentationMapper() }

    viewModel {
        PeopleListViewModel(
            event = SingleLiveEvent(),
            reducer = PeopleReducer(),
            getPeopleUseCase = get(),
            mapper = getMapper(),
            rx = DefaultRXExecutor()
        )
    }
    viewModel {
        PersonDetailsViewModel(
            event = SingleLiveEvent(),
            getPersonUseCase = get(),
            mapper = getMapper(),
            rx = DefaultRXExecutor()
        )
    }
}