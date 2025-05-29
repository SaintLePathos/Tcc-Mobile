package com.example.prjmobiletcc;


import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class Verificalogin {
    private static final String BASE_URL = "http://10.0.0.170/a1/android/"; // Substitua pelo seu servidor

    // Interface para comunicação com o servidor
    public interface ApiService {
        @FormUrlEncoded
        @POST("verificaLogin.php")
        Call<ResponseBody> verificarCredenciais(@Field("email") String email, @Field("senha") String senha);
    }

    private ApiService apiService;

    // Construtor para inicializar Retrofit
    public Verificalogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    // Método para verificar login e obter ID do usuário
    public void verificarLogin(String email, String senha, final LoginCallback callback) {
        Call<ResponseBody> call = apiService.verificarCredenciais(email, senha);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        boolean sucesso = jsonResponse.getString("status").equals("success");
                        String mensagem = jsonResponse.getString("message");
                        String idUsuario = jsonResponse.optString("user_id", null);

                        callback.onSuccess(sucesso, idUsuario, mensagem);
                    } catch (Exception e) {
                        callback.onFailure("Erro ao processar resposta do servidor.");
                    }
                } else {
                    callback.onFailure("Erro na resposta do servidor.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Erro na conexão: " + t.getMessage());
            }
        });
    }

    // Interface para retorno assíncrono
    public interface LoginCallback {
        void onSuccess(boolean isCorrect, String userId, String message);
        void onFailure(String error);
    }
}
