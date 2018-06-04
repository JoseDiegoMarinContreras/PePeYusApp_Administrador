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


public class AdapterItem extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<IAdapter> items;

    public AdapterItem (Activity activity, ArrayList<IAdapter> items) {
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

    public void addAll(ArrayList<IAdapter> category) {
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
            v = inf.inflate(R.layout.item_promocion, null);
        }

        IAdapter promo = items.get(position);

        TextView title = v.findViewById(R.id.titulo);
        title.setText(promo.nombre);

        TextView description = v.findViewById(R.id.precio);
        description.setText(promo.precio+"");

        ImageView imagen = v.findViewById(R.id.imagen);
        imagen.setImageBitmap(promo.imagen);

        return v;
    }
}
