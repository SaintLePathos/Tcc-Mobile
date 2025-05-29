package com.example.prjmobiletcc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContaFragment extends Fragment {
    View ver;
    TextView txtvusuario,txtvinfologin,btndadospessoais,btnmeuenderecos,btnmeuspedidos,btntermoscondicoes,btnpoliticaspriv,btnsobre,btnsair;
    Guardalogin grdlogin;
    Cnxbd bdcnx = new Cnxbd();
    public ContaFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_conta, container, false);
        txtvusuario = ver.findViewById(R.id.txtDadosusuario);
        txtvinfologin = ver.findViewById(R.id.txtvDadoslogin);
        btndadospessoais = ver.findViewById(R.id.txtvDadospessoais);
        btnmeuenderecos = ver.findViewById(R.id.txtvMeusenderecos);
        btnmeuspedidos = ver.findViewById(R.id.txtvMeuspedidos);
        btntermoscondicoes = ver.findViewById(R.id.txtvCondicoesdeuso);
        btnpoliticaspriv = ver.findViewById(R.id.txtvPoliticasprivacidade);
        btnsobre = ver.findViewById(R.id.txtvSobre);
        btnsair = ver.findViewById(R.id.txtvSair);
        grdlogin = new Guardalogin(requireContext());
        preencherusuario();
        btndadospessoais.setOnClickListener(v->mudatela(new UsuariodadosFragment()));
        btnmeuenderecos.setOnClickListener(v->mudatela(new UsuarioenderecoFragment()));
        btnmeuspedidos.setOnClickListener(v->mudatela(new UsuariopedidoFragment()));
        btntermoscondicoes.setOnClickListener(v->mudatela(new TermosecondicoesdeusoFragment()));
        btnpoliticaspriv.setOnClickListener(v->mudatela(new PoliticaseprivacidadeFragment()));

        btnsobre.setOnClickListener(v->{
            abrepaginaweb(bdcnx.urlsite);
        });
        btnsair.setOnClickListener(v->sair());

        return ver;
    }
    private void abrepaginaweb(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    private void sair(){
        grdlogin.limparLogin();
        mudatela(new LoginFragment());
    }
    private void preencherusuario(){
        try {
            bdcnx.entBanco(requireContext());
            String idcli = grdlogin.getId();
            String sql = "SELECT * FROM Cliente WHERE Id_Cliente = " + idcli;

            bdcnx.RS = bdcnx.stmt.executeQuery(sql);
            if (bdcnx.RS.next()){
                System.out.println(idcli);
                String user = bdcnx.RS.getString("Usuario_Cliente");
                String nome = bdcnx.RS.getString("Nome_Cliente");
                String email = bdcnx.RS.getString("Email_Cliente");
                if(user != null){
                    txtvusuario.setText("Bem vindo de volta, " + user);
                }else{
                    txtvusuario.setText("Bem vindo de volta ");
                }

                txtvinfologin.setText(nome + "\n" + email);
            }else {
                System.out.println("nao achou o id");
            }
        }catch (Exception ex){
            System.out.println("erro "+ex);
        }
    }
    private void mudatela(Fragment fragment){
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, fragment);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
}