package com.apprajapati.mvp_stackoverflow.screens.stackoverflow_question_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apprajapati.mvp_stackoverflow.R
import com.apprajapati.mvp_stackoverflow.repository.network.models.Question

class StackQuestionsAdapter(
    private val questions: List<Question>,
    private val clickListener: onClickListener
) :
    RecyclerView.Adapter<StackQuestionsAdapter.StackQuestionsHolder>() {

    class StackQuestionsHolder(view: View) : RecyclerView.ViewHolder(view) {
        var questionId = view.findViewById<TextView>(R.id.questionId)
        var question = view.findViewById<TextView>(R.id.question)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackQuestionsHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_questions_view, parent, false)
        return StackQuestionsHolder(view)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: StackQuestionsHolder, position: Int) {
        val question = questions[position]
        holder.questionId.text = question.question_id.toString()
        holder.question.text = question.title

        holder.questionId.setOnClickListener {
            clickListener.OnQuestionClick(question.question_id)
        }
        holder.question.setOnClickListener {
            clickListener.OnQuestionClick(question.question_id)
        }
    }

    interface onClickListener {
        fun OnQuestionClick(id: Int)
    }
}