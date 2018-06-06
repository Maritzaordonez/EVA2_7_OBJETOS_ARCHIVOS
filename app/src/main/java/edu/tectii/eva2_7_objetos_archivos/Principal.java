package edu.tectii.eva2_7_objetos_archivos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Principal extends AppCompatActivity {
    EditText edtnom, edtape, edtedad;
    CheckBox cbEstuActi;
    RadioButton rbmas, rbfem, rbot;
    TextView txtVwDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        edtnom = findViewById(R.id.edtnom);
        edtape = findViewById(R.id.edtape);
        edtedad = findViewById(R.id.edtedad);
        cbEstuActi = findViewById(R.id.checkBox);
        rbmas = findViewById(R.id.hombre);
        rbfem = findViewById(R.id.mujer);
        rbot = findViewById(R.id.indefinido);
        txtVwDatos = findViewById(R.id.info);
    }


    public void onGuardar (View v) {
        //LEER LOS WIDGETS
        String sNom = edtnom.getText().toString();
        String sApe = edtape.getText().toString();
        int iEdad= Integer.parseInt(edtedad.getText().toString());
        boolean bEstuAct = cbEstuActi.isChecked();
        int iSex;
        if (rbmas.isChecked()){
            iSex = 0;
        } else if (rbfem.isChecked()) {
            iSex = 1;
        } else {
            iSex = 2;
        }

        Datos dDato = new Datos(sNom, sApe, iEdad, bEstuAct, iSex);

        try {
            FileOutputStream fosAbrir = openFileOutput("mis_objetos.flk",0);
            ObjectOutputStream oosGuardarObjeto = new ObjectOutputStream(fosAbrir);
            oosGuardarObjeto.writeObject(dDato);
            oosGuardarObjeto.close();
        } catch (IOException E) {E.printStackTrace();}

    }

    public void onLeer (View v) {
        FileInputStream fisAbrir = null;
        ObjectInputStream oisLeer = null;

        try {
            fisAbrir = openFileInput("mis_objetos.flk");
            oisLeer = new ObjectInputStream(fisAbrir);
            Datos dDato = (Datos) oisLeer.readObject();
            while (true) {
                txtVwDatos.append("Nombre: " + dDato.getNombre());
                txtVwDatos.append("Apellido: " + dDato.getApellido());
                txtVwDatos.append("Edad: " + dDato.getEdad());
                if (dDato.isActivo()) {
                    txtVwDatos.append("Estudiante Activo");
                } else {
                    txtVwDatos.append("Inactivo");
                }
                switch (dDato.getGruposexo()) {
                    case 0:
                        txtVwDatos.append("Genero Masculino");
                    case 1:
                        txtVwDatos.append("Genero Femenino");
                    default:
                        txtVwDatos.append("Genero Indefinido");
                }
                dDato = (Datos) oisLeer.readObject();

            }
        } catch (IOException E) {E.printStackTrace();} catch (ClassNotFoundException E) {E.printStackTrace();} finally {
            if (oisLeer != null) {
                try {
                    oisLeer.close();
                } catch (IOException E) {E.printStackTrace();}
            }
        }



    }


}



class Datos implements Serializable {
    private String nombre;
    private String apellido;
    private int edad;
    private boolean activo;
    private int gruposexo;

    public Datos(String nombre, String apellido, int edad, boolean activo, int gruposexo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.activo = activo;
        this.gruposexo = gruposexo;
    }

    public Datos() {
        this.nombre = "Maritza";
        this.apellido = "Ordoñez";
        this.edad = 23;
        this.activo = true;
        this.gruposexo = 2;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isActivo() {
        return activo;
    }

    public int getGruposexo() {
        return gruposexo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setGruposexo(int gruposexo) {
        this.gruposexo = gruposexo;
    }
}
