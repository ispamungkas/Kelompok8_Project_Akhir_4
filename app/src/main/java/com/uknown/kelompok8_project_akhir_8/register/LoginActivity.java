package com.uknown.kelompok8_project_akhir_8.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.uknown.kelompok8_project_akhir_8.homepage.HomepageActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.model.User;
import com.uknown.kelompok8_project_akhir_8.R;
import com.uknown.kelompok8_project_akhir_8.databinding.LoginBinding;

import java.util.List;


public class LoginActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mDatabase;
    private LoginBinding binding;
    private FirebaseFirestore ff;
    private User dataUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ff = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Konfigurasi Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {
                    Toast.makeText(LoginActivity.this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Memulai proses login
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        if(mAuth.getCurrentUser() != null){
            Intent go = new Intent(getApplicationContext(), HomepageActivity.class);
            startActivity(go);
            finish();
        };
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Hasil dari proses login Google
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.e("GoogleSignIn", "Google Sign-In failed.");
//                    finish();
                Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("GoogleSignIn", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("GoogleSignIn", "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Simpan UID pengguna ke database Firebase
                        saveUserToDatabase(user);

                        // Setelah berhasil login, arahkan ke halaman berikutnya
                        redirectToNextPage(user.getUid());
                    } else {
                        Log.w("GoogleSignIn", "signInWithCredential:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void saveUserToDatabase(FirebaseUser user) {
        // Simpan UID pengguna ke dalam database Firebase
        String userId = user.getUid();
        String email = user.getEmail();
        String name = user.getDisplayName();
        String photo = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "";

        // Buat objek User
        User userModel = new User(email, name, "", photo, userId);

        // Simpan objek User ke dalam database Firebase
        mDatabase.child("users").child(userId).setValue(userModel);

        // Simpan objecy User ke dalam database Firebase
        ff.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(!task.getResult().exists()){
                            createDatabaseFirestore(userModel);
                            redirectToNextPage(userModel.getUserId());
                        }
                    }
                });
    }

    private void createDatabaseFirestore(User userModel) {
        ff.collection("users")
                .document(userModel.getUserId())
                .set(userModel)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Add Account Failure", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void redirectToNextPage(String id) {
        dataUser = new User();
        ff.collection("users").whereEqualTo("userId", id).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : data){
                            dataUser = ds.toObject(User.class);

                            Intent intent = null;
                            // Intent untuk membuka halaman SetupNumberActivity
                            if(!dataUser.getPhoneNumber().isEmpty()){
                                intent = new Intent(getApplicationContext(), HomepageActivity.class);
                            } else {
                                intent = new Intent(getApplicationContext(), SetupNumberActivity.class);
                            }
                            startActivity(intent);
                            finish();
                        }
                    }
                });


    }
}
