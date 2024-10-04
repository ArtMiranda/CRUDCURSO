package br.dev.arturmiranda.crudcurso.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.dev.arturmiranda.crudcurso.R
import br.dev.arturmiranda.crudcurso.model.Curso

class CursoAdapter(private val context: Context, private val cursos: List<Curso>) : BaseAdapter() {

    override fun getCount(): Int = cursos.size

    override fun getItem(position: Int): Any = cursos[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_curso, parent, false)
        val curso = cursos[position]

        val codigoTextView = view.findViewById<TextView>(R.id.codigoEditText)
        val nomeTextView = view.findViewById<TextView>(R.id.nomeEditText)
        val numeroAlunosTextView = view.findViewById<TextView>(R.id.numeroAlunosEditText)
        val notaMECTextView = view.findViewById<TextView>(R.id.notaMECEditText)
        val areaTextView = view.findViewById<TextView>(R.id.areaEstudosEditText)

        codigoTextView.text = curso.codigo.toString()
        nomeTextView.text = curso.nome
        numeroAlunosTextView.text = curso.numeroAlunos.toString()
        notaMECTextView.text = curso.notaMEC.toString()
        areaTextView.text = curso.area

        return view
    }
}