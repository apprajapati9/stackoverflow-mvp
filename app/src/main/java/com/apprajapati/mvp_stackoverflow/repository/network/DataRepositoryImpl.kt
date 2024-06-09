package com.apprajapati.mvp_stackoverflow.repository.network

import android.util.Log
import com.apprajapati.mvp_stackoverflow.repository.network.models.Question


class DataRepositoryImpl : DataRepository {
    override suspend fun getQuestions(): List<Question> {

        val allQuestions = mutableListOf<Question>()

        try {
            val questions = GetRetrofit.stackoverflowApi.getAndroidQuestions()
            Log.d("ajay-dataRepo", Thread.currentThread().name)
            if (questions.items.isNotEmpty()) {

                questions.items.forEach {
                    allQuestions.add(it.toQuestion())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() //this triggers mainly when there's no internet connection.
        }


//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val questions = GetRetrofit.StackoverflowApi.getAndroidQuestions()
//                Log.d("ajay-dataRepo", Thread.currentThread().name)
//                if (questions.items.isNotEmpty()) {
//
//                    questions.items.forEach {
//                        allQuestions.add(it.toQuestion())
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace() //this triggers mainly when there's no internet connection.
//            }
//        }.join()

        return allQuestions

    }
}