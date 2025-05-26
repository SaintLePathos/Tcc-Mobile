  package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

  public class LoginFragment extends Fragment {
    public LoginFragment() {
        // Required empty public constructor
    }
    View ver;
    EditText email,senha;
    Button btnlogar,btnregistrar;
    TextView esqueceusenha;
    Cnxbd bdcnx = new Cnxbd();
    Guardalogin grdlogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_login, container, false);
        email = ver.findViewById(R.id.txtbEmaillogin);
        senha = ver.findViewById(R.id.txtbSenhalogin);
        btnlogar = ver.findViewById(R.id.btnAcessarconta);
        btnregistrar = ver.findViewById(R.id.btnCadastrese);
        esqueceusenha = ver.findViewById(R.id.txtvEscreceusenha);
        grdlogin = new Guardalogin(requireContext());

        btnlogar.setOnClickListener(v->entrar());

        if (grdlogin.loginexpiracao()){mudatela();}
        return ver;
    }
    private void entrar(){

        String mail = email.getText().toString();
        String snh = senha.getText().toString();
        try{
            bdcnx.entBanco(requireContext());
            String sql = "SELECT * FROM Cliente WHERE Email_Cliente = '" + mail + "' AND Senha_Cliente = '" + snh + "'";
            System.out.println(sql);
            bdcnx.RS = bdcnx.stmt.executeQuery(sql);
            if (bdcnx.RS.next()){
                String id = bdcnx.RS.getString("Id_Cliente");
                Toast.makeText(requireContext(),"Aprovado",Toast.LENGTH_SHORT).show();
                grdlogin.salvarLogin(id, mail, snh);
                mudatela();
            }else{
                System.out.println("loginincorreto");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            Toast.makeText(requireContext(), "Erro no banco de dados: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void mudatela(){
        ContaFragment cntFramg = new ContaFragment();
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, cntFramg);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
}