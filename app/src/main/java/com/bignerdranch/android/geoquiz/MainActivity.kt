package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button

    private lateinit var questionTextView: TextView
    private val msg = "Congratulations, you finished the Quiz!!\nYour score for the test is"

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called");
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            hideButton()
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            hideButton()
        }

        nextButton.setOnClickListener {

            quizViewModel.moveToNext()
            updateQuestion()
            visibleButton()
            nextButton.setText( R.string.next_button)

        }

        updateQuestion()

    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        var messageResId = 0
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if (userAnswer == correctAnswer) {
            quizViewModel.score++
            messageResId = R.string.correct_toast
        } else {
            messageResId = R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        //check the end of the quiz &  display the score

        if (quizViewModel.currentIndex == quizViewModel.getQuestionBankSize() - 1) {

            nextButton.visibility = View.GONE
            questionTextView.setText("")
            Toast.makeText(
                this,
                "$msg ${"%.2f".format(quizViewModel.score / quizViewModel.getQuestionBankSize() * 100.0)}",
                Toast.LENGTH_LONG
            ).show()
            quizViewModel.score = 0.0

            nextButton.setText( R.string.restart_button)
            nextButton.visibility = View.VISIBLE
        }
    }

    fun hideButton() {
        trueButton.visibility = View.GONE
        falseButton.visibility = View.GONE
    }

    fun visibleButton() {
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
    }
}
