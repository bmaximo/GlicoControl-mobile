package com.edu.fatec.glicocontrol.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.edu.fatec.glicocontrol.POJO.UsuarioVO;

/**
 * Created by 1050481413035 on 07/05/2016.
 */
public class UsuarioDAO {

    private SQLiteDatabase database;
    private BaseDAO dbHelper;


    private String[] colunas = {BaseDAO.USUARIO_ID,
            BaseDAO.USUARIO_EMAIL,
            BaseDAO.USUARIO_NOME,
            //BaseDAO.USUARIO_SENHA,
            BaseDAO.USUARIO_NASCIMENTO,
            BaseDAO.USUARIO_SEXO};

    public UsuarioDAO(Context context) {
        dbHelper = new BaseDAO(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long Inserir(UsuarioVO pValue) {
        ContentValues values = new ContentValues();

        //Carregar os valores nos campos do Contato que será incluído
        values.put(BaseDAO.USUARIO_NOME, pValue.getNome());
        values.put(BaseDAO.USUARIO_EMAIL, pValue.getEmail());
        //values.put(BaseDAO.USUARIO_SENHA, pValue.getSenha());
        values.put(BaseDAO.USUARIO_NASCIMENTO, String.valueOf(pValue.getDataNasc()));
        values.put(BaseDAO.USUARIO_SEXO, pValue.getSexo());

        return database.insert(BaseDAO.TBL_USUARIO, null, values);
    }

    public long Alterar(UsuarioVO pValue){
        long id = pValue.getId();
        ContentValues values = new ContentValues();

        values.put(BaseDAO.USUARIO_NOME, pValue.getNome());
        values.put(BaseDAO.USUARIO_EMAIL, pValue.getEmail());
        //values.put(BaseDAO.USUARIO_SENHA, pValue.getSenha());
        values.put(BaseDAO.USUARIO_NASCIMENTO, String.valueOf(pValue.getDataNasc()));
        values.put(BaseDAO.USUARIO_SEXO, pValue.getSexo());

        return database.update(BaseDAO.TBL_USUARIO, values, BaseDAO.USUARIO_ID + " = " + id, null);
    }

    public void Excluir(UsuarioVO pValue) {
        long id = pValue.getId();

        //Exclui o registro com base no ID
        database.delete(BaseDAO.TBL_USUARIO, BaseDAO.USUARIO_ID + " = " + id, null);
    }




}
