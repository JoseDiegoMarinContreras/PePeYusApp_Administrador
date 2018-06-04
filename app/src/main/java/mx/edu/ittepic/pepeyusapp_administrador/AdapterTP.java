package mx.edu.ittepic.pepeyusapp_administrador;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AdapterTP extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<TipoProducto> items;

    public AdapterTP (Activity activity, ArrayList<TipoProducto> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<TipoProducto> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_tp, null);
        }

        TipoProducto tp = items.get(position);

        TextView titulo = v.findViewById(R.id.tptituloitem);
        titulo.setText(tp.tipo);

        ImageView imagen = v.findViewById(R.id.tpimagenitem);
        imagen.setImageBitmap(tp.imagen);

        return v;
    }
}