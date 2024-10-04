// EditCursoActivity.kt
package br.dev.arturmiranda.crudcurso

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.dev.arturmiranda.crudcurso.cursoDB.CursoDAO

class EditCursoActivity : AppCompatActivity() {

    private lateinit var cursoDAO: CursoDAO
    private var cursoCodigo: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_curso)

        cursoDAO = CursoDAO(this)
        cursoCodigo = intent.getLongExtra("CURSO_CODIGO", -1)

        if (cursoCodigo != -1L) {
            val curso = cursoDAO.getCursoByCodigo(cursoCodigo)
            Log.i("Curso", curso.toString())
            println(curso)
            curso?.let {
                findViewById<EditText>(R.id.nomeEditText).setText(it.nome)
                findViewById<EditText>(R.id.numeroAlunosEditText).setText(it.numeroAlunos.toString())
                findViewById<EditText>(R.id.notaMECEditText).setText(it.notaMEC.toString())
                findViewById<EditText>(R.id.areaEstudosEditText).setText(it.area)
            }
        }
    }
}