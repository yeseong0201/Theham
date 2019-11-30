package com.example.theham;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ItemViewHolder> {

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    private int visible_count = 1;

    private ArrayList<CardList> listData;


    public CardAdapter(ArrayList<CardList> cardList) {
        this.listData = cardList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list, parent, false);
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

            // changeVisibility();


            if (isItemSelected(position)) {
                holder.card.setVisibility(View.VISIBLE);
            }
        }

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


//    private void changeVisibility(final boolean isExpanded) {
//        // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
//        int dpValue = 150;
//        float d = context.getResources().getDisplayMetrics().density;
//        int height = (int) (dpValue * d);
//
//        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
//        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
//        // Animation이 실행되는 시간, n/1000초
//        va.setDuration(600);
//        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // value는 height 값
//                int value = (int) animation.getAnimatedValue();
//                // imageView의 높이 변경
//                imageView2.getLayoutParams().height = value;
//                imageView2.requestLayout();
//                // imageView가 실제로 사라지게하는 부분
//                imageView2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//            }
//        });
//        // Animation start
//        va.start();
//    }
}