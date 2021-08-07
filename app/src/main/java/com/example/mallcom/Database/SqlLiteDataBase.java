package com.example.mallcom.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mallcom.Models.ModelCart;

import java.util.ArrayList;

public class SqlLiteDataBase  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mallcom.db";
    private static final int DATABASE_VERSION = 2;
    //table
    private static final String TABLE_CART_ORDER = "cart_order";

    public SqlLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CART_ORDER + "" +
                "(id VARCHAR(255) " +
                ",name VARCHAR(255)" +
                ",price1 VARCHAR(255)" +
                ",price2 VARCHAR(255)" +
                ",qty VARCHAR(255)" +
                ",description VARCHAR(255)" +
                ",rate VARCHAR(255)" +
                ",color VARCHAR(255)" +
                ",size VARCHAR(255)" +
                ",availability INTEGER" +
                ",img_url VARCHAR(255)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ORDER);
        onCreate(db);
    }

    //Delete all table data
    public boolean deleteAllTableData(String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name, null, null) > 0;
    }
    //Delete row from table_cart_order
    public boolean deleteCartItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CART_ORDER, "id='" + id + "'", null) > 0;
    }
    //add cart item
    public boolean addToCart(ModelCart cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", cartItem.getId());
        contentValues.put("name", cartItem.getName());
        contentValues.put("price1", cartItem.getPrice1());
        contentValues.put("price2", cartItem.getPrice2());
        contentValues.put("qty", cartItem.getQty());
        contentValues.put("description", cartItem.getDescription());
        contentValues.put("rate", cartItem.getRate());
        contentValues.put("img_url", cartItem.getImage());
        contentValues.put("color", cartItem.getColor());
        contentValues.put("size", cartItem.getSize());
        contentValues.put("availability", cartItem.getAvailability());


        if (cartItem.getAvailability()==0){
            return false;
        }

        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor res = db2.rawQuery("select qty from "+TABLE_CART_ORDER+" where id='" + cartItem.getId() + "'", null);
        res.moveToFirst();

        if (!res.isAfterLast()) {
            //do update
            int db_count = res.getInt(0);
            ContentValues contentValues2 = new ContentValues();
            if (db_count>=cartItem.getAvailability()){
                return false;
            }
            contentValues2.put("qty",db_count+Integer.parseInt(cartItem.getQty()));
            long result1 = db2.update(TABLE_CART_ORDER, contentValues2, "id='" + cartItem.getId() + "'", null);
            if (result1 == -1) {
                return false;
            } else {
                return true;
            }

        }else{
            long result1 = db.insert(TABLE_CART_ORDER, null, contentValues);
            if (result1 == -1) {
                return false;
            } else {
                return true;
            }
        }


    }
    //update cart item
    public boolean updateToCart(ModelCart cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", cartItem.getId());
        contentValues.put("name", cartItem.getName());
        contentValues.put("price1", cartItem.getPrice1());
        contentValues.put("price2", cartItem.getPrice2());
        contentValues.put("qty", cartItem.getQty());
        contentValues.put("description", cartItem.getDescription());
        contentValues.put("rate", cartItem.getRate());
        contentValues.put("img_url", cartItem.getImage());
        contentValues.put("availability", cartItem.getAvailability());


        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor res = db2.rawQuery("select qty from "+TABLE_CART_ORDER+" where id='" + cartItem.getId() + "'", null);
        res.moveToFirst();

        if (!res.isAfterLast()) {
            //do update
            int db_count = res.getInt(0);
            if (db_count>=cartItem.getAvailability()){
                return false;
            }

        }

        try {
            long result1 = db.update(TABLE_CART_ORDER, contentValues, "id='" + cartItem.getId() + "'", null);
            if (result1 == -1) {
                return false;
            } else {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

//        SQLiteDatabase db2 = this.getReadableDatabase();


//        Cursor res = db2.rawQuery("select qty from "+TABLE_CART_ORDER+" where id='" + cartItem.getId() + "'", null);
//        res.moveToFirst();

//        if (!res.isAfterLast()) {
//            //do update
//            ContentValues contentValues2 = new ContentValues();
//            contentValues2.put("qty",Integer.parseInt(cartItem.getQty()));
//            long result1 = db2.update(TABLE_CART_ORDER, contentValues2, "id='" + cartItem.getId() + "'", null);
//            if (result1 == -1) {
//                return false;
//            } else {
//                return true;
//
//            }
//
//        }else{
//            long result1 = db.insert(TABLE_CART_ORDER, null, contentValues);
//            if (result1 == -1) {
//                return false;
//            } else {
//                return true;
//
//            }
//        }

    }    //update cart item
    public boolean updateToCart(ModelCart cartItem,boolean decrease) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", cartItem.getId());
        contentValues.put("name", cartItem.getName());
        contentValues.put("price1", cartItem.getPrice1());
        contentValues.put("price2", cartItem.getPrice2());
        contentValues.put("qty", cartItem.getQty());
        contentValues.put("description", cartItem.getDescription());
        contentValues.put("rate", cartItem.getRate());
        contentValues.put("img_url", cartItem.getImage());
        contentValues.put("availability", cartItem.getAvailability());


        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor res = db2.rawQuery("select qty from "+TABLE_CART_ORDER+" where id='" + cartItem.getId() + "'", null);
        res.moveToFirst();

        if (!res.isAfterLast()&&!decrease) {
            //do update
            int db_count = res.getInt(0);
            if (db_count>=cartItem.getAvailability()){
                return false;
            }

        }

        try {
            long result1 = db.update(TABLE_CART_ORDER, contentValues, "id='" + cartItem.getId() + "'", null);
            if (result1 == -1) {
                return false;
            } else {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

//        SQLiteDatabase db2 = this.getReadableDatabase();


//        Cursor res = db2.rawQuery("select qty from "+TABLE_CART_ORDER+" where id='" + cartItem.getId() + "'", null);
//        res.moveToFirst();

//        if (!res.isAfterLast()) {
//            //do update
//            ContentValues contentValues2 = new ContentValues();
//            contentValues2.put("qty",Integer.parseInt(cartItem.getQty()));
//            long result1 = db2.update(TABLE_CART_ORDER, contentValues2, "id='" + cartItem.getId() + "'", null);
//            if (result1 == -1) {
//                return false;
//            } else {
//                return true;
//
//            }
//
//        }else{
//            long result1 = db.insert(TABLE_CART_ORDER, null, contentValues);
//            if (result1 == -1) {
//                return false;
//            } else {
//                return true;
//
//            }
//        }

    }

    public ArrayList<ModelCart> GetAllCart() {
        String query = "SELECT * FROM "+TABLE_CART_ORDER+" ";

        ArrayList<ModelCart> cartItemArrayList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null&&c.getCount()>0) {
            while (c.moveToNext()) {
                ModelCart cartItem = new ModelCart();

                cartItem.setId(c.getString(c.getColumnIndex("id")));
                cartItem.setName(c.getString(c.getColumnIndex("name")));
                cartItem.setPrice1(c.getString(c.getColumnIndex("price1")));
                cartItem.setPrice2(c.getString(c.getColumnIndex("price2")));
                cartItem.setQty(c.getString(c.getColumnIndex("qty")));
                cartItem.setDescription(c.getString(c.getColumnIndex("description")));
                cartItem.setRate(c.getString(c.getColumnIndex("rate")));

                cartItem.setImage(c.getString(c.getColumnIndex("img_url")));
                cartItem.setSize(c.getString(c.getColumnIndex("size")));
                cartItem.setColor(c.getString(c.getColumnIndex("color")));
                cartItem.setAvailability(c.getInt(c.getColumnIndex("availability")));

                cartItemArrayList.add(cartItem);
            }
            c.close();
        }

        return cartItemArrayList;
    }


}
