package mx.edu.ittepic.pepeyusapp_administrador;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IConexion {

    int aux = 0;
    ConexionWeb conexionWeb;
    ConexionWebIMGs conexionWebIMGs;
    ProgressDialog dialogo;
    int opc;
    //ArrayLists
    ArrayList<TipoProducto> tipoProductos = new ArrayList<>();
    ArrayList<Bitmap> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
                Fragment fragment = getVisibleFragment();

                if(fragment instanceof Fragmento1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layin, new FragmInsProd()).commit();

                } else if(fragment instanceof Fragmento2){


                }else if(fragment instanceof  Fragmento3){
                    FragmInsTipProd fragmInsTipProd = new FragmInsTipProd();
                    getSupportFragmentManager().beginTransaction().replace(R.id.layin, new FragmInsTipProd()).commit();

                }
                Toast.makeText(MainActivity.this, "CLick en FLoating", Toast.LENGTH_LONG).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layin, new Fragmento1()).commit();

        opc = 2;
        obtenerTipProd();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.Fragment fragmentito = null;
        boolean hayFragmento = false;

        if (id == R.id.opc_producto) {
            fragmentito = new Fragmento1();
            hayFragmento = !hayFragmento;

        } else if (id == R.id.opc_promocion) {
            fragmentito = new Fragmento2();
            hayFragmento = !hayFragmento;

        } else if (id == R.id.opc_tipo_prod){
            Fragmento3 fr3 = new Fragmento3();
            fr3.tipoProductos = tipoProductos;
            fragmentito = fr3;
            for(int i = 0; i < tipoProductos.size(); i++){
                tipoProductos.get(i).imagen = imgs.get(i);
            }
            hayFragmento = !hayFragmento;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(hayFragmento){
            getSupportFragmentManager().beginTransaction().replace(R.id.layin, fragmentito).commit();
        }

        return true;
    }

    @Override
    public synchronized void procesarRespuesta(String r) {
        if(r.equals("NO HAY DATOS") || r.contains("ERROR")){
            dialogo.dismiss();
            Toast.makeText(this, r,Toast.LENGTH_LONG).show();
            return;
        }

        String []registros = r.split("<br>");
        switch(opc){
            case 0:
                dialogo.dismiss();
                break;

            case 1:
                break;

            case 2:
                for(String registro : registros){
                    String []datos = registro.split(",");
                    TipoProducto temp = new TipoProducto();
                    temp.id = Integer.parseInt(datos[0]);
                    temp.tipo = datos[1];
                    temp.descripcion = datos[2];
                    tipoProductos.add(temp);
                }
                obtenerIMG(tipoProductos.get(aux).tipo);
                break;
        }

    }

    @Override
    public void procesarRespuesta(Bitmap img) {
        if(img == null){
            dialogo.dismiss();
            return;
        }
        imgs.add(img);
        System.out.println("Imagen. " + aux);
        aux++;
        if(aux == tipoProductos.size()){
            dialogo.dismiss();
            return;
        }else{
            obtenerIMG(tipoProductos.get(aux).tipo);
        }
    }

    @Override
    public void cambiarMensaje(String mensaje) {
        dialogo.setMessage(mensaje);
    }


    public void obtenerTipProd(){
        try{
            conexionWeb = new ConexionWeb(MainActivity.this);
            URL direccion = new URL("https://thecaveoflittlereik.000webhostapp.com/Pepe_Yus_App/Phps/obtenerTipProds.php");
            dialogo = ProgressDialog.show(MainActivity.this, "Atencion", "Conectando al servidor");
            conexionWeb.execute(direccion);
        }catch(MalformedURLException err){
            Toast.makeText(MainActivity.this, "ERROR\n"+err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void obtenerIMG(String name){
        name = name.replace(" ","_").toLowerCase()+".jpg";
        try{
            conexionWebIMGs = new ConexionWebIMGs(MainActivity.this);
            URL direccion = new URL("https://thecaveoflittlereik.000webhostapp.com/Pepe_Yus_App/Contenido/"+name);
            conexionWebIMGs.execute(direccion);
        }catch(MalformedURLException err){
            Toast.makeText(MainActivity.this, "ERROR\n"+err.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible()) {
                    return fragment;
                }
            }
        }
        return null;
    }

}