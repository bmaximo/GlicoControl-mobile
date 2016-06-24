package com.edu.fatec.glicocontrol.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.edu.fatec.glicocontrol.POJO.AlarmeVO;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barbara Maximo on 23/06/2016.
 */
public class AlarmeDAO {
    private SQLiteDatabase database;
    private BaseDAO dbHelper;

    //Campos da tabela alarme
    private String[] colunas = {BaseDAO.ALARME_ID,
            BaseDAO.ALARME_HORA};



    public AlarmeDAO(Context context) {
        dbHelper = new BaseDAO(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long Inserir(AlarmeVO pValue) {
        ContentValues values = new ContentValues();

        //Carregar os valores nos campos do alarme que será incluído
        values.put(BaseDAO.ALARME_HORA, String.valueOf(pValue.getHora()));
        return database.insert(BaseDAO.TBL_ALARME, null, values);
    }


    public int Alterar(AlarmeVO pValue) {
        long id = pValue.getId();
        ContentValues values = new ContentValues();

        //Carregar os novos valores nos campos que serão alterados
        values.put(BaseDAO.ALARME_HORA, String.valueOf(pValue.getHora()));

        //Alterar o registro com base no ID
        return database.update(BaseDAO.TBL_ALARME, values, BaseDAO.ALARME_ID + " = " + id, null);
    }

    public void Excluir(AlarmeVO pValue) {
        long id = pValue.getId();

        //Exclui o registro com base no ID
        database.delete(BaseDAO.TBL_ALARME, BaseDAO.ALARME_ID + " = " + id, null);
    }

    public List<AlarmeVO> Consultar() {
        List<AlarmeVO> lstAlarme = new ArrayList<AlarmeVO>();

        //Consulta para trazer todos os dados da tabela Alarme
        Cursor cursor = database.query(BaseDAO.TBL_ALARME, colunas, null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AlarmeVO lAlarmeVO = cursorToAlarme(cursor);
            lstAlarme.add(lAlarmeVO);
            cursor.moveToNext();
        }

        //Tenha certeza que você fechou o cursor
        cursor.close();
        return lstAlarme;
    }

    //Converter o Cursor de dados no objeto POJO ContatoVO
    private AlarmeVO cursorToAlarme(Cursor cursor) {
        AlarmeVO lAlarmeVO = new AlarmeVO();
        lAlarmeVO.setId(cursor.getLong(0));
        lAlarmeVO.setHora(new Date(cursor.getLong(1)));
        return lAlarmeVO;
    }
}



