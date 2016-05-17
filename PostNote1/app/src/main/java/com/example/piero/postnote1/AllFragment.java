package com.example.piero.postnote1;


import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class AllFragment extends Fragment {

    private static ArrayList<PostItem> allList = new ArrayList<PostItem>();
    private static RecyclerView recyclerView;
    public static PostAdapter mAdapter;
    public String TAGCICLO = "CICLODIVITA";

    public AllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAGCICLO, "On Attach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAGCICLO, "On Create");
    }

    private static final String TAG = "RecyclerViewFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all, container, false);
        rootView.setTag(TAG);

        Log.d(TAGCICLO, "onCreateView");
        if (allList.isEmpty()){
            allList = getArguments().getParcelableArrayList("postList");
        } else {

        }


        Log.d("Hey, listen", "" + allList);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        mAdapter = new PostAdapter(allList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        //mLayoutManager.scrollToPosition(1);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(25));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PostItem item = allList.get(position);
                Intent i = new Intent(getActivity(), Dettaglio.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MyPost", item);
                bundle.putInt("ID", position);
                startActivity(i.putExtras(bundle));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAGCICLO, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAGCICLO, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAGCICLO, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAGCICLO, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAGCICLO, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAGCICLO, "onDetach");
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private AllFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final AllFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static void UpdateList() {



        recyclerView.scrollToPosition(allList.size()-1);
        mAdapter.notifyDataSetChanged();
    }

}
