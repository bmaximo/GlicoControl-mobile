package com.edu.fatec.glicocontrol.POJO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Barbara Maximo on 23/06/2016.
 */
public class AlarmeVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private Date hora;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
}
