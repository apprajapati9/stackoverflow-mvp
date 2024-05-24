package com.apprajapati.mvp_stackoverflow.repository.network

import com.apprajapati.mvp_stackoverflow.repository.network.DTOs.QuestionDto
import com.apprajapati.mvp_stackoverflow.repository.network.DTOs.QuestionsDto
import retrofit2.http.GET

interface StackoverflowApi {

    @GET("2.3/questions?order=desc&sort=activity&tagged=android&site=stackoverflow")
    suspend fun getAndroidQuestions(): QuestionsDto
}