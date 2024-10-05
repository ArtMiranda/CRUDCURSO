package br.dev.arturmiranda.crudcurso

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.dev.arturmiranda.crudcurso.cursoDB.CursoDAO
import java.io.File
import java.io.FileWriter
import java.io.IOException

class DadosUniversidadeActivity : AppCompatActivity() {
    private lateinit var cursoDAO: CursoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dados_universidade)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dados"

        val backButton: Button = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val downloadButton: Button = findViewById(R.id.downloadButton)
        downloadButton.setOnClickListener {
            saveDataToFile()
        }

        cursoDAO = CursoDAO(this)

        val cursos = cursoDAO.getAllCursos()
        val totalAlunos = cursoDAO.getTotalAlunos()
        val cursoMaiorNumeroAlunos = cursoDAO.getCursoMaiorNumeroAlunos()

        val tvCursoMaiorNumeroAlunos: TextView = findViewById(R.id.tvCursoMaiorNumeroAlunos)
        val tvTotalAlunos: TextView = findViewById(R.id.tvTotalAlunos)
        val cardView: CardView = findViewById(R.id.cardView)

        if (cursos.isEmpty()) {
            cardView.visibility = View.VISIBLE
            tvCursoMaiorNumeroAlunos.text = "Não há cursos cadastrados."
            tvTotalAlunos.text = ""
        } else {
            tvCursoMaiorNumeroAlunos.text = "Curso com maior número de alunos: ${cursoMaiorNumeroAlunos.nome} (${cursoMaiorNumeroAlunos.numeroAlunos} alunos)"
            tvTotalAlunos.text = "Total de alunos na universidade: $totalAlunos"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun saveDataToFile() {
        val cursos = cursoDAO.getAllCursos()
        val totalAlunos = cursoDAO.getTotalAlunos()
        val cursoMaiorNumeroAlunos = cursoDAO.getCursoMaiorNumeroAlunos()

        val data = StringBuilder()
        data.append("Dados da Universidade\n\n")
        if (cursos.isEmpty()) {
            data.append("Não há cursos cadastrados.\n")
        } else {
            data.append("Cursos cadastrados: ${cursos.size}\n")
            data.append("Total de alunos na universidade: $totalAlunos\n\n")
            data.append("Curso com maior número de alunos: ${cursoMaiorNumeroAlunos.nome} (${cursoMaiorNumeroAlunos.numeroAlunos} alunos)\n\n")
            cursos.forEach { curso ->
                data.append("Curso: ${curso.nome}\n")
                data.append("Código: ${curso.codigo}\n")
                data.append("Número de Alunos: ${curso.numeroAlunos}\n")
                data.append("Nota MEC: ${curso.notaMEC}\n")
                data.append("Área: ${curso.area}\n\n")
            }
        }

        val fileName = "dados_universidade.txt"
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

        try {
            FileWriter(file).use { writer ->
                writer.write(data.toString())
            }
            Toast.makeText(this, "Dados salvos em $fileName", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, "Erro ao salvar dados: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}