package com.edu.fatec.glicocontrol.DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.fatec.glicocontrol.POJO.AlarmeVO;
import com.edu.fatec.glicocontrol.R;

import java.util.List;

/**
 * Created by Barbara Maximo on 23/06/2016.
 */
public class AlarmeAdapter extends BaseAdapter {
    private Context context;

    private List<AlarmeVO> lstAlarme;
    private LayoutInflater inflater;

    public AlarmeAdapter(Context context, List<AlarmeVO> listAlarme) {
        this.context = context;
        this.lstAlarme = listAlarme;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Atualizar ListView de acordo com o lstAlarme
    @Override
    public void notifyDataSetChanged() {
        try{
            super.notifyDataSetChanged();
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public int getCount() {
        return lstAlarme.size();
    }

    //Remover item da lista
    public void remove(final AlarmeVO item) {
        this.lstAlarme.remove(item);
    }

    //Adicionar item na lista
    public void add(final AlarmeVO item) {
        this.lstAlarme.add(item);
    }

    public Object getItem(int position) {
        return lstAlarme.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        try
        {

            AlarmeVO alarme = lstAlarme.get(position);

            //O ViewHolder irá guardar a instâncias dos objetos do alarme_row
            ViewHolder holder;

            //Quando o objeto convertView não for nulo nós não precisaremos inflar
            //os objetos do XML, ele será nulo quando for a primeira vez que for carregado
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.alarme_row, null);

                //Cria o Viewholder e guarda a instância dos objetos
                holder = new ViewHolder();
                holder.tvHora = (TextView) convertView.findViewById(R.id.txtHora);

                convertView.setTag(holder);
            } else {
                //pega o ViewHolder para ter um acesso rápido aos objetos do XML
                //ele sempre passará por aqui quando,por exemplo, for efetuado uma rolagem na tela
                holder = (ViewHolder) convertView.getTag();
            }

            //talvez precise fazer dateFormat
            holder.tvHora.setText(""+ alarme.getHora());


            return convertView;

        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
        return convertView;
    }


    public void toast (String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show ();
    }

    private void trace (String msg)
    {
        toast (msg);
    }

    //Criada esta classe estática para guardar a referência dos objetos abaixo
    static class ViewHolder {
        public TextView tvHora;
    }

}

