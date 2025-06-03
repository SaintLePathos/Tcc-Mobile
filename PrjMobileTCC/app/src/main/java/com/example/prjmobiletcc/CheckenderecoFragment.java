package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CheckenderecoFragment extends Fragment {

    public CheckenderecoFragment() {
        // Required empty public constructor
    }
    View ver;
    Button btnCheckpagamento;
    LinearLayout lytcntnrend, lytcntnrenvio, lytcntnrresumo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_checkendereco, container, false);
        btnCheckpagamento = ver.findViewById(R.id.btnCkpagamento);
        lytcntnrend = ver.findViewById(R.id.lytCktendereco);
        lytcntnrenvio = ver.findViewById(R.id.lytCktformaenvio);
        lytcntnrresumo = ver.findViewById(R.id.lytCktresumo3);

        btnCheckpagamento.setOnClickListener(v ->mudatela(new CheckpagamentoFragment()));
        carregamento();

        return ver;
    }
    private void carregamento(){
        Criacntnrcheckend cntenderc = new Criacntnrcheckend(requireContext());
        Criacntnrcheckenvio cntenvio = new Criacntnrcheckenvio(requireContext());
        Criacntnrresumo cntresumo = new Criacntnrresumo(requireContext());
        lytcntnrend.removeAllViews();
        cntenderc.txt2.setOnClickListener(view -> mudatela(new UsuarioenderecoFragment()));
        lytcntnrend.addView(cntenderc);
        lytcntnrenvio.removeAllViews();
        lytcntnrenvio.addView(cntenvio);
        lytcntnrresumo.removeAllViews();
        lytcntnrresumo.addView(cntresumo) ;
    }
    private void mudatela(Fragment fragment){
        Guardalogin guardalogin = new Guardalogin(requireContext());
        if (guardalogin.existeEnderecoPadrao()) {
            System.out.println("Endereço padrão encontrado!");
            FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
            mudaFragm.replace(R.id.contFrmnts, fragment);
            mudaFragm.addToBackStack(null);
            mudaFragm.commit();
        } else {
            System.out.println("Nenhum endereço padrão cadastrado.");
            Toast.makeText(requireContext(), "Escolha um endereço de envio", Toast.LENGTH_SHORT).show();
        }
    }

}