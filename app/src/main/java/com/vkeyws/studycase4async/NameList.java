package com.vkeyws.studycase4async;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class NameList extends AppCompatActivity {
    //membuat variable
    private ListView mListView;
    private ProgressBar mProgressBar;
    private Button mStartAsyncTask;
    private AddItem mAddItemToListView;

    //membuat array nama mahasiswa
    private String[] nama = { "Brad",
            "Pitt",
            "Agnes",
            "Stewart",
            "Cassie",
            "John",
            "Jane",
            "Camp",
            "Aggie",
            "Lisa",
            "Commit",
            "Chargi",
            "Shaggy",
            "Bang",
            "Beng"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_list);

        //inisialisasi variabel
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.listView);
        mStartAsyncTask = (Button) findViewById(R.id.button_startAsyncTask);

        //Membuat progressbar visible ketika aplikasi berjalan
        mListView.setVisibility(View.GONE);
        //set adapater
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));

        //membuat action pada button
        mStartAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Adapter proses dengan async task
                mAddItemToListView = new AddItem(); //memanggil class AddItem dan mengeksekusinya
                mAddItemToListView.execute();
            }
        });
    }


    //membuat class AddItem yang mengextends Async
    public class AddItem  extends AsyncTask<Void, String, Void> {

        private ArrayAdapter<String> mAdapter;
        private int counter=1;
        ProgressDialog mProgressDialog = new ProgressDialog(NameList.this);

        //menjalankan thread UI, dan digunakan untuk menyiapkan tugas
        @Override
        protected void onPreExecute() {
            mAdapter = (ArrayAdapter<String>) mListView.getAdapter(); //casting suggestion

            //Untuk progress dialog
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setProgress(0);
            //memperbarui kemajuan dialog dengan beberapa nilai
            //menampilkan progress dialog

            //Menghandle tombol cancel pada dialog
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAddItemToListView.cancel(true);
                    //Menampilkan (Visible) progress bar pada layar dialog setelah diklik cancel
                    mProgressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            mProgressDialog.show();
        }

        //tempat Anda mengimplementasikan kode untuk mengeksekusi pekerjaan yang akan dilakukan pada thread terpisah
        @Override
        protected Void doInBackground(Void... params) {
            //membuat perulangan untuk memunculkan nama mahasiswa
            for (String item : nama){
                publishProgress(item);
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    mAddItemToListView.cancel(true);
                }
            }
            return null;  //mengembalikkan nilai
        }

        //akan dipanggil pada thread UI dan digunakan untuk memperbarui kemajuan dalam UI
        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]); //adapter array memluai dari array 0

            Integer current_status = (int)((counter/(float)nama.length)*100);
            mProgressBar.setProgress(current_status);

            //Set tampilan progress pada dialog progress
            mProgressDialog.setProgress(current_status);

            //Set message berupa persentase progress pada dialog progress
            mProgressDialog.setMessage(String.valueOf(current_status+"%"));
            counter++; //mengeset hitungan di dalam progress dialog
        }

        //untuk memperbarui hasil ke UI setelah AsyncTask telah selesai dimuat
        @Override
        protected void onPostExecute(Void Void) {
            //Menyembunyikan progressbar
            mProgressBar.setVisibility(View.GONE);

            //setelah loading progress sudah full maka otomatis akan hilang progress dialognya
            mProgressDialog.dismiss();
            mListView.setVisibility(View.VISIBLE);
        }
    }
}
