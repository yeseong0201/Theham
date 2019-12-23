package com.example.theham.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theham.Activities.MainActivity;
import com.example.theham.CardAdapter;
import com.example.theham.CardInfo;
import com.example.theham.CardList;
import com.example.theham.R;
import com.example.theham.controllers.SwipeController;
import com.example.theham.controllers.SwipeControllerActions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class RecyclerFragment extends Fragment {

    Bitmap bitmap;

    ImageView card;

    SwipeController swipeController = null;

    FloatingActionButton fab;

    RecyclerView recyclerView;


    String get_user_name;
    String get_user_tel;
    String get_user_email;
    String get_user_division;

    RecyclerView.LayoutManager layoutManager;

    MainActivity mainActivity = (MainActivity) getActivity();

    public ArrayList<CardList> cardList;

    CardAdapter cardAdapter;

    public RecyclerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CardData();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        fab = v.findViewById(R.id.fab);

        recyclerView = v.findViewById(R.id.recyclerview);

        card = v.findViewById(R.id.card);

        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(mainActivity);

        recyclerView.setLayoutManager(layoutManager);

        cardAdapter = new CardAdapter(cardList);

        recyclerView.setAdapter(cardAdapter);

        cardAdapter.notifyDataSetChanged();

        cardList.size();

        setupRecyclerView();

        fabClicked();


        return v;
    }

    public void fabClicked() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.card_dialog, null, false);
                builder.setView(view);

                final EditText set_user_name = view.findViewById(R.id.user_set_name);
                final EditText set_user_tel = view.findViewById(R.id.user_set_tel);
                final EditText set_user_email = view.findViewById(R.id.user_set_email);
                final EditText set_user_division = view.findViewById(R.id.user_set_division);

                set_user_division.setInputType(InputType.TYPE_CLASS_TEXT);
                set_user_tel.setInputType(InputType.TYPE_CLASS_NUMBER);
                set_user_tel.setInputType(InputType.TYPE_CLASS_NUMBER);
                set_user_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                final Button cancel_btn = view.findViewById(R.id.cancel_btn);
                Button add_btn = view.findViewById(R.id.add_btn);

                final AlertDialog dialog = builder.create();

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        get_user_name = set_user_name.getText().toString();
                        get_user_tel = set_user_tel.getText().toString();
                        get_user_email = set_user_email.getText().toString();
                        get_user_division = set_user_division.getText().toString();

                        if (get_user_name.length() <= 0 || get_user_division.length() <= 0 || get_user_email.length() <= 0 || get_user_tel.length() <= 0) {
                            Toast.makeText(getContext(), "필수 작성 목록입니다.", Toast.LENGTH_SHORT).show();
                        } else {

                            CardList list = new CardList(R.drawable.ic_person_black_24dp, R.drawable.card_image, get_user_name, get_user_division, get_user_email, get_user_tel);
                            cardList.add(list);

//                            savePreferences();

                            cardAdapter.notifyItemInserted(cardList.size());
                            cardAdapter.notifyDataSetChanged();

                            Intent intent = new Intent(getActivity().getApplicationContext(), CardInfo.class);
                            intent.putExtra("info_name", get_user_name);
                            intent.putExtra("info_tel", get_user_tel);
                            intent.putExtra("info_email", get_user_email);
                            intent.putExtra("info_division", get_user_division);

                            startActivity(intent);

                            dialog.dismiss();

                        }
                    }
                });
                dialog.show();
                getCardImage();

            }
        });
    }

    // 값 저장하기
    private void savePreferences() {

        SharedPreferences prefN = mainActivity.getSharedPreferences("name", MODE_PRIVATE);
        SharedPreferences prefD = mainActivity.getSharedPreferences("division", MODE_PRIVATE);
        SharedPreferences prefE = mainActivity.getSharedPreferences("email", MODE_PRIVATE);
        SharedPreferences prefT = mainActivity.getSharedPreferences("tel", MODE_PRIVATE);
        SharedPreferences.Editor editor1, editor2, editor3, editor4;
        editor1 = prefN.edit();
        editor2 = prefD.edit();
        editor3 = prefE.edit();
        editor4 = prefT.edit();
        editor1.putString("nameString", get_user_name);
        editor2.putString("divisionString", get_user_division);
        editor3.putString("emailString", get_user_email);
        editor4.putString("telString", get_user_tel);
        editor1.commit();
        editor2.commit();
        editor3.commit();
        editor4.commit();
    }


    public void getCardImage() {

        // CardInfo 에서 bitmap 받기
        if (bitmap != null) {
            CardInfo.getScannedImage();
            card.setImageBitmap(bitmap);
        }

    }


    public void CardData() {
        cardList = new ArrayList<>();
    }

    private void setupRecyclerView() {

        swipeController = new SwipeController(new SwipeControllerActions() {

            @Override
            public void onRightClicked(int position) {
//                cardList.remove(position);
//                cardAdapter.notifyItemRemoved(position);
//                cardAdapter.notifyItemRangeChanged(position, cardAdapter.getItemCount());

                Intent intent = new Intent(getActivity(), CardInfo.class);

                String putName = cardList.get(position).getItem_name();
                String putDivision = cardList.get(position).getItem_division();
                String putEmail = cardList.get(position).getItem_Email();
                String putTel = cardList.get(position).getItem_tel();

                intent.putExtra("putName", putName);
                intent.putExtra("putDivision", putDivision);
                intent.putExtra("putEmail", putEmail);
                intent.putExtra("putTel", putTel);
                startActivity(intent);
            }

            @Override
            public void onLeftClicked(int position) {

                Log.d("position left", String.valueOf(position));
            }

        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}