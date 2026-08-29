package com.ycngmn.nobook.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ycngmn.nobook.data.local.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = SettingsDataStore(application)
    private val started = SharingStarted.WhileSubscribed(5_000)

    val removeAds = dataStore.removeAds.stateIn(viewModelScope, started, true)
    val enableDownloadContent = dataStore.enableDownloadContent.stateIn(viewModelScope, started, false)
    val enableCopyToClipboard = dataStore.enableCopyToClipboard.stateIn(viewModelScope, started, false)
    val desktopLayout = dataStore.desktopLayout.stateIn(viewModelScope, started, false)
    val immersiveMode = dataStore.immersiveMode.stateIn(viewModelScope, started, false)
    val stickyNavbar = dataStore.stickyNavbar.stateIn(viewModelScope, started, true)
    val pinchToZoom = dataStore.pinchToZoom.stateIn(viewModelScope, started, false)
    val amoledBlack = dataStore.amoledBlack.stateIn(viewModelScope, started, false)
    val hideSuggested = dataStore.hideSuggested.stateIn(viewModelScope, started, false)
    val hideReels = dataStore.hideReels.stateIn(viewModelScope, started, false)
    val hideStories = dataStore.hideStories.stateIn(viewModelScope, started, false)
    val hidePeopleYouMayKnow = dataStore.hidePeopleYouMayKnow.stateIn(viewModelScope, started, false)
    val hideGroups = dataStore.hideGroups.stateIn(viewModelScope, started, false)
    val isRevertDesktop = dataStore.revertDesktop.stateIn(viewModelScope, started, false)

    fun setRemoveAds(value: Boolean) = launch { dataStore.setRemoveAds(value) }
    fun setEnableDownloadContent(value: Boolean) = launch { dataStore.setEnableDownloadContent(value) }
    fun setEnableCopyToClipboard(value: Boolean) = launch { dataStore.setEnableCopyToClipboard(value) }
    fun setDesktopLayout(value: Boolean) = launch { dataStore.setDesktopLayout(value) }
    fun setImmersiveMode(value: Boolean) = launch { dataStore.setImmersiveMode(value) }
    fun setStickyNavbar(value: Boolean) = launch { dataStore.setStickyNavbar(value) }
    fun setPinchToZoom(value: Boolean) = launch { dataStore.setPinchToZoom(value) }
    fun setAmoledBlack(value: Boolean) = launch { dataStore.setAmoledBlack(value) }
    fun setHideSuggested(value: Boolean) = launch { dataStore.setHideSuggested(value) }
    fun setHideReels(value: Boolean) = launch { dataStore.setHideReels(value) }
    fun setHideStories(value: Boolean) = launch { dataStore.setHideStories(value) }
    fun setHidePeopleYouMayKnow(value: Boolean) = launch { dataStore.setHidePeopleYouMayKnow(value) }
    fun setHideGroups(value: Boolean) = launch { dataStore.setHideGroups(value) }
    fun setRevertDesktop(value: Boolean) = launch { dataStore.setRevertDesktop(value) }

    private fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}
