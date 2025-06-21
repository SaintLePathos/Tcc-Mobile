package com.example.prjmobiletcc;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView botNavView;
    LinearLayout lytcntnrpesquisa;
    SearchView buscador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botNavView = findViewById(R.id.bottomNavigationView);
        buscador = findViewById(R.id.schvBuscaproduto);
        lytcntnrpesquisa = findViewById(R.id.lytCntnrpesquisa);

        botNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.navigation_casa:
                        selectedFragment = new CasaFragment();
                        break;
                    case R.id.navigation_produtos:
                        selectedFragment = new ProdutosFragment();
                        break;
                    case R.id.navigation_carrinho:
                        selectedFragment = new CarrinhoFragment();
                        break;
                    case R.id.navigation_conta:
                        selectedFragment = new LoginFragment();
                        break;
                }
                // Trocar o fragmento dentro do container
                if (selectedFragment != null) {
                    //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contFrmnts, selectedFragment).addToBackStack(null)
                            .commit();
                }
                return true;
            }
        });
        // Definir um fragmento inicial (Home)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contFrmnts, new CasaFragment())
                    .commit();
        }
        buscador.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    lytcntnrpesquisa.setVisibility(View.VISIBLE);
                }else{
                    lytcntnrpesquisa.setVisibility(View.GONE);
                }
            }
        });
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                carregarpesquisa(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //carregarpesquisa(s);
                return false;
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (!buscador.isIconified()) {
            buscador.setIconified(true); // Fecha o campo de busca
            buscador.clearFocus();       // Remove o foco
        } else {
            super.onBackPressed();         // Comportamento padrão
        }
    }

    private void carregarpesquisa(String textobuscado){
        lytcntnrpesquisa.removeAllViews();
        Cnxbd bdcnx = new Cnxbd();
        String sql = "SELECT " +
                "Id_Produto, " +
                "Nome_Produto, " +
                "Tamanho_Produto, " +
                "Tecido_Produto, " +
                "Cor_Produto " +
                "FROM Produto " +
                "WHERE 1 = 1 " +
                "ORDER BY " +
                "CASE " +
                "WHEN Nome_Produto LIKE '"+textobuscado+"' " +
                "   OR Cor_Produto LIKE '"+textobuscado+"' " +
                "   OR Tamanho_Produto LIKE '"+textobuscado+"' " +
                "   OR Tecido_Produto LIKE '"+textobuscado+"' " +
                "THEN 1 " +
                "WHEN Nome_Produto LIKE '"+textobuscado+"%' " +
                "   OR Cor_Produto LIKE '"+textobuscado+"%' " +
                "   OR Tamanho_Produto LIKE '"+textobuscado+"%' " +
                "   OR Tecido_Produto LIKE '"+textobuscado+"%' " +
                "THEN 2 " +
                "WHEN Nome_Produto LIKE '%"+textobuscado+"%' " +
                "   OR Cor_Produto LIKE '%"+textobuscado+"%' " +
                "   OR Tamanho_Produto LIKE '%"+textobuscado+"%' " +
                "   OR Tecido_Produto LIKE '%"+textobuscado+"%' " +
                "THEN 3 " +
                "WHEN Nome_Produto LIKE '%"+textobuscado+"' " +
                "   OR Cor_Produto LIKE '%"+textobuscado+"' " +
                "   OR Tamanho_Produto LIKE '%"+textobuscado+"' " +
                "   OR Tecido_Produto LIKE '%"+textobuscado+"' " +
                "THEN 4 " +
                "ELSE 5 " +
                "END;";
        try{
            bdcnx.entBanco(this);
            bdcnx.RS = bdcnx.stmt.executeQuery(sql);
            while(bdcnx.RS.next()){
                String id_Produto,
                        nome_Produto,
                        tamanho_Produto,
                        tecido_Produto,
                        cor_Produto;
                Criacntnrpesquisa cntnrProduto = new Criacntnrpesquisa(this);
                id_Produto = bdcnx.RS.getString("Id_Produto");
                nome_Produto = bdcnx.RS.getString("Nome_Produto");
                tamanho_Produto = bdcnx.RS.getString("Tamanho_Produto");
                tecido_Produto = bdcnx.RS.getString("Tecido_Produto");
                cor_Produto = bdcnx.RS.getString("Cor_Produto");
                String nome = nome_Produto+", "+tamanho_Produto+", "+cor_Produto+", "+tecido_Produto;
                try{
                    Cnxbd novacnx = new Cnxbd();
                    novacnx.entBanco(this);
                    String imgsql = "SELECT Url_ImgProduto FROM Imagem_Produto WHERE Id_Produto = "+id_Produto+" AND Ordem_ImgProduto = 0";
                    novacnx.RS = novacnx.stmt.executeQuery(imgsql);
                    if (novacnx.RS.next()){
                        String urlimg = novacnx.RS.getString("Url_ImgProduto");
                        cntnrProduto.inserirdados(nome,urlimg);
                    }
                }catch (SQLException ex){
                    System.out.println(ex);
                }
                cntnrProduto.setOnClickListener(v->trocateladetalhes(id_Produto));
                lytcntnrpesquisa.addView(cntnrProduto);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    private void trocateladetalhes(String idproduto){
        Valores vlrs = new Valores();
        vlrs.idproduto = idproduto;
        DetalhesFragment detalhesFragm = new DetalhesFragment();
        FragmentTransaction mudaFramg = this.getSupportFragmentManager().beginTransaction();
        mudaFramg.replace(R.id.contFrmnts, detalhesFragm);
        mudaFramg.addToBackStack(null);
        mudaFramg.commit();
        System.out.println(vlrs.idproduto);
        if (!buscador.isIconified()) {
            buscador.setIconified(true); // Fecha o campo de busca
            buscador.clearFocus();       // Remove o foco
        }
        //Toast.makeText(requireContext(), vlrs.idproduto, Toast.LENGTH_SHORT).show();
    }

}