package com.apprajapati.mvp_stackoverflow.repository.network

import com.apprajapati.mvp_stackoverflow.repository.network.models.Question

interface DataRepository {
    suspend fun getQuestions() : List<Question>
}