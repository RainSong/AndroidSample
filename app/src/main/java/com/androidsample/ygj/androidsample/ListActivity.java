package com.androidsample.ygj.androidsample;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.androidsample.ygj.androidsample.models.HttpParas;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private ProgressBar progressBar;

    private String dataUrl = "http://mate.xn--g6r18kq05d.com/mobile/searchresult.aspx?sid=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.list_progressBar);

        HttpParas httpParas = new HttpParas();
        httpParas.URL = dataUrl;
        httpParas.Paras = BuildParas();

        GetProductDataTask loadDatatask = new GetProductDataTask();
        loadDatatask.execute(httpParas);

    }

    /*
    * 给列表绑定数据
    * */
    private void BindList(List<Map<String, Object>> listData) {
        if (listView == null || listData == null) return;
        //数据中Map的Key
        String[] stringArrayFrom = new String[]
                {
                        "Name",
                        "Price",
                        "SellCount",
                        "ImgUrl"
                };
        //ListView中空间的ID
        int[] intArrayTo = new int[]
                {
                        R.id.listitem_textview_name,
                        R.id.listitem_textview_price,
                        R.id.listitem_textview_sellcount,
                        R.id.listitem_imageView
                };
        SimpleAdapter adapter;
        adapter = new SimpleAdapter(this, listData, R.layout.list_item, stringArrayFrom, intArrayTo);

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                int viewId = view.getId();
                if (viewId == R.id.listitem_textview_price) {
                    TextView txtView = (TextView) view;
                    if (txtView == null) return false;
                    txtView.setText(String.format("价格:￥%s", data));
                    return true;
                } else if (viewId == R.id.listitem_textview_sellcount) {
                    TextView txtView = (TextView) view;
                    if (txtView == null) return false;
                    txtView.setText(String.format("已售出:%s件", data));
                    return true;
                } else if (viewId == R.id.listitem_imageView) {
                    ImageView imgView = (ImageView) view;
                    String imgUrl = "http://www.xn--g6r18kq05d.com/heima_member/" + data.toString();
                    imgView.setTag(imgUrl);
                    //Log.i("ygj", imgUrl);
                    try {
                        (new LoadImgTask(imgView, imgUrl)).execute(imgUrl);
                    } catch (Exception ex) {
                    }
                    return true;
                }

                return false;
            }
        });

        listView.setAdapter(adapter);
    }

    private Map<String, Object> BuildParas() {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("sid", 1);
        paras.put("pageIndex", 1);
        paras.put("pageSize", 5);//太大会造成OOM
        paras.put("method", "getdata");
        return paras;
    }

    private String GetJson() {
        Map<String, Object> paras = BuildParas();
        String json = null;
        try {
            json = HttpUtil.Post(dataUrl, paras);
        } catch (Exception ex) {
        }
        return json;
    }

    private List<Map<String, Object>> GetListData(String json) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if (json != null && json.length() > 0) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("Message");
                if (jsonArray != null) {
                    for (int i = 0, j = jsonArray.length(); i < j; i++) {
                        JSONObject obj = jsonArray.optJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();

                        Iterator<String> keys = obj.keys();

                        int id = obj.getInt("PID");
                        String name = obj.getString("P_Name");
                        double price = obj.getDouble("P_Price");
                        int sellCount = obj.getInt("P_Count1");
                        String imgUrl = obj.getString("P_Img");

                        map.put("ID", id);
                        map.put("Name", name);
                        map.put("Price", price);
                        map.put("SellCount", sellCount);
                        map.put("ImgUrl", imgUrl);

                        list.add(map);

                    }
                }

            } catch (Exception ex) {

            }
        }
        return list;
    }

    class GetProductDataTask extends AsyncTask<HttpParas, Void, List<Map<String, Object>>> {

        @Override
        protected List<Map<String, Object>> doInBackground(HttpParas... params) {
            String url = params[0].URL;
            Map<String, Object> paras = params[0].Paras;

            String json = GetJson();
            List<Map<String, Object>> listData = GetListData(json);
            return listData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> maps) {
            super.onPostExecute(maps);
            BindList(maps);
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    class LoadImgTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private String imgUrl;

        public LoadImgTask(ImageView imageView, String imgUrl) {
            this.imageView = imageView;
            this.imgUrl = imgUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return HttpUtil.GetHttpBitmap(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            String tag = this.imageView.getTag().toString();
            if (tag.equals(this.imgUrl)) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
