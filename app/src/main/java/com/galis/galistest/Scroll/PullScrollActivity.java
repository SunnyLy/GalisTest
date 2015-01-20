package com.galis.galistest.Scroll;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import static com.galis.galistest.Scroll.QuickReturnListViewOnScrollListener.QuickReturnType.*;

/**
 * Author: galis
 * Date: 2014/12/23 18:07
 * Version: 2.0
 * Describe:
 */


public class PullScrollActivity extends Activity {


    private View mHeader;
    private View mFooter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeader = new View(this);
        mHeader.setBackgroundColor(Color.YELLOW);
        mHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));

        mFooter= new View(this);
        mFooter.setBackgroundColor(Color.GRAY);
        mFooter.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,150));

        mListView = new ListView(this);
        mListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf",
                "sldjfsljf"
        }));

        mListView.addHeaderView(mHeader);
        mListView.addFooterView(mFooter);
        QuickReturnListViewOnScrollListener scrollListener = new QuickReturnListViewOnScrollListener(TWITTER,mHeader,-150,mFooter,150);
        scrollListener.setCanSlideInIdleScrollState(true);
        mListView.setOnScrollListener(scrollListener);
        setContentView(mListView);
    }
}
