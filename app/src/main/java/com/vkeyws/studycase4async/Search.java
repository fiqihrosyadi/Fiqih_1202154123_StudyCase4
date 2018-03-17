package com.vkeyws.studycase4async;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class Search extends AppCompatActivity {
    //membuat variable
    private ImageView downloadedImage;
    private ProgressDialog mProgressDialog;
    private EditText linkUrl;
    private Button imageDownloaderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //menginisialisasi variable
        imageDownloaderButton = (Button) findViewById(R.id.button_startAsyncTask);
        downloadedImage = (ImageView) findViewById(R.id.ImageView);
        linkUrl = (EditText)findViewById(R.id.urlText);

        //membuat action pada button imageDownloaderButton
        imageDownloaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String downloadUrl = linkUrl.getText().toString();
                if(downloadUrl.isEmpty()){
                    //Menampilkan toast ketika button diklik namun edit text url kosong
                    Toast.makeText(Search.this,"Masukkan URL gambar terlebih dahulu",Toast.LENGTH_SHORT).show();
                }else {
                    // memanggil class ImageDownloader
                    new ImageDownloader().execute(downloadUrl);
                }
            }
        });
    }
    //membuat class ImageDownloader
    class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPostExecute(Bitmap result) {
            downloadedImage.setImageBitmap(result);
            // Tutup progress dialog
            mProgressDialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Search.this);
            // Set judul progress dialog
            mProgressDialog.setTitle("Search Image");
            // Set pesan pada progress dialog
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progress dialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image dari URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
