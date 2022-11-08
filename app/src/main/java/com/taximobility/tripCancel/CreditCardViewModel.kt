package com.taximobility.tripCancel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class CreditCardViewModel(val cardRepository: CreditCardRepository, private val mApplication: Application) : AndroidViewModel(mApplication) {

    fun getCardList() = cardRepository.getCardList()

    fun deleteAllCards() = cardRepository.deleteAllCards()
}