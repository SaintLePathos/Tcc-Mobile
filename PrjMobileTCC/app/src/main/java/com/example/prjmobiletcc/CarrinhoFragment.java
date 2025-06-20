package com.example.prjmobiletcc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class CarrinhoFragment extends Fragment {

    public CarrinhoFragment() {
        // Required empty public constructor
    }
    View ver;
    LinearLayout lytcntnrcarrinnho,lytaviso;
    Button btnCheckendereco,btnaviso;
    ImageButton btnapagarcarrinho;
    TextView nitems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_carrinho, container, false);

        btnCheckendereco = ver.findViewById(R.id.btnCkendereco);
        lytcntnrcarrinnho = ver.findViewById(R.id.lytCntnrcarrinho);
        btnapagarcarrinho = ver.findViewById(R.id.btnLimparcarrinho);
        lytaviso = ver.findViewById(R.id.lytAvisocarinho);
        nitems = ver.findViewById(R.id.txtvNumitemscar);
        btnaviso = ver.findViewById(R.id.btnVerprodutos);

        carregacarrinho();
        btnCheckendereco.setOnClickListener(v ->trocatelacheckout());
        btnapagarcarrinho.setOnClickListener(view ->apagarcar());
        btnaviso.setOnClickListener(view -> mudatlproduto());
        Guardalogin grdlogin = new Guardalogin(requireContext());

        if (!grdlogin.loginexpiracao()){
            mudatela();
        }
        return ver;
    }
    private void mudatlproduto(){
        ProdutosFragment cntFramg = new ProdutosFragment();
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, cntFramg);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
    private void mudatela(){
        LoginFragment cntFramg = new LoginFragment();
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, cntFramg);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
    private void apagarcar(){
        Carrinho car = new Carrinho();
        car.limparCarrinho(requireContext());
        carregacarrinho();
    }

    public void carregacarrinho(){
        lytcntnrcarrinnho.removeAllViews();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("CarrinhoPrefs", Context.MODE_PRIVATE);
        String jsonCarrinho = sharedPreferences.getString("carrinho", "[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonCarrinho);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject produto = jsonArray.getJSONObject(i);
                String idProduto = produto.getString("id_produto");
                int quantidade = produto.getInt("quantidade");
                Criacntnrcarrinho criacntnrcarrinho = new Criacntnrcarrinho(requireContext());
                criacntnrcarrinho.dadoscarrinho(idProduto,quantidade,requireContext());
                Valores vlrs = new Valores();
                lytcntnrcarrinnho.addView(criacntnrcarrinho);
                // Exibe os dados no console
                System.out.println("carrega");
            }
            int numeroitems = jsonArray.length();
            if(numeroitems > 0){
                nitems.setText(numeroitems + " Items");
                btnCheckendereco.setVisibility(View.VISIBLE);
                lytaviso.setVisibility(View.GONE);
            }else{
                nitems.setText("Nenhum item no carrinho");
                lytaviso.setVisibility(View.VISIBLE);
                btnCheckendereco.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            System.out.println(e);

        }
    }


    private void trocatelacheckout(){
        Guardalogin grdlogin = new Guardalogin(requireContext());
        if (grdlogin.existeLogin()) {
            Carrinho car = new Carrinho();
            System.out.println("Login ativo!");
            if (car.carrinhoEstaVazio(requireContext())) {
                System.out.println("O carrinho está vazio.");
                Toast.makeText(requireContext(), "Adicione produtos no carrinho para continuar", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("O carrinho contém itens.");
                CheckenderecoFragment ckEndereco = new CheckenderecoFragment();
                FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
                mudaFragm.replace(R.id.contFrmnts, ckEndereco);
                mudaFragm.addToBackStack(null);
                mudaFragm.commit();
            }
        } else {
            System.out.println("Nenhum login encontrado.");
            Toast.makeText(requireContext(),"Faça login para continuar", Toast.LENGTH_SHORT).show();
        }



    }
}