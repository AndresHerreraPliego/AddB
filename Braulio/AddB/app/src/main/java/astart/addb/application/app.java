package astart.addb.application;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import astart.addb.models.Producto;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class app extends Application{

    public static AtomicInteger ProductoId = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getInstance(config);
        ProductoId = getIdByTable(realm, Producto.class);
        realm.close();
    }

    private <T extends RealmObject> AtomicInteger getIdByTable (Realm realm, Class<T> annyClass){
        RealmResults<T> results = realm.where(annyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }

}
