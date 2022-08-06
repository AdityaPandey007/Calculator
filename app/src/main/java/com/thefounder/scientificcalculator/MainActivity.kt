package com.thefounder.scientificcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private var pi = "3.14159265"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // on click listener

        btn1.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "1"
        }

        btn2.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "2"
        }

        btn3.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "3"
        }

        btn4.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "4"
        }

        btn5.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "5"
        }

        btn6.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "6"
        }

        btn7.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "7"
        }

        btn8.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "8"
        }

        btn9.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "9"
        }

        btn0.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "0"
        }

        btnDot.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "."
        }

        btnAC.setOnClickListener {

            txtMain.text = ""
            txtSec.text = ""
        }

        btnC.setOnClickListener {

            var C = txtMain.text.toString()
            C = C.substring(0,C.length - 1)
            txtMain.text = C

        }

        btnAdd.setOnClickListener {

            txtMain. text = txtMain.text.toString() + "+"
        }

        btnSubs.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "-"
        }

        btnMultiply.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "×"
        }

        btnDiv.setOnClickListener {

            txtMain.text = txtMain.text.toString()+"÷"
        }

        btnRoot.setOnClickListener {

            val value = txtMain.text.toString()
            val double : Double = Math.sqrt(value.toDouble())
            txtMain.text = double.toString()


        }

        btnb1.setOnClickListener {

            txtMain.text = txtMain.text.toString() +"("
        }

        btnb2.setOnClickListener {
            txtMain.text = txtMain.txtMain.toString() + ")"

        }

        btnPi.setOnClickListener {

            txtSec.text = btnPi.text
            txtMain.text = txtMain.text.toString() + pi
        }

        btnSin.setOnClickListener {

            txtSec.text = btnPi.text
            txtMain.text = txtMain.text.toString() + "sin"
        }

        btnCos.setOnClickListener {

            txtSec.text = btnPi.text
            txtMain.text = txtMain.text.toString() + "cos"
        }

        btnTan.setOnClickListener {

            txtSec.text = btnPi.text
            txtMain.text = txtMain.text.toString() + "tan"
        }

        btnInv.setOnClickListener {

            txtSec.text = btnPi.text
            txtMain.text = txtMain.text.toString() + pi
        }

        btnFact.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val `val`: Int = txtMain.getText().toString().toInt()
                val fact: Int = factorial(`val`)
                txtMain.setText(fact.toString())
                txtSec.setText("$`val`!")
            }
        })

        btnSquare.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val d: Double = txtMain.getText().toString().toDouble()
                val square = d * d
                txtMain.setText(square.toString())
                txtSec.setText("$d²")
            }
        })

        btnEqual.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val `val`: String = txtMain.getText().toString()
                val replacedstr = `val`.replace('÷', '/').replace('×', '*')
                val result: Double = eval(replacedstr)
                txtMain.setText(result.toString())
                txtSec.setText(`val`)
            }
        })

        btnLn.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "ln"
        }

        btnLog.setOnClickListener {

            txtMain.text = txtMain.text.toString() + "log"
        }
    }

    //factorial function
    fun factorial(n: Int): Int {
        return if (n == 1 || n == 0) 1 else n * factorial(n - 1)
    }

    //eval function
    fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0
            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor
            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.code)) x += parseTerm() // addition
                    else if (eat('-'.code)) x -= parseTerm() // subtraction
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.code)) x *= parseFactor() // multiplication
                    else if (eat('/'.code)) x /= parseFactor() // division
                    else return x
                }
            }

            fun parseFactor(): Double {

                if (eat('+'.code)) return parseFactor() // unary plus
                if (eat('-'.code)) return -parseFactor() // unary minus
                var x: Double
                val startPos = pos
                if (eat('('.code)) { // parentheses
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) { // numbers
                    while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                    x = str.substring(startPos, pos).toDouble()
                } else if (ch >= 'a'.code && ch <= 'z'.code) { // functions
                    while (ch >= 'a'.code && ch <= 'z'.code) nextChar()
                    val func = str.substring(startPos, pos)
                    x = parseFactor()
                    if (func == "sqrt") x =
                        Math.sqrt(x) else if (func == "sin") x =
                        Math.sin(
                            Math.toRadians(x)
                        ) else if (func == "cos") x = Math.cos(
                        Math.toRadians(x)
                    ) else if (func == "tan") x = Math.tan(
                        Math.toRadians(x)
                    ) else if (func == "log") x =
                        Math.log10(x) else if (func == "ln") x =
                        Math.log(x) else throw RuntimeException("Unknown function: " + func)
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                if (eat('^'.code)) x =
                    Math.pow(x, parseFactor()) // exponentiation
                return x
            }
        }.parse()
    }
}
