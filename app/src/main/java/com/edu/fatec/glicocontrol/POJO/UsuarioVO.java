package com.edu.fatec.glicocontrol.POJO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 1050481413035 on 06/05/2016.
 */
public class UsuarioVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String nome;
    private String email;
    //private String senha;
    private Date dataNasc;
    private String sexo;

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }
}
