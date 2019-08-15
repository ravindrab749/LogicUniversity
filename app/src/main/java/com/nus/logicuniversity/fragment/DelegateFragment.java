package com.nus.logicuniversity.fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.nus.logicuniversity.R;
import com.nus.logicuniversity.adapter.RepPopupAdapter;
import com.nus.logicuniversity.model.Delegate;
import com.nus.logicuniversity.model.Employee;
import com.nus.logicuniversity.model.RepresentativesResponse;
import com.nus.logicuniversity.retrofit.Api;
import com.nus.logicuniversity.retrofit.RetrofitClient;
import com.nus.logicuniversity.utility.Util;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DelegateFragment extends Fragment implements View.OnClickListener {

    private RepresentativesResponse res;
    private EditText empEt;
    private Calendar fromCal;
    private EditText etFromDate;
    private EditText etToDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delegate, container, false);
        initView(view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(View view) {
        updateToolbarTitle();
        getEmployees();
        etFromDate = view.findViewById(R.id.et_from_date);
        etFromDate.setKeyListener(null);
        etToDate = view.findViewById(R.id.et_to_date);
        etToDate.setKeyListener(null);
        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        fromCal = Calendar.getInstance();
                        fromCal.set(i, i1, i2);
                        if(fromCal.before(calendar)) {
                            Util.showToast(getActivity(), "Select valid date");
                            return;
                        }
                        etFromDate.setText(sdf.format(fromCal.getTime()));
                        etToDate.setText(null);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromCal == null) {
                    Util.showToast(getActivity(), "From date is mandatory");
                    return;
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(i, i1, i2);
                        if(cal.before(fromCal)) {
                            Util.showToast(getActivity(), "Select valid date");
                            return;
                        }
                        etToDate.setText(sdf.format(cal.getTime()));
                    }
                }, fromCal.get(Calendar.YEAR), fromCal.get(Calendar.MONTH), fromCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        empEt = view.findViewById(R.id.et_emp_name);
        empEt.setKeyListener(null);

        empEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    return false;
                } else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDialog();
                    return true;
                }
                return false;
            }
        });

        Button delegateBtn = view.findViewById(R.id.btn_delegate);
        delegateBtn.setOnClickListener(this);
    }

    private void updateToolbarTitle() {
        Util.updateTitle(getString(R.string.title_fragment_delegate), Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onClick(View view) {

        String fromDate = etFromDate.getText().toString();
        if(fromDate.isEmpty()) {
            Util.showToast(getActivity(), "From date should not be empty");
            return;
        }

        String toDate = etToDate.getText().toString();
        if(toDate.isEmpty()) {
            Util.showToast(getActivity(), "To date should not be empty");
            return;
        }

        String emp = empEt.getText().toString();
        if(emp.isEmpty()) {
            Util.showToast(getActivity(), "Employee should not be empty");
            return;
        }

        Delegate delegate = new Delegate();
        try {
            delegate.setFromDate(new Timestamp(Objects.requireNonNull(sdf.parse(fromDate)).getTime()));
            delegate.setToDate(new Timestamp(Objects.requireNonNull(sdf.parse(toDate)).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Employee employee = new Employee();
        employee.setEmpId((long)empEt.getTag());
        delegate.setEmployee(employee);

        delegateAuth(delegate);

    }

    private void delegateAuth(Delegate delegate) {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());

        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();
        api.authDelegate(header, delegate).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                String msg = response.body();
                if("Success".equalsIgnoreCase(msg)) {
                    Util.showToast(getActivity(), "Success");
                    Util.showProgressBar(getActivity(), false);
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
            }
        });
    }

    private void getEmployees() {

        String header = Util.getHeaderValueFromSharedPreferences(getActivity());

        Util.showProgressBar(getActivity(), true);

        Api api = RetrofitClient.getInstance().getApi();
        api.getAllEmployees(header).enqueue(new Callback<RepresentativesResponse>() {
            @Override
            public void onResponse(@NotNull Call<RepresentativesResponse> call, @NotNull Response<RepresentativesResponse> response) {
                res = response.body();
                Util.showProgressBar(getActivity(), false);
            }

            @Override
            public void onFailure(@NotNull Call<RepresentativesResponse> call, @NotNull Throwable t) {
                Util.showProgressBar(getActivity(), false);
            }
        });
    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle(getString(R.string.title_select_emp));
        final ArrayList<Employee> list = res.getRepList();

        RepPopupAdapter adapter = new RepPopupAdapter(getActivity(), R.layout.popup_rep, list);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                empEt.setTag(list.get(i).getEmpId());
                empEt.setText(list.get(i).getEmpName());
                dialogInterface.dismiss();
            }
        });

        builder.create().show();

    }
}
