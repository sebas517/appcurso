package com.example.sebas.appcurso.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sebas.appcurso.MainActivity;
import com.example.sebas.appcurso.Pojo.Lugar;

import java.util.ArrayList;
import java.util.List;

public class GestorLugar {

    private Ayudante ayudante;
    private SQLiteDatabase bd;

    public GestorLugar(Context c){
        this(c, true);
    }

    public GestorLugar(Context c, boolean write){
        Log.v(MainActivity.LOG, "constructor gestor");
        if (write){
            bd = ayudante.getWritableDatabase();
        }else {
            bd = ayudante.getReadableDatabase();
        }
    }

    public void cerrar(){ayudante.close();}

    //create . add . insert. persist
    public long add(Lugar lugar){
        return bd.insert(Contrato.TableLugar.TABLE, null,
                GestorLugar.get(lugar));
    }

    //alias
    public long insert(Lugar lugar){
        return add(lugar);
    }

    //alias
    public long persits(Lugar lugar){
        return  add(lugar);
    }

    //read - uno, todos, condición + lista, cursor
    public List<Lugar> get(){
        return get(null, null);
    }

    public List<Lugar> get(String condicion, String[] parametros){
        List<Lugar> todos = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        while(cursor.moveToNext()){
            todos.add(getLugar(cursor));
        }
        cursor.close();
        return todos;
    }

    public Lugar get (long id){
        Lugar l = null;
        List<Lugar> contactos = get(Contrato.TableLugar._ID + " = ?", new String[]{id + ""});
        if(contactos.size()>0){
            l = contactos.get(0);
        }
        return l;
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros){
        return bd.query(Contrato.TableLugar.TABLE,
                null,
                condicion,
                parametros,
                null,
                null,
                Contrato.TableLugar.PAIS + " DESC");
    }

    public Cursor getCursor(long id){
        return getCursor(Contrato.TableLugar._ID + " = ?", new String[]{id + ""});
    }

    //update - edit - save
    public int edit (Lugar lugar){
        return bd.update(Contrato.TableLugar.TABLE,
                GestorLugar.get(lugar),
                Contrato.TableLugar._ID + "= ?",
                new String[]{lugar.getId() + ""});
    }

    //delete -> delete - erase - remove
    public int remove(Lugar l){
        return remove(l.getId());
    }

    public int remove(long id){
        String condicion = Contrato.TableLugar._ID + " = ?";
        String[] argumentos = {id + ""};
        return bd.delete(Contrato.TableLugar.TABLE, condicion, argumentos);

    }

    public static Lugar getLugar(Cursor c){
        Lugar lugar = new Lugar();
        lugar.setId(c.getLong(c.getColumnIndex(Contrato.TableLugar._ID)));
        lugar.setNombre(c.getString(c.getColumnIndex(Contrato.TableLugar.NOMBRE)));
        lugar.setLatitud(c.getDouble(c.getColumnIndex(Contrato.TableLugar.LATITUD)));
        lugar.setLongitud(c.getDouble(c.getColumnIndex(Contrato.TableLugar.LONGITUD)));
        lugar.setLocalidad(c.getString(c.getColumnIndex(Contrato.TableLugar.LOCALIDAD)));
        lugar.setPais(c.getString(c.getColumnIndex(Contrato.TableLugar.PAIS)));
        lugar.setComentario(c.getString(c.getColumnIndex(Contrato.TableLugar.COMENTARIO)));
        lugar.setPuntos(c.getInt(c.getColumnIndex(Contrato.TableLugar.PUNTOS)));
        lugar.setFecha(c.getString(c.getColumnIndex(Contrato.TableLugar.FECHA)));
        return lugar;
    }

    private static ContentValues get(Lugar lugar){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contrato.TableLugar.NOMBRE, lugar.getNombre());
        contentValues.put(Contrato.TableLugar.LATITUD, lugar.getLatitud());
        contentValues.put(Contrato.TableLugar.LONGITUD, lugar.getLongitud());
        contentValues.put(Contrato.TableLugar.LOCALIDAD, lugar.getLocalidad());
        contentValues.put(Contrato.TableLugar.PAIS, lugar.getPais());
        contentValues.put(Contrato.TableLugar.COMENTARIO, lugar.getComentario());
        contentValues.put(Contrato.TableLugar.PUNTOS, lugar.getPuntos());
        contentValues.put(Contrato.TableLugar.FECHA, lugar.getFecha());
        return contentValues;
    }

}

