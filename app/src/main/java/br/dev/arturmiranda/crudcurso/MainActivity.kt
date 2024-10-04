// MainActivity.kt
package br.dev.arturmiranda.crudcurso

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.dev.arturmiranda.crudcurso.adapter.CursoAdapter
import br.dev.arturmiranda.crudcurso.cursoDB.CursoDAO
import br.dev.arturmiranda.crudcurso.model.Curso
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var cursoAdapter: CursoAdapter
    private lateinit var cursoDAO: CursoDAO
    private lateinit var cursos: List<Curso>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cursoDAO = CursoDAO(this)
        cursos = cursoDAO.getAllCursos()
        cursoAdapter = CursoAdapter(this, cursos)

        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = cursoAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val curso = cursos[position]
            val intent = Intent(this, EditCursoActivity::class.java)
            intent.putExtra("CURSO_CODIGO", curso.codigo)
            startActivity(intent)
        }

        val floatingActionButton: FloatingActionButton = findViewById(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddCursoActivity::class.java)
            startActivity(intent)
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cursos Cadastrados"

    }

    override fun onResume() {
        super.onResume()
        cursos = cursoDAO.getAllCursos()
        cursoAdapter = CursoAdapter(this, cursos)
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = cursoAdapter
    }
}