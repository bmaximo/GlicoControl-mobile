package com.edu.fatec.glicocontrol.UI;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.edu.fatec.glicocontrol.DAO.AlarmeAdapter;
import com.edu.fatec.glicocontrol.DAO.AlarmeDAO;
import com.edu.fatec.glicocontrol.POJO.AlarmeVO;
import com.edu.fatec.glicocontrol.R;

import java.util.List;

public class ListaAlarme extends ListActivity {

    private static final int INCLUIR = 0;
    private static final int ALTERAR = 1;

    private AlarmeDAO lAlarmeDAO; //instância responsável pela persistência dos dados
    List<AlarmeVO> lstAlarme;  //lista de alarmes cadastrados no BD
    AlarmeAdapter adapter;   //Adapter responsável por apresentar os alarmes na tela

    boolean blnShort = false;
    int Posicao = 0;    //determinar a posição do alarme dentro da lista lstAlarme

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alarme);


        lAlarmeDAO = new AlarmeDAO(this);
        lAlarmeDAO.open();

        lstAlarme = lAlarmeDAO.Consultar();

        adapter = new AlarmeAdapter(this, lstAlarme);
        setListAdapter(adapter);

        registerForContextMenu(getListView());


    }

    // Este evento será chamado pelo atributo onClick
    // que está definido no botão criado no arquivo alarme.xml
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                InserirAlarme();
                break;
        }
    }

    //Rotina executada quando finalizar a Activity AlarmeUI
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        AlarmeVO lAlarmeVO = null;

        try
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK)
            {
                //obtem dados inseridos/alterados na Activity AlarmeUI
                lAlarmeVO = (AlarmeVO)data.getExtras().getSerializable("alarme");

                //o valor do requestCode foi definido na função startActivityForResult
                if (requestCode == INCLUIR)
                {
                    //verifica se digitou algo no nome do alarme
                    if (!(lAlarmeVO.getHora() == null))
                    {
                        //necessário abrir novamente o BD pois ele foi fechado no método onPause()
                        lAlarmeDAO.open();

                        //insere o alarme no Banco de Dados SQLite
                        lAlarmeDAO.Inserir(lAlarmeVO);

                        //insere o alarme na lista de alarmes em memória
                        lstAlarme.add(lAlarmeVO);
                    }
                }else if (requestCode == ALTERAR){
                    lAlarmeDAO.open();
                    //atualiza o alarme no Banco de Dados SQLite
                    lAlarmeDAO.Alterar(lAlarmeVO);

                    //atualiza o alarme na lista de controes em memória
                    lstAlarme.set(Posicao, lAlarmeVO);
                }

                //método responsável pela atualiza da lista de dados na tela
                adapter.notifyDataSetChanged();
            }
        }
        catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    private void InserirAlarme(){
        try
        {
            //a variável "tipo" tem a função de definir o comportamento da Activity
            //AlarmeUI, agora a variável tipo está definida com o valor "0" para
            //informar que será uma inclusão de Alarme

            Intent it = new Intent(this, Alarme.class);
            it.putExtra("tipo", INCLUIR);
            startActivityForResult(it, INCLUIR);//chama a tela e incusão
        }
        catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        //quando a Activity main receber o foco novamente abre-se novamente a conexão
        lAlarmeDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //toda vez que o programa peder o foco fecha-se a conexão com o BD
        lAlarmeDAO.close();
        super.onPause();
    }

    public void toast (String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
    }

    private void trace (String msg)
    {
        toast (msg);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        try
        {
            //Criação do popup menu com as opções que termos sobre
            //nossos Alarmes

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            if (!blnShort)
            {
                Posicao = info.position;
            }
            blnShort = false;

            menu.setHeaderTitle("Selecione:");
            //a origem dos dados do menu está definido no arquivo arrays.xml
            String[] menuItems = getResources().getStringArray(R.array.menuu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }


    //Este método é disparado quando o usuário clicar em um item do ContextMenu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AlarmeVO lAlarmeVO = null;
        try
        {
            int menuItemIndex = item.getItemId();

            //Carregar a instância POJO com a posição selecionada na tela
            lAlarmeVO = (AlarmeVO) getListAdapter().getItem(Posicao);

            if (menuItemIndex == 0){
                //Carregar a Activity AlarmeUI com o registro selecionado na tela

                Intent it = new Intent(this, Alarme.class);
                it.putExtra("tipo", ALTERAR);
                it.putExtra("alarme", lAlarmeVO);
                startActivityForResult(it, ALTERAR); //chama a tela de alteração
            }else if (menuItemIndex == 1){
                //Excluir do Banco de Dados e da tela o registro selecionado

                lAlarmeDAO.Excluir(lAlarmeVO);
                lstAlarme.remove(lAlarmeVO);
                adapter.notifyDataSetChanged(); //atualiza a tela
            }
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
        return true;

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //por padrão o ContextMenu, só é executado através de LongClick, mas
        //nesse caso toda vez que executar um ShortClick, abriremos o menu
        //e também guardaremos qual a posição do itm selecionado

        Posicao = position;
        blnShort = true;
        this.openContextMenu(l);
    }


}