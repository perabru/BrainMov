package com.brunosjc.brainmov.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.brunosjc.brainmov.R;
import com.brunosjc.brainmov.modelo.Paciente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PainelAdmActivity  extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Button btnBuscarPADM;

    private ListView listView;
    private EditText edtBuscar;

    ArrayList<String> arrayList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_adm);

        edtBuscar = findViewById(R.id.edtBuscar);

        btnBuscarPADM = findViewById(R.id.btnBuscarPADM);
        popularLista();

        //create object of listview
        listView = (ListView) findViewById(R.id.listview);

        btnBuscarPADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < arrayList.size(); i++){

                    if(edtBuscar.getText().toString().equalsIgnoreCase(arrayList.get(i))){

                        Toast.makeText(PainelAdmActivity.this, "LOCALIZADO", Toast.LENGTH_SHORT).show();

                        // setTemporario(edtBuscar.getText().toString());


                        Intent intent = new Intent(getApplicationContext(), ProntuarioAdmActivity.class);
                        intent.putExtra("info", edtBuscar.getText().toString());
                        startActivity(intent);




                    }
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                //Toast.makeText(PainelAdmActivity.this, "clicked item:" + i + " " + arrayList.get(i), Toast.LENGTH_SHORT).show();
                edtBuscar.setText(arrayList.get(i).toString());

                // myRef.child("Leito"+1).child("status").setValue("Ocupado");

            }
        });


    }

    private void popularLista(){

        FirebaseDatabase.getInstance().getReference().child("paciente")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Paciente user = snapshot.getValue(Paciente.class);

                            Log.d("AQUI ------------->",user.getEmail());

                            arrayList.add(user.getNome());
                            arrayList.add(user.getEmail());


                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);

                            listView.setAdapter(arrayAdapter);


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });
    }
}
