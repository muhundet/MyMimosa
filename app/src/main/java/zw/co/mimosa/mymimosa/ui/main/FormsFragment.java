package zw.co.mimosa.mymimosa.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.ui.finance.FinanceDashboardActivity;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationMine;
import zw.co.mimosa.mymimosa.ui.harare_office.petty_cash_authorisation_harare_office.HarareDashboardActivity;
import zw.co.mimosa.mymimosa.ui.hr.HrDashboardActivity;
import zw.co.mimosa.mymimosa.ui.hr.HrMainDashboardActivity;
import zw.co.mimosa.mymimosa.ui.medical_services.MedicalServicesDashboardActivity;
import zw.co.mimosa.mymimosa.ui.transport.TransportDashboardActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class FormsFragment extends Fragment  {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    MaterialCardView materialCardViewMedicalServicesMenu, materialCardViewHrMenu, materialCardViewHarareOfficeMenu,
    materialCardViewSheMenu, materialCardViewIctMenu, materialCardViewTransportMenu, materialCardViewFinanceMenu, materialCardViewSupplyChainMenu;

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
        getActivity().setTitle("DASHBOARD");
//        final TextView textView = root.findViewById(R.id.section_label);
//        final MaterialCardView materialCardViewHrMenu = root.findViewById(R.id.hr_menu);
//        final TextView loggedInAs = root.findViewById(R.id.tv_logged_in_as);
        materialCardViewHrMenu = root.findViewById(R.id.cv_hr_menu);
        materialCardViewMedicalServicesMenu = root.findViewById(R.id.cv_medical_services_menu);
        materialCardViewHarareOfficeMenu = root.findViewById(R.id.cv_harare_office_menu);
        materialCardViewSheMenu = root.findViewById(R.id.cv_she_menu);
        materialCardViewIctMenu = root.findViewById(R.id.cv_ict_menu);
        materialCardViewTransportMenu = root.findViewById(R.id.cv_transport_menu);
        materialCardViewFinanceMenu = root.findViewById(R.id.cv_finance_menu);
        materialCardViewSupplyChainMenu = root.findViewById(R.id.cv_supply_menu);
//        loggedInAs.setText(lastName);
//        materialCardViewHrMenu.setOnClickListener(this);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        materialCardViewHrMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HrMainDashboardActivity.class);
                startActivity(intent);
            }
        });

        materialCardViewMedicalServicesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MedicalServicesDashboardActivity.class);
                startActivity(intent);
            }
        });

        materialCardViewHarareOfficeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HarareDashboardActivity.class);
                startActivity(intent);
            }
        });

        materialCardViewFinanceMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FinanceDashboardActivity.class);
                startActivity(intent);
            }
        });

        materialCardViewTransportMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TransportDashboardActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

//    @Override
//    public void onClick(View v) {
//        Intent intent;
//
//        switch(v.getId()){
//            case R.id.hr_menu:
//
//
//        }
//    }
}