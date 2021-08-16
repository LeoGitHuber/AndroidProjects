package com.example.schooltimetable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class PageAdapter extends FragmentStateAdapter {
    List<String> dayList = Arrays.asList("Mon.", "Tue.", "Wed.", "Thur.", "Fri.", "Sat.", "Sun.");
    public PageAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("day", dayList.get(position) + "%'");
        NotEmptyFragment notEmptyFragment = new NotEmptyFragment();
        notEmptyFragment.setArguments(bundle);
        return notEmptyFragment;
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}