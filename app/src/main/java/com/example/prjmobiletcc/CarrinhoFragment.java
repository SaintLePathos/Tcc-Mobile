package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class CarrinhoFragment extends Fragment {

    public CarrinhoFragment() {
        // Required empty public constructor
    }
    View ver;
    LinearLayout lytcntnrcarrinnho;
    Button btnCheckendereco;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_carrinho, container, false);

        btnCheckendereco = ver.findViewById(R.id.btnCkendereco);
        lytcntnrcarrinnho = ver.findViewById(R.id.lytCntnrcarrinho);
        Criacntnrcarrinho criacntnrcarrinho = new Criacntnrcarrinho(requireContext());
        lytcntnrcarrinnho.removeAllViews();
        lytcntnrcarrinnho.addView(criacntnrcarrinho);
        btnCheckendereco.setOnClickListener(v ->trocatelacheckout());
        return ver;
    }
    private void trocatelacheckout(){
        CheckenderecoFragment ckEndereco = new CheckenderecoFragment();
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, ckEndereco);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
}