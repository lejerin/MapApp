package com.example.mapapp.data.repositories

import com.example.mapapp.data.models.LocalMapData
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginRepository() {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    fun login(email: String, password: String) : Single<String> = Single.create { subscriber ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!subscriber.isDisposed) {
                        if (it.isSuccessful)
                            subscriber.onSuccess(it.result?.user?.uid!!)
                        else
                            subscriber.onError(Throwable(it.exception))
                    }
                }
        }

    fun register(email: String, password: String) : Single<String> = Single.create { subscriber ->
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!subscriber.isDisposed) {
                    if (it.isSuccessful)
                        subscriber.onSuccess(it.result?.user?.uid!!)
                    else
                        subscriber.onError(Throwable(it.exception))
                }
            }
    }


    fun firebaseAuthWithGoogle(idToken: String) : Single<String> = Single.create { subscriber ->
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
            .addOnCompleteListener {
                if (!subscriber.isDisposed) {
                    if (it.isSuccessful)
                        subscriber.onSuccess(it.result?.user?.uid!!)
                    else
                        subscriber.onError(Throwable(it.exception))
                }
            }
    }


    fun facebookHandleFacebookAccessToken(token: AccessToken) : Single<String> = Single.create { subscriber ->
        firebaseAuth.signInWithCredential(FacebookAuthProvider.getCredential(token.token))
            .addOnCompleteListener {
                if (!subscriber.isDisposed) {
                    if (it.isSuccessful)
                        subscriber.onSuccess(it.result?.user?.uid!!)
                    else
                        subscriber.onError(Throwable(it.exception))
                }
            }
    }

}