package astart.addb.fragmets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import astart.addb.R;
import astart.addb.adapters.AdapterProduct;
import astart.addb.application.app;
import astart.addb.models.Producto;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class Fragment_list_prooduct extends Fragment implements RealmChangeListener<RealmResults<Producto>> {

    private RealmResults<Producto> productos;
    private RecyclerView recyclerView;
    private AdapterProduct myAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_list_prooduct, container, false);

        realm = Realm.getDefaultInstance();

        //llenado();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());

        myAdapter = new AdapterProduct(productos, R.layout.list_product_format, getActivity(), new AdapterProduct.OnItemClickListener() {
            @Override
            public void OnItemClick(Producto producto, int position) {

            }
        });

        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(myAdapter);

        return view;
    }

    @Override
    public void onChange(RealmResults<Producto> productos) {
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();
    }

    private RealmResults<Producto> getAllProduct () {
        return realm.where(Producto.class).findAll();
    }

   private void llenado(){
        productos = getAllProduct();
    }

}
