package br.dev.arturmiranda.crudcurso

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import br.dev.arturmiranda.crudcurso.cursoDB.CursoDAO
import br.dev.arturmiranda.crudcurso.model.Curso

class AddCursoActivity : AppCompatActivity() {

    private lateinit var cursoDAO: CursoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_curso)

        cursoDAO = CursoDAO(this)

        val codigoEditText = findViewById<TextInputEditText>(R.id.codigoEditText)
        val nomeEditText = findViewById<TextInputEditText>(R.id.nomeEditText)
        val numeroAlunosEditText = findViewById<TextInputEditText>(R.id.numeroAlunosEditText)
        val notaMECEditText = findViewById<TextInputEditText>(R.id.notaMECEditText)
        val areaEditText = findViewById<TextInputEditText>(R.id.areaEstudosEditText)

        val saveButton = findViewById<MaterialButton>(R.id.saveButton)
        saveButton.setOnClickListener {
            val codigo = codigoEditText.text.toString().toLong()
            val nome = nomeEditText.text.toString()
            val numeroAlunos = numeroAlunosEditText.text.toString().toInt()
            val notaMEC = notaMECEditText.text.toString().toFloat()
            val area = areaEditText.text.toString()

            val curso = Curso(codigo, nome, numeroAlunos, notaMEC, area)
            cursoDAO.insertCurso(curso)

            val resultIntent = Intent()
            resultIntent.putExtra("curso", curso)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}