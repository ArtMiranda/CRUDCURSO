package br.dev.arturmiranda.crudcurso.cursoDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import br.dev.arturmiranda.crudcurso.model.Curso

class CursoDAO(context: Context) {

    private val database: SQLiteDatabase = DatabaseHelper(context).writableDatabase

    fun insertCurso(curso: Curso): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_CODIGO, curso.codigo)
            put(DatabaseHelper.COLUMN_NOME, curso.nome)
            put(DatabaseHelper.COLUMN_NUMERO_ALUNOS, curso.numeroAlunos)
            put(DatabaseHelper.COLUMN_NOTA_MEC, curso.notaMEC)
            put(DatabaseHelper.COLUMN_AREA, curso.area)
        }
        return database.insert(DatabaseHelper.TABLE_CURSOS, null, values)
    }

    fun getAllCursos(): List<Curso> {
        val cursos = mutableListOf<Curso>()
        val cursor: Cursor = database.query(DatabaseHelper.TABLE_CURSOS, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val codigo = getLong(getColumnIndexOrThrow(DatabaseHelper.COLUMN_CODIGO))
                val nome = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOME))
                val numeroAlunos = getInt(getColumnIndexOrThrow(DatabaseHelper.COLUMN_NUMERO_ALUNOS))
                val notaMEC = getFloat(getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOTA_MEC))
                val area = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_AREA))
                cursos.add(Curso(codigo, nome, numeroAlunos, notaMEC, area))
            }
            close()
        }
        return cursos
    }

    fun updateCurso(curso: Curso): Int {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_CODIGO, curso.codigo)
            put(DatabaseHelper.COLUMN_NOME, curso.nome)
            put(DatabaseHelper.COLUMN_NUMERO_ALUNOS, curso.numeroAlunos)
            put(DatabaseHelper.COLUMN_NOTA_MEC, curso.notaMEC)
            put(DatabaseHelper.COLUMN_AREA, curso.area)
        }
        return database.update(DatabaseHelper.TABLE_CURSOS, values, "${DatabaseHelper.COLUMN_CODIGO} = ?", arrayOf(curso.codigo.toString()))
    }

    fun getCursoByCodigo(codigoCurso: Long): Curso? {
        val cursor: Cursor = database.query(DatabaseHelper.TABLE_CURSOS, null, "${DatabaseHelper.COLUMN_CODIGO} = ?", arrayOf(codigoCurso.toString()), null, null, null)
        return if (cursor.moveToNext()) {
            val codigo = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CODIGO))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOME))
            val numeroAlunos = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NUMERO_ALUNOS))
            val notaMEC = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOTA_MEC))
            val area = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AREA))
            Curso(codigo, nome, numeroAlunos, notaMEC, area)
        } else {
            null
        }
    }

    fun getCursoMaiorNumeroAlunos(): Curso {
        val cursor: Cursor = database.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_CURSOS} ORDER BY ${DatabaseHelper.COLUMN_NUMERO_ALUNOS} DESC LIMIT 1", null)
        return if (cursor.moveToNext()) {
            val codigo = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CODIGO))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOME))
            val numeroAlunos = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NUMERO_ALUNOS))
            val notaMEC = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOTA_MEC))
            val area = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AREA))
            Curso(codigo, nome, numeroAlunos, notaMEC, area)
        } else {
            Curso(-1, "", -1, -1f, "")
        }
    }

    fun getTotalAlunos(): Int {
        val cursor: Cursor = database.rawQuery("SELECT SUM(${DatabaseHelper.COLUMN_NUMERO_ALUNOS}) FROM ${DatabaseHelper.TABLE_CURSOS}", null)
        return if (cursor.moveToNext()) {
            cursor.getInt(0)
        } else {
            0
        }
    }

    fun deleteCursoByCodigo(codigoCurso: Long): Int {
        return database.delete(DatabaseHelper.TABLE_CURSOS, "${DatabaseHelper.COLUMN_CODIGO} = ?", arrayOf(codigoCurso.toString()))
    }
}