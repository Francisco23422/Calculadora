package com.example.calculadoraclase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Calculadora : AppCompatActivity() {

    private lateinit var pantalla: TextView
    private val input = StringBuilder()
    private var ultimoOperador: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        pantalla = findViewById(R.id.txt_pantalla)

        val btnAC: Button = findViewById(R.id.btn_AC)
        val btnBorrar: Button = findViewById(R.id.btn_borrar)
        val btnCambiarSigno: Button = findViewById(R.id.btn_cambiar_signo)
        val btnPorcentaje: Button = findViewById(R.id.btn_porcentaje)
        val btnDividir: Button = findViewById(R.id.btn_dividir)
        val btn7: Button = findViewById(R.id.btn_7)
        val btn8: Button = findViewById(R.id.btn_8)
        val btn9: Button = findViewById(R.id.btn_9)
        val btnMultiplicar: Button = findViewById(R.id.btn_multiplicar)
        val btn4: Button = findViewById(R.id.btn_4)
        val btn5: Button = findViewById(R.id.btn_5)
        val btn6: Button = findViewById(R.id.btn_6)
        val btnRestar: Button = findViewById(R.id.btn_restar)
        val btn3: Button = findViewById(R.id.btn_3)
        val btn2: Button = findViewById(R.id.btn_2)
        val btn1: Button = findViewById(R.id.btn_1)
        val btnSumar: Button = findViewById(R.id.btn_sumar)
        val btn0: Button = findViewById(R.id.btn_0)
        val btnComa: Button = findViewById(R.id.btn_coma)
        val btnIgual: Button = findViewById(R.id.btn_igual)

        btnAC.setOnClickListener { borrarInput() }
        btnBorrar.setOnClickListener { limpiarPantalla() }
        btnCambiarSigno.setOnClickListener { cambiarSigno() }
        btnPorcentaje.setOnClickListener { manejarPorcentaje() }
        btnDividir.setOnClickListener { manejarOperador('/') }
        btn7.setOnClickListener { agregarAlInput("7") }
        btn8.setOnClickListener { agregarAlInput("8") }
        btn9.setOnClickListener { agregarAlInput("9") }
        btnMultiplicar.setOnClickListener { manejarOperador('*') }
        btn4.setOnClickListener { agregarAlInput("4") }
        btn5.setOnClickListener { agregarAlInput("5") }
        btn6.setOnClickListener { agregarAlInput("6") }
        btnRestar.setOnClickListener { manejarOperador('-') }
        btn3.setOnClickListener { agregarAlInput("3") }
        btn2.setOnClickListener { agregarAlInput("2") }
        btn1.setOnClickListener { agregarAlInput("1") }
        btnSumar.setOnClickListener { manejarOperador('+') }
        btn0.setOnClickListener { agregarAlInput("0") }
        btnComa.setOnClickListener { agregarAlInput(".") }
        btnIgual.setOnClickListener { calcularResultado() }
    }

    private fun limpiarPantalla() {
        if (input.isNotEmpty()) {
            input.deleteCharAt(input.length - 1)
            actualizarPantalla()
        } else if (ultimoOperador != null) {
            ultimoOperador = null
            actualizarPantalla()
        }
    }

    private fun borrarInput() {
        input.clear()
        ultimoOperador = null
        actualizarPantalla()
    }
    private fun agregarAlInput(valor: String) {
        input.append(valor)
        actualizarPantalla()
    }

    private fun actualizarPantalla() {
        pantalla.text = input.toString()
    }

    private fun cambiarSigno() {
        if (input.isNotEmpty()) {
            val primerCaracter = input[0]
            if (primerCaracter == '-') {
                input.deleteCharAt(0)
            } else {
                input.insert(0, '-')
            }
            actualizarPantalla()
        }
    }

    private fun manejarOperador(operador: Char) {
        if (input.isNotEmpty() && ultimoOperador == null) {
            ultimoOperador = operador
            agregarAlInput(operador.toString())
        } else if (input.isNotEmpty() && ultimoOperador != null) {
            input.setCharAt(input.length - 1, operador)
            ultimoOperador = operador
            actualizarPantalla()
        }
    }

    private fun manejarPorcentaje() {
        if (input.isNotEmpty()) {
            try {
                val valor = input.toString().toDouble()
                val resultado = valor / 100
                borrarInput()
                input.append(obtenerResultadoFormateado(resultado))
                actualizarPantalla()
            } catch (e: NumberFormatException) {
                borrarInput()
                input.append("Error")
                actualizarPantalla()
            }
        }
    }

    private fun calcularResultado() {
        if (input.isNotEmpty() && ultimoOperador != null) {
            val operandos = input.toString().split(ultimoOperador!!)
            if (operandos.size == 2) {
                try {
                    val operando1 = operandos[0].toDouble()
                    val operando2 = operandos[1].toDouble()
                    val resultado = when (ultimoOperador) {
                        '+' -> operando1 + operando2
                        '-' -> operando1 - operando2
                        '*' -> operando1 * operando2
                        '/' -> operando1 / operando2
                        else -> throw IllegalArgumentException("Operador no válido")
                    }
                    borrarInput()
                    input.append(obtenerResultadoFormateado(resultado))
                    actualizarPantalla()
                } catch (e: NumberFormatException) {
                    borrarInput()
                    input.append("Error")
                    actualizarPantalla()
                } catch (e: ArithmeticException) {
                    borrarInput()
                    input.append("Error")
                    actualizarPantalla()
                }
            }
        }
    }

    private fun obtenerResultadoFormateado(resultado: Double): String {
        return if (resultado % 1 == 0.0) {
            resultado.toInt().toString()
        } else {
            resultado.toString()
        }
    }
}
