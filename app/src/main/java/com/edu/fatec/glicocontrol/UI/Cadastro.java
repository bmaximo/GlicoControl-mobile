package com.edu.fatec.glicocontrol.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.edu.fatec.glicocontrol.DAO.BaseDAO;
import com.edu.fatec.glicocontrol.DAO.UsuarioDAO;
import com.edu.fatec.glicocontrol.POJO.UsuarioVO;
import com.edu.fatec.glicocontrol.R;
import com.edu.fatec.glicocontrol.Util.Mask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.ViewDebug.trace;

public class Cadastro extends AppCompatActivity {
    UsuarioDAO dao = new UsuarioDAO(getBaseContext());
    UsuarioVO usuarioVO = new UsuarioVO();
    int selectedS;
    Date dataFormat;
    EditText nome;
    EditText email;
    RadioButton sexo;
    EditText dataNasc;
    RadioGroup sexoGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        //Button salvar = (Button) findViewById(R.id.btCadastrar);

        nome = (EditText) findViewById(R.id.edtNome);
        email = (EditText) findViewById(R.id.edtEmail);
        sexoGroup = (RadioGroup) findViewById(R.id.rdSexo);
        selectedS = sexoGroup.getCheckedRadioButtonId();
        sexo = (RadioButton) findViewById(selectedS);
        dataNasc = (EditText) findViewById(R.id.edtDataNasc);
    }

    public void salvarDados(View v){
        usuarioVO.setNome(nome.getText().toString());
        usuarioVO.setEmail(email.getText().toString());
        if(selectedS == R.id.rdF){
            usuarioVO.setSexo("F");
        }else{
            if(selectedS == R.id.rdM){
                usuarioVO.setSexo("M");
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataFormat = sdf.parse(String.valueOf(dataNasc.getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        usuarioVO.setDataNasc(dataFormat);


        dao.Inserir(usuarioVO);

        Toast.makeText(getApplicationContext(), "Cadastro efetuado", Toast.LENGTH_LONG).show();
    }

}
