package zw.co.mimosa.mymimosa.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.ui.hr.HrDashboardActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class FormsFragment extends Fragment  implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static FormsFragment newInstance(int index) {
        FormsFragment fragment = new FormsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
//        int index = 1;
//        if (getArguments() != null) {
//            index = getArguments().getInt(ARG_SECTION_NUMBER);
//        }
//        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        String lastName = getArguments().getString("LASTNAME");
        View root = inflater.inflate(R.layout.fragment_forms, container, false);
//        final TextView textView = root.findViewById(R.id.section_label);
        final MaterialCardView materialCardViewHrMenu = root.findViewById(R.id.hr_menu);
        final TextView loggedInAs = root.findViewById(R.id.tv_logged_in_as);
        loggedInAs.setText(lastName);
        materialCardViewHrMenu.setOnClickListener(this);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()){
            case R.id.hr_menu:
                intent = new Intent(getContext(), HrDashboardActivity.class);
                startActivity(intent);
        }
    }
}