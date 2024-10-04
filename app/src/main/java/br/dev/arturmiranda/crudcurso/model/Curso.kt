package br.dev.arturmiranda.crudcurso.model

import java.io.Serializable

data class Curso(
    var codigo: Long,
    var nome: String,
    var numeroAlunos: Int,
    var notaMEC: Float,
    var area: String
) : Serializable