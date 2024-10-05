package br.dev.arturmiranda.crudcurso

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Adicionar curso"

        val codigoEditText = findViewById<TextInputEditText>(R.id.codigoEditText)
        val nomeEditText = findViewById<TextInputEditText>(R.id.nomeEditText)
        val numeroAlunosEditText = findViewById<TextInputEditText>(R.id.numeroAlunosEditText)
        val notaMECEditText = findViewById<TextInputEditText>(R.id.notaMECEditText)
        val areaEditText = findViewById<TextInputEditText>(R.id.areaEstudosEditText)

        val saveButton = findViewById<MaterialButton>(R.id.saveButton)
        saveButton.setOnClickListener {
            val codigo = codigoEditText.text.toString()
            val nome = nomeEditText.text.toString()
            val numeroAlunos = numeroAlunosEditText.text.toString()
            val notaMEC = notaMECEditText.text.toString()
            val area = areaEditText.text.toString()

            if (codigo.isEmpty() || nome.isEmpty() || numeroAlunos.isEmpty() || notaMEC.isEmpty() || area.isEmpty()) {
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
            } else {
                val curso = Curso(codigo.toLong(), nome, numeroAlunos.toInt(), notaMEC.toFloat(), area)
                cursoDAO.insertCurso(curso)

                val resultIntent = Intent()
                resultIntent.putExtra("curso", curso)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}