package br.dev.arturmiranda.crudcurso

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.dev.arturmiranda.crudcurso.cursoDB.CursoDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchActivity : AppCompatActivity() {

    private lateinit var cursoDAO: CursoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        cursoDAO = CursoDAO(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Pesquisar curso"
        enableEdgeToEdge()

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val searchButton: FloatingActionButton = findViewById(R.id.buttonSearch)
        searchButton.setOnClickListener {
            val searchId = searchEditText.text.toString().toLongOrNull()
            if (searchId != null) {
                val curso = cursoDAO.getCursoByCodigo(searchId)
                if (curso != null) {
                    val intent = Intent(this, EditCursoActivity::class.java).apply {
                        putExtra("CURSO_CODIGO", searchId)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Curso não encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor coloque um id válido", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}