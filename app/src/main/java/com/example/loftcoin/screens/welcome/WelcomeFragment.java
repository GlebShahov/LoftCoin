package com.example.loftcoin.screens.welcome;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loftcoin.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


public class WelcomeFragment extends Fragment {

    public static final String KEY_PAGE = "page";

    public static WelcomeFragment newInstance(WelcomePage page) {

        Bundle args = new Bundle();

        WelcomeFragment fragment = new WelcomeFragment();
        args.putParcelable(KEY_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.subtitle)
    TextView subTitle;


    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle args = getArguments();


        WelcomePage page = null;
        if (args != null) {
            page = args.getParcelable(KEY_PAGE);
        }
        if (page != null) {
            icon.setImageResource(page.getIcon());
            title.setText(page.getTitle());
            subTitle.setText(page.getSubTitle());
        }
    }
}
