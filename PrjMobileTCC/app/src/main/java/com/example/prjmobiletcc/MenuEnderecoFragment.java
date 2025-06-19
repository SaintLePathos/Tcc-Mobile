package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.GatheringByteChannel;
import java.security.Guard;
import java.sql.SQLException;

public class MenuEnderecoFragment extends BottomSheetDialogFragment {
    View ver;
    Button btndefinepadrao, btnapagaendereco;
    TextView txtdetalhesendereco;
    Valores vlrs = new Valores();
    Guardalogin grdlogin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaBundle) {
        ver = inflater.inflate(R.layout.fragment_menu_endereco, container, false);
        txtdetalhesendereco = ver.findViewById(R.id.txtvEnderecoinfomenu);
        btnapagaendereco = ver.findViewById(R.id.btnApagarendereco);
        btndefinepadrao = ver.findViewById(R.id.btnEnderecopadrao);
        btnapagaendereco.setOnClickListener(v->apagarendereco());
        btndefinepadrao.setOnClickListener(v->definircomopadrao());
        grdlogin = new Guardalogin(requireContext());
        carrega();

        return ver;
    }
    private void carrega(){
        String txt = vlrs.detalhesendereco;
        txtdetalhesendereco.setText("Endereço: \n\n"+txt);
    }
    private void apagarendereco(){
        try{
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());
            String idenderec = vlrs.idendereco;
            int result = bdcnx.stmt.executeUpdate("UPDATE " +
                    "Endereco_Cliente " +
                    "SET Visivel = 0 WHERE Id_Endereco_Cliente = " + idenderec);
            if (result > 0){
                mudatela(new UsuarioenderecoFragment());
                Toast.makeText(requireContext(),"Endereco apagado com sucesso",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(requireContext(), "Erro ao apagar endereço", Toast.LENGTH_SHORT).show();
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }

    }
    private void definircomopadrao(){
        String txt = vlrs.idendereco;
        grdlogin.definirenderecopadrao(txt);
        String teste = grdlogin.getEnderecopadrao();
        System.out.println(txt);
        System.out.println(teste);
        mudatela(new UsuarioenderecoFragment());
        Toast.makeText(requireContext(),"Endereço definico como padrão",Toast.LENGTH_SHORT);

    }
    private void mudatela(Fragment fragment){
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, fragment);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
}