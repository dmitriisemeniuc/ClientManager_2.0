package com.semeniuc.dmitrii.clientmanager.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

public class GoogleAuthenticator implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleSignInOptions mGso;
    private GoogleApiClient apiClient;

    public GoogleAuthenticator(){
        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    /**
     * Build a GoogleApiClient with access to the Google Sign-In API and the
     * options specified by gso.
     * */
    public void setGoogleApiClient(Context context, FragmentActivity fragmentActivity) {
        apiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(fragmentActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGso)
                .build();
    }

    public GoogleApiClient getApiClient() {
        return apiClient;
    }

    public OptionalPendingResult<GoogleSignInResult> getOptionalPendingResult() {
        //return Auth.GoogleSignInApi.silentSignIn(apiClient);
        return Auth.GoogleSignInApi.silentSignIn(apiClient);
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
}