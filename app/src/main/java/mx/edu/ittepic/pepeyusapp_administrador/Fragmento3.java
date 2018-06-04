package mx.edu.ittepic.pepeyusapp_administrador;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmento3 extends Fragment {

    ArrayList<TipoProducto> tipoProductos;
    View v;

    public Fragmento3() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_fragmento3, container, false);

        ListView lvtp = v.findViewById(R.id.listaTipProductos);

        AdapterTP adaptertp = new AdapterTP(getActivity(), tipoProductos);
        lvtp.setAdapter(adaptertp);
        lvtp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



        return v;
    }

}
