package com.example.prjmobiletcc;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cnxbd {
    String iP = "10.0.0.170";
    String urlimgsrv = "http://"+iP+"/a1/git%20hub/Tcc-Web/assets/img/";
    ResultSet RS;
    java.sql.Statement stmt;
    Connection con;

    public Connection entBanco(Context ctx){


        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //Toast.makeText(ctx.getApplicationContext(), "Drive correto", Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            Toast.makeText(ctx.getApplicationContext(), "Drive não correto", Toast.LENGTH_SHORT).show();
        }

        try{
            String url = "jdbc:jtds:sqlserver://"+iP+":1433;databaseName=Loja_Ecommerce";
            con = DriverManager.getConnection(url, "sa", "1234");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //Toast.makeText(ctx.getApplicationContext(), "conectado", Toast.LENGTH_SHORT).show();
        }
        catch(SQLException ex){
            //Toast.makeText(ctx.getApplicationContext(), "Erro de conexão", Toast.LENGTH_SHORT).show();
            Toast.makeText(ctx.getApplicationContext(), "Erro de conexão: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace(); // Isso mostrará detalhes no Logcat
        }
        return con;

    }
    //Fim do método que vai conectar o banco
}
