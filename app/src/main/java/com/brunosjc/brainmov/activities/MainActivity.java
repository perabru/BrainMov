package com.brunosjc.brainmov.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.brunosjc.brainmov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView txtEmailMA;
    private TextView txtSenhaMA;
    private Button btnEntrarMA;
    private Button btnCadastrarMA;
    private TextView lblEsqueciaSenha;
    private TextView lblLogindAdmMA;
    FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtEmailMA = findViewById(R.id.txtEmailMA);
        txtSenhaMA = findViewById(R.id.txtSenhaMA);
        btnEntrarMA = findViewById(R.id.btnEntrarMA);
        btnCadastrarMA = findViewById(R.id.btnCadastrarMA);
        lblEsqueciaSenha = findViewById(R.id.lblEsqueciSenhaMA);
        lblLogindAdmMA = findViewById(R.id.lblLoginAdmMA);

        lblEsqueciaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecuperarSenhaActivity.class);
                startActivity(intent);

            }
        });


        btnCadastrarMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);

            }
        });

        btnEntrarMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), PrincipalPacienteActivity.class);
                startActivity(intent);*/
                logarUsuario();
               // finish();
            }
        });

        lblLogindAdmMA.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginAdmActivity.class);
                startActivity(intent);
                finish();
                return false;
            }
        });
    }
        private void logarUsuario(){


// Initialize Firebase Auth

            if(!txtEmailMA.getText().toString().isEmpty() && !txtSenhaMA.getText().toString().isEmpty()) {
                mAuth = FirebaseAuth.getInstance();
                //Conectando se já existe
                mAuth.signInWithEmailAndPassword(txtEmailMA.getText().toString(), txtSenhaMA.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Intent intent = new Intent(getApplicationContext(), PrincipalPacienteActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(MainActivity.this, "Falha ao logar",
                                            Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
            }else{
                Toast.makeText(MainActivity.this, "Falha ao logar",
                        Toast.LENGTH_SHORT).show();
            }


        }
    }

