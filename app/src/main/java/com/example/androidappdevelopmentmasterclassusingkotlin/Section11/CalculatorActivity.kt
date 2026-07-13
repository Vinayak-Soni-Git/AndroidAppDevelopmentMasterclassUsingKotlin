package com.example.androidappdevelopmentmasterclassusingkotlin.Section11

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.androidappdevelopmentmasterclassusingkotlin.R

class CalculatorActivity : AppCompatActivity() {
    private lateinit var result: EditText
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.tv_equation) }
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        viewModel.stringResult.observe(
            this,
            Observer<String> { stringResult -> result.setText(stringResult) })
        viewModel.stringNewNumber.observe(
            this,
            Observer<String> { stringNumber -> newNumber.setText(stringNumber) })
        viewModel.stringOperation.observe(
            this,
            Observer<String> { stringOperation -> displayOperation.text = stringOperation })

        val listener = View.OnClickListener { v ->
            viewModel.digitPressed((v as Button).text.toString())
        }

        result = findViewById(R.id.tv_result)
        newNumber = findViewById(R.id.tv_equation)

        val button0: Button = findViewById(R.id.btn_0)
        val button1: Button = findViewById(R.id.btn_1)
        val button2: Button = findViewById(R.id.btn_2)
        val button3: Button = findViewById(R.id.btn_3)
        val button4: Button = findViewById(R.id.btn_4)
        val button5: Button = findViewById(R.id.btn_5)
        val button6: Button = findViewById(R.id.btn_6)
        val button7: Button = findViewById(R.id.btn_7)
        val button8: Button = findViewById(R.id.btn_8)
        val button9: Button = findViewById(R.id.btn_9)
        val buttonDot: Button = findViewById(R.id.btn_dot)

        val buttonEquals: Button = findViewById(R.id.btn_equal)
        val buttonDivide: Button = findViewById(R.id.btn_divide)
        val buttonMultiply: Button = findViewById(R.id.btn_multiply)
        val buttonMinus: Button = findViewById(R.id.btn_subtract)
        val buttonPlus: Button = findViewById(R.id.btn_add)

        val buttonNeg: Button = findViewById(R.id.btn_neg)

        buttonNeg.setOnClickListener {
            viewModel.negPressed()
        }

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            viewModel.operandPressed((v as Button).text.toString())

        }
        buttonEquals.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
    }
}