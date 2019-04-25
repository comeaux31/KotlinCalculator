package com.anthonycomeaux.kotlincalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


private const val RESULT_TEXT: String = "result"
private const val INPUT_TEXT: String = "input"
private const val OPERATOR_TEXT: String = "operator"
class MainActivity : AppCompatActivity() {
    private val resultEditText by lazy { findViewById<EditText>(R.id.result) }
    private val inputEditText by lazy { findViewById<EditText>(R.id.input) }
    private val operator by lazy { findViewById<TextView>(R.id.operator) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        resultEditText?.setText(savedInstanceState?.getString(RESULT_TEXT, ""))
        inputEditText?.setText(savedInstanceState?.getString(INPUT_TEXT, ""))
        operator?.text = savedInstanceState?.getString(OPERATOR_TEXT, "")
    }


    fun appendText(v: View) {
        val characterToFind: CharSequence = (v as Button).text
        if (characterToFind != "." || (characterToFind == "." && !inputEditText?.text?.contains(characterToFind)!!))
            inputEditText?.text?.append(v.text)
    }

    fun setOperator(v: View) {
        if (resultEditText?.text.isNullOrBlank()) {
            replaceResultWithInput()
        } else if (!inputEditText?.text.isNullOrBlank()) {
            calculateValue(v)
        }
        operator?.text = (v as Button).text

    }

    fun calculateValue(v: View) {
        if (operator?.text.isNullOrBlank() || resultEditText?.text.isNullOrBlank()) {
            replaceResultWithInput()
        } else {
            val calculation: Double = when (operator?.text.toString()) {
                "*" -> resultEditText?.text.toString().toDouble() * inputEditText?.text.toString().toDouble()
                "/" -> resultEditText?.text.toString().toDouble() / inputEditText?.text.toString().toDouble()
                "-" -> resultEditText?.text.toString().toDouble() - inputEditText?.text.toString().toDouble()
                else -> resultEditText?.text.toString().toDouble() + inputEditText?.text.toString().toDouble()
            }
            resultEditText?.setText("$calculation")
            inputEditText?.text?.clear()
        }
        operator?.text = ""
    }

    private fun replaceResultWithInput() {
        resultEditText?.text = inputEditText?.text
        inputEditText?.text?.clear()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(RESULT_TEXT, resultEditText.text.toString())
        outState?.putString(INPUT_TEXT, inputEditText.text.toString())
        outState?.putString(OPERATOR_TEXT, operator.text.toString())
    }


}
