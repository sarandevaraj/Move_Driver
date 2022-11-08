package com.taximobility.tripCancel

import android.content.Context
import android.os.AsyncTask

class CreditCardRepository private constructor(val mContext: Context) {
    private val creditCardDao = AppDataBase.getDatabase(mContext)?.creditCardDao()

    companion object {
        @Volatile
        private var creditCardRepository: CreditCardRepository? = null

        @JvmStatic
        fun getRepository(mContext: Context): CreditCardRepository? {
            if (creditCardRepository == null) {
                synchronized(CreditCardRepository::class.java) {
                    if (creditCardRepository == null) {
                        creditCardRepository = CreditCardRepository(mContext)
                    }
                }
            }
            return creditCardRepository
        }
    }

    fun getCardList() = creditCardDao?.loadAllCards()

    fun deleteAllCards() = DeleteAllCreditCards().execute()

    fun insertAllCreditCards(cardList: List<CreditCardData>) {
        InsertAllCreditCards(cardList).execute()
    }

    fun insertCreditCard(creditCardData: CreditCardData) {
        InsertCreditCardLog(creditCardData).execute()
    }

    private inner class InsertAllCreditCards(private val cardList: List<CreditCardData>) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {

            creditCardDao?.run {
                this.insertAllCreditCards(cardList)
            }
        }
    }

    private inner class InsertCreditCardLog(private val creditCardData: CreditCardData) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {

            creditCardDao?.run {
                insertCreditCard(creditCardData)
            }
        }
    }

    private inner class DeleteAllCreditCards : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {

            creditCardDao?.run {
                deleteAllCards()
            }
        }
    }

}
