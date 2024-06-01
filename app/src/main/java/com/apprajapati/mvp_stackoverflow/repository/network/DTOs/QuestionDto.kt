package com.apprajapati.mvp_stackoverflow.repository.network.DTOs

import com.apprajapati.mvp_stackoverflow.repository.network.models.Question

data class QuestionDto(
    val answer_count: Int,
    val content_license: String,
    val creation_date: Int,
    val is_answered: Boolean,
    val last_activity_date: Int,
    val link: String,
    val owner: Owner,
    val question_id: Int,
    val score: Int,
    val tags: List<String>,
    val title: String,
    val view_count: Int,
    val body: String
) {

      fun toQuestion(): Question {
        return Question(title, question_id)
    }

}