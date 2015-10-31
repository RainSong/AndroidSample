package com.androidsample.ygj.androidsample;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidsample.ygj.androidsample.models.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListActivity extends AppCompatActivity {
    private ListView listView;
    private ProgressBar progressBar;

    private final int pageSize = 10;
    private final String dataUrl = "http://mate.xn--g6r18kq05d.com/mobile/searchresult.aspx?sid=1";
    private Map<String, Object> requestParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        new ListDataLoaderTask().execute(dataUrl);
    }

    /**
     * 获取请求的参数
     **/
    private void buildHttpRequstParams(int pageIndex) {
        if (requestParams == null) {
            requestParams = new HashMap<>();
            requestParams.put("sid", 1);
            requestParams.put("pageSize", this.pageSize);
            requestParams.put("pageIndex", pageIndex);
            requestParams.put("method", "getdata");
        } else {
            requestParams.put("pageIndex", pageIndex);
        }
    }

    /**
     * 获取JSON数据
     * @return
     */
    private String getJson() {
        String json = null;
        try {
            buildHttpRequstParams(1);
            json = HttpUtil.Post(dataUrl, requestParams);
        } catch (Exception ex) {
        }
        return json;
    }

    /**
     * 将JSON数据解析为List
     * @param json
     * @return
     */
    private List<Product> getListData(String json) {
        List<Product> list = new ArrayList<Product>();

        if (json != null && json.length() > 0) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("Message");
                if (jsonArray != null) {
                    for (int i = 0, j = jsonArray.length(); i < j; i++) {
                        JSONObject obj = jsonArray.optJSONObject(i);

                        Product product = new Product();
                        product.ID = obj.getInt("PID");
                        product.Name = obj.getString("P_Name");
                        product.Price = obj.getDouble("P_Price");
                        product.SellCount = obj.getInt("P_Count1");
                        product.ImgUrl = obj.getString("P_Img");

                        list.add(product);
                    }
                }

            } catch (Exception ex) {
                Log.e("ygj", "解析JSON字符串失败", ex);
            }
        }
        return list;
    }

    /**
     * 给ListView绑定数据
     * @param listData
     */
    private void bindListData(List<Product> listData) {
        ProductListAdapter productListAdapter = new ProductListAdapter(this, listData);
        listView.setAdapter(productListAdapter);
    }

    /**
     * 跳转至商品详情Activity
     * @param proeuctId 商品ID
     */
    private void gotoProductActivity(int proeuctId){
        Intent intent = new Intent(ProductListActivity.this, ProductActivity.class);

        startActivity(intent);
    }

    /**
     * 异步获取列表数据
     */
    class ListDataLoaderTask extends AsyncTask<String, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(String... params) {
            List<Product> list = null;
            try {
                String json = getJson();
                list = getListData(json);
            } catch (Exception ex) {
                Log.e("ygj", "获取订单列表数据失败", ex);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Product> list) {
            bindListData(list);
            if (listView != null) listView.setVisibility(View.VISIBLE);
            if (progressBar != null) progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * 定义数据绑定
     */
    class ProductListAdapter extends BaseAdapter {
        private List<Product> values;
        private LayoutInflater layoutInflater;

        public ProductListAdapter(Context context, List<Product> listData) {
            this.values = listData;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (this.values == null) return 0;
            return values.size();
        }

        @Override
        public Product getItem(int position) {
            if (this.values == null || this.values.size() <= position)
                return null;
            return this.values.get(position);
        }

        @Override
        public long getItemId(int position) {
            if (this.values == null)
                return 0;
            Product product = this.getItem(position);
            if (product == null)
                return 0;
            return product.ID;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            Product product = this.values.get(position);
            if (product == null) return convertView;

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_item_product, null);
                viewHolder = new ViewHolder();

                viewHolder.ImageView = (ImageView) convertView.findViewById(R.id.product_listitem_img);
                viewHolder.textViewName = (TextView) convertView.findViewById(R.id.product_listitem_textview_name);
                viewHolder.textViewPrice = (TextView) convertView.findViewById(R.id.product_listitem_textview_price);
                viewHolder.textViewSellCount = (TextView) convertView.findViewById(R.id.product_listitem_textview_sellcount);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textViewName.setText(product.Name);
            viewHolder.textViewPrice.setText("价格：" + product.Price);
            viewHolder.textViewSellCount.setText("已售出：" + product.SellCount + "件");
            return convertView;
        }

        class ViewHolder {
            public ImageView ImageView;
            public TextView textViewName;
            public TextView textViewPrice;
            public TextView textViewSellCount;
        }
    }


}
