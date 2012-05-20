package org.dyndns.phpusr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import org.dyndns.phpusr.dao.DBHelper;
import org.dyndns.phpusr.domains.Coast;
import org.dyndns.phpusr.domains.Lunch;
import org.dyndns.phpusr.enums.CoastType;
import org.dyndns.phpusr.enums.DateDialogType;
import org.dyndns.phpusr.store.Store;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: phpusr
 * Date: 08.03.12
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */


public class AddCoast extends Activity {
    private TextView coastDateLabel;
    private TextView coastDate;
    private TextView expectedPrice;
    private TextView price;
    
    private Spinner spinnerDrink;
    private Spinner spinnerGarnish;
    private Spinner spinnerMeat;
    private Spinner spinnerSalad;
    private Spinner spinnerFlour;

    private double priceDrink = 0, priceGarnish = 0, priceMeat = 0, priceSalad = 0, priceFlour = 0, coastsSum = 0;

    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_coast);
        
        coastDateLabel = (TextView) findViewById(R.id.coastDateLabel);
        coastDate = (TextView) findViewById(R.id.coastDate);
        expectedPrice = (TextView) findViewById(R.id.expectedPrice);
        price = (TextView) findViewById(R.id.price);

        spinnerDrink = (Spinner) findViewById(R.id.spinnerDrink);
        spinnerGarnish = (Spinner) findViewById(R.id.spinnerGarnish);
        spinnerMeat = (Spinner) findViewById(R.id.spinnerMeat);
        spinnerSalad = (Spinner) findViewById(R.id.spinnerSalad);
        spinnerFlour = (Spinner) findViewById(R.id.spinnerFlour);

        mDbHelper = new DBHelper(getApplicationContext());

        coastDateLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog.callMe(AddCoast.this, DateDialogType.ADD_COAST);
            }
        });

        spinnerDrink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long row) {
                Coast coast = (Coast) spinnerDrink.getAdapter().getItem(position);
                if (coast != null) {
                    priceDrink = coast.getPrice();
                    generateSum();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinnerGarnish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long row) {
                Coast coast = (Coast) spinnerGarnish.getAdapter().getItem(position);
                if (coast != null) {
                    priceGarnish = coast.getPrice();
                    generateSum();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinnerMeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long row) {
                Coast coast = (Coast) spinnerMeat.getAdapter().getItem(position);
                if (coast != null) {
                    priceMeat = coast.getPrice();
                    generateSum();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinnerSalad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long row) {
                Coast coast = (Coast) spinnerSalad.getAdapter().getItem(position);
                if (coast != null) {
                    priceSalad = coast.getPrice();
                    generateSum();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinnerFlour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long row) {
                Coast coast = (Coast) spinnerFlour.getAdapter().getItem(position);
                if (coast != null) {
                    priceFlour = coast.getPrice();
                    generateSum();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        findViewById(R.id.addCoastCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.addCoastOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbHelper.insertIntoCoastList(
                        new Lunch(getCoastSum(), Store.getDate()
                            ,(Coast) spinnerDrink.getSelectedItem()
                            ,(Coast) spinnerGarnish.getSelectedItem()
                            ,(Coast) spinnerMeat.getSelectedItem()
                            ,(Coast) spinnerSalad.getSelectedItem()
                            ,(Coast) spinnerFlour.getSelectedItem()
                        ));
                onBackPressed();
            }
        });
    }

    private double getCoastSum() {
        if (price.getText() != null && price.getText().equals("")) {
            price.setText("0");
        }
        return Double.valueOf(price.getText().toString());
    }

    private void generateSum() {
        coastsSum = priceDrink + priceGarnish + priceMeat + priceSalad + priceFlour;
        expectedPrice.setText(Double.toString(coastsSum));
    }

    private void fillList() {
        ArrayAdapter<Coast> adapter = new MyCustomAdapter( this, R.layout.list, mDbHelper.getCoastItemsByTypeId(CoastType.DRINK.getId()));
        spinnerDrink.setAdapter(adapter);

        adapter = new MyCustomAdapter( this, R.layout.list, mDbHelper.getCoastItemsByTypeId(CoastType.GARNISH.getId()));
        spinnerGarnish.setAdapter(adapter);

        adapter = new MyCustomAdapter( this, R.layout.list, mDbHelper.getCoastItemsByTypeId(CoastType.MEAT.getId()));
        spinnerMeat.setAdapter(adapter);

        adapter = new MyCustomAdapter( this, R.layout.list, mDbHelper.getCoastItemsByTypeId(CoastType.SALAD.getId()));
        spinnerSalad.setAdapter(adapter);

        adapter = new MyCustomAdapter( this, R.layout.list, mDbHelper.getCoastItemsByTypeId(CoastType.FLOUR.getId()));
        spinnerFlour.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillList();
        coastDate.setText(Store.getDateString());
    }

    class MyCustomAdapter extends ArrayAdapter<Coast>{

        public MyCustomAdapter(Context context, int textViewResourceId, List<Coast> items) {
            super(context, textViewResourceId, items);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.list, parent, false);
            TextView label=(TextView)row.findViewById(R.id.list);
            label.setText(getItem(position).getName());

            return row;
        }
    }

    public static void callMe(Context context) {
        Intent intent = new Intent( context, AddCoast.class );
        context.startActivity(intent);
    }
}
