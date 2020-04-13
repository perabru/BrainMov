package com.brunosjc.brainmov.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.brunosjc.brainmov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAdmActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btnEntrarAdm;
    private TextView txtEmailAdm;
    private TextView txtSenhaAdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_adm);

        txtEmailAdm = findViewById(R.id.txtEmailAdm);
        txtSenhaAdm = findViewById(R.id.txtSenhaAdm);
        btnEntrarAdm = findViewById(R.id.btnEntrarAdm);


        btnEntrarAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                entrarAdm();
                /*Intent intent = new Intent(getApplicationContext(), PainelAdmActivity.class);
                startActivity(intent);*/

            }
        });
    }


    private void entrarAdm(){



// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Conectando se já existe

        if(txtEmailAdm.getText().toString().equalsIgnoreCase("smartclinica@smartclinica.com.br")) {
            if(txtSenhaAdm.getText().toString().equalsIgnoreCase("brainmov123456")) {
                mAuth.signInWithEmailAndPassword(txtEmailAdm.getText().toString(), txtSenhaAdm.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Toast.makeText(LoginAdmActivity.this, "O administrador está online",
                                            Toast.LENGTH_LONG).show();


                                    Intent intent = new Intent(getApplicationContext(), PainelAdmActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(LoginAdmActivity.this, "Falha ao logar",
                                            Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
            }

        }

    }
}

