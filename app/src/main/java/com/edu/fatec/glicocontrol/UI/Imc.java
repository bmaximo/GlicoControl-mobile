package com.edu.fatec.glicocontrol.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.edu.fatec.glicocontrol.R;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Imc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
    }
    public double truncate (Double valor, int precisao){
        BigDecimal bd = BigDecimal.valueOf(valor);
        bd = bd.setScale(precisao, BigDecimal.ROUND_DOWN);
        return bd.doubleValue();
    }
    public void calculo(View v){
        EditText altura = (EditText) findViewById(R.id.edtAltura);
        EditText peso = (EditText) findViewById(R.id.edtPeso);
        TextView result = (TextView) findViewById(R.id.txtResultado);
        TextView coment = (TextView) findViewById(R.id.txtComentario);

        Double a = Double.parseDouble(altura.getText().toString());
        Double p = Double.parseDouble(peso.getText().toString());
        Double r = p/(Math.pow(a,2));
        r = truncate(r,2);
        result.setText(r.toString());
        if(r<=18.5){
            coment.setText("Abaixo do Peso");
        }
        if(r>=18.6 && r<=24.9){
            coment.setText("Peso Ideal");
        }
        if(r>=25.0 && r<=29.9){
            coment.setText("Levemente Acima do Peso");
        }
        if(r>=30.0 && r<=34.9){
            coment.setText("Obesidade Grau I");
        }
        if(r>=35.0 && r<=39.9){
            coment.setText("Obesidade Grau II");
        }
        if(r>=40.0){
            coment.setText("Obesidade Grau III");
        }

    }
}
