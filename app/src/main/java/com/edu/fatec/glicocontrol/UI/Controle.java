package com.edu.fatec.glicocontrol.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.edu.fatec.glicocontrol.POJO.ControleVO;
import com.edu.fatec.glicocontrol.R;

public class Controle extends Activity {

    private static final int INCLUIR = 0;
    //private static final int ALTERAR = 1;
    ControleVO lControleVO;
    EditText txtMedicao;
    EditText txtInsulina;
    Spinner txtPeriodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controle);

        try
        {


            final Bundle data = (Bundle) getIntent().getExtras();
            int lint = data.getInt("tipo");
            if (lint == INCLUIR)
            {
                //quando for incluir um contato criamos uma nova instância
                lControleVO = new ControleVO();
            }else{
                //quando for alterar o contato carregamos a classe que veio por Bundle
                lControleVO = (ControleVO)data.getSerializable("controle");
            }

            //Criação dos objetos da Activity
            txtMedicao = (EditText)findViewById(R.id.edtMedicao);
            txtInsulina = (EditText)findViewById(R.id.edtInsulina);
            txtPeriodo = (Spinner)findViewById(R.id.spHorario);

            //Carregando os objetos com os dados do Controle
            //caso seja uma inclusão ele virá carregado com os atributos text
            //definido no arquivo main.xml
            txtMedicao.setText((int) lControleVO.getMedicao());
            txtInsulina.setText((int) lControleVO.getInsulina());
            //txtPeriodo.setText(lControleVO.getPeriodo());
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public void btnConfirmar_click(View view)
    {
        try
        {
            //Quando confirmar a inclusão ou alteração deve-se devolver
            //o registro com os dados preenchidos na tela e informar
            //o RESULT_OK e em seguida finalizar a Activity


            Intent data = new Intent();
            lControleVO.setMedicao(Float.parseFloat(txtMedicao.getText().toString()));
            lControleVO.setInsulina(Float.parseFloat(txtInsulina.getText().toString()));
            lControleVO.setPeriodo(String.valueOf(txtPeriodo.getSelectedItem()));
            data.putExtra("controle", lControleVO);
            setResult(Activity.RESULT_OK, data);
            finish();
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public void btnCancelar_click(View view)
    {
        try
        {
            //Quando for simplesmente cancelar a operação de inclusão
            //ou alteração deve-se apenas informar o RESULT_CANCELED
            //e em seguida finalizar a Activity

            setResult(Activity.RESULT_CANCELED);
            finish();
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public void toast (String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
    }

    private void trace (String msg)
    {
        toast(msg);
    }

}
