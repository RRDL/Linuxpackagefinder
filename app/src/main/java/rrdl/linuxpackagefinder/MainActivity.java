package rrdl.linuxpackagefinder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView l;
    EditText S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(),"Total available packages : 57957",Toast.LENGTH_SHORT).show();

        try {

            InputStream stream = getBaseContext().getAssets().open("packages.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String str;
            ArrayList<String> lines = new ArrayList<>();

            while ((str = in.readLine()) != null ) {
                lines.add(str);
            }
            in.close();
            l = (ListView) findViewById(R.id.List);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item, R.id.textView, lines);
            l.setAdapter(adapter);
            l.setTextFilterEnabled(true);
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder Bld=new AlertDialog.Builder(MainActivity.this);
                    Bld.setMessage("To install this package , in terminal write : \n sudo apt-get install "+l.getItemAtPosition(position).toString());
                    Bld.setCancelable(false);
                    Bld.setPositiveButton("OK",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog,int id){dialog.cancel();}});
                    AlertDialog A=Bld.create();
                    A.show();

                }
            });
            S =(EditText)findViewById(R.id.editText);
            S.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(S.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } catch (IOException e) {
            Log.i("exception",e.toString());
        }


        }

}