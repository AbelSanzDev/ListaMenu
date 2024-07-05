package com.example.menubuttonactivity.DataBase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase

class dbAlumnos(private val context: Context) {
    private val dbHelper: AlumnosDbHelper = AlumnosDbHelper(context)
    private lateinit var db: SQLiteDatabase

    private val leerRegistro = arrayOf(
        DefinirDB.Alumnos.ID,
        DefinirDB.Alumnos.MATRICULA,
        DefinirDB.Alumnos.NOMBRE,
        DefinirDB.Alumnos.DOMICILIO,
        DefinirDB.Alumnos.ESPECIALIDAD,
        DefinirDB.Alumnos.FOTO
    )

    fun openDataBase(){
        db = dbHelper.writableDatabase
    }

    // Insertar alumnos
    fun insertarAlumno(alumno: Alumno): Long {
        val db = dbHelper.readableDatabase
        val value = ContentValues().apply {
            put(DefinirDB.Alumnos.MATRICULA, alumno.matricula)
            put(DefinirDB.Alumnos.NOMBRE, alumno.nombre)
            put(DefinirDB.Alumnos.DOMICILIO, alumno.domicilio)
            put(DefinirDB.Alumnos.ESPECIALIDAD, alumno.especialidad)
            put(DefinirDB.Alumnos.FOTO, alumno.foto)
        }
        return try {
            db.insertOrThrow(DefinirDB.Alumnos.TABLA, null, value)
        } catch (e: SQLiteConstraintException) {
            // La matrícula ya existe
            -1
        } finally {
            db.close()
        }
    }



    // Actualizar Alumnos
    fun actualizarAlumno(alumno: Alumno, id:Int) : Int{
        val values = ContentValues().apply {
            put(DefinirDB.Alumnos.MATRICULA, alumno.matricula)
            put(DefinirDB.Alumnos.NOMBRE, alumno.nombre)
            put(DefinirDB.Alumnos.DOMICILIO, alumno.domicilio)
            put(DefinirDB.Alumnos.ESPECIALIDAD, alumno.especialidad)
            put(DefinirDB.Alumnos.FOTO, alumno.foto)
        }
        return db.update(DefinirDB.Alumnos.TABLA,values,"${DefinirDB.Alumnos.ID}",null)
    }

    // Borrar Alumnos
    fun borrarAlumno(id: Int): Boolean {
        val rowsDeleted = db.delete(DefinirDB.Alumnos.TABLA, "${DefinirDB.Alumnos.MATRICULA} = ?", arrayOf(id.toString()))
        return rowsDeleted > 0
    }

    // Mostrar Alumnos
    fun mostrarAlumnos(cursor: Cursor) : Alumno {
        return Alumno().apply {
            id = cursor.getInt(0)
            matricula = cursor.getString(1)
            nombre = cursor.getString(2)
            domicilio = cursor.getString(3)
            especialidad = cursor.getString(4)
            foto = cursor.getString(5)
        }
    }

    // Buscar Alumnos por ID
    fun getAlumno(id: String): Alumno? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DefinirDB.Alumnos.TABLA, leerRegistro,
            "${DefinirDB.Alumnos.MATRICULA} = ?", arrayOf(id), null,
            null, null
        )
        return if (cursor.moveToFirst()) {
            val alumno = mostrarAlumnos(cursor)
            cursor.close()
            alumno
        } else {
            cursor.close()
            null
        }
    }

    // Obtener todos los registros
    fun leerTodos(): ArrayList<Alumno> {
        val cursor = db.query(DefinirDB.Alumnos.TABLA,leerRegistro, null, null, null, null, null)
        val listaAlumno = ArrayList<Alumno>()
        cursor.moveToFirst()

        while(!cursor.isAfterLast) {
            val alumno = mostrarAlumnos(cursor)
            listaAlumno.add(alumno)
            cursor.moveToNext()
        }
        cursor.close()
        return listaAlumno
    }

    fun close(){
        dbHelper.close()
    }

}
