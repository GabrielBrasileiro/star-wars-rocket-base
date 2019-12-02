package com.universodoandroid.starwarsjetpack.presentation.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {

    val isLoadingObserver = MutableLiveData<Boolean>()

}