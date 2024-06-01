package com.apprajapati.mvp_stackoverflow.repository.network.DTOs

/*
DTO - Data transfer object, it is different from the model we use in our app
i.e we only show question title and id in our model but response has a lot of other fields.
 */

data class QuestionsDto(val items: List<QuestionDto>)
