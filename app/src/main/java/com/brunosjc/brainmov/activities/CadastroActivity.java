package com.brunosjc.brainmov.activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.brunosjc.brainmov.R;
import com.brunosjc.brainmov.modelo.Paciente;
import com.brunosjc.brainmov.modelo.Prontuario;
import com.brunosjc.brainmov.utilidade.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class CadastroActivity  extends AppCompatActivity {


    private FirebaseAuth mAuth;
    //----Flags para o microfone

   private boolean flagNome=false;
    private boolean flagEmail=false;
    private boolean flagSenha=false;
    private boolean flagIdade=false;
    private boolean flagNascimento=false;
    private boolean flagEstadoCivil=false;
    private boolean flagEndereco=false;
    private boolean flagBairro =false;
    private boolean flagCidade =false;

    private boolean flagTelefone =false;
    private boolean flagProfissao =false;
    private boolean flagMedicamentos =false;
    //--------------------------------

    private final int REQ_CODE_SPEECH_OUTPUT = 1;

    private ArrayList<String> voiceInText;


    private TextView txtNome;
    private TextView txtEmail;
    private TextView txtSenha;
    private TextView txtIdade;
    private TextView txtDataNascimento;

    private TextView txtEstadoCivil;
    private TextView txtEndereco;
    private TextView txtBairro;
    private TextView txtCidade;

    private TextView txtTelefone;
    private TextView txtProfissao;
    private TextView txtMedicamentos;

    private Button btnCadastrar;

    private Paciente paciente;
    private Prontuario prontuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_cadastro);
        mAuth = FirebaseAuth.getInstance();

        btnCadastrar = findViewById(R.id.btnCadastrar2);

        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmailC);
        txtSenha = findViewById(R.id.txtSenhaC);
        txtIdade = findViewById(R.id.txtIdade);
        txtDataNascimento = findViewById(R.id.txtDataNascimento);
        txtEstadoCivil = findViewById(R.id.txtEstadoCivil);
        txtEndereco = findViewById(R.id.txtEndereco);
        txtBairro = findViewById(R.id.txtBairro);
        txtCidade = findViewById(R.id.txtCidade);
       txtTelefone = findViewById(R.id.txtTelefone);
        txtProfissao = findViewById(R.id.txtProfissao);
        txtMedicamentos = findViewById(R.id.txtMedicamentos);


        //---------------Cadastrando usuário
       btnCadastrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String textoNome = txtNome.getText().toString();
               String textoEmail = txtEmail.getText().toString();
               String textoSenha = txtSenha.getText().toString();
               String textoIdade = txtIdade.getText().toString();
               String textoDataNascimento = txtDataNascimento.getText().toString();
               String textoEstadoCivil = txtEstadoCivil.getText().toString();
               String textoEndereco = txtEndereco.getText().toString();
               String textoBairro = txtBairro.getText().toString();
               String textoCidade = txtCidade.getText().toString();
               String textoTelefone = txtTelefone.getText().toString();
               String textoProfissao = txtProfissao.getText().toString();
               String textoMedicamentos = txtMedicamentos.getText().toString();
               SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
               Date data = new Date();
               String textoDataAtual = formataData.format(data);




               if(!textoNome.isEmpty()){
                   if(!textoEmail.isEmpty()){
                       if(!textoSenha.isEmpty()){
                           if(!textoIdade.isEmpty()){
                               if(!textoDataNascimento.isEmpty()){
                                   if(!textoEstadoCivil.isEmpty()){
                                       if(!textoEndereco.isEmpty()){
                                           if(!textoBairro.isEmpty()){
                                               if(!textoCidade.isEmpty()){
                                                   if(!textoTelefone.isEmpty()){
                                                       if(!textoProfissao.isEmpty()){

                                                           if(!textoMedicamentos.isEmpty()){

                                                               paciente = new Paciente();
                                                               paciente.setNome(textoNome);
                                                               paciente.setEmail(textoEmail);
                                                               paciente.setSenha(textoSenha);
                                                               paciente.setIdade(textoIdade);
                                                               paciente.setDataNascimento(textoDataNascimento);
                                                               paciente.setEstadoCivil(textoEstadoCivil);
                                                               paciente.setEndereco(textoEndereco);
                                                               paciente.setBairro(textoBairro);
                                                               paciente.setCidade(textoCidade);
                                                               paciente.setTelefone(textoTelefone);
                                                               paciente.setProfissao(textoProfissao);
                                                               paciente.setMedicamentos(textoMedicamentos);
                                                               paciente.setDataAtual(textoDataAtual);


                                                               //----Parte 2 do Prontuario
                                                               prontuario = new Prontuario();

                                                               prontuario.setOrteses("Aguardando órteses");
                                                               prontuario.setPressaoArterial("Aguardando pressão");
                                                               prontuario.setFrequenciaCardiaca("Aguardando Frequência Cardiaca");
                                                               prontuario.setReflexosOsteotendineos("Aguardando Reflexos Osteotendineos");
                                                               prontuario.setTonusMuscular("Aguardando Tônus Muscular");
                                                               prontuario.setSensibilidadeProfunda("Aguardando Sensibilidade Profunda");
                                                               prontuario.setSensibilidadeSuperficial("Aguardando Sensibilidade Superficial");
                                                               prontuario.setTrocasPosturais("Aguardando Trocas Posturais");
                                                               prontuario.setForcaMuscularPorSeguimento("Aguardando Força Muscular Por Segmento");
                                                               prontuario.setEncurtamentoPorSeguimento("Aguardando Força Encurtamento Por Segmento");
                                                               prontuario.setComplicacoesEDeformidadesPorSeguimento("Aguardando Complicações e Deformidades por Segmento");
                                                               prontuario.setMensagem("Aguardando Mensagem");





                                                              // finish();
                                                               cadastrarPaciente();
                                                               Toast.makeText(CadastroActivity.this,"Cadastro feito com sucesso!", Toast.LENGTH_SHORT).show();


                                                           }else{
                                                               Toast.makeText(CadastroActivity.this,"Preencha sobre Medicamentos", Toast.LENGTH_SHORT).show();
                                                           }

                                                       }else{
                                                           Toast.makeText(CadastroActivity.this,"Preencha a Profissão", Toast.LENGTH_SHORT).show();
                                                       }

                                                   }else{
                                                       Toast.makeText(CadastroActivity.this,"Preencha o Telefone", Toast.LENGTH_SHORT).show();
                                                   }

                                               }else{
                                                   Toast.makeText(CadastroActivity.this,"Preencha a Cidade", Toast.LENGTH_SHORT).show();
                                               }

                                           }else{
                                               Toast.makeText(CadastroActivity.this,"Preencha o Bairro", Toast.LENGTH_SHORT).show();
                                           }

                                       }else{
                                           Toast.makeText(CadastroActivity.this,"Preencha o Endereço", Toast.LENGTH_SHORT).show();
                                       }

                                      }else{
                                       Toast.makeText(CadastroActivity.this,"Preencha o Estado Civil", Toast.LENGTH_SHORT).show();
                                   }




                               }else{
                                   Toast.makeText(CadastroActivity.this,"Preencha a data de nascimento", Toast.LENGTH_SHORT).show();
                               }

                           }else{
                               Toast.makeText(CadastroActivity.this,"Preencha a idade", Toast.LENGTH_SHORT).show();
                           }



                       }else{
                           Toast.makeText(CadastroActivity.this,"Preencha a senha", Toast.LENGTH_SHORT).show();
                       }

                   }else{
                       Toast.makeText(CadastroActivity.this,"Preencha o e-mail", Toast.LENGTH_SHORT).show();
                   }

               }else{
                   Toast.makeText(CadastroActivity.this,"Preencha o nome", Toast.LENGTH_SHORT).show();
               }


           }
       });




        //------------Fim


        txtNome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                 flagNome=true;
                 flagEmail=false;
                 flagSenha=false;
                 flagIdade=false;
                 flagNascimento=false;
                 flagEstadoCivil=false;
                 flagEndereco=false;
                 flagBairro =false;
                 flagCidade =false;

                 flagTelefone =false;
                 flagProfissao =false;
                 flagMedicamentos =false;

            openMic();

                        /*Toast.makeText(CadastroActivity.this, "Long click selected",
                        Toast.LENGTH_SHORT).show();*/
                return false;
            }
        });

        txtEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=true;
                flagSenha=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;

                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });



        txtSenha.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=true;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;

                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });


        txtIdade.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=false;
                flagIdade=true;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;

                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });
        txtDataNascimento.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=false;
                flagIdade=false;
                flagNascimento=true;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;

                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });

        txtEstadoCivil.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=true;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;

                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });


        txtEndereco.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=true;
                flagBairro =false;
                flagCidade =false;

                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });


        txtBairro.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =true;
                flagCidade =false;

                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });

        txtCidade.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =true;

                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });

        txtTelefone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;

                flagTelefone =true;
                flagProfissao =false;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });


        txtProfissao.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;

                flagTelefone =false;
                flagProfissao =true;
                flagMedicamentos =false;

                openMic();
                return false;
            }
        });


        txtMedicamentos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                flagNome=false;
                flagEmail=false;
                flagSenha=false;
                flagIdade=false;
                flagNascimento=false;
                flagEstadoCivil=false;
                flagEndereco=false;
                flagBairro =false;
                flagCidade =false;

                flagTelefone =false;
                flagProfissao =false;
                flagMedicamentos =true;

                openMic();
                return false;
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
                        }else if(flagSenha == true){
                            txtSenha.setText(voiceInText.get(0));
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



    private void cadastrarPaciente(){
        //-----------------------------------Método para criar usuário
        mAuth.createUserWithEmailAndPassword(paciente.getEmail(), paciente.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String idUsuario = Base64Custom.codificarBase64(paciente.getEmail());
                            paciente.setIdUsuario(idUsuario);
                            paciente.salvar();

                            prontuario.setIdUsuario(idUsuario);
                            prontuario.salvarProntuario();



                            finish();
                            Intent intent = new Intent(getApplicationContext(), PrincipalPacienteActivity.class);
                            startActivity(intent);

                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            // FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            String excecao ="";
                            try{
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e){
                                excecao ="Digite uma senha mais forte";

                            } catch (FirebaseAuthInvalidCredentialsException e){
                                excecao = "Por favor, digite um email válido";
                            }
                            catch (FirebaseAuthUserCollisionException e){
                                excecao = "Essa conta ja foi cadastrada";
                            }
                            catch (Exception e){
                                excecao ="Erro ao cadastrar usuário: "+e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(CadastroActivity.this,"Erro ao cadastrar", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
        //------------------------------------FIM

    }
}
