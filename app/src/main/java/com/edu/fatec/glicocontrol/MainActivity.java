package com.edu.fatec.glicocontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.edu.fatec.glicocontrol.UI.Cadastro;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cadastro (View v){
        Intent tCadastro = new Intent(this, Cadastro.class);
        startActivity(tCadastro);
    }


}
