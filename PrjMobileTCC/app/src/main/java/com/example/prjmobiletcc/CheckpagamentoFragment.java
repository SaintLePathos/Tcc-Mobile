package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CheckpagamentoFragment extends Fragment {

    public CheckpagamentoFragment() {
        // Required empty public constructor
    }
    View ver;
    LinearLayout lytcntnrresumo;
    RadioGroup rdpgrp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ver = inflater.inflate(R.layout.fragment_checkpagamento, container, false);
        Button btnckrevisao = ver.findViewById(R.id.btnCkrevisao);
        lytcntnrresumo = ver.findViewById(R.id.lytCktresumo2);
        rdpgrp = ver.findViewById(R.id.rdogrpPagamento);
        rdpgrp.setOnCheckedChangeListener((radioGroup, i) -> pegarvalordrdo());



        lytcntnrresumo.removeAllViews();
        Criacntnrresumo cntresumo = new Criacntnrresumo(requireContext());
        lytcntnrresumo.addView(cntresumo);

        btnckrevisao.setOnClickListener(v -> mudatela());

        return ver;
    }

    String textopag;
    private void pegarvalordrdo(){
        int selectedId = rdpgrp.getCheckedRadioButtonId(); // Obtém o ID do botão selecionado
        if (selectedId != -1) { // Verifica se algum botão está marcado
            RadioButton selectedRadioButton = ver.findViewById(selectedId);
            String selectedText = selectedRadioButton.getText().toString(); // Obtém o texto do botão
            Valores vlrs = new Valores();

            switch (selectedText){
                case "CARTÃO DE CRÉDITO":
                    textopag = "cartao";
                    vlrs.pagamento = textopag;
                    break;
                case "BOLETO BÁNCARIO":
                    textopag = "boleto";
                    vlrs.pagamento = textopag;
                    break;
                case "PAGUE VIA PIX":
                    textopag = "pix";
                    vlrs.pagamento = textopag;
                    break;
            }
            Toast.makeText(requireContext(),vlrs.pagamento,Toast.LENGTH_SHORT).show();
        }
    }
    public boolean radioSelecionado() {
        return rdpgrp.getCheckedRadioButtonId() != -1;
    }


    private void mudatela(){
        if (radioSelecionado()) {
            System.out.println("Um método de pagamento foi selecionado.");
            CheckrevisaoFragment fragmRevisao = new CheckrevisaoFragment();
            FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
            mudaFragm.replace(R.id.contFrmnts, fragmRevisao);
            mudaFragm.addToBackStack(null);
            mudaFragm.commit();
        } else {
            System.out.println("Nenhum método de pagamento foi escolhido.");
            Toast.makeText(requireContext(), "Selecione uma opção de pagamento", Toast.LENGTH_SHORT).show();
        }
    }
}