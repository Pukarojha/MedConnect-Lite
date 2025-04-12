package com.first.demo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.DbHelperClasses.MedicationTrackingDbHelper;
import com.first.demo.models.MedicationTracking;
import com.first.demo.patient.ViewPatientsFragment;
import com.first.demo.utilities.DataValidator;
import com.first.demo.utilities.FragmentHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddMedicationFragment extends Fragment implements FragmentHelper {

    EditText medicationInput;
    EditText medicationDosage;
    EditText medicationDuration;
    Button addMedicationBtn;
    Button addMoreMedicationBtn;
    int consultationId;

    public AddMedicationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_medication, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAndAssignLayoutElements(view);
        setLayoutListeners();
        initializeRequiredData();

        // 🔄 TextWatcher to fetch medication data as user types
        medicationInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3) {
                    fetchMedicationDetails(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void findAndAssignLayoutElements(View view) {
        medicationInput = view.findViewById(R.id.medicationTitleInput);
        medicationDosage = view.findViewById(R.id.medicationDosageInput);
        medicationDuration = view.findViewById(R.id.medicationDurationInput);
        addMedicationBtn = view.findViewById(R.id.addMedicationBtn);
        addMoreMedicationBtn = view.findViewById(R.id.addMoreMedicationBtn);
    }

    public void processData() {
        medicationInput.setText(medicationInput.getText().toString().trim());
        medicationDosage.setText(medicationDosage.getText().toString().trim());
        medicationDuration.setText(medicationDuration.getText().toString().trim());
    }

    public boolean saveMedication(View view) {
        processData();
        String medTitle = medicationInput.getText().toString();
        String medDosage = medicationDosage.getText().toString();
        String medDuration = medicationDuration.getText().toString();

        if (DataValidator.validateText(getContext(), medTitle) &&
                DataValidator.validateText(getContext(), medDosage) &&
                DataValidator.validateText(getContext(), medDuration)) {
            DatabaseHelper db = DatabaseHelper.getInstance(getContext());
            MedicationTrackingDbHelper.add(db, new MedicationTracking(medTitle, medDosage, medDuration, consultationId));
            toPatients();
            return true;
        }
        return false;
    }

    public void toPatients() {
        FragmentManager fragmentManager = getParentFragmentManager();
        ViewPatientsFragment patientsFragment = new ViewPatientsFragment();
        FragmentTransaction fragmentTrans = fragmentManager.beginTransaction();
        fragmentTrans.replace(R.id.frameLayout, patientsFragment);
        fragmentTrans.commit();
    }

    public void addMoreMedication(View view) {
        if (saveMedication(view)) {
            FragmentManager fragmentManager = getParentFragmentManager();
            AddMedicationFragment addMedication = new AddMedicationFragment();
            Bundle b = new Bundle();
            b.putLong("consultation_id", consultationId);
            addMedication.setArguments(b);
            FragmentTransaction fragmentTrans = fragmentManager.beginTransaction();
            fragmentTrans.replace(R.id.frameLayout, addMedication);
            fragmentTrans.addToBackStack("name");
            fragmentTrans.setReorderingAllowed(true);
            fragmentTrans.commit();
        }
    }

    @Override
    public void setLayoutListeners() {
        addMedicationBtn.setOnClickListener(this::saveMedication);
        addMoreMedicationBtn.setOnClickListener(this::addMoreMedication);
    }

    @Override
    public void initializeRequiredData() {
        assert getArguments() != null;
        consultationId = (int) getArguments().getLong("consultation_id");
    }

    // 🌐 API CALL METHOD
    private void fetchMedicationDetails(String medName) {
        String url = "https://api.fda.gov/drug/label.json?search=openfda.brand_name:" + medName + "&limit=1";

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        if (results.length() > 0) {
                            JSONObject firstResult = results.getJSONObject(0);
                            JSONArray indicationsArray = firstResult.optJSONArray("indications_and_usage");
                            JSONArray dosageArray = firstResult.optJSONArray("dosage_and_administration");

                            if (indicationsArray != null && indicationsArray.length() > 0) {
                                medicationDuration.setText(indicationsArray.getString(0));
                            }

                            if (dosageArray != null && dosageArray.length() > 0) {
                                medicationDosage.setText(dosageArray.getString(0));
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("MedFetch", "Parsing error: " + e.getMessage());
                    }
                },
                error -> Log.e("MedFetch", "API call failed: " + error.getMessage())
        );

        queue.add(jsonObjectRequest);
    }
}
