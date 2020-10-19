package com.silentnuke.navGraphViewmodels.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val KEY_STATE = "state2"

class MainViewModel2(handle: SavedStateHandle) : ViewModel() {

    val state = handle.getLiveData(KEY_STATE, "none")

}