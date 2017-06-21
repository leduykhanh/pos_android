package com.gg.pos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

public class ModelConfirmDialogFragment extends CommonAlertDialogFragment {

    public static final String SELECTED_INDEX = "bundle_selected_index";

    public static ModelConfirmDialogFragment newInstance(String tag, int model) {
        ModelConfirmDialogFragment dialogFragment = new ModelConfirmDialogFragment();

        Bundle args = new Bundle();
        args.putString(DIALOG_TAG, tag);
        args.putBoolean(CANCEL, false);
        args.putBoolean(CALLBACK, true);
        args.putString(LABEL_POSITIVE, "Yes");
        args.putString(LABEL_NEGATIVE, "No");
        args.putInt(CommonActivity.SELECTED_MODEL, model);

        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public @NonNull
    Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String modelTitle = ModelCapability.getModelTitle(getArguments().getInt(CommonActivity.SELECTED_MODEL));

        builder.setTitle("Confirm.");
        builder.setMessage("Is your printer " + modelTitle + " ?");

        builder.setPositiveButton(args.getString(LABEL_POSITIVE), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentForPassingData = new Intent();
                intentForPassingData.putExtra(LABEL_POSITIVE, LABEL_POSITIVE);
                intentForPassingData.putExtra(SELECTED_INDEX, getArguments().getInt(SELECTED_INDEX));
                intentForPassingData.putExtra(CommonActivity.SELECTED_MODEL, getArguments().getInt(CommonActivity.SELECTED_MODEL));

                callbackToTarget(args.getString(DIALOG_TAG), intentForPassingData);

                dialog.dismiss();
            }
        });

        builder.setNegativeButton(args.getString(LABEL_NEGATIVE), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentForPassingData = new Intent();
                intentForPassingData.putExtra(LABEL_NEGATIVE, LABEL_NEGATIVE);
                intentForPassingData.putExtra(SELECTED_INDEX, getArguments().getInt(SELECTED_INDEX));

                callbackToTarget(args.getString(DIALOG_TAG), intentForPassingData);

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
