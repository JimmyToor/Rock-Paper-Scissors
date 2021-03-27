package com.example.jimmy.rockpaperscissors;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class NewWeaponDialogFragment extends DialogFragment {
    private static final int PICK_IMG_FILE_1 = 1;
    private static final int PICK_IMG_FILE_2 = 2;
    private Uri img1 = null;
    private Uri img2 = null;
    private int ordinal = 0;

    @Override @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        WeaponViewModel weaponsModel = new ViewModelProvider(requireActivity()).get(WeaponViewModel.class);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_new_weapon_dialog, null);
        final EditText et1 = view.findViewById(R.id.edit_weapon1);
        final EditText et2 = view.findViewById(R.id.edit_weapon2);
        final ImageButton imgBtn1 = view.findViewById(R.id.img_weapon1);
        final ImageButton imgBtn2 = view.findViewById(R.id.img_weapon2);

        ordinal = getArguments().getInt("ordinal");
        imgBtn1.setOnClickListener(v -> openFile(PICK_IMG_FILE_1));
        imgBtn2.setOnClickListener(v -> openFile(PICK_IMG_FILE_2));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view).setMessage(R.string.dialog_add_weapons)
                .setPositiveButton(R.string.button_save, (dialog, id) -> {
                    if (TextUtils.isEmpty(et1.getText()) || TextUtils.isEmpty(et1.getText()))
                        Toast.makeText(getContext(),R.string.weapon_empty, Toast.LENGTH_SHORT).show();
                    else
                    {
                        // use default image if one was not provided
                        if (img1 != null)
                            weaponsModel.insert(new Weapon(et1.getText().toString(), img1, ordinal+1));
                        else
                            weaponsModel.insert(new Weapon(et1.getText().toString(), ordinal+1));
                        if (img2 != null)
                            weaponsModel.insert(new Weapon(et2.getText().toString(), img2, ordinal+2));
                        else
                            weaponsModel.insert(new Weapon(et2.getText().toString(),ordinal+2));
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

        return builder.create();
    }

    private void openFile(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_FILE_1 && resultCode == RESULT_OK)
            img1 = data.getData();
        else if (requestCode == PICK_IMG_FILE_2 && resultCode == RESULT_OK)
            img2 = data.getData();
    }

}