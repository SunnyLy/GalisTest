package com.galis.demo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.galis.demo.Matrix.MatrixActivity;
import com.galis.demo.Scroll.PullScrollActivity;
import com.galis.demo.Scroll.ScrollActivity;
import com.galis.demo.draw.CalTimeActivity;
import com.galis.demo.draw.CanvasDraw;
import com.galis.demo.tag.TagActivity;

import java.util.LinkedList;


public class AppStartActivity extends ActionBarActivity {


    LinkedList<String> list = new LinkedList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initList();
        ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        AlertDialog dialog = new AlertDialog.Builder(AppStartActivity.this, R.style.MyDialogStyleBottom).setTitle("").create();
                        dialog.getWindow().setGravity(Gravity.BOTTOM);
                        dialog.show();
                        dialog.setContentView(R.layout.h_dialog_good_style);
                        break;
                    case 1:
                        startActivity(new Intent(AppStartActivity.this, TagActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(AppStartActivity.this, CalTimeActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(AppStartActivity.this, MatrixActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(AppStartActivity.this, ScrollActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(AppStartActivity.this, PullScrollActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(AppStartActivity.this, CanvasDraw.class));
                        break;
                }
            }
        });
        setContentView(listView);
    }

    private void initList() {
        list.add("TestPopUpDialog");
        list.add("TagActivity");
        list.add("CalTimeActvity");
        list.add("MatrixActvity");
        list.add("ScrollActivity");
        list.add("PullScrollActivity");
        list.add("CanvasDraw");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
