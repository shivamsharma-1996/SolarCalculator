package com.shivam.solarcalculator.ui.dashboard.bookmarks;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shivam.solarcalculator.R;
import com.shivam.solarcalculator.data.db.DatabaseHelper;
import com.shivam.solarcalculator.data.models.Address;

import java.util.List;

public class BookmarksDialog extends DialogFragment implements View.OnClickListener {

    //shows the saved address list
    RecyclerView rvBookmarkList;
    BookmarksAdapter bookmarksAdapter;
    TextView tvCancel;
    List<Address> addressList;

    DatabaseHelper databaseHelper;

    public interface OnItemSelectListener{
        void onItemSelected(Address selectedAddress);
    }

    private OnItemSelectListener onItemSelectListener;

    public BookmarksDialog() {
        databaseHelper = DatabaseHelper.getInstance(getActivity());
    }

    public void setOnItemClickListener(OnItemSelectListener onItemSelectListener){
        this.onItemSelectListener = onItemSelectListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_bookmarks, null);
        tvCancel  = dialogView.findViewById(R.id.tv_cancel);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        addressList = databaseHelper.getAllAddresses();
        rvBookmarkList = dialogView.findViewById(R.id.rv_bookmark_list);
        bookmarksAdapter = new BookmarksAdapter(addressList, BookmarksDialog.this);
        rvBookmarkList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvBookmarkList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvBookmarkList.setAdapter(bookmarksAdapter);
        return dialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cl_item_bookmark:
                BookmarksDialog.this.dismiss();
                onItemSelectListener.onItemSelected(addressList.get(((Integer) v.getTag()).intValue()));
                break;
        }
    }

}
