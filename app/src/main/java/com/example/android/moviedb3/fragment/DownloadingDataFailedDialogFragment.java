package com.example.android.moviedb3.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.eventHandler.OnDataChooseListener;

/**
 * Created by nugroho on 21/09/17.
 */

public class DownloadingDataFailedDialogFragment extends DialogFragment
{
    OnDataChooseListener<Boolean> onAgreeToTryAgainListener;

    public void setOnAgreeToTryAgainListener(OnDataChooseListener<Boolean> onAgreeToTryAgainListener)
    {
        this.onAgreeToTryAgainListener = onAgreeToTryAgainListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.downloading_fail)
                .setMessage(R.string.downloading_fail_dialog)
                .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        onAgreeToTryAgainListener.OnDataChoose(true);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                onAgreeToTryAgainListener.OnDataChoose(false);
            }
        });

        return builder.create();
    }
}
