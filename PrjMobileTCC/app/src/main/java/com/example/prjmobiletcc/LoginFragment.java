  package com.example.prjmobiletcc;

import android.content.Intent;
import android.net.Uri;
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
        btnregistrar.setOnClickListener(v->{
            abrepaginaweb(bdcnx.urlsite);
        });
        esqueceusenha.setOnClickListener(v->{
            abrepaginaweb(bdcnx.urlsite);
        });

        if (grdlogin.loginexpiracao()){mudatela();}
        return ver;
    }
      private void abrepaginaweb(String url){
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          startActivity(intent);
      }
    private void entrar(){
        String mail = email.getText().toString();
        String snh = senha.getText().toString();

        if (mail.isEmpty() || snh.isEmpty()) {
            Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        Verificalogin verificalogin = new Verificalogin();

        verificalogin.verificarLogin(mail, snh, new Verificalogin.LoginCallback() {
            @Override
            public void onSuccess(boolean isCorrect, String userId, String message) {
                if (isCorrect) {
                    Toast.makeText(requireContext(), "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                    // Salvar ID do usuário no sistema
                    grdlogin.salvarLogin(userId, mail, snh);
                    System.out.println(userId);

                    // Navegar para próxima tela
                    mudatela();
                } else {
                    Toast.makeText(requireContext(), "Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(requireContext(), "Erro na conexão: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mudatela(){
        ContaFragment cntFramg = new ContaFragment();
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, cntFramg);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
}