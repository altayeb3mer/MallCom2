package com.example.mallcom.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.mallcom.R;

import java.util.ArrayList;

public class SingleChoiceDialogFragment extends DialogFragment {
    ArrayList<String> list;

    public SingleChoiceDialogFragment(ArrayList<String> list) {
        this.list = list;
    }

    int position = 0; //default selected position

    public interface SingleChoiceListener {
        void onPositiveButtonClicked(ArrayList<String> list, int position);

        void onNegativeButtonClicked();
    }

    SingleChoiceListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SingleChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + " SingleChoiceListener must implemented");
        }
    }


//    public void setDialogTitle(CharSequence title){
//        builder.setTitle(title);
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

//        final String[] list = getActivity().getResources().getStringArray(R.array.choice_items);

        final String [] listItems = new String[]{};
        for (int i = 0; i < list.size(); i++) {
            listItems[i] = list.get(i);
        }




        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice, list);
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                position = i;
                try {
                    builder.setTitle(getColorFromCode(list.get(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setTitle("قم باختيار لون")
//                .setSingleChoiceItems(listItems, position, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        position = i;
//                        try {
//                            builder.setTitle(getColorFromCode(list.get(i)));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                })
                .setPositiveButton("اختيار", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveButtonClicked(list, position);
                    }
                })
                .setNegativeButton("رجوع", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onNegativeButtonClicked();
                    }
                });

        return builder.create();
    }
    private Spanned getColorFromCode(String colorCode){
        //∎
        return Html.fromHtml("<span style='color:"+colorCode+"'>  ∎  </span>");
//        return Html.fromHtml("<span style='color:#F59C14'>او اختر احد </span> الارقام المخزنة").toString();
    }
}
