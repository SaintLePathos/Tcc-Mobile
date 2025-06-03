package com.example.prjmobiletcc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UsuariodadosFragment extends Fragment {

    TextView txtvnome, txtvcpf, txtvusuario, txtvemail, txtvtelefone;
    Button btnalterardados;
    View ver;
    Cnxbd bdcnx = new Cnxbd();
    Guardalogin grdlogin;
    public UsuariodadosFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_usuariodados, container, false);
        txtvnome = ver.findViewById(R.id.txtvClientenome);
        txtvcpf = ver.findViewById(R.id.txtvClientecpf);
        txtvusuario = ver.findViewById(R.id.txtvClienteusuario);
        txtvemail = ver.findViewById(R.id.txtvClientemail);
        txtvtelefone = ver.findViewById(R.id.txtvClientetelefone);
        btnalterardados = ver.findViewById(R.id.btnAlterarDados);
        grdlogin = new Guardalogin(requireContext());
        btnalterardados.setOnClickListener(v->{
            abrepaginaweb(bdcnx.urlsite);
        });
        carregadados();
        return ver;
    }
    private void abrepaginaweb(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    private void carregadados(){
        try {
            bdcnx.entBanco(requireContext());
            String idcli = grdlogin.getId();
            String sql = "SELECT * FROM Cliente WHERE Id_Cliente = " + idcli;
            System.out.println(sql);
            bdcnx.RS = bdcnx.stmt.executeQuery(sql);

            if (bdcnx.RS.next()){
                System.out.println(idcli);
                String cpf = bdcnx.RS.getString("CPF_Cliente");
                String nome = bdcnx.RS.getString("Nome_Cliente");
                String email = bdcnx.RS.getString("Email_Cliente");
                String tel = bdcnx.RS.getString("Telefone_Cliente");
                txtvcpf.setText(cpf);
                txtvnome.setText(nome);
                txtvemail.setText(email);
                txtvtelefone.setText(tel);
            }else {
                System.out.println("nao achou o id");
            }
        }catch (Exception ex){
            System.out.println("erro "+ex);
        }
    }
}