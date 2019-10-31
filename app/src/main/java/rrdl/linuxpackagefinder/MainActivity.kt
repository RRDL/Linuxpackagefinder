package rrdl.linuxpackagefinder

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    internal var l: ListView
    internal var S: EditText

    @Override
    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(getApplicationContext(), "Total available packages : 57957", Toast.LENGTH_SHORT).show()

        try {

            val stream = getBaseContext().getAssets().open("packages.txt")
            val `in` = BufferedReader(InputStreamReader(stream, "UTF-8"))
            var str: String
            val lines = ArrayList()

            while ((str = `in`.readLine()) != null) {
                lines.add(str)
            }
            `in`.close()
            l = findViewById(R.id.List) as ListView
            val adapter = ArrayAdapter(getApplicationContext(), R.layout.item, R.id.textView, lines)
            l.setAdapter(adapter)
            l.setTextFilterEnabled(true)
            l.setOnItemClickListener(object : AdapterView.OnItemClickListener() {
                @Override
                fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val Bld = AlertDialog.Builder(this@MainActivity)
                    Bld.setMessage("To install this package , in terminal write : \n sudo apt-get install " + l.getItemAtPosition(position).toString())
                    Bld.setCancelable(false)
                    Bld.setPositiveButton("OK", object : DialogInterface.OnClickListener() {
                        fun onClick(dialog: DialogInterface, id: Int) {
                            dialog.cancel()
                        }
                    })
                    val A = Bld.create()
                    A.show()

                }
            })
            S = findViewById(R.id.editText) as EditText
            S.addTextChangedListener(object : TextWatcher() {
                @Override
                fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                @Override
                fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    adapter.getFilter().filter(S.getText().toString())
                }

                @Override
                fun afterTextChanged(s: Editable) {

                }
            })

        } catch (e: IOException) {
            Log.i("exception", e.toString())
        }
    }
}