package com.example.mob2041_vunlph12949.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob2041_vunlph12949.R;
import com.example.mob2041_vunlph12949.adapter.ThongKeAdapter;
import com.example.mob2041_vunlph12949.dao.SachDAO;
import com.example.mob2041_vunlph12949.model.Top;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class TopFragment extends Fragment {
    SachDAO dao;
    List<Top> list;
    ThongKeAdapter adapter;
    RecyclerView rvTK;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTK = view.findViewById(R.id.rvThongke);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvTK.setLayoutManager(layoutManager);

        dao = new SachDAO(getActivity());
        dao.open();

        list = new ArrayList<>();
        list = dao.getTop();
        adapter = new ThongKeAdapter(list);

        rvTK.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}