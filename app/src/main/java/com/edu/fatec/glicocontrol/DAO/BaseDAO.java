package com.edu.fatec.glicocontrol.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 1050481413035 on 06/05/2016.
 */
public class BaseDAO extends SQLiteOpenHelper{

    public static final String TBL_USUARIO =   "usuario";
    public static final String USUARIO_EMAIL = "email";
    //public static final String USUARIO_SENHA = "senha";
    public static final String USUARIO_NOME = "nome";
    public static final String USUARIO_NASCIMENTO = "nascimento";
    public static final String USUARIO_ID = "id";
    public static final String USUARIO_SEXO = "sexo";

    //Estrutura da tabela Agenda (sql statement)
    private static final String CREATE_USUARIO = "create table " +
            TBL_USUARIO + "( " + USUARIO_ID       + " integer primary key autoincrement, " +
            USUARIO_EMAIL     + " text, " +
            //USUARIO_SENHA + " text not null, " +
            USUARIO_NOME + " text, "+
            USUARIO_NASCIMENTO + " date, "+
            USUARIO_SEXO + " text);";

    private static final String DATABASE_NAME="pi.db";
    private static final int DATABASE_VERSION=3;


    public BaseDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Criação da tabela
        db.execSQL(CREATE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Caso seja necessário mudar a estrutura da tabela
        //deverá primeiro excluir a tabela e depois recriá-la
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USUARIO);
        onCreate(db);
    }

}
