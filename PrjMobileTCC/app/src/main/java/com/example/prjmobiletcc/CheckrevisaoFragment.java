package com.example.prjmobiletcc;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class CheckrevisaoFragment extends Fragment {

    public CheckrevisaoFragment() {
        // Required empty public constructor
    }
    View ver;
    LinearLayout lytenderec, lytenvio, lytresumo;
    TextView txtvnomeus,txtvcpfus,txtvformapag;
    ImageView icformapag;
    Button btnfazerpedido;
    Guardalogin grdlogin;
    int idpedido;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_checkrevisao, container, false);
        lytenderec = ver.findViewById(R.id.lytCktendereco2);
        lytenvio = ver.findViewById(R.id.lytCktformaenvio2);
        lytresumo = ver.findViewById(R.id.lytCktresumo3);
        txtvnomeus = ver.findViewById(R.id.txtvCktnomeus);
        txtvcpfus = ver.findViewById(R.id.txtvCktcpfus);
        icformapag = ver.findViewById(R.id.imgvTipopagamento);
        txtvformapag = ver.findViewById(R.id.txtvTipopagamento);
        btnfazerpedido = ver.findViewById(R.id.btnCkfinalizar);
        btnfazerpedido.setOnClickListener(view -> cadatropedido());
        grdlogin = new Guardalogin(requireContext());

        preenchedados();
        return ver;
    }

    private void cadatropedido(){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("CarrinhoPrefs", requireContext().MODE_PRIVATE);
        String jsonCarrinho = sharedPreferences.getString("carrinho", "[]");
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());

            JSONArray jsonArray = new JSONArray(jsonCarrinho);
            fazpedido();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject produto = jsonArray.getJSONObject(i);
                String idProduto = produto.getString("id_produto");
                int quantidade = produto.getInt("quantidade");
                String valorproduto;

                try {
                    bdcnx.RS = bdcnx.stmt.executeQuery("SELECT * FROM Produto WHERE Id_Produto = " + idProduto);
                    if (bdcnx.RS.next()){
                        valorproduto = bdcnx.RS.getString("Valor_Produto");
                        String sql = "INSERT INTO Produto_Pedido (Id_Produto, Id_Pedido, Quantidade_Produto_Pedido, Valor_Produto_Pedido) VALUES ("+idProduto+","+idpedido+", "+quantidade+", "+valorproduto+")";
                        int res = bdcnx.stmt.executeUpdate(sql);
                        if(res != 0){
                            System.out.println("insert bem feito");
                            String sqlUpdate = "UPDATE Produto SET Quantidade_Produto = Quantidade_Produto - " + quantidade + " WHERE Id_Produto = " + idProduto;
                            int resul = bdcnx.stmt.executeUpdate(sqlUpdate);
                            if (resul != 0){
                                System.out.println("tbl produto atualizado");
                            }else {
                                System.out.println("erro update");
                            }
                        }else{
                            System.out.println("insertm mal ");
                        }
                    }else {
                        System.out.println("select ruim");
                    }

                }catch (SQLException ex){
                    System.out.println(ex);
                }
                Valores vlrs = new Valores();
                // Exibe os dados no console
                System.out.println("carrega");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        mudatela(new UsuariopedidoFragment());
        Carrinho car = new Carrinho();
        car.limparCarrinho(requireContext());
    }
    private void fazpedido(){
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());
            String idendereco = grdlogin.getEnderecopadrao();
            Calendar calendar = Calendar.getInstance();
            int ano = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH) + 1; // Janeiro é 0, então somamos 1
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            String sql = "INSERT INTO Pedido (Id_Endereco_Cliente, Data_Pedido) OUTPUT INSERTED.Id_Pedido VALUES (" + idendereco + ", '" + ano + "-" + mes + "-" + dia + "')";
            PreparedStatement pstmt = bdcnx.con.prepareStatement(sql);
            bdcnx.RS = pstmt.executeQuery();
            if (bdcnx.RS.next()) {
                idpedido = bdcnx.RS.getInt("Id_Pedido");
            }
        }catch (SQLException ex){
            System.out.println("fazpedido"+ex);
        }
    }
    private void preenchedados(){
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());

            String idusu = new Guardalogin(requireContext()).getId();
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT * FROM Cliente WHERE Id_Cliente = " + idusu);
            if (bdcnx.RS.next()){
                String nomeusu = bdcnx.RS.getString("Nome_Cliente");
                String cpfusu = bdcnx.RS.getString("CPF_Cliente");
                txtvnomeus.setText(nomeusu);
                txtvcpfus.setText(formatarCPF(cpfusu));

                System.out.println(nomeusu);
                //Toast.makeText(requireContext(),nomeusu,Toast.LENGTH_SHORT).show();
            }
        }catch (SQLException ex){
            System.out.println("Erro checkrevisao" +  ex);
        }
        Valores vlrs = new Valores();
        String tipopag = vlrs.pagamento;
        switch (tipopag){
            case "cartao":
                txtvformapag.setText("CARTÃO DE CRÉDITO");
                icformapag.setImageResource(R.drawable.ic_baseline_credit_card_24);
                break;
            case "boleto":
                txtvformapag.setText("BOLETO BÁNCARIO");
                icformapag.setImageResource(R.drawable.boleto_64);
                break;
            case "pix":
                txtvformapag.setText("PAGUE VIA PIX");
                icformapag.setImageResource(R.drawable.ic_baseline_pix_24);
                break;
        }
        carregamento();
    }
    private void carregamento(){
        Criacntnrcheckend cntenderc = new Criacntnrcheckend(requireContext());
        Criacntnrcheckenvio cntenvio = new Criacntnrcheckenvio(requireContext());
        Criacntnrresumo cntresumo = new Criacntnrresumo(requireContext());
        lytenderec.removeAllViews();
        cntenderc.txt2.setOnClickListener(view -> mudatela(new UsuarioenderecoFragment()));
        lytenderec.addView(cntenderc);
        lytenvio.removeAllViews();
        lytenvio.addView(cntenvio);
        lytresumo.removeAllViews();
        lytresumo.addView(cntresumo) ;
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
    private static String formatarCPF(String cpf) {
        return cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6,9) + "-" + cpf.substring(9,11);
    }
}