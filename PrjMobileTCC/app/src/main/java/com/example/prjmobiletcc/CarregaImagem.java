package com.example.prjmobiletcc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CarregaImagem extends AsyncTask<String, Void, Bitmap> {
    private final ImageView imgv;

    public CarregaImagem(ImageView imgv) {
        this.imgv = imgv;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlString = urls[0];
        Bitmap bitmap = null;
        if (urlString == null || urlString.isEmpty()) {
            //System.out.println("URL inválida!");
            return null;
        }
        try {
            //System.out.println("Carregando imagem da URL: " + urlString); // Debug

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(10000); // Tempo limite de conexão
            connection.setReadTimeout(15000);    // Tempo limite de leitura
            connection.connect();

            int responseCode = connection.getResponseCode();
            //ystem.out.println("Código de resposta HTTP: " + responseCode); // Debug

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);

                if (bitmap != null) {
                    //System.out.println("Imagem carregada com sucesso: " + bitmap.getWidth() + "x" + bitmap.getHeight());
                } else {
                    System.out.println("Erro ao converter stream para Bitmap.");
                }
            } else {
                System.out.println("Erro na conexão HTTP, código: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a imagem: " + e.getMessage());
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            imgv.setImageBitmap(bitmap);
        } else {
            //Toast.makeText(imgv.getContext(), "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
        }
    }
}
