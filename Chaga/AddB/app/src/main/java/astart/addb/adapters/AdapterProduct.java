package astart.addb.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import astart.addb.R;
import astart.addb.models.Producto;
import io.realm.RealmResults;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder>{

    private RealmResults<Producto> productos;
    private int layout;
    private Activity activitie;
    private OnItemClickListener listener;

    public AdapterProduct(RealmResults<Producto> productos, int layout, Activity activitie, OnItemClickListener listener) {
        this.productos = productos;
        this.layout = layout;
        this.activitie = activitie;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activitie).inflate(layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView cant;
        public TextView precio;
        public ImageView icon;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            cant = (TextView) view.findViewById(R.id.cant);
            precio = (TextView) view.findViewById(R.id.price);

            icon = (ImageView) view.findViewById(R.id.icon);

        }

        public void bind (final Producto producto, final  OnItemClickListener listener){
            this.name.setText(producto.getCombre());
            this.cant.setText(producto.getCantidad()+" "+producto.getUnidad());
            this.precio.setText(producto.getPrecioventa()+" $");

            Picasso.get().load(R.mipmap.ic_icon_product).fit().into(icon);

            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(producto, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{

        void OnItemClick(Producto producto, int position);

    }

}

