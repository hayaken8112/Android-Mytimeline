package com.example.ken.mytimeline;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.IOException;


/**
 * Created by ken on 2016/01/05.
 */
public class InputActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mButton;
    private EditText mInputText;
    private static final int RESULT_PICK_IMAGEFILE = 1001;
    //private ImageView imageView;
    //private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_layout);
        //戻り値の指定
        setResult(Activity.RESULT_CANCELED);
        //インテントからのパラメータ取得
        String text = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) text = extras.getString("text");


        mInputText = (EditText)findViewById(R.id.inputText);
        mInputText.setText(text);

        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(this);

        //imageView = (ImageView)findViewById(R.id.imageView);
        //imageButton = (ImageButton)findViewById(R.id.imageButton);
        //imageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if (v.equals(mButton)) {
            //アプリ内のアクティビティを呼び出すインテントの生成
            Intent intent = new Intent();

            //インテントへの戻り値の指定
            intent.putExtra("text", mInputText.getText().toString());
            setResult(Activity.RESULT_OK, intent);


            //アクティビティの終了
            finish();
        }
        /*
        else if (v.equals(imageButton)){
            //ACTION_OPEN_DOCUMENT はシステムファイルブラウザーを通してファイルを選ぶインテント
            Intent intent = new Intent();
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
        }*/

    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("", "Uri: " + uri.toString());

                try {
                    Bitmap bmp = getBitmapFromUri(uri);
                    imageView.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
    */
}
