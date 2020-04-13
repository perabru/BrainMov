package com.brunosjc.brainmov.activities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.brunosjc.brainmov.R;

import com.brunosjc.brainmov.modelo.Paciente;
import com.brunosjc.brainmov.modelo.Prontuario;
import com.brunosjc.brainmov.utilidade.Base64Custom;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProntuarioAdmActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_VIDEO_REQUEST = 22;
    //----Flags para o microfone

    private boolean flagNome=false;
    private boolean flagEmail=false;
    private boolean flagIdade=false;
    private boolean flagNascimento=false;
    private boolean flagEstadoCivil=false;
    private boolean flagEndereco=false;
    private boolean flagBairro =false;
    private boolean flagCidade =false;
    private boolean flagTelefone =false;
    private boolean flagProfissao =false;
    private boolean flagMedicamentos =false;
    private boolean flagOrteses =false;
    private boolean flagPressaoArterial =false;
    private boolean flagFrequenciaCardiaca =false;
    private boolean flagReflexosOsteotendinosos =false;
    private boolean flagTonusMuscular =false;
    private boolean flagSensibilidadeSuperficial =false;
    private boolean flagSensibilidadeProfunda =false;
    private boolean flagTrocaPosturais =false;
    private boolean flagForcaMuscularSegmento =false;
    private boolean flagEncurtamentoSegmento =false;
    private boolean flagDeformidadeSegmento =false;
    private boolean flagMensagem =false;


    //-----------------------------------

    private final int REQ_CODE_SPEECH_OUTPUT = 1;

    private ArrayList<String> voiceInText;

    private  Object resp;
    private TextView txtIdUsuario;
    private TextView txtNome;
    private TextView txtEmail;

    private TextView txtIdade;
    private TextView txtDataNascimento;

    private TextView txtEstadoCivil;
    private TextView txtEndereco;
    private TextView txtBairro;
    private TextView txtCidade;

    private TextView txtTelefone;
    private TextView txtProfissao;
    private TextView txtMedicamentos;

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

    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
    Date data = new Date();
    String dataFormatada = formataData.format(data);


    private Intent i;

    private Button btnVerVideosPAA;
    private Button btnGravarAlteracoesPAA;

    private Paciente paciente;
    private Prontuario prontuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prontuario_adm);

        btnVerVideosPAA = findViewById(R.id.btnVerVideosPAA);

        btnGravarAlteracoesPAA = findViewById(R.id.btnGravarPAA);


        paciente = new Paciente();
        prontuario = new Prontuario();

        Intent intent = getIntent();
        resp = intent.getSerializableExtra("info");






        carregarUsuario();
        carregarProntuario();

        btnVerVideosPAA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideosPacienteActivity.class);
                startActivity(intent);


                Intent i = new Intent(ProntuarioAdmActivity.this, VideosPacienteActivity.class);
                i.putExtra("info", resp.toString());
                startActivity(i);

                finish();


            }
        });


        txtIdUsuario = findViewById(R.id.txtUsuarioIDPAA);
        txtNome = findViewById(R.id.txtNomePAA);
        txtEmail = findViewById(R.id.txtEmailPAA);
        txtIdade = findViewById(R.id.txtIdadePAA);
        txtDataNascimento = findViewById(R.id.txtDataNascimentoPAA);
        txtEstadoCivil = findViewById(R.id.txtEstadoCivilPAA);
        txtEndereco = findViewById(R.id.txtEnderecoPAA);
        txtBairro = findViewById(R.id.txtBairroPAA);
        txtCidade = findViewById(R.id.txtCidadePAA);
        txtTelefone = findViewById(R.id.txtTelefonePAA);
        txtProfissao = findViewById(R.id.txtProfissaoPAA);
        txtMedicamentos = findViewById(R.id.txtMedicamentosPAA);

        txtOrteses = findViewById(R.id.txtOrtesesPAA);
        txtPressaoArterial = findViewById(R.id.txtPressaoArterialPAA);
        txtFrequenciaCardiaca = findViewById(R.id.txtFrequenciaCardiacaPAA);
        txtReflexosOsteotendinosos = findViewById(R.id.txtReflexosOsteotendinososPAA);
        txtTonusMuscular = findViewById(R.id.txtTonusMuscularPAA);
        txtSensibilidadeSuperficial = findViewById(R.id.txtSensibilidadeSuperficialPAA);
        txtSensibilidadeProfunda = findViewById(R.id.txtSensibilidadeProfundaPAA);
        txtTrocasPosturais = findViewById(R.id.txtTrocasPosturaisPAA);
        txtForcaMuscularSegmento = findViewById(R.id.txtForcaMuscularSegmentoPAA);
        txtEncurtamentoSegmento = findViewById(R.id.txtEncurtamentoSegmentoPAA);
        txtDeformidadeSegmento = findViewById(R.id.txtDeformidadePAA);
        txtMensagem = findViewById(R.id.txtMensagemPAA);



        txtNome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                 flagNome=true;
                 flagEmail=false;
                 flagIdade=false;
                 flagNascimento=false;
                 flagEstadoCivil=false;
                 flagEndereco=false;
                 flagBairro =false;
                 flagCidade =false;
                 flagTelefone =false;
                 flagProfissao =false;
                 flagMedicamentos =false;
                 flagOrteses =false;
                 flagPressaoArterial =false;
                 flagFrequenciaCardiaca =false;
                 flagReflexosOsteotendinosos =false;
                 flagTonusMuscular =false;
                 flagSensibilidadeSuperficial =false;
                 flagSensibilidadeProfunda =false;
                 flagTrocaPosturais =false;
                 flagForcaMuscularSegmento =false;
                 flagEncurtamentoSegmento =false;
                 flagDeformidadeSegmento =false;
                 flagMensagem =false;
                 openMic();


                return false;
            }
        });

        txtEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                flagNome=false;
                flagEmail=true;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();






                return false;
            }
        });

        txtIdade.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {





                flagNome=false;
                flagEmail=false;
                flagIdade=true;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();










                return false;
            }
        });

        txtDataNascimento.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=true;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();


                return false;
            }
        });

        txtEstadoCivil.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=true;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();


                return false;




            }
        });

        txtEndereco.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=true;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();


                return false;
            }
        });

        txtBairro.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =true;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();




                return false;
            }
        });

        txtCidade.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =true;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();





                return false;
            }
        });

        txtTelefone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =true;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();



                return false;
            }
        });

        txtProfissao.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =true;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();

                return false;
            }
        });

        txtMedicamentos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =true;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();


                return false;
            }
        });

        txtOrteses.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =true;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();


                return false;
            }
        });

        txtPressaoArterial.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =true;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();



                return false;
            }
        });

        txtFrequenciaCardiaca.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =true;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();


                return false;
            }
        });

        txtReflexosOsteotendinosos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =true;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();




                return false;
            }
        });

        txtTonusMuscular.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =true;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();


                return false;
            }
        });

        txtSensibilidadeSuperficial.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =true;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();



                return false;
            }
        });

        txtSensibilidadeProfunda.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {




                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =true;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();

                return false;
            }
        });

        txtTrocasPosturais.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =true;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();



                return false;
            }
        });

        txtForcaMuscularSegmento.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =true;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();



                return false;
            }
        });

        txtEncurtamentoSegmento.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =true;
                flagDeformidadeSegmento =false;
                flagMensagem =false;
                openMic();


                return false;
            }
        });

        txtDeformidadeSegmento.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =true;
                flagMensagem =false;
                openMic();



                return false;
            }
        });

        txtMensagem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {



                flagNome=false;
                flagEmail=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;
                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;
                flagOrteses =false;
                flagPressaoArterial =false;
                flagFrequenciaCardiaca =false;
                flagReflexosOsteotendinosos =false;
                flagTonusMuscular =false;
                flagSensibilidadeSuperficial =false;
                flagSensibilidadeProfunda =false;
                flagTrocaPosturais =false;
                flagForcaMuscularSegmento =false;
                flagEncurtamentoSegmento =false;
                flagDeformidadeSegmento =false;
                flagMensagem =true;
                openMic();


                return false;
            }
        });



        btnGravarAlteracoesPAA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paciente.setIdUsuario(txtIdUsuario.getText().toString());
                paciente.setNome(txtNome.getText().toString());

                paciente.setEmail(txtEmail.getText().toString());
                paciente.setEndereco(txtEndereco.getText().toString());

                paciente.setProfissao(txtProfissao.getText().toString());
                paciente.setTelefone(txtTelefone.getText().toString());
                paciente.setDataNascimento(txtDataNascimento.getText().toString());

                paciente.setCidade(txtCidade.getText().toString());
                paciente.setBairro(txtBairro.getText().toString());
                paciente.setTelefone(txtTelefone.getText().toString());
                paciente.setEstadoCivil(txtEstadoCivil.getText().toString());
                paciente.setIdade(txtIdade.getText().toString());
                paciente.setMedicamentos(txtMedicamentos.getText().toString());
                paciente.setSenha("*******");
                paciente.setDataAtual(dataFormatada);


                paciente.salvar();


                prontuario.setIdUsuario(txtIdUsuario.getText().toString());
                prontuario.setOrteses(txtOrteses.getText().toString());
                prontuario.setPressaoArterial(txtPressaoArterial.getText().toString());
                prontuario.setFrequenciaCardiaca(txtFrequenciaCardiaca.getText().toString());
                prontuario.setReflexosOsteotendineos(txtReflexosOsteotendinosos.getText().toString());
                prontuario.setTonusMuscular(txtTonusMuscular.getText().toString());
                prontuario.setSensibilidadeSuperficial(txtSensibilidadeSuperficial.getText().toString());
                prontuario.setSensibilidadeProfunda(txtSensibilidadeProfunda.getText().toString());
                prontuario.setTrocasPosturais(txtTrocasPosturais.getText().toString());
                prontuario.setForcaMuscularPorSeguimento(txtForcaMuscularSegmento.getText().toString());
                prontuario.setEncurtamentoPorSeguimento(txtEncurtamentoSegmento.getText().toString());
                prontuario.setComplicacoesEDeformidadesPorSeguimento(txtDeformidadeSegmento.getText().toString());
                prontuario.setMensagem(txtMensagem.getText().toString());

                //prontuario = new Prontuario();
                prontuario.salvarProntuario();
                carregarProntuario();

                Toast.makeText(ProntuarioAdmActivity.this, "Gravado com sucesso!", Toast.LENGTH_SHORT).show();






            }
        });



    }


    private void openMic() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "BrainMov");
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);
        } catch (ActivityNotFoundException tim) {

            Toast.makeText(getApplicationContext(), "Opa! seu aparelho não suporta reconhecimento de voz para aplicativos", Toast.LENGTH_SHORT).show();
        }

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_OUTPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    try {
                        voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        if(flagNome == true){

                            txtNome.setText(voiceInText.get(0));

                        }else if(flagEmail == true){
                            txtEmail.setText(voiceInText.get(0));
                        }else if(flagIdade == true){
                            txtIdade.setText(voiceInText.get(0));
                        }else if(flagNascimento == true){
                            txtDataNascimento.setText(voiceInText.get(0));
                        }else if(flagEstadoCivil == true){
                            txtEstadoCivil.setText(voiceInText.get(0));
                        }else if(flagEndereco == true){
                            txtEndereco.setText(voiceInText.get(0));
                        }else if(flagBairro == true){
                            txtBairro.setText(voiceInText.get(0));
                        }else if(flagCidade == true){
                            txtCidade.setText(voiceInText.get(0));
                        }else if(flagTelefone == true){
                            txtTelefone.setText(voiceInText.get(0));
                        }else if(flagProfissao == true){
                            txtProfissao.setText(voiceInText.get(0));
                        }else if(flagOrteses == true){
                            txtOrteses.setText(voiceInText.get(0));
                        }else if(flagPressaoArterial == true){
                            txtPressaoArterial.setText(voiceInText.get(0));
                        }else if(flagFrequenciaCardiaca == true){
                            txtFrequenciaCardiaca.setText(voiceInText.get(0));
                        }else if(flagReflexosOsteotendinosos == true){
                            txtReflexosOsteotendinosos.setText(voiceInText.get(0));
                        }else if(flagTonusMuscular == true){
                            txtTonusMuscular.setText(voiceInText.get(0));
                        }else if(flagSensibilidadeSuperficial == true){
                            txtSensibilidadeSuperficial.setText(voiceInText.get(0));
                        }else if(flagSensibilidadeProfunda == true){
                            txtSensibilidadeProfunda.setText(voiceInText.get(0));
                        }else if(flagTrocaPosturais == true){
                            txtTrocasPosturais.setText(voiceInText.get(0));
                        }else if(flagForcaMuscularSegmento == true){
                            txtForcaMuscularSegmento.setText(voiceInText.get(0));
                        }else if(flagDeformidadeSegmento == true){
                            txtDeformidadeSegmento.setText(voiceInText.get(0));
                        }else if(flagEncurtamentoSegmento == true){
                            txtEncurtamentoSegmento.setText(voiceInText.get(0));
                        }else if(flagMensagem == true){
                            txtMensagem.setText(voiceInText.get(0));
                        }else if(flagMedicamentos == true){
                            txtMedicamentos.setText(voiceInText.get(0));
                        }






                        // txtNome.setText(voiceInText.get(0).toString());

                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Não foi possível gravar " + ex.toString(), Toast.LENGTH_LONG).show();
                        //Log.i("ERRO",ex.toString());
                        openMic();
                        break;

                    }
                }
                break;
            }
        }

    }

    private void carregarUsuario(){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("paciente").child(Base64Custom.codificarBase64(resp.toString()));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                txtIdUsuario.setText(dataSnapshot.child("idUsuario").getValue().toString());
                txtNome.setText(dataSnapshot.child("nome").getValue().toString());
                txtEmail.setText(dataSnapshot.child("email").getValue().toString());
                txtIdade.setText(dataSnapshot.child("idade").getValue().toString());
                txtDataNascimento.setText(dataSnapshot.child("dataNascimento").getValue().toString());
                txtEstadoCivil.setText(dataSnapshot.child("estadoCivil").getValue().toString());
                txtEndereco .setText(dataSnapshot.child("endereco").getValue().toString());
                txtBairro.setText(dataSnapshot.child("bairro").getValue().toString());
                txtCidade.setText(dataSnapshot.child("cidade").getValue().toString());
                txtTelefone.setText(dataSnapshot.child("telefone").getValue().toString());
                txtProfissao.setText(dataSnapshot.child("profissao").getValue().toString());
                txtMedicamentos.setText(dataSnapshot.child("medicamentos").getValue().toString());
                //txtOrteses.setText(dataSnapshot.child("orteses").getValue().toString());




            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Erro ao ler banco de dados", Toast.LENGTH_LONG).show();

            }

        });


    }




    private void carregarProntuario() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("prontuario").child(Base64Custom.codificarBase64(resp.toString()));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot resultSnapshot: dataSnapshot.getChildren()){

                    //teste = resultSnapshot.getKey();
                    String textoOrteses = resultSnapshot.child("orteses").getValue().toString();

                    String textoPressaoArterial = resultSnapshot.child("pressaoArterial").getValue().toString();
                    String textoFrequenciaCardiaca = resultSnapshot.child("frequenciaCardiaca").getValue().toString();
                    String textoReflexosOsteotendineos = resultSnapshot.child("reflexosOsteotendineos").getValue().toString();
                    String textoTonusMuscular = resultSnapshot.child("tonusMuscular").getValue().toString();
                    String textoSensibilidadeSuperficial = resultSnapshot.child("sensibilidadeSuperficial").getValue().toString();
                    String textoSensibilidadeProfunda = resultSnapshot.child("sensibilidadeProfunda").getValue().toString();
                    String textoTrocasPosturais = resultSnapshot.child("trocasPosturais").getValue().toString();
                    String textoForcaMuscularSegmento = resultSnapshot.child("forcaMuscularPorSeguimento").getValue().toString();
                    String textoEncurtamentoPorSegmento = resultSnapshot.child("encurtamentoPorSeguimento").getValue().toString();
                    String textoComplicaoesEDeformidadesPorSegmentos = resultSnapshot.child("complicacoesEDeformidadesPorSeguimento").getValue().toString();
                    String textoMensagem = resultSnapshot.child("mensagem").getValue().toString();



                    //Log.d("TESTE1--------->", textoOrteses);
                    txtOrteses.setText(textoOrteses);
                    txtPressaoArterial.setText(textoPressaoArterial);
                    txtFrequenciaCardiaca.setText(textoFrequenciaCardiaca);
                    txtReflexosOsteotendinosos.setText(textoReflexosOsteotendineos);
                    txtTonusMuscular.setText(textoTonusMuscular);
                    txtSensibilidadeProfunda.setText(textoSensibilidadeProfunda);
                    txtSensibilidadeSuperficial.setText(textoSensibilidadeSuperficial);
                    txtForcaMuscularSegmento.setText(textoForcaMuscularSegmento);
                    txtTrocasPosturais.setText(textoTrocasPosturais);
                    txtEncurtamentoSegmento.setText(textoEncurtamentoPorSegmento);
                    txtDeformidadeSegmento.setText(textoComplicaoesEDeformidadesPorSegmentos);
                    txtMensagem.setText(textoMensagem);
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Erro ao ler banco de dados", Toast.LENGTH_LONG).show();

            }

        });



    }












    }















