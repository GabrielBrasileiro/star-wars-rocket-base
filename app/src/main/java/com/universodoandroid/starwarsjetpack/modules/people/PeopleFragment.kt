package com.universodoandroid.starwarsjetpack.modules.people

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.universodoandroid.starwarsjetpack.R
import com.universodoandroid.starwarsjetpack.constants.Constants
import com.universodoandroid.starwarsjetpack.databinding.FragmentPeopleBinding
import com.universodoandroid.starwarsjetpack.exetensions.show
import com.universodoandroid.starwarsjetpack.presentation.dto.PersonDto
import com.universodoandroid.starwarsjetpack.presentation.models.people.PeopleListViewModel
import com.universodoandroid.starwarsjetpack.presentation.models.people.PeopleState
import com.universodoandroid.starwarsjetpack.ui.BindingFragment
import com.universodoandroid.starwarsjetpack.ui.resourses.defaultNumberOfColumns
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : BindingFragment<FragmentPeopleBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_people

    private val viewModel by viewModel<PeopleListViewModel>()
    private val progressBar: ProgressBar by lazy { binding.progressBar }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPeopleObserver()
    }

    private fun initPeopleObserver() {
        viewModel.getState().observe(this, Observer { state ->
            when (state) {
                is PeopleState.ShowData -> showPeople(state.data)
                is PeopleState.ShowError -> showError(state.error)
                is PeopleState.ShowLoading -> showLoader()
                is PeopleState.HideLoading -> hideLoader()
            }
        })

        lifecycle.addObserver(viewModel)
    }

    private fun showPeople(people: List<PersonDto>?) {
        people?.let {
            setupRecyclerView(people = it)
        }
    }

    private fun setupRecyclerView(people: List<PersonDto>) {
        binding.peopleRecyclerView.run {
            layoutManager = GridLayoutManager(requireContext(), resources.defaultNumberOfColumns())
            adapter = PeopleAdapter(people) { personDto ->
                personDetails(personDto)
            }
        }
    }

    private fun showError(message: String?) {
        binding.errorMessage.visibility = View.VISIBLE
        println("Error: $message")
    }

    private fun personDetails(personDto: PersonDto) {
        val args = Bundle().apply { putSerializable(Constants.PERSON_DTO, personDto) }
        navController.navigate(R.id.people_navigation_to_person_details, args)
    }

    private fun showLoader() = progressBar.show(true)

    private fun hideLoader() = progressBar.show(false)

}
