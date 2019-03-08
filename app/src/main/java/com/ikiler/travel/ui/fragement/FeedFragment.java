package com.ikiler.travel.ui.fragement;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikiler.travel.APIconfig;
import com.ikiler.travel.Adapter.FeedRecyclerViewAdapter;
import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.FeedLiveDataModel;
import com.ikiler.travel.Model.RssItem;
import com.ikiler.travel.R;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class FeedFragment extends Fragment {

    private FeedRecyclerViewAdapter adapter;

    private static FeedFragment feedFragment;

    public static FeedFragment instance(){
        if (null == feedFragment)
            feedFragment = new FeedFragment();
        return feedFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new FeedRecyclerViewAdapter();
            recyclerView.setAdapter(adapter);
            adapter.setOnRecyclerItemClickLitener(new BaseRecyleAdapter.onRecyclerItemClickLitener() {
                @Override
                public void onRecyclerItemClick(Object object, int position) {
                    FeedLiveDataModel.instance().getMutableLiveData().setValue((RssItem) object);
                    FeedContentFragment feedContentFragment = new FeedContentFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.content,feedContentFragment);
                    transaction.commit();
                }
            });

        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        APIconfig.getFeed();
        FeedLiveDataModel.instance().getMutableLiveDatas().observe(this, new Observer<List<RssItem>>() {
            @Override
            public void onChanged(List<RssItem> rssItems) {
                adapter.setList(rssItems);
            }
        });
    }
}
