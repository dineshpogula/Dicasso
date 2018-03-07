package com.dinesh.dicasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String [] mFilePath;
    private String [] mFileName;
    private File [] mListfile;

    GridView  mGridView;
    File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check for SD card
        if(!Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this, "Error in mountng SD card", Toast.LENGTH_SHORT).show();
        } else {
            //Locating Image folder internal storage
            mFile = new File(Environment.getExternalStorageDirectory() + File.separator + "Camera");
            mFile.mkdir();
        }

        if(mFile.isDirectory()){
            mListfile = mFile.listFiles();
            //creating a string array for file paths
            mFilePath = new String[mListfile.length];
            //Creating a String array for fil names
            mFileName = new String[mListfile.length];

            for (int i = 0; i < mListfile.length; i++){
                // Getting path and names for image file
                mFilePath[i] = mListfile[i].getAbsolutePath();
                mFileName[i] = mListfile[i].getName();
            }

        }
        mGridView = findViewById(R.id.simpleGridView);
        GridAdapter gridAdapter = new GridAdapter(this, mFilePath, mFileName);
        mGridView.setAdapter(gridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Image git was selected", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class GridAdapter extends BaseAdapter {
        private String[] filePath;
        private String[] fileName;
        Context mContext;

        private LayoutInflater inflater;

        private GridAdapter(Context context, String[] mFilePath, String[] mFileName){
            mContext = context;
            filePath = mFilePath;
            fileName = mFileName;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return filePath.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = inflater.inflate(R.layout.adapter_layout, null);
            ImageView imageView = findViewById(R.id.gridImage);
            Bitmap bitmapFactory = BitmapFactory.decodeFile(filePath[position]);
            imageView.setImageBitmap(bitmapFactory);
            //Picasso.with(mContext).load().error(R.drawable.ic_launcher_background).into(imageView);

            return convertView;
        }
    }
}
