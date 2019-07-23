package com.leothon.cogito.Mvp.View.Activity.OrderActivity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.BuyClassAdapter;
import com.leothon.cogito.Adapter.OrderHisAdapter;
import com.leothon.cogito.Bean.Bill;
import com.leothon.cogito.Bean.OrderHis;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.BuyClassActivity.BuyClassActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.Mvp.View.Activity.WalletActivity.WalletActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class OrderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rv_order_his)
    RecyclerView rvOrderHis;
    @BindView(R.id.swp_order_his)
    SwipeRefreshLayout swpOrderHis;


    private ArrayList<OrderHis> orderHis;
    private OrderHisAdapter orderHisAdapter;

    @Override
    public int initLayout() {
        return R.layout.activity_order;
    }



    @Override
    public void initData() {
        swpOrderHis.setProgressViewOffset (false,100,300);
        swpOrderHis.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

    }


    private void initAdapter(ArrayList<OrderHis> orderHis){
        swpOrderHis.setOnRefreshListener(this);
        orderHisAdapter = new OrderHisAdapter(OrderActivity.this,orderHis);
        rvOrderHis.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvOrderHis.setAdapter(orderHisAdapter);
        orderHisAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString("classId",orderHis.get(position).getOrderId());
//                IntentUtils.getInstence().intent(BuyClassActivity.this, SelectClassActivity.class,bundle);
            }
        });

        orderHisAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

    }
    @Override
    public void initView() {
        setToolbarTitle("我的订单");
        setToolbarSubTitle("");
        swpOrderHis.setRefreshing(true);
        getNewOrders();
    }

    private void getNewOrders(){

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getOrder(activitysharedPreferencesUtils.getParams("token","").toString())
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        MyToast.getInstance(OrderActivity.this).show(errorMsg, Toast.LENGTH_SHORT);

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        ArrayList<OrderHis> orderHis = (ArrayList<OrderHis>)baseResponse.getData();
                        if (swpOrderHis.isRefreshing()){
                            swpOrderHis.setRefreshing(false);
                        }
                        initAdapter(orderHis);
                    }
                });

    }

    @Override
    public void onRefresh() {

        getNewOrders();
    }
}
