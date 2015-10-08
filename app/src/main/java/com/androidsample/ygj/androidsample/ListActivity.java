package com.androidsample.ygj.androidsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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

    /*
    * 构建列表数据
    * */
    private List<Map<String, Object>> BuildData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 1; i < 21; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pic", R.mipmap.ic_launcher);
            map.put("name", String.format("商品{0}", i));
            map.put("price", 10F);
            map.put("scount", 10);

            list.add(map);
        }
        return list;
    }

    /*
    * 给列表绑定数据
    * */
    private void BindList() {
        List<Map<String, Object>> listData = BuildData();
        if (listView == null || listData == null) return;
        String[] stringArrayFrom = new String[]
                {
                        "pic",
                        "name"
                };
        int[] intArrayTo = new int[]
                {
                        R.id.listitem_imageView,
                        R.id.listitem_textview_name,
                };
        SimpleAdapter adapter = new SimpleAdapter(this, listData, R.layout.list_item, stringArrayFrom, intArrayTo);
        listView.setAdapter(adapter);
    }
}
