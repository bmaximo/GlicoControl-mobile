package com.edu.fatec.glicocontrol.UI;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.edu.fatec.glicocontrol.DAO.ControleAdapter;
import com.edu.fatec.glicocontrol.DAO.ControleDAO;
import com.edu.fatec.glicocontrol.POJO.ControleVO;
import com.edu.fatec.glicocontrol.R;

import java.util.List;

public class ListaControle extends ListActivity {

    private static final int INCLUIR = 0;
    private static final int ALTERAR = 1;

    private ControleDAO lControleDAO; //instância responsável pela persistência dos dados
    List<ControleVO> lstControle;  //lista de contatos cadastrados no BD
    ControleAdapter adapter;   //Adapter responsável por apresentar os contatos na tela

    boolean blnShort = false;
    int Posicao = 0;    //determinar a posição do contato dentro da lista lstContatos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_controle);


        lControleDAO = new ControleDAO(this);
        lControleDAO.open();

        lstControle = lControleDAO.Consultar();

        adapter = new ControleAdapter(this, lstControle);
        setListAdapter(adapter);

        registerForContextMenu(getListView());


    }

    // Este evento será chamado pelo atributo onClick
    // que está definido no botão criado no arquivo main.xml
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                InserirContato();
                break;
        }
    }

    //Rotina executada quando finalizar a Activity ContatoUI
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ControleVO lControleVO = null;

        try
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK)
            {
                //obtem dados inseridos/alterados na Activity ContatoUI
                lControleVO = (ControleVO)data.getExtras().getSerializable("controle");

                //o valor do requestCode foi definido na função startActivityForResult
                if (requestCode == INCLUIR)
                {
                    //verifica se digitou algo no nome do contato
                    if (!(lControleVO.getMedicao() == 0))
                    {
                        //necessário abrir novamente o BD pois ele foi fechado no método onPause()
                        lControleDAO.open();

                        //insere o controle no Banco de Dados SQLite
                        lControleDAO.Inserir(lControleVO);

                        //insere o controle na lista de contatos em memória
                        lstControle.add(lControleVO);
                    }
                }else if (requestCode == ALTERAR){
                    lControleDAO.open();
                    //atualiza o controle no Banco de Dados SQLite
                    lControleDAO.Alterar(lControleVO);

                    //atualiza o controle na lista de controes em memória
                    lstControle.set(Posicao, lControleVO);
                }

                //método responsável pela atualiza da lista de dados na tela
                adapter.notifyDataSetChanged();
            }
        }
        catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    private void InserirContato(){
        try
        {
            //a variável "tipo" tem a função de definir o comportamento da Activity
            //ControleUI, agora a variável tipo está definida com o valor "0" para
            //informar que será uma inclusão de Contato

            Intent it = new Intent(this, Controle.class);
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
        lControleDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //toda vez que o programa peder o foco fecha-se a conexão com o BD
        lControleDAO.close();
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        try
        {
            //Criação do popup menu com as opções que termos sobre
            //nossos Contatos

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
        ControleVO lControleVO = null;
        try
        {
            int menuItemIndex = item.getItemId();

            //Carregar a instância POJO com a posição selecionada na tela
            lControleVO = (ControleVO) getListAdapter().getItem(Posicao);

            if (menuItemIndex == 0){
                //Carregar a Activity ContatoUI com o registro selecionado na tela

                Intent it = new Intent(this, Controle.class);
                it.putExtra("tipo", ALTERAR);
                it.putExtra("controle", lControleVO);
                startActivityForResult(it, ALTERAR); //chama a tela de alteração
            }else if (menuItemIndex == 1){
                //Excluir do Banco de Dados e da tela o registro selecionado

                lControleDAO.Excluir(lControleVO);
                lstControle.remove(lControleVO);
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
