package com.example.contactroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactroom.model.Contact;
import com.example.contactroom.model.ContactViewModel;
import com.google.android.material.snackbar.Snackbar;

public class NewContact extends AppCompatActivity {
    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION = "occupation";
    private EditText enterName;
    private EditText enterOccupation;
    private Button saveInfoButton;
    private ContactViewModel contactViewModel;
    private int contactId = 0;
    private boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        enterName = findViewById(R.id.enter_name);
        enterOccupation = findViewById(R.id.enter_occupation);
        saveInfoButton = findViewById(R.id.save_button);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(
                NewContact.this.getApplication())
                .create(ContactViewModel.class);

        if(getIntent().hasExtra(MainActivity.CONTACT_ID)) {
            contactId = getIntent().getIntExtra(MainActivity.CONTACT_ID, 0);

            contactViewModel.getContact(contactId).observe(this, new Observer<Contact>() {
                @Override
                public void onChanged(Contact contact) {
                    if(contact != null) {
                        enterName.setText(contact.getName());
                        enterOccupation.setText(contact.getOccupation());
                    }
                }
            });
            isEdit = true;
        }

        saveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if(!TextUtils.isEmpty(enterName.getText()) && !TextUtils.isEmpty(enterOccupation.getText())) {
                    String name = enterName.getText().toString();
                    String occupation = enterOccupation.getText().toString();

                    replyIntent.putExtra(NAME_REPLY, name);
                    replyIntent.putExtra(OCCUPATION, occupation);
                    setResult(RESULT_OK, replyIntent);

//                    Contact contact = new Contact(enterName.getText().toString(),
//                            enterOccupation.getText().toString());
                    
//                    ContactViewModel.insert(contact);
                } else {
                    setResult(RESULT_CANCELED, replyIntent);
//                    Toast.makeText(NewContact.this, R.string.empty, Toast.LENGTH_SHORT)
//                            .show();
                }
                finish();
            }
        });

        // Update button
        updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> {
            String name = enterName.getText().toString().trim();
            String occupation = enterOccupation.getText().toString().trim();

            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)) {
                Snackbar.make(enterName, R.string.empty, Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                Contact contact = new Contact();
                contact.setId(contactId);
                contact.setName(name);
                contact.setOccupation(occupation);
                contactViewModel.update(contact);
                finish();
            }
        });

        // Delete button
        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            int id = contactId;
            Contact contact = new Contact();
            contact.setId(id);
            contactViewModel.delete(contact);
            finish();
        });

        if(isEdit) {
            saveInfoButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

    }
}