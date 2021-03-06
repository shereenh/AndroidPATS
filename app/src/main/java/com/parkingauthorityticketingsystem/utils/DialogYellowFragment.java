package com.parkingauthorityticketingsystem.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parkingauthorityticketingsystem.R;
import com.parkingauthorityticketingsystem.database.ShiftlogDBHelper;
import com.parkingauthorityticketingsystem.entity.Shiftlog;

public class DialogYellowFragment extends DialogFragment {
    ShiftlogDBHelper helper;
    private DialogYellowFragment.DialogFragmentDBEdit dialogFragmentDBEdit;

    public DialogYellowFragment.DialogFragmentDBEdit getDialogFragmentDBEdit() {
        return dialogFragmentDBEdit;
    }

    public void setDialogFragmentDBEdit(DialogYellowFragment.DialogFragmentDBEdit dialogFragmentDBEdit) {
        this.dialogFragmentDBEdit = dialogFragmentDBEdit;
    }

    public interface DialogFragmentDBEdit{//Define an interface, use it to get DBHelper.
        //The Fragment should implement this interface when it use DialogYellowFragment
        void remove(int id);
        void ticket(Shiftlog sl);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_yellow,container);
        return view;
    }
    public static DialogYellowFragment newInstance(Shiftlog sl, double timeMilli){

        DialogYellowFragment fragment = new DialogYellowFragment();
        Bundle bundle = new Bundle();
        bundle.putString("platenumber", sl.getPlateNumber());
        bundle.putString("state", sl.getState());
        bundle.putDouble("time", timeMilli);
        bundle.putString("fo", sl.getFirstObserve());
        bundle.putInt("id", sl.getId());
        fragment.setArguments(bundle);//Send parageter to DialogFragment

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        helper = new ShiftlogDBHelper(getContext());
        View customView = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_yellow, null);

        TextView title = (TextView) customView.findViewById(R.id.dialog_yellow_title);
        TextView time = (TextView) customView.findViewById(R.id.dialog_yellow_time);
        title.setText(getArguments().getString("platenumber") + "/" +
                getArguments().getString("state") );
        time.setText("FO:" +  getArguments().getString("fo")+ ", TimeLeft:" +(int)getArguments().getDouble("time")
        );// Set TextView
        Button button_remove = (Button) customView.findViewById(R.id.dialog_yellow_remove_btn);

        button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  helper.deleteRecord(getArguments().getInt("id"));

                dialogFragmentDBEdit.remove(getArguments().getInt("id")); // use remove() rewrite in MapViewFragment
                dismiss();


                //getActivity().getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new CreateShiftlogFragment())
                //       .commit();


            }
        });
        return new AlertDialog.Builder(getActivity()).setView(customView)
                .create();
    }
}
