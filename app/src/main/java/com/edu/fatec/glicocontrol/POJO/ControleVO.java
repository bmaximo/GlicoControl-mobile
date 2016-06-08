package com.edu.fatec.glicocontrol.POJO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Barbara Maximo on 08/06/2016.
 */
public class ControleVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private float medicao;
    private float insulina;
    private String periodo;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getMedicao() {
        return medicao;
    }

    public void setMedicao(float medicao) {
        this.medicao = medicao;
    }

    public float getInsulina() {
        return insulina;
    }

    public void setInsulina(float insulina) {
        this.insulina = insulina;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }


    @Override
    public String toString() {
        return java.lang.String.valueOf(id);
    }
}
