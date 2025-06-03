package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class UsuariopedidoFragment extends Fragment {
    View ver;
    LinearLayout lytcntnrpedido;
    Guardalogin grdlogin;
    public UsuariopedidoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_usuariopedido, container, false);
        lytcntnrpedido = ver.findViewById(R.id.lytCntnrpedido);



        grdlogin = new Guardalogin(requireContext());



        carregamento();

        return ver;
    }
    private void carregamento(){
        String idcliente = grdlogin.getId();
        String sql = "SELECT " +
                "p.Id_Pedido, " +
                "FORMAT(p.Data_Pedido, 'dd-MM-yyyy') AS Data_Pedido, " +
                "FORMAT(p.Data_Envio_Pedido, 'dd-MM-yyyy') AS Data_Envio, " +
                "FORMAT(p.Data_Entrega_Pedido, 'dd-MM-yyyy') AS Data_Entrega, " +
                "COALESCE(SUM(pp.Valor_Produto_Pedido * pp.Quantidade_Produto_Pedido), 0) AS Valor_Total " +
                "FROM Pedido p " +
                "JOIN Endereco_Cliente ec ON p.Id_Endereco_Cliente = ec.Id_Endereco_Cliente " +
                "LEFT JOIN Produto_Pedido pp ON p.Id_Pedido = pp.Id_Pedido " +
                "WHERE ec.Id_Cliente = "+idcliente+" " +
                "GROUP BY p.Id_Pedido, p.Data_Pedido, p.Data_Envio_Pedido, p.Data_Entrega_Pedido;";
        try{
            lytcntnrpedido.removeAllViews();
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery(sql);
            while(bdcnx.RS.next()){
                String idpedido = bdcnx.RS.getString("Id_Pedido");
                String datapedido = bdcnx.RS.getString("Data_Pedido");
                String dataenvio = bdcnx.RS.getString("Data_Envio");
                String dataentrega = bdcnx.RS.getString("Data_Entrega");
                String valortotalpedido = bdcnx.RS.getString("Valor_Total");
                Criacntnrpedidos criacntnrpedidos = new Criacntnrpedidos(requireContext());
                String statuspedido = "";
                if (dataenvio != null){
                    if (dataentrega != null){
                        statuspedido = "Pedido Entrege ao Destinatario";
                    }else{
                        statuspedido = "Pedido enviado";
                        criacntnrpedidos.lytBotoes.addView(criacntnrpedidos.btnRastrearPedido);
                    }
                }else {
                    statuspedido = "Em preparação";
                }
                criacntnrpedidos.inserirdados(idpedido,datapedido,statuspedido,valortotalpedido);

                criacntnrpedidos.btnRastrearPedido.setOnClickListener(v->abredialog(idpedido,dataenvio));
                criacntnrpedidos.btnVerPedido.setOnClickListener(v->mudatela(new PedidodetalhesFragment(),idpedido));
                lytcntnrpedido.addView(criacntnrpedidos);
            }

        }catch (Exception ex){
            System.out.println(ex);
        }
    }
    private void mudatela(Fragment fragment, String idpedid){
        Valores vlrs = new Valores();
        vlrs.idpedido = idpedid;
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, fragment);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
    private void abredialog(String idped, String dtenvio){
        Valores vlrs = new Valores();
        vlrs.idpedido = idped;
        vlrs.dtenviopedido = dtenvio;
        System.out.println("Idendereco"+idped);
        PedidoRastreioFragment bottomSheetDialog = new PedidoRastreioFragment();
        bottomSheetDialog.show(getChildFragmentManager(), "BottomSheetDialog");
    }
}