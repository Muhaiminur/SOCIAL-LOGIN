package com.muhaiminuabir.sociallogin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GITHUB_ACTIVITY extends AppCompatActivity {

    @BindView(R.id.buttonAnonymousSignIn)
    Button buttonAnonymousSignIn;
    @BindView(R.id.buttonAnonymousSignOut)
    Button buttonAnonymousSignOut;
    @BindView(R.id.titleLinking)
    TextView titleLinking;
    @BindView(R.id.fieldEmail)
    EditText fieldEmail;
    @BindView(R.id.fieldPassword)
    EditText fieldPassword;
    @BindView(R.id.buttonLinkAccount)
    Button buttonLinkAccount;

    private static final String TAG = "AnonymousAuth";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github__activity);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick({R.id.buttonAnonymousSignIn, R.id.buttonAnonymousSignOut, R.id.buttonLinkAccount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonAnonymousSignIn:
                signInAnonymously();
                break;
            case R.id.buttonAnonymousSignOut:
                signOut();
                break;
            case R.id.buttonLinkAccount:
                linkAccount();
                break;
        }
    }

    private void signInAnonymously() {
        //showProgressDialog();
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(GITHUB_ACTIVITY.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END signin_anonymously]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void linkAccount() {
        // Make sure form is valid
        if (!validateLinkForm()) {
            return;
        }

        // Get email and password from form
        String email = fieldEmail.getText().toString();
        String password = fieldPassword.getText().toString();

        // Create EmailAuthCredential with email and password
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // Link the anonymous user to the email credential
        //showProgressDialog();

        // [START link_credential]
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "linkWithCredential:failure", task.getException());
                            Toast.makeText(GITHUB_ACTIVITY.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END link_credential]
    }

    private boolean validateLinkForm() {
        boolean valid = true;

        String email = fieldEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            fieldEmail.setError("Required.");
            valid = false;
        } else {
            fieldEmail.setError(null);
        }

        String password = fieldPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            fieldPassword.setError("Required.");
            valid = false;
        } else {
            fieldPassword.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();

        //TextView idView = findViewById(R.id.anonymousStatusId);
        //TextView emailView = findViewById(R.id.anonymousStatusEmail);
        boolean isSignedIn = (user != null);

        // Status text
        if (isSignedIn) {
            //idView.setText(getString(R.string.id_fmt, user.getUid()));
            //emailView.setText(getString(R.string.email_fmt, user.getEmail()));
        } else {
            //idView.setText(R.string.signed_out);
            //emailView.setText(null);
        }

        // Button visibility
        findViewById(R.id.buttonAnonymousSignIn).setEnabled(!isSignedIn);
        findViewById(R.id.buttonAnonymousSignOut).setEnabled(isSignedIn);
        findViewById(R.id.buttonLinkAccount).setEnabled(isSignedIn);
    }
}
