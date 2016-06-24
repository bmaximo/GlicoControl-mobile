package com.edu.fatec.glicocontrol.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.edu.fatec.glicocontrol.POJO.AlarmeVO;
import com.edu.fatec.glicocontrol.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Alarme extends Activity {

    private static final int INCLUIR = 0;
    //private static final int ALTERAR = 1;
    AlarmeVO lAlarmeVO;
    TimePicker txtHora;
    Date dataFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);

        try
        {


            final Bundle data = (Bundle) getIntent().getExtras();
            int lint = data.getInt("tipo");
            if (lint == INCLUIR)
            {
                //quando for incluir um contato criamos uma nova instância
                lAlarmeVO = new AlarmeVO();
            }else{
                //quando for alterar o contato carregamos a classe que veio por Bundle
                lAlarmeVO = (AlarmeVO)data.getSerializable("alarme");
            }

            //Criação dos objetos da Activity
            txtHora = (TimePicker)findViewById(R.id.edtHora);

            Calendar c = Calendar.getInstance();
            c.setTime(lAlarmeVO.getHora());

            txtHora.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
            txtHora.setCurrentMinute(c.get(Calendar.MINUTE));

        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void btnConfirmar_click(View view)
    {
        try
        {
            //Quando confirmar a inclusão ou alteração deve-se devolver
            //o registro com os dados preenchidos na tela e informar
            //o RESULT_OK e em seguida finalizar a Activity

            Intent data = new Intent();

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            try {
                dataFormat = sdf.parse(String.valueOf(txtHora.getHour()));
                dataFormat = sdf.parse(String.valueOf(txtHora.getMinute()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            lAlarmeVO.setHora(dataFormat);

            data.putExtra("alarme", lAlarmeVO);
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
