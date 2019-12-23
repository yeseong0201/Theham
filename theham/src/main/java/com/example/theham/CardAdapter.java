package com.example.theham;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ItemViewHolder> {

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    private int visible_count = 1;

    private ArrayList<CardList> listData;

    private Context context;


    public CardAdapter(ArrayList<CardList> cardList) {
        this.listData = cardList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list, parent, false);
        context = parent.getContext();
        getPreferences();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (holder != null) {

            holder.card.setImageResource(listData.get(position).getCard());
            holder.item_profile.setImageResource(listData.get(position).getDrawableId());
            holder.item_name.setText(listData.get(position).getItem_name());
            holder.item_division.setText(listData.get(position).getItem_division());
            holder.item_email.setText(listData.get(position).getItem_Email());
            holder.item_tel.setText(listData.get(position).getItem_tel());

            SharedPreferences prefName = context.getSharedPreferences("name", MODE_PRIVATE);
            SharedPreferences prefDivision = context.getSharedPreferences("division", MODE_PRIVATE);
            SharedPreferences prefEmail = context.getSharedPreferences("email", MODE_PRIVATE);
            SharedPreferences prefTel = context.getSharedPreferences("tel", MODE_PRIVATE);

            String name = prefName.getString("nameString", "");
            String division = prefDivision.getString("divisionString", "");
            String email = prefEmail.getString("emailString", "");
            String tel = prefTel.getString("telString", "");

            if (isItemSelected(position)) {
                holder.card.setVisibility(View.VISIBLE);
            }
        }

    }

    // 값 불러오기
    private void getPreferences() {
        SharedPreferences prefName = context.getSharedPreferences("name", MODE_PRIVATE);
        SharedPreferences prefDivision = context.getSharedPreferences("division", MODE_PRIVATE);
        SharedPreferences prefEmail = context.getSharedPreferences("email", MODE_PRIVATE);
        SharedPreferences prefTel = context.getSharedPreferences("tel", MODE_PRIVATE);

        String name = prefName.getString("nameString", "");
        String division = prefDivision.getString("divisionString", "");
        String email = prefEmail.getString("emailString", "");
        String tel = prefTel.getString("telString", "");


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_profile, card;
        private TextView item_name, item_division, item_email, item_tel;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            item_profile = itemView.findViewById(R.id.item_profile);
            item_name = itemView.findViewById(R.id.item_name);
            item_division = itemView.findViewById(R.id.item_division);
            item_email = itemView.findViewById(R.id.item_email);
            item_tel = itemView.findViewById(R.id.item_tel);

            if (listData.size() == 0) {

            }

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && visible_count % 2 != 0) {
                        listData.get(pos);
                        card.setVisibility(View.GONE);
                        visible_count++;

                    } else if (visible_count % 2 == 0) {
                        card.setVisibility(View.VISIBLE);
                        visible_count++;

                    }
                }
            });
        }
    }


    private boolean isItemSelected(int position) {
        return selectedItems.get(position, false);
    }

    public void clearSelectedItem() {
        int position;

        for (int i = 0; i < selectedItems.size(); i++) {
            position = selectedItems.keyAt(i);
            selectedItems.put(position, false);
            notifyItemChanged(position);
        }

        selectedItems.clear();
    }
}