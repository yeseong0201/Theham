package com.example.theham.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theham.Activities.MainActivity;
import com.example.theham.CardAdapter;
import com.example.theham.CardList;
import com.example.theham.R;
import com.example.theham.controllers.SwipeController;
import com.example.theham.controllers.SwipeControllerActions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class RecyclerFragment extends Fragment {

    Bitmap mBitmap;
    Bitmap mResult;

    ImageView null_image;


    SwipeController swipeController = null;

    FloatingActionButton fab;

    RecyclerView recyclerView;


    RecyclerView.LayoutManager layoutManager;

    Context context = getContext();


    MainActivity mainActivity = (MainActivity) getActivity();

    public ArrayList<CardList> cardList;

    CardAdapter cardAdapter;

    Menu menu;

    public RecyclerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CardData();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        fab = v.findViewById(R.id.fab);

        null_image = v.findViewById(R.id.null_image);

        recyclerView = v.findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(mainActivity);

        recyclerView.setLayoutManager(layoutManager);

        cardAdapter = new CardAdapter(cardList);

        recyclerView.setAdapter(cardAdapter);

        cardAdapter.notifyDataSetChanged();

        cardList.size();

        setupRecyclerView();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.card_dialog, null, false);
                builder.setView(view);

             //   ImageButton set_user_card = view.findViewById(R.id.select_scan_mode);

//                set_user_card.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(getContext().getApplicationContext(), ImageScan.class));
//                    }
//                });

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

                        String get_user_name = set_user_name.getText().toString();
                        String get_user_tel = set_user_tel.getText().toString();
                        String get_user_email = set_user_email.getText().toString();
                        String get_user_division = set_user_division.getText().toString();

                        if (get_user_name.length() <= 0 || get_user_division.length() <= 0 || get_user_email.length() <= 0 || get_user_tel.length() <= 0) {
                            Toast.makeText(getContext(), "필수 작성 목록입니다.", Toast.LENGTH_SHORT).show();
                        } else {

                            CardList list = new CardList(R.drawable.ic_person_black_24dp, R.drawable.card_image, get_user_name, get_user_division);
                            cardList.add(list);

                            cardAdapter.notifyItemInserted(cardList.size());

                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();

            }
        });


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                cardAdapter.getFilter().filter(newText);

                return false;
            }
        });

    }

    public void CardData() {
        cardList = new ArrayList<>();
//        cardList.add(new CardList(R.drawable.ic_person_black_24dp, R.drawable.ic_person_black_24dp, "asdf", "asdf"));
//        cardList.add(new CardList(R.drawable.ic_person_black_24dp, R.drawable.ic_person_black_24dp, "123", "asdf"));
//        cardList.add(new CardList(R.drawable.ic_person_black_24dp, R.drawable.ic_person_black_24dp, "456", "asdf"));
//        cardList.add(new CardList(R.drawable.ic_person_black_24dp, R.drawable.ic_person_black_24dp, "789", "asdf"));
//        cardList.add(new CardList(R.drawable.ic_person_black_24dp, R.drawable.ic_person_black_24dp, "000", "asdf"));
//        cardList.add(new CardList(R.drawable.ic_person_black_24dp, R.drawable.ic_person_black_24dp, "이예성", "asdf"));
//        cardList.add(new CardList(R.drawable.ic_person_black_24dp, R.drawable.ic_person_black_24dp, "김민준", "asdf"));
//        cardList.add(new CardList(R.drawable.ic_person_black_24dp, R.drawable.ic_person_black_24dp, "김현우", "asdf"));

    }


    private void setupRecyclerView() {
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                cardList.remove(position);
                cardAdapter.notifyItemRemoved(position);
                cardAdapter.notifyItemRangeChanged(position, cardAdapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
                super.onLeftClicked(position);

                cardList.get(position);
                cardAdapter.notifyItemRangeChanged(0, cardList.size());

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