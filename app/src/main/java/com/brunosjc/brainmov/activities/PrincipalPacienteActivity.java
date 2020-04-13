package com.brunosjc.brainmov.activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.brunosjc.brainmov.R;
import com.brunosjc.brainmov.utilidade.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PrincipalPacienteActivity extends AppCompatActivity {

    private Button btnSair;
    private Button bntGerarPDF;

    private TextView txtOrteses;
    private TextView txtPressaoArterial;

    private TextView txtFrequenciaCardiaca;
    private TextView txtReflexosOsteotendinosos;
    private TextView txtTonusMuscular;
    private TextView txtSensibilidadeSuperficial;

    private TextView txtSensibilidadeProfunda;
    private TextView txtTrocasPosturais;
    private TextView txtForcaMuscularSegmento;


    private TextView txtEncurtamentoSegmento;
    private TextView txtDeformidadeSegmento;
    private TextView txtMensagem;

    private TextView txtNome;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_paciente);

        btnSair = findViewById(R.id.btnSairPP);
        bntGerarPDF = findViewById(R.id.btnGerarPDFPP);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            email = user.getEmail();
        }
        carregarInfo();
        txtOrteses = findViewById(R.id.txtOrtesesPP);
        txtPressaoArterial = findViewById(R.id.txtPressaoArterialPP);
        txtFrequenciaCardiaca = findViewById(R.id.txtFrequenciaCardiacaPP);
        txtReflexosOsteotendinosos = findViewById(R.id.txtReflexosOsteotendinososPP);
        txtTonusMuscular = findViewById(R.id.txtTonusMuscularPP);
        txtSensibilidadeSuperficial = findViewById(R.id.txtSensibilidadeSuperficialPP);
        txtSensibilidadeProfunda = findViewById(R.id.txtSensibilidadeProfundaPP);
        txtTrocasPosturais = findViewById(R.id.txtTrocasPosturaisPP);
        txtForcaMuscularSegmento = findViewById(R.id.txtForcaMuscularSegmentoPP);
        txtEncurtamentoSegmento = findViewById(R.id.txtEncurtamentoSegmentoPP);
        txtDeformidadeSegmento = findViewById(R.id.txtDeformidadePP);
        txtMensagem = findViewById(R.id.txtMensagemPP);
        txtNome = findViewById(R.id.txtNomePP);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bntGerarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPdf(txtNome.getText().toString()+"\n"+
                        txtOrteses.getText().toString()+"\n"+
                        txtPressaoArterial.getText().toString()+"\n"+
                        txtFrequenciaCardiaca.getText().toString()+"\n"+
                        txtReflexosOsteotendinosos.getText().toString()+"\n"+
                        txtTonusMuscular.getText().toString()+"\n"+
                        txtSensibilidadeSuperficial.getText().toString()+"\n"+
                        txtSensibilidadeProfunda.getText().toString()+"\n"+
                        txtTrocasPosturais.getText().toString()+"\n"+
                        txtForcaMuscularSegmento.getText().toString()+"\n"+
                        txtEncurtamentoSegmento.getText().toString()+"\n"+
                        txtDeformidadeSegmento.getText().toString()+"\n"+
                        txtMensagem.getText().toString());
            }
        });


    }


    private void carregarInfo() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("prontuario").child(Base64Custom.codificarBase64(email));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // String teste = dataSnapshot.getValue().toString();

                /*String teste = dataSnapshot.child("etapa").getValue().toString();
                Log.d("AQUI --------->ETAPA",teste);*/

                //Log.d("TESTE1--------->", textoOrteses);

                for (DataSnapshot resultSnapshot : dataSnapshot.getChildren()) {

                    txtOrteses.setText(resultSnapshot.child("orteses").getValue().toString());
                    txtPressaoArterial.setText(resultSnapshot.child("pressaoArterial").getValue().toString());
                    txtFrequenciaCardiaca.setText(resultSnapshot.child("frequenciaCardiaca").getValue().toString());
                    txtReflexosOsteotendinosos.setText(resultSnapshot.child("reflexosOsteotendineos").getValue().toString());

                    txtTonusMuscular.setText(resultSnapshot.child("tonusMuscular").getValue().toString());
                    txtSensibilidadeProfunda.setText(resultSnapshot.child("sensibilidadeProfunda").getValue().toString());
                    txtSensibilidadeSuperficial.setText(resultSnapshot.child("sensibilidadeSuperficial").getValue().toString());
                    txtForcaMuscularSegmento.setText(resultSnapshot.child("forcaMuscularPorSeguimento").getValue().toString());
                    txtTrocasPosturais.setText(resultSnapshot.child("trocasPosturais").getValue().toString());
                    txtEncurtamentoSegmento.setText(resultSnapshot.child("encurtamentoPorSeguimento").getValue().toString());
                    txtDeformidadeSegmento.setText(resultSnapshot.child("complicacoesEDeformidadesPorSeguimento").getValue().toString());
                    txtMensagem.setText(resultSnapshot.child("mensagem").getValue().toString());
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database2.getReference("paciente").child(Base64Custom.codificarBase64(email));

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                txtNome.setText(dataSnapshot.child("nome").getValue().toString());
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }


    private void createPdf(String sometext) {
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(50, 50, 30, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(sometext, 80, 50, paint);
        //canvas.drawt
        // finish the page
        document.finishPage(page);

        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"brainmov.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {

            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }

}


