package com.example.prjmobiletcc;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView botNavView = findViewById(R.id.bottomNavigationView);

        botNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.navigation_casa:
                        selectedFragment = new CasaFragment();
                        break;
                    case R.id.navigation_produtos:
                        selectedFragment = new ProdutosFragment();
                        break;
                    case R.id.navigation_carrinho:
                        selectedFragment = new CarrinhoFragment();
                        break;
                    case R.id.navigation_conta:
                        selectedFragment = new ContaFragment();
                        break;
                }
                // Trocar o fragmento dentro do container
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contFrmnts, selectedFragment)
                            .commit();
                }
                return true;
            }
        });
        // Definir um fragmento inicial (Home)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contFrmnts, new CasaFragment())
                    .commit();
        }
    }
}