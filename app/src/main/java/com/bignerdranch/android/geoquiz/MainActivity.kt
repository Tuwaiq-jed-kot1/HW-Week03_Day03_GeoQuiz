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

    private  var scor:Int=0//point
    private var numberQuestiond=1//size array
    var totalScor:Int=0


    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    fun countSizeQuestion(){
        if(quizViewModel.questionBank.size==numberQuestiond){

            Toast.makeText(this, "${quizViewModel.currentIndex}", Toast.LENGTH_SHORT).show()

            trueButton.setEnabled(false);
            falseButton.setEnabled(false)

        }else{

            //Toast.makeText(this, "${quizViewModel.currentIndex}", Toast.LENGTH_SHORT).show()

            numberQuestiond++
            quizViewModel.moveToNext()
            updateQuestion()


        }

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
            countSizeQuestion()
        }

        falseButton.setOnClickListener { view: View ->

            checkAnswer(false)

            countSizeQuestion()
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
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

        if (correctAnswer==userAnswer){

            scor+=20
            Toast.makeText(this, " //// $scor", Toast.LENGTH_SHORT)
                .show()

        }else{
            scor=scor
        }

        if (quizViewModel.questionBank.size==numberQuestiond){

             totalScor=(quizViewModel.questionBank.size/scor)*100

            Toast.makeText(this, "Total your scor is$totalScor ", Toast.LENGTH_LONG)
                .show()
        }



    }
}
