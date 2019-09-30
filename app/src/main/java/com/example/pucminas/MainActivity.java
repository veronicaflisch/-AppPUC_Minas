package com.example.pucminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText tLogin = (EditText) findViewById(R.id.tLogin);
        EditText tSenha = (EditText) findViewById(R.id.tSenha);
        String login = tLogin.getText().toString();
        String senha = tSenha.getText().toString();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null ){
                    alert("Login realizado com sucesso!");
                    Intent i = new Intent(MainActivity.this, TelaPrincipal.class);
                    startActivity(i);
                }else{
                    alert("Por favor, faça seu Login no app");

                }
            }
        };

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tLogin = (EditText) findViewById(R.id.tLogin);
                EditText tSenha = (EditText) findViewById(R.id.tSenha);
                String login = tLogin.getText().toString();
                String senha = tSenha.getText().toString();
                if(login.isEmpty()){
                    tLogin.setError("O campo Login/Usuário não pode ser deixado em branco");
                    tLogin.requestFocus();
                }else if(senha.isEmpty()){
                    tSenha.setError("Por favor, digite sua senha");
                    tSenha.requestFocus();
                }else if(login.isEmpty() && senha.isEmpty()){
                    alert("Os campos estão em branco");
                    //Toast.makeText(MainActivity.this,"Os campos estão em branco",Toast.LENGTH_LONG);
                }else if(!(login.isEmpty() && senha.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(login,senha).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                alert("Dados incorretos!");
                            }else{
                                Intent telaPrincipal = new Intent(MainActivity.this, TelaPrincipal.class);
                                startActivity(telaPrincipal);
                            }
                        }
                    });

                }else{
                    alert("Erro inesperado!");
                }

                //if(login.equals("veronica")&&senha.equals("123")){
                        //alert("Login realizado com sucesso");
                //}else{
                    //alert("Erro");
                //}
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();

    }
}
