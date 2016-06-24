package com.edu.fatec.glicocontrol.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.edu.fatec.glicocontrol.POJO.ControleVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barbara Maximo on 08/06/2016.
 */
public class ControleDAO {
    private SQLiteDatabase database;
    private BaseDAO dbHelper;

    //Campos da tabela Controle
    private String[] colunas = {BaseDAO.CONTROLE_ID,
            BaseDAO.CONTROLE_MEDICAO,
            BaseDAO.CONTROLE_INSULINA,
            BaseDAO.CONTROLE_PERIODO};


    public ControleDAO(Context context) {
        dbHelper = new BaseDAO(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long Inserir(ControleVO pValue) {
        ContentValues values = new ContentValues();

        //Carregar os valores nos campos do Controle que será incluído
        values.put(BaseDAO.CONTROLE_MEDICAO, pValue.getMedicao());
        values.put(BaseDAO.CONTROLE_INSULINA, pValue.getInsulina());
        values.put(BaseDAO.CONTROLE_PERIODO, pValue.getPeriodo());


        return database.insert(BaseDAO.TBL_CONTROLE, null, values);
    }


    public int Alterar(ControleVO pValue) {
        long id = pValue.getId();
        ContentValues values = new ContentValues();

        //Carregar os novos valores nos campos que serão alterados
        values.put(BaseDAO.CONTROLE_MEDICAO, pValue.getMedicao());
        values.put(BaseDAO.CONTROLE_INSULINA, pValue.getInsulina());
        values.put(BaseDAO.CONTROLE_PERIODO, pValue.getPeriodo());


        //Alterar o registro com base no ID
        return database.update(BaseDAO.TBL_CONTROLE, values, BaseDAO.CONTROLE_ID + " = " + id, null);
    }

    public void Excluir(ControleVO pValue) {
        long id = pValue.getId();

        //Exclui o registro com base no ID
        database.delete(BaseDAO.TBL_CONTROLE, BaseDAO.CONTROLE_ID + " = " + id, null);
    }

    public List<ControleVO> Consultar() {
        List<ControleVO> lstControle = new ArrayList<ControleVO>();

        //Consulta para trazer todos os dados da tabela Controle
        Cursor cursor = database.query(BaseDAO.TBL_CONTROLE, colunas,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ControleVO lControleVO = cursorToControle(cursor);
            lstControle.add(lControleVO);
            cursor.moveToNext();
        }

        //Tenha certeza que você fechou o cursor
        cursor.close();
        return lstControle;
    }

    //Converter o Cursor de dados no objeto POJO ControleVO
    private ControleVO cursorToControle(Cursor cursor) {
        ControleVO lControleVO = new ControleVO();
        lControleVO.setId(cursor.getLong(0));
        lControleVO.setMedicao(cursor.getFloat(1));
        lControleVO.setInsulina(cursor.getFloat(2));
        lControleVO.setPeriodo(cursor.getString(3));
        return lControleVO;
    }
}


