package br.dev.arturmiranda.crudcurso.cursoDB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cursos.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_CURSOS = "cursos"
        const val COLUMN_ID = "id"
        const val COLUMN_CODIGO = "codigo"
        const val COLUMN_NOME = "nome"
        const val COLUMN_NUMERO_ALUNOS = "numero_alunos"
        const val COLUMN_NOTA_MEC = "nota_mec"
        const val COLUMN_AREA = "area"

        private const val TABLE_CREATE =
            "CREATE TABLE $TABLE_CURSOS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_CODIGO INTEGER, " +
                    "$COLUMN_NOME TEXT, " +
                    "$COLUMN_NUMERO_ALUNOS INTEGER, " +
                    "$COLUMN_NOTA_MEC REAL, " +
                    "$COLUMN_AREA TEXT" +
                    ");"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CURSOS")
        onCreate(db)
    }
}