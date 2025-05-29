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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class CarrinhoFragment extends Fragment {

    public CarrinhoFragment() {
        // Required empty public constructor
    }
    View ver;
    LinearLayout lytcntnrcarrinnho;
    Button btnCheckendereco;
    ImageButton btnapagarcarrinho;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_carrinho, container, false);

        btnCheckendereco = ver.findViewById(R.id.btnCkendereco);
        lytcntnrcarrinnho = ver.findViewById(R.id.lytCntnrcarrinho);
        btnapagarcarrinho = ver.findViewById(R.id.btnLimparcarrinho);

        carregacarrinho();
        btnCheckendereco.setOnClickListener(v ->trocatelacheckout());
        btnapagarcarrinho.setOnClickListener(view ->apagarcar());
        return ver;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void trocatelacheckout(){
        CheckenderecoFragment ckEndereco = new CheckenderecoFragment();
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, ckEndereco);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
}