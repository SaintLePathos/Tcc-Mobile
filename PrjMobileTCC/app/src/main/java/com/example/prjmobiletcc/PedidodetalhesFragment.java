package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;

public class PedidodetalhesFragment extends Fragment {

    public PedidodetalhesFragment() {
        // Required empty public constructor
    }
    View ver;
    LinearLayout lytcntnr;
    TextView txtvnumpedido, txtvvalortotal, txtvstatus, txtvdtpedido, txtvdtenvio, txtvdtentrega, txtvenderecoentrega;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver =  inflater.inflate(R.layout.fragment_pedidodetalhes, container, false);
        lytcntnr = ver.findViewById(R.id.lytCntnrprodutospedidos);

        txtvnumpedido = ver.findViewById(R.id.txtvNumeropedido);
        txtvvalortotal = ver.findViewById(R.id.txtvValortotalped);
        txtvstatus = ver.findViewById(R.id.txtvStatusped);
        txtvdtpedido = ver.findViewById(R.id.txtvDataped);
        txtvdtenvio = ver.findViewById(R.id.txtvDtenvioped);
        txtvdtentrega = ver.findViewById(R.id.txtvDtentregaped);
        txtvenderecoentrega = ver.findViewById(R.id.txtvEndentregaped);


        carregadados();
        carregaprod();
        return ver;
    }
    private void carregadados(){
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());
            Valores vlrs = new Valores();
            String idped = vlrs.idpedido;
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT  " +
                    "    p.Id_Pedido, " +
                    "   FORMAT(p.Data_Pedido, 'dd-MM-yyyy') AS Data_Pedido, " +
                    "   FORMAT(p.Data_Envio_Pedido, 'dd-MM-yyyy') AS Data_Envio_Pedido, " +
                    "   FORMAT(p.Data_Entrega_Pedido, 'dd-MM-yyyy') AS Data_Entrega_Pedido, " +
                    "    e.CEP_Cliente, " +
                    "    e.Estado_Cliente, " +
                    "    e.Cidade_Cliente, " +
                    "    e.Bairro_Cliente, " +
                    "    e.Rua_Cliente, " +
                    "    e.Numero_Cliente, " +
                    "    e.Complemento_Cliente, " +
                    "    SUM(pp.Valor_Produto_Pedido * pp.Quantidade_Produto_Pedido) AS Valor_Total_Pedido " +
                    "FROM Pedido p " +
                    "JOIN Endereco_Cliente e ON p.Id_Endereco_Cliente = e.Id_Endereco_Cliente " +
                    "JOIN Produto_Pedido pp ON p.Id_Pedido = pp.Id_Pedido " +
                    "WHERE p.Id_Pedido = "+idped+" " +
                    "GROUP BY p.Id_Pedido, p.Data_Pedido, p.Data_Envio_Pedido, p.Data_Entrega_Pedido, " +
                    "         e.CEP_Cliente, e.Estado_Cliente, e.Cidade_Cliente, e.Bairro_Cliente,  " +
                    "         e.Rua_Cliente, e.Numero_Cliente, e.Complemento_Cliente;");
            if (bdcnx.RS.next()){
                String num = bdcnx.RS.getString("Id_Pedido");
                String valorttl = bdcnx.RS.getString("Valor_Total_Pedido").replace(".",",");
                String dtpedido = bdcnx.RS.getString("Data_Pedido");
                String dtenvio = bdcnx.RS.getString("Data_Envio_Pedido");
                String dtentrega = bdcnx.RS.getString("Data_Entrega_Pedido");
                String endnum = bdcnx.RS.getString("Numero_Cliente");
                String endrua = bdcnx.RS.getString("Rua_Cliente");
                String endbairro = bdcnx.RS.getString("Bairro_Cliente");
                String endereco = endrua + "," + endnum + "," + endbairro;
                txtvnumpedido.setText("Nº do Pedido: "+num);
                txtvdtpedido.setText(dtpedido);
                if (dtenvio != null){
                    txtvstatus.setText("Enviado");
                    txtvdtenvio.setText(dtenvio);
                }else if (dtentrega != null){
                    txtvstatus.setText("Entregue");
                    txtvdtentrega.setText(dtentrega);
                }else{
                    txtvstatus.setText("Em Preparação");
                    txtvdtenvio.setText("XX-XX-XXXX");
                    txtvdtentrega.setText("XX-XX-XXXX");
                }
                txtvenderecoentrega.setText(endereco);
                txtvvalortotal.setText("R$"+valorttl);

            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    private void carregaprod(){
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());
            Valores vlrs = new Valores();
            String idped = vlrs.idpedido;
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT " +
                    "p.Id_Produto, " +
                    "p.Nome_Produto, " +
                    "pp.Quantidade_Produto_Pedido, " +
                    "pp.Valor_Produto_Pedido, " +
                    "(pp.Valor_Produto_Pedido * pp.Quantidade_Produto_Pedido) AS Valor_Total_Produto " +
                    "FROM Produto_Pedido pp " +
                    "JOIN Produto p ON pp.Id_Produto = p.Id_Produto " +
                    "WHERE pp.Id_Pedido = "+idped+";");
            while (bdcnx.RS.next()){
                String idproduto = bdcnx.RS.getString("Id_Produto");
                String nomeproduto = bdcnx.RS.getString("Nome_Produto");
                String quantidade = bdcnx.RS.getString("Quantidade_Produto_Pedido");
                String valorunit = bdcnx.RS.getString("Valor_Produto_Pedido").replace(".",",");
                String valortotal = bdcnx.RS.getString("Valor_Total_Produto").replace(".",",");

                Criacntnrprodutopedido cntnrprodutoped = new Criacntnrprodutopedido(requireContext());

                cntnrprodutoped.inserirdds(nomeproduto, quantidade,valorunit,valortotal,idproduto, requireContext());

                lytcntnr.addView(cntnrprodutoped);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
}