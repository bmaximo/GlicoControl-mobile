package com.edu.fatec.glicocontrol.DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.fatec.glicocontrol.POJO.ControleVO;
import com.edu.fatec.glicocontrol.R.id;
import com.edu.fatec.glicocontrol.R.layout;

import java.util.List;

/**
 * Created by Barbara Maximo on 08/06/2016.
 */
public class ControleAdapter extends BaseAdapter{
    private Context context;

    private List<ControleVO> lstControle;
    private LayoutInflater inflater;

    public ControleAdapter(Context context, List<ControleVO> listControle) {
        this.context = context;
        this.lstControle = listControle;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Atualizar ListView de acordo com o lstControle
    @Override
    public void notifyDataSetChanged() {
        try{
            super.notifyDataSetChanged();
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public int getCount() {
        return lstControle.size();
    }

    //Remover item da lista
    public void remove(final ControleVO item) {
        this.lstControle.remove(item);
    }

    //Adicionar item na lista
    public void add(final ControleVO item) {
        this.lstControle.add(item);
    }

    public Object getItem(int position) {
        return lstControle.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        try
        {

            ControleVO controle = lstControle.get(position);

            //O ViewHolder irá guardar a instâncias dos objetos do estado_row
            ViewHolder holder;

            //Quando o objeto convertView não for nulo nós não precisaremos inflar
            //os objetos do XML, ele será nulo quando for a primeira vez que for carregado
            if (convertView == null) {
                convertView = inflater.inflate(layout.controle_row, null);

                //Cria o Viewholder e guarda a instância dos objetos
                holder = new ViewHolder();
                holder.tvMedicao = (TextView) convertView.findViewById(id.txtMedicao);
                holder.tvInsulina = (TextView) convertView.findViewById(id.txtInsulina);
                holder.tvPeriodo = (TextView) convertView.findViewById(id.txtPeriodo);

                convertView.setTag(holder);
            } else {
                //pega o ViewHolder para ter um acesso rápido aos objetos do XML
                //ele sempre passará por aqui quando,por exemplo, for efetuado uma rolagem na tela
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvMedicao.setText(""+ controle.getMedicao());
            holder.tvInsulina.setText(""+ controle.getInsulina());
            holder.tvPeriodo.setText(controle.getPeriodo());

            return convertView;

        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
        return convertView;
    }


    public void toast (String msg)
    {
        Toast.makeText (context, msg, Toast.LENGTH_SHORT).show ();
    }

    private void trace (String msg)
    {
        toast (msg);
    }

    //Criada esta classe estática para guardar a referência dos objetos abaixo
    static class ViewHolder {
        public TextView tvMedicao;
        public TextView tvInsulina;
        public TextView tvPeriodo;
    }

}
