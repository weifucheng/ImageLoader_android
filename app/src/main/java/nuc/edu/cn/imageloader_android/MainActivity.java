package nuc.edu.cn.imageloader_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRcList;
    private RecyclerAdapter mRcAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }
    private void initView(){
        mRcList= (RecyclerView) findViewById(R.id.rv);
        mLayoutManager=new LinearLayoutManager(this);
        mRcList.setHasFixedSize(true);
        mRcList.setItemAnimator(new DefaultItemAnimator());
        mRcList.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
    }

    /**
     *
     */
    private void initData(){
        mData.add("http://img3.3lian.com/2006/005/15/192.jpg");
        mData.add("http://pic1.win4000.com/mobile/d/5299cdf579a64.jpg");
        mData.add("http://pic22.nipic.com/20120625/5844292_103917659171_2.png");
        mData.add("http://pic28.nipic.com/20130409/4546121_000900982170_2.jpg");
        mData.add("http://pic28.nipic.com/20130409/4546121_000900982170_2.jpg");
        mData.add("http://pic28.nipic.com/20130409/4546121_000900982170_2.jpg");
        mData.add("http://img3.3lian.com/2006/005/15/185.jpg");
        mData.add("http://img3.3lian.com/2006/005/15/185.jpg");
        mData.add("http://img3.3lian.com/2006/005/15/185.jpg");
        mData.add("http://pic1.nipic.com/2008-08-11/200881111020799_2.jpg");
        mData.add("http://pic1.nipic.com/2008-08-11/200881111020799_2.jpg");
        mData.add("http://pic1.nipic.com/2008-08-11/200881111020799_2.jpg");
        mData.add("http://cdn.duitang.com/uploads/item/201201/28/20120128134054_CHEdy.jpg");
        mData.add("http://cdn.duitang.com/uploads/item/201201/28/20120128134054_CHEdy.jpg");
        mData.add("http://cdn.duitang.com/uploads/item/201201/28/20120128134054_CHEdy.jpg");
        mData.add("http://pic.dbw.cn/0/03/49/63/3496358_543660.jpg");
        mData.add("http://pic.dbw.cn/0/03/49/63/3496358_543660.jpg");
        mData.add("http://pic.dbw.cn/0/03/49/63/3496358_543660.jpg");
        mRcAdapter=new RecyclerAdapter(mData,this);
        mRcList.setAdapter(mRcAdapter);
    }
}
