package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class EnderecocadastroFragment extends Fragment {
    View ver;
    EditText cep,estado,cidade,bairro,rua,numero,complemento;
    Button btncadastrar;
    Guardalogin grdlogin;
    public EnderecocadastroFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_enderecocadastro, container, false);
        cep = ver.findViewById(R.id.txtbCep);
        estado = ver.findViewById(R.id.txtbEstado);
        cidade = ver.findViewById(R.id.txtbCidade);
        bairro = ver.findViewById(R.id.txtbBairro);
        rua = ver.findViewById(R.id.txtbRua);
        numero = ver.findViewById(R.id.txtbNumero);
        complemento = ver.findViewById(R.id.txtbComplemento);
        btncadastrar = ver.findViewById(R.id.btnCadastraendereco);
        formatacep();
        grdlogin = new Guardalogin(requireContext());

        btncadastrar.setOnClickListener(v->cadastroendereco());

        return ver;
    }
    private void cadastroendereco(){
        String idcliente = grdlogin.getId();
        String cepcomtraco = cep.getText().toString();
        System.out.println(cepcomtraco);
        String endcep = cepcomtraco.replace("-", "");  // Remove o traço
        System.out.println(endcep);
        String endestado = estado.getText().toString();
        String endcidade = cidade.getText().toString();
        String endbairro = bairro.getText().toString();
        String endrua = rua.getText().toString();
        String endnumero = numero.getText().toString();
        String endcomplemento = complemento.getText().toString();

        String sql = "INSERT INTO Endereco_Cliente (" +
                "Id_Cliente, " +
                "CEP_Cliente, " +
                "Estado_Cliente, " +
                "Cidade_Cliente, " +
                "Bairro_Cliente, " +
                "Rua_Cliente, " +
                "Numero_Cliente, " +
                "Complemento_Cliente " +
                ") " +
                "VALUES(" +
                "" + idcliente + "," +
                "'"+endcep+"'," +
                "'"+endestado+"'," +
                "'"+endcidade+"'," +
                "'"+endbairro+"'," +
                "'"+endrua+"'," +
                "'"+endnumero+"'," +
                "'"+endcomplemento+"');";
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());
            int retorno = bdcnx.stmt.executeUpdate(sql);
            if (retorno != 0){
                Toast.makeText(requireContext(),"Endereço adicionado",Toast.LENGTH_SHORT).show();
                mudatela(new UsuarioenderecoFragment());
            }else {
                Toast.makeText(requireContext(),"Erro ao adicionar o endereco",Toast.LENGTH_SHORT).show();
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
    private void formatacep(){
        cep.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textoFiltrado = s.toString().replaceAll("[^0-9]", ""); // Remove caracteres não numéricos

                if (textoFiltrado.length() > 5) {
                    textoFiltrado = textoFiltrado.substring(0, 5) + "-" + textoFiltrado.substring(5); // Insere traço
                }

                if (!textoFiltrado.equals(s.toString())) {
                    cep.setText(textoFiltrado);
                    cep.setSelection(textoFiltrado.length()); // Mantém o cursor no final
                }
            }
        });

    }
}