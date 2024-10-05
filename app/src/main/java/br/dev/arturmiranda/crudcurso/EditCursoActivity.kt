// EditCursoActivity.kt
package br.dev.arturmiranda.crudcurso

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.dev.arturmiranda.crudcurso.cursoDB.CursoDAO
import br.dev.arturmiranda.crudcurso.model.Curso

class EditCursoActivity : AppCompatActivity() {

    private lateinit var cursoDAO: CursoDAO
    private var cursoCodigo: Long = -1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_curso)

        cursoDAO = CursoDAO(this)
        cursoCodigo = intent.getLongExtra("CURSO_CODIGO", -1)

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Editar curso"

        if (cursoCodigo != -1L) {
            val curso = cursoDAO.getCursoByCodigo(cursoCodigo)
            curso?.let {
                findViewById<EditText>(R.id.nomeEditText).setText(it.nome)
                findViewById<EditText>(R.id.numeroAlunosEditText).setText(it.numeroAlunos.toString())
                findViewById<EditText>(R.id.notaMECEditText).setText(it.notaMEC.toString())
                findViewById<EditText>(R.id.areaEstudosEditText).setText(it.area)
            }
        }

        findViewById<Button>(R.id.excluirButton).setOnClickListener {
            if (cursoCodigo != -1L) {
                cursoDAO.deleteCursoByCodigo(cursoCodigo)
                finish()
            }
        }

        findViewById<Button>(R.id.editarButton).setOnClickListener {
            val nome = findViewById<EditText>(R.id.nomeEditText).text.toString()
            val numeroAlunos = findViewById<EditText>(R.id.numeroAlunosEditText).text.toString().toInt()
            val notaMEC = findViewById<EditText>(R.id.notaMECEditText).text.toString().toFloat()
            val area = findViewById<EditText>(R.id.areaEstudosEditText).text.toString()

            if (cursoCodigo != -1L) {
                val curso = Curso(cursoCodigo, nome, numeroAlunos, notaMEC, area)
                cursoDAO.updateCurso(curso)
                finish()
            }
        }
    }
}