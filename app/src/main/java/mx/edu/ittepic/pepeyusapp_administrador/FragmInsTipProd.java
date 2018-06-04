package mx.edu.ittepic.pepeyusapp_administrador;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmInsTipProd extends Fragment {
    TipoProducto item = null;
    Uri imageUri;
    ImageView img;
    Button insrt, sbImg, elm, act, sal;
    EditText tip, desc;


    public FragmInsTipProd() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fragm_ins_tip_prod, container, false);
        img = v.findViewById(R.id.tpImg);
        insrt = v.findViewById(R.id.tpIns);
        sbImg = v.findViewById(R.id.tpSubImg);
        elm = v.findViewById(R.id.tpElm);
        act = v.findViewById(R.id.tpAct);
        sal = v.findViewById(R.id.tpSal);
        tip = v.findViewById(R.id.tpTipo);
        desc = v.findViewById(R.id.tpDes);

        if(item == null){
            elm.setVisibility(View.INVISIBLE);
            act.setVisibility(View.INVISIBLE);
        }else{
            insrt.setVisibility(View.INVISIBLE);
        }

        sal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.layin, new Fragmento3()).commit();
            }
        });

        sbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 100);
            }
        });

        insrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tip.getText().toString().isEmpty()){
                    Toast.makeText(FragmInsTipProd.this.getContext(), "Campo tipo vacio", Toast.LENGTH_LONG).show();
                    return;
                }
                if(desc.getText().toString().isEmpty()){
                    Toast.makeText(FragmInsTipProd.this.getContext(), "Campo descripcion vacio", Toast.LENGTH_LONG).show();
                    return;
                }
                if(imageUri == null){
                    Toast.makeText(FragmInsTipProd.this.getContext(), "Sin seleccionar imagen", Toast.LENGTH_LONG).show();
                    return;
                }
                insertarTP(imageUri.getPath());
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 100){
            imageUri = data.getData();
            img.setImageURI(imageUri);
        }
    }

    public static void insertarTP(String ImageLink){
        RequestParams params = new RequestParams();
        try {
            params.put("imagen", new File(ImageLink));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.0.103:8080/android/foto/subirfoto.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, byte[] responseBody) {
                System.out.println("statusCode "+statusCode);//statusCode 200
            }

            @Override
            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
