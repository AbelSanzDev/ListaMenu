package com.example.menubuttonactivity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.menubuttonactivity.DataBase.Alumno
import com.example.menubuttonactivity.DataBase.dbAlumnos

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DbFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DbFragment : Fragment() {
    private lateinit var txtMatricula : EditText
    private lateinit var txtNombre : EditText
    private lateinit var txtDomicilio : EditText
    private lateinit var txtEspecialidad : EditText
    private lateinit var txtFoto : TextView
    private lateinit var btnAgrega: Button
    private lateinit var btnBuscar: Button
    private lateinit var btnBorrar: Button
    private lateinit var db : dbAlumnos


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View = inflater.inflate(R.layout.fragment_db,container,false)
        txtMatricula = View.findViewById(R.id.txtMatricula)
        txtNombre = View.findViewById(R.id.txtNombre)
        txtDomicilio = View.findViewById(R.id.txtDomicilio)
        txtEspecialidad = View.findViewById(R.id.txtEspecialidad)
        txtFoto = View.findViewById(R.id.txtFoto)
        btnAgrega = View.findViewById(R.id.btnGuardar)
        btnBuscar = View.findViewById(R.id.btnBuscar)
        btnBorrar = View.findViewById(R.id.btnBorrar)

        db = dbAlumnos(requireContext())
        db.openDataBase()

        // Habilitar o deshabilitar el botón de borrar según la existencia de registros en la base de datos
        if (db.leerTodos().isNotEmpty()) {
            btnBorrar.isEnabled = true
        } else {
            btnBorrar.isEnabled = false
        }
        btnAgrega.setOnClickListener {
            if (txtNombre.text.toString().isEmpty() ||
                txtMatricula.text.toString().isEmpty() ||
                txtDomicilio.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Faltan por rellenar Campos", Toast.LENGTH_SHORT).show()
            } else {
                val alumno = Alumno().apply {
                    nombre = txtNombre.text.toString()
                    matricula = txtMatricula.text.toString()
                    domicilio = txtDomicilio.text.toString()
                    especialidad = txtEspecialidad.text.toString()
                    foto = "Pendiente"
                }

                db = dbAlumnos(requireContext())
                db.openDataBase()
                val id = db.insertarAlumno(alumno)
                if (id == -1L) {
                    Toast.makeText(requireContext(), "La matrícula ya existe", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Se realizó un registro Exitoso con ID $id", Toast.LENGTH_SHORT).show()
                }
                db.close()
            }
        }


        btnBuscar.setOnClickListener {
            // Validación
            if (txtMatricula.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Falta por capturar la Matricula", Toast.LENGTH_SHORT).show()
            } else {
                db = dbAlumnos(requireContext())
                db.openDataBase()
                val alumno: Alumno? = db.getAlumno(txtMatricula.text.toString())
                if (alumno != null) {
                    txtNombre.setText(alumno.nombre)
                    txtDomicilio.setText(alumno.domicilio)
                    txtEspecialidad.setText(alumno.especialidad)
                } else {
                    Toast.makeText(requireContext(), "No se encontró la matrícula", Toast.LENGTH_SHORT).show()
                }
                db.close()
            }
        }
        btnBorrar.setOnClickListener {
            // Validación
            if (txtMatricula.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Falta por capturar la Matricula", Toast.LENGTH_SHORT).show()
            } else {
                val matriculaTexto = txtMatricula.text.toString()
                val matriculaInt = matriculaTexto.toInt()

                // Crear un AlertDialog para confirmar la acción
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Confirmar Borrado")
                builder.setMessage("¿Estás seguro de que quieres borrar al alumno con matrícula $matriculaInt?")

                // Añadir botón de confirmar
                builder.setPositiveButton("Sí") { dialog, which ->
                    // Proceder con el borrado
                    val success: Boolean = db.borrarAlumno(matriculaInt)
                    if (success) {
                        Toast.makeText(requireContext(), "Se realizó el borrado exitoso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "No se encontró la matrícula para borrar", Toast.LENGTH_SHORT).show()
                    }

                    // Cerrar base de datos si es necesario
                    // db.close() // Solo si realmente necesitas cerrar la base de datos aquí
                }

                // Añadir botón de cancelar
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }

                // Mostrar el AlertDialog
                builder.show()
            }
        }


        return View


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DbFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DbFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}