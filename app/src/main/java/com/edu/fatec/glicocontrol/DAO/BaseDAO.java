package com.edu.fatec.glicocontrol.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 1050481413035 on 06/05/2016.
 */
public class BaseDAO extends SQLiteOpenHelper{

    //tabela usuario
    public static final String TBL_USUARIO =   "usuario";
    public static final String USUARIO_EMAIL = "email";
    public static final String USUARIO_NOME = "nome";
    public static final String USUARIO_NASCIMENTO = "nascimento";
    public static final String USUARIO_ID = "id";
    public static final String USUARIO_SEXO = "sexo";
    //tabela controle
    public static final String TBL_CONTROLE = "controle";
    public static final String CONTROLE_ID = "id";
    public static final String CONTROLE_MEDICAO ="medicao";
    public static final String CONTROLE_INSULINA = "insulina";
    public static final String CONTROLE_PERIODO = "periodo";
    //tabela alarme
    public static final String TBL_ALARME = "alarme";
    public static final String ALARME_ID = "id";
    public static final String ALARME_HORA ="hora";


    //Estrutura da tabela Usuario (sql statement)
    private static final String CREATE_USUARIO = "create table " +
            TBL_USUARIO + "( " + USUARIO_ID       + " integer primary key autoincrement, " +
            USUARIO_EMAIL     + " text, " +
            //USUARIO_SENHA + " text not null, " +
            USUARIO_NOME + " text, "+
            USUARIO_NASCIMENTO + " date, "+
            USUARIO_SEXO + " text);";
    //estrutura da tabela controle
    private static final String CREATE_CONTROLE = "create table "+
            TBL_CONTROLE + "(" + CONTROLE_ID + " integer primary key autoincrement, " +
            CONTROLE_MEDICAO + " float, " +
            CONTROLE_INSULINA + " float, " +
            CONTROLE_PERIODO + " text); ";
    //Estrutura da tabela alarme
    private static final String CREATE_ALARME = "create table "+
            TBL_ALARME + "(" + ALARME_ID + " integer primary key autoincrement, " +
            ALARME_HORA + " time); ";


    private static final String DATABASE_NAME="pi.db";
    private static final int DATABASE_VERSION=5;


    public BaseDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Criação da tabela
        db.execSQL(CREATE_USUARIO);
        db.execSQL(CREATE_CONTROLE);
        db.execSQL(CREATE_ALARME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Caso seja necessário mudar a estrutura da tabela
        //deverá primeiro excluir a tabela e depois recriá-la
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CONTROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_ALARME);
        onCreate(db);
    }

}
