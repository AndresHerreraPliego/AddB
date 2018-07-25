package astart.addb.fragmets;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import astart.addb.R;
import astart.addb.application.app;
import astart.addb.models.Producto;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class Agregar_Nuevo extends Fragment implements View.OnClickListener{

    ImageButton[] Botones = new ImageButton[5];
    EditText[] datos = new EditText[7];
    final int[] textos = {R.id.codigo, R.id.nombre, R.id.cantidad, R.id.reserva, R.id.unidad, R.id.venta, R.id.compra};

    View vista;
    CircleImageView circleImageView;
    final int permiso = 1;
    Bitmap mapa;
    private Realm realm;

    public Agregar_Nuevo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.fragment_agregar__nuevo, container, false);

        for (int i=0; i<datos.length; i++){
            datos[i] = (EditText) vista.findViewById(textos[i]);
        }

        Redondea(vista);

        Botones[0] = (ImageButton) vista.findViewById(R.id.camara);
        Botones[1] = (ImageButton) vista.findViewById(R.id.galeria);
        Botones[2] = (ImageButton) vista.findViewById(R.id.eliminar);
        Botones[3] = (ImageButton) vista.findViewById(R.id.cancelar);
        Botones[4] = (ImageButton) vista.findViewById(R.id.aceptar);
        for (int i=0; i<Botones.length; i++){
            Botones[i].setOnClickListener(this);
        }

        return vista;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camara:
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED){
                    Tomar_foto();
                }else{
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, permiso);
                }
                break;
            case R.id.galeria:
                Selecciona_foto();
                break;
            case R.id.eliminar:
                //se devuelve la imagen a por defecto
                Redondea(vista);
                break;
            case R.id.cancelar:
                //todo se reestablece
                break;
            case R.id.aceptar:
                //se guardan los datos
                Obten_Datos();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {//permiso para la camara
        switch(requestCode){
            case permiso:{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "Con permisos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Sin permisos", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //Captura el resultado del intent de camara o galeria
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == 0){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap)bundle.get("data");
            bitmap.setHeight(bitmap.getWidth());
            Redondea(vista, bitmap);
        }
        if(resultCode == Activity.RESULT_OK && requestCode == 1){
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                Redondea(vista, bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void Redondea(View vista){ //Imagen por default
        circleImageView = (CircleImageView)vista.findViewById(R.id.imagen);
        mapa = null;
        Picasso.get().load(R.mipmap.ic_icon_product).fit().into(circleImageView);
    }

    private void Redondea(View vista, Bitmap bitmap){ //Una imagen de la camara o galeria
        circleImageView = (CircleImageView)vista.findViewById(R.id.imagen);
        mapa = bitmap;
        circleImageView.setImageBitmap(bitmap);
    }

    private void Tomar_foto(){ //Captura foto de la camara
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private void Selecciona_foto(){ //Seleciona imagen de la galeria
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    private void Obten_Datos(){
        int codigo = Integer.parseInt(datos[0].getText().toString());
        String nombre = datos[1].getText().toString();
        double cantidad = Double.parseDouble(datos[2].getText().toString());
        String unidad = datos[4].getText().toString();
        double minima = Double.parseDouble(datos[3].getText().toString());
        double venta = Double.parseDouble(datos[5].getText().toString());
        double compra = Double.parseDouble(datos[6].getText().toString());

        if(mapa != null){
            ByteArrayOutputStream flujo = new ByteArrayOutputStream();
            mapa.compress(Bitmap.CompressFormat.PNG, 100, flujo);
            byte[] imagen = flujo.toByteArray();
            mapa.recycle();

            LLenado(imagen, codigo,nombre,cantidad,unidad,minima,venta,compra);

        }else{
            LLenado(null, codigo,nombre,cantidad,unidad,minima,venta,compra);
        }
    }

    private void LLenado(final byte[] imagen, final int codigo, final String nombre, final double cantidad, final String unidad, final double minima, final double venta, final double compra){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Producto p = new Producto(codigo, imagen, nombre, cantidad, unidad, minima, venta, compra, new Date());
                realm.copyToRealmOrUpdate(p);


            }
        });
    }
}
