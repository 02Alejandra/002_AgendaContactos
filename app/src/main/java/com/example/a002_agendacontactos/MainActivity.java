package com.example.a002_agendacontactos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.content.DialogInterface;
import android.text.InputType;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText txt_nombre, txt_telefono, txt_email;
    Button btn_guardar, btn_buscar;
    List<Contacto> listaContactos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txt_nombre = findViewById(R.id.txt_nombre);
        txt_telefono = findViewById(R.id.txt_telefono);
        txt_email = findViewById(R.id.txt_email);
        btn_guardar = findViewById(R.id.btn_guardar);
        btn_buscar = findViewById(R.id.btn_buscar);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarContacto();
            }
        });
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoBusqueda();
            }
        });
    }

    private void guardarContacto() {
        String nombre = txt_nombre.getText().toString();
        String telefono = txt_telefono.getText().toString();
        String email = txt_email.getText().toString();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo objeto Contacto y agregarlo a la lista de contactos
        Contacto contacto = new Contacto(nombre, telefono, email);
        listaContactos.add(contacto);

        // Limpiar los campos de entrada después de guardar el contacto
        txt_nombre.setText("");
        txt_telefono.setText("");
        txt_email.setText("");

        Toast.makeText(MainActivity.this, "Contacto guardado exitosamente", Toast.LENGTH_SHORT).show();
    }

    private void mostrarDialogoBusqueda() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Buscar Contacto");

        // Crear un EditText en el cuadro de diálogo para que los usuarios ingresen el nombre del contacto a buscar
        final EditText editTextBusqueda = new EditText(this);
        editTextBusqueda.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editTextBusqueda);

        // Configurar los botones "Buscar" y "Cancelar" en el cuadro de diálogo
        builder.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombreBusqueda = editTextBusqueda.getText().toString();
                buscarContacto(nombreBusqueda);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Mostrar el cuadro de diálogo
        builder.create().show();
    }

    private void buscarContacto(String nombreBusqueda) {
        // Iterar sobre la lista de contactos y buscar el contacto con el nombre especificado
        boolean encontrado = false;
        for (Contacto contacto : listaContactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombreBusqueda)) {
                // Mostrar los detalles del contacto encontrado (por ejemplo, en un Toast)
                String detalles = "Nombre: " + contacto.getNombre() + "\nTeléfono: " + contacto.getTelefono() + "\nEmail: " + contacto.getEmail();
                Toast.makeText(this, detalles, Toast.LENGTH_LONG).show();
                encontrado = true;
                break;
            }
        }
        // Si no se encuentra ningún contacto con el nombre especificado, mostrar un mensaje indicando que no se encontraron resultados
        if (!encontrado) {
            Toast.makeText(this, "No se encontraron resultados para \"" + nombreBusqueda + "\"", Toast.LENGTH_SHORT).show();
        }
    }


}