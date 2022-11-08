package com.taximobility.tripCancel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Context

class CommonViewModelFactory(val context: Context) : ViewModelProvider.NewInstanceFactory() {
    private fun getRepository() = CreditCardRepository.getRepository(context)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return getRepository()?.let { CreditCardViewModel(it, context.applicationContext as Application) } as T
    }
}