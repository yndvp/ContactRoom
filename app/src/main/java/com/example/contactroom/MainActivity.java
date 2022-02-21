package com.example.contactroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.contactroom.adapter.RecyclerViewAdapter;
import com.example.contactroom.model.Contact;
import com.example.contactroom.model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnContactClickListener {
    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    private ContactViewModel contactViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private static final String TAG = "Clicked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication())
                .create(ContactViewModel.class);

        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                // Set up adapter
                recyclerViewAdapter = new RecyclerViewAdapter(contacts, MainActivity.this, MainActivity.this);
                recyclerView.setAdapter(recyclerViewAdapter);

//                StringBuilder builder = new StringBuilder();
//                for(Contact contact: contacts) {
//                    builder.append(" - ").append(contact.getName()).append(" ").append(contact.getOccupation());
//                    Log.d("TAG", "onCreate: " + contact.getName());
//                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.add_contact_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewContact.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(NewContact.NAME_REPLY);
            String occupation = data.getStringExtra(NewContact.OCCUPATION);

            Contact contact = new Contact(name, occupation);

//            Log.d("TAG", "onActivityResult: " + name);
//            Log.d("TAG", "onActivityResult: " + occupation);

            ContactViewModel.insert(contact);
        }
    }

    @Override
    public void onContactClick(int position) {
        Log.d(TAG, "onContactClick: " + position);
        Contact contact = contactViewModel.allContacts.getValue().get(position);
        Log.d(TAG, "onContactClick: " + contact.getName());
//        startActivity(new Intent(MainActivity.this, NewContact.class));
    }
}