package com.androidsample.ygj.androidsample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
        listView = (ListView) findViewById(R.id.listView);
        List<Map<String, Object>> listData = BuildData();

        BindList(listData);
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

        String[] imgUrls = new String[]
                {
                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-5/20150514094324.jpg",
                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-4/20150413195435.jpg",
                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-4/20150413200110.jpg",
                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-4/20150413202021.jpg",
                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-4/20150413200737.jpg",

                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-3/20150301082647.jpg",
                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-3/20150301083014.jpg",
                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-2/20150228092133.jpg",
                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-2/20150228144318.jpg",
                        "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-1/20150130165745.jpg"
                };

        for (int i = 1; i < 11; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String imgUrl = imgUrls[i - 1];
            Bitmap bitmap = null;
            try {
                bitmap = HttpUtil.GetHttpBitmap(imgUrl);
            } catch (Exception ex) {
                Log.e("error", String.format("加载图片失败，URL:%s", imgUrl));
            }
            map.put("pic", bitmap);
            map.put("name", String.format("商品%d", i));
//            map.put("price", 10F);
//            map.put("scount", 10);

            list.add(map);
        }
        return list;
    }

    /*
    * 给列表绑定数据
    * */
    private void BindList(List<Map<String, Object>> listData) {
        if (listView == null || listData == null) return;
        //数据中Map的Key
        String[] stringArrayFrom = new String[]
                {
                        "pic",
                        "name"
                };
        //ListView中空间的ID
        int[] intArrayTo = new int[]
                {
                        R.id.listitem_imageView,
                        R.id.listitem_textview_name,
                };
        SimpleAdapter adapter;
        adapter = new SimpleAdapter(this, listData, R.layout.list_item, stringArrayFrom, intArrayTo);

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView imgView = (ImageView) view;
                    imgView.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });

        listView.setAdapter(adapter);
    }
}
