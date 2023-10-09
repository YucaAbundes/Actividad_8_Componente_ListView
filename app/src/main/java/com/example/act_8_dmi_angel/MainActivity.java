package com.example.act_8_dmi_angel;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText1, editText2;
    private Button btnSumar;
    private ListView listView;
    private ArrayList<String> sumasList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        btnSumar = findViewById(R.id.btnSumar);
        listView = findViewById(R.id.listView);

        sumasList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sumasList);
        listView.setAdapter(adapter);

        // Configurar el listener para el clic en el ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mostrarInformacion(sumasList.get(position));
            }
        });

        // Configurar los EditText para aceptar solo números
        configurarEditTextNumerico(editText1);
        configurarEditTextNumerico(editText2);

        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumarYGuardar();
            }
        });
    }

    private void configurarEditTextNumerico(EditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)}); // Cambia 5 por la longitud máxima permitida
    }

    private void sumarYGuardar() {
        // Obtener los valores ingresados en los EditText
        String num1Str = editText1.getText().toString();
        String num2Str = editText2.getText().toString();

        // Verificar que ambos campos no estén vacíos
        if (!num1Str.isEmpty() && !num2Str.isEmpty()) {
            // Convertir los valores a enteros
            int num1 = Integer.parseInt(num1Str);
            int num2 = Integer.parseInt(num2Str);

            // Calcular la suma
            int suma = num1 + num2;

            // Crear una cadena con el formato "num1 + num2 = suma"
            String sumaString = num1 + " + " + num2 + " = " + suma;

            // Agregar la cadena al ArrayList y notificar al adaptador que los datos han cambiado
            sumasList.add(sumaString);
            adapter.notifyDataSetChanged();

            // Limpiar los EditText después de sumar
            editText1.getText().clear();
            editText2.getText().clear();

            // Ocultar el teclado virtual
            ocultarTeclado();
        }
    }

    // Método para ocultar el teclado virtual
    private void ocultarTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void mostrarInformacion(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}