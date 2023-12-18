package com.uknown.kelompok8_project_akhir_8.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uknown.kelompok8_project_akhir_8.homepage.model.User;
import com.uknown.kelompok8_project_akhir_8.R;

public class SetupNumberActivity extends AppCompatActivity {
    private EditText etPhoneNumber;
    private Button btnRegister;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_number);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        btnRegister = findViewById(R.id.btnSaveNumber);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        db = FirebaseFirestore.getInstance();
    }

    private void register() {
        AlertDialog.Builder alert = new AlertDialog.Builder(SetupNumberActivity.this);
        alert.setTitle(R.string.app_name);
        alert.setMessage("Are you sure you have entered the right phone number?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                String userName = account.getDisplayName();
                String userEmail = account.getEmail();
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                String photo = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";

                if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
                    etPhoneNumber.setError("Please fill the Phone Number blank field with at least 10 digits!");
                    etPhoneNumber.requestFocus();
                    return;
                }

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    String userId = firebaseUser.getUid();

                    User user = new User(userEmail, userName, phoneNumber, photo, userId);

                    db.collection("users")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SetupNumberActivity.this, "Phone number added successfully!", Toast.LENGTH_SHORT).show();
                                    Intent go = new Intent(SetupNumberActivity.this, FinisRegisterActivity.class);
                                    go.putExtra("uid", user.getUserId());
                                    startActivity(go);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SetupNumberActivity.this, "Phone number failed to add!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }
}
