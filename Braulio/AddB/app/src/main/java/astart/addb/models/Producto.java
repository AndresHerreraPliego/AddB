package astart.addb.models;

import java.util.Date;

import astart.addb.application.app;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Producto extends RealmObject{

    @PrimaryKey
    protected int id;
    protected int cb;
    protected String combre;
    protected double cantidad;
    protected String unidad;
    protected double cantidad_min;
    protected double precioventa;
    protected double preciocompra;
    protected Date date;

    public Producto(int cb, String combre, double cantidad, String unidad, double cantidad_min, double precioventa, double preciocompra, Date date) {
        this.id = app.ProductoId.incrementAndGet();
        this.cb = cb;
        this.combre = combre;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.cantidad_min = cantidad_min;
        this.precioventa = precioventa;
        this.preciocompra = preciocompra;
        this.date = date;
    }

    public Producto() {
    }

    public int getCb() {
        return cb;
    }

    public void setCb(int cb) {
        this.cb = cb;
    }

    public String getCombre() {
        return combre;
    }

    public void setCombre(String combre) {
        this.combre = combre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public double getCantidad_min() {
        return cantidad_min;
    }

    public void setCantidad_min(double cantidad_min) {
        this.cantidad_min = cantidad_min;
    }

    public double getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(double precioventa) {
        this.precioventa = precioventa;
    }

    public double getPreciocompra() {
        return preciocompra;
    }

    public void setPreciocompra(double preciocompra) {
        this.preciocompra = preciocompra;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
