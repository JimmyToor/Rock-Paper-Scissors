package com.example.jimmy.rockpaperscissors;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class WeaponViewAdapter extends ListAdapter<Weapon, WeaponViewAdapter.WeaponViewHolder> {
    WeaponViewModel weaponsModel;

    public class WeaponViewHolder extends RecyclerView.ViewHolder {
        CardView weaponCard;
        ImageView weaponImage;
        TextView weaponName;

        public WeaponViewHolder(@NonNull View itemView) {
            super(itemView);
            weaponCard = itemView.findViewById(R.id.weaponCard);
            weaponName = itemView.findViewById(R.id.weaponName);
            weaponImage = itemView.findViewById(R.id.weaponImage);
        }
    }

    public WeaponViewAdapter(WeaponViewModel weaponsModel) {
        super(DIFF_CALLBACK);
        this.weaponsModel = weaponsModel;
    }

    private static final DiffUtil.ItemCallback<Weapon> DIFF_CALLBACK = new DiffUtil.ItemCallback<Weapon>() {
        @Override
        public boolean areItemsTheSame(@NonNull Weapon oldItem, @NonNull Weapon newItem) {
            return oldItem.getWeaponName().equals(newItem.getWeaponName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Weapon oldItem, @NonNull Weapon newItem) {
            return (oldItem.getWeaponName().equals(newItem.getWeaponName()) && oldItem.getOrdinal() == newItem.getOrdinal());
        }
    };

    @NonNull
    @Override
    public WeaponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weapon_card, parent, false);
        return new WeaponViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeaponViewHolder holder, int position) {
        Weapon currWeapon = getItem(position);
        holder.weaponName.setText(currWeapon.getWeaponName());
        holder.weaponImage.setImageURI(currWeapon.getImgUri());
    }

    @Override
    public void submitList(final List<Weapon> list) {
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    public Weapon getWeaponAtIndex(int index)
    {
        return super.getItem(index);
    }

}
