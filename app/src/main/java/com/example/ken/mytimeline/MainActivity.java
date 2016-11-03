package com.example.ken.mytimeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static List<Integer> touchedCount = new ArrayList<>();
    private static List<CellItem> cells = new ArrayList<>();
    private final static int REQUEST_TEXT = 0;//テキストID
    private FloatingActionButton fab;
    private CustomAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        ListView listView;



        listView = (ListView)findViewById(R.id.listView);


        adapter = new CustomAdapter(this);
        listView.setAdapter(adapter); //重要

        //リストアイテムクリック時
        // アイテムクリック時ののイベントを追加
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            // 選択アイテムを取得
                int backgroundDrawable;
                touchedCount.set(pos, touchedCount.get(pos) + 1);
                if(touchedCount.get(pos) % 3 == 0){
                    backgroundDrawable = R.drawable.list_item_background_unread;
                } else if(touchedCount.get(pos) % 3 == 1){
                    backgroundDrawable = R.drawable.list_item_background_read;
                } else {
                    backgroundDrawable = R.drawable.list_item_background_important;
                }
                view.setBackground(view.getResources().getDrawable(backgroundDrawable));



            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //アプリ内のアクティビティを呼び出すインテントの生成
                Intent intent = new Intent(MainActivity.this, InputActivity.class);

                //インテントへのパラメータ指定
                intent.putExtra("text", cells.get(position).text.toString());
                //アクティビティを呼び出す
                startActivityForResult(intent, REQUEST_TEXT);

                return false;
            }


        });




        fab.setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        if (view.equals(fab)) {


            //アプリ内のアクティビティを呼び出すインテントの生成
            Intent intent = new Intent(this, InputActivity.class);

            //アクティビティを呼び出す
            startActivityForResult(intent, REQUEST_TEXT);

        }
    }


    //アクティビティ呼び出し結果の取得
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent ) {
        if (requestCode == REQUEST_TEXT && resultCode == RESULT_OK) {
            //インテントからのパラメータ取得
            String text = "";

            Bundle extras = intent.getExtras();
            if (extras != null) {
                text = extras.getString("text");

            }

            //リスト追加
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = new Date(System.currentTimeMillis());
            String nowDate = sdf.format(date);
            CellItem item = new CellItem(text, nowDate);
            adapter.add(item);

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private static class CustomAdapter extends BaseAdapter {
        private LayoutInflater mInflater;


        public CustomAdapter(Context context){
            mInflater = LayoutInflater.from(context);
        }

        public void add(CellItem item){
            cells.add(0, item);
            touchedCount.add(0, 0);
            notifyDataSetChanged();

        }





        static class ViewHolder {
            TextView text;
            TextView dateText;
            Button delete;
        }
        public int getCount() {
            return cells.size();
        }

        public Object getItem(int position){
            return position;
        }

        public long getItemId(int position){
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent){
            ViewHolder holder;

            if (convertView == null){
                Log.d("convertView == null()", "position= "+position);
                convertView = mInflater.inflate(R.layout.list_item_icon_text, null);

                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text_view);
                holder.dateText = (TextView) convertView.findViewById(R.id.cellDate);
                holder.delete = (Button) convertView.findViewById(R.id.buttonDelete);


                convertView.setTag(holder);
            } else {
                Log.d("convertView != null()", "position= "+position);
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText("    " + cells.get(position).text);

            holder.dateText.setText(cells.get(position).date);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cells.remove(position);
                    touchedCount.remove(position);
                    notifyDataSetChanged();
                }
            });



            if (touchedCount.get((position)) % 3 == 0) {
                convertView.setBackground(convertView.getResources().getDrawable(R.drawable.list_item_background_unread));
            } else if (touchedCount.get((position)) % 3 == 1){
                convertView.setBackground(convertView.getResources().getDrawable(R.drawable.list_item_background_read));
            } else {
                convertView.setBackground(convertView.getResources().getDrawable(R.drawable.list_item_background_important));
            }







            return convertView;


        }

    }



}
