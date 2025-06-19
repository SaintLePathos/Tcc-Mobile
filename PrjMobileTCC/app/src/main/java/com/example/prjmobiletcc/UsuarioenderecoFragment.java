package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;

public class UsuarioenderecoFragment extends Fragment {

    LinearLayout lytcntnrenderecos,btnmostrasenderecos, btnmenosdetalhesend, btnmaisdetalhesend, cntnrdetalhes, lytsemenderecopadrao, lytenderecopadrao;
    TextView txtvnomerua, txtvdetalhesend, txtvtodosend;
    ImageView icenddown,icendup;
    Button btnnovoend;
    View ver;
    int numend;
    Cnxbd bdcnx = new Cnxbd();
    Guardalogin grdlogin;
    public UsuarioenderecoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_usuarioendereco, container, false);
        lytcntnrenderecos = ver.findViewById(R.id.lytCntnrenderecos);
        btnmostrasenderecos = ver.findViewById(R.id.lytMaisenderec);
        icenddown = ver.findViewById(R.id.icEndDOWN);
        icendup = ver.findViewById(R.id.icEndUP);
        btnmaisdetalhesend = ver.findViewById(R.id.lytMaisdetalhesenderec);
        btnmenosdetalhesend = ver.findViewById(R.id.lytMenosdetalhesenderec);
        txtvnomerua = ver.findViewById(R.id.txtvNomerua);
        txtvdetalhesend = ver.findViewById(R.id.txtvDetalhesendereco);
        cntnrdetalhes = ver.findViewById(R.id.lytDetalhesenderec);
        txtvtodosend = ver.findViewById(R.id.txtvTodosenderecos);
        btnnovoend = ver.findViewById(R.id.btnUsnovoendereco);
        lytenderecopadrao = ver.findViewById(R.id.lytEnderecopadrao);
        lytsemenderecopadrao = ver.findViewById(R.id.lytSemendereopadrao);

        btnmaisdetalhesend.setOnClickListener(v->ocdetalhes(cntnrdetalhes,btnmaisdetalhesend));
        btnmenosdetalhesend.setOnClickListener(v->ocdetalhes(cntnrdetalhes,btnmaisdetalhesend));

        btnmostrasenderecos.setOnClickListener(v->abrirefechar(lytcntnrenderecos,icendup,icenddown));
        btnnovoend.setOnClickListener(v->mudatela(new EnderecocadastroFragment()));

        grdlogin = new Guardalogin(requireContext());
        carregaenderecopadrao();
        carregamentoend();

        txtvtodosend.setText(numend + " Endereços cadastrados");

        return ver;
    }

    private void carregaenderecopadrao(){
        String txt1 = grdlogin.getEnderecopadrao();
        String sql = "SELECT " +
                "Id_Endereco_Cliente, " +
                "CEP_Cliente, " +
                "Estado_Cliente, " +
                "Cidade_Cliente, " +
                "Bairro_Cliente, " +
                "Rua_Cliente, " +
                "Numero_Cliente, " +
                "Complemento_Cliente " +
                " FROM Endereco_Cliente WHERE Id_Endereco_Cliente = " + txt1;
        lytenderecopadrao.removeAllViews();
        try{
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery(sql);
            bdcnx.RS.last();
            numend = bdcnx.RS.getRow();
            bdcnx.RS.beforeFirst();
            if(bdcnx.RS.next()){
                String idenderec = bdcnx.RS.getString("Id_Endereco_Cliente");
                String cidade = bdcnx.RS.getString("Cidade_Cliente");
                String estado = bdcnx.RS.getString("Estado_Cliente");
                String numero = bdcnx.RS.getString("Numero_Cliente");
                String rua = bdcnx.RS.getString("Rua_Cliente");
                String cep = bdcnx.RS.getString("CEP_Cliente");
                String complemento = bdcnx.RS.getString("Complemento_Cliente");
                String bairro = bdcnx.RS.getString("Bairro_Cliente");
                Criacntnrenderecos criacntnrenderecos = new Criacntnrenderecos(requireContext());

                criacntnrenderecos.inserirdados(estado, cidade, bairro, rua, numero, complemento, cep);
                String txt = criacntnrenderecos.txtvdetalhesendec.getText().toString();

                criacntnrenderecos.icmenu.setOnClickListener(view -> abredialog(idenderec, txt));
                lytenderecopadrao.addView(criacntnrenderecos);
                lytsemenderecopadrao.setVisibility(View.GONE);
                lytenderecopadrao.setVisibility(View.VISIBLE);
            }else{
                lytenderecopadrao.setVisibility(View.GONE);
                lytsemenderecopadrao.setVisibility(View.VISIBLE);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }

    }
    private void mudatela(Fragment fragment){
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, fragment);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
    private void abredialog(String idend,String txt1){
        Valores vlrs = new Valores();
        vlrs.idendereco = idend;
        vlrs.detalhesendereco = txt1;
        System.out.println("Idendereco"+idend);
        MenuEnderecoFragment bottomSheetDialog = new MenuEnderecoFragment();
        bottomSheetDialog.show(getChildFragmentManager(), "BottomSheetDialog");
    }
    private void carregamentoend(){
        String id = grdlogin.getId();
        String sql = "SELECT " +
                "Id_Endereco_Cliente, " +
                "CEP_Cliente, " +
                "Estado_Cliente, " +
                "Cidade_Cliente, " +
                "Bairro_Cliente, " +
                "Rua_Cliente, " +
                "Numero_Cliente, " +
                "Complemento_Cliente " +
                " FROM Endereco_Cliente WHERE Id_Cliente = " + id +
                " AND Visivel = 1";
        lytcntnrenderecos.removeAllViews();
        try{
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery(sql);
            bdcnx.RS.last();
            numend = bdcnx.RS.getRow();
            bdcnx.RS.beforeFirst();
            while(bdcnx.RS.next()){
                String idenderec = bdcnx.RS.getString("Id_Endereco_Cliente");
                String cidade = bdcnx.RS.getString("Cidade_Cliente");
                String estado = bdcnx.RS.getString("Estado_Cliente");
                String numero = bdcnx.RS.getString("Numero_Cliente");
                String rua = bdcnx.RS.getString("Rua_Cliente");
                String cep = bdcnx.RS.getString("CEP_Cliente");
                String complemento = bdcnx.RS.getString("Complemento_Cliente");
                String bairro = bdcnx.RS.getString("Bairro_Cliente");
                Criacntnrenderecos criacntnrenderecos = new Criacntnrenderecos(requireContext());

                criacntnrenderecos.inserirdados(estado, cidade, bairro, rua, numero, complemento, cep);
                String txt = criacntnrenderecos.txtvdetalhesendec.getText().toString();

                criacntnrenderecos.icmenu.setOnClickListener(view -> abredialog(idenderec, txt));
                lytcntnrenderecos.addView(criacntnrenderecos);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    private void ocdetalhes(LinearLayout lyt1, LinearLayout lyt2){
        lyt1.setVisibility(lyt1.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        lyt2.setVisibility(lyt2.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    private void abrirefechar(LinearLayout lyt, ImageView icUP, ImageView icDOWN){
        lyt.setVisibility(lyt.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icUP.setVisibility(icUP.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icDOWN.setVisibility(icDOWN.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }


}