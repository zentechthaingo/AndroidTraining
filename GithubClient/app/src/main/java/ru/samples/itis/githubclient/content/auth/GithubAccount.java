package ru.samples.itis.githubclient.content.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.samples.itis.githubclient.BuildConfig;
import ru.samples.itis.githubclient.R;

/**
 * @author Artur Vasilov
 */
public class GithubAccount extends Account {

    public static final Creator<Account> CREATOR = Account.CREATOR;

    public static final String LOGIN_KEY = "login";

    private static final String ACCOUNT_NAME = "GithubAccount";

    private static GithubAccount sInstance;

    private String mAuthToken;

    public GithubAccount(@NonNull String name) {
        super(name, BuildConfig.ACCOUNT_TYPE);
    }

    @NonNull
    public static GithubAccount getInstance() {
        return sInstance == null ? stub() : sInstance;
    }

    public static void setup(Context context) {
        final Account account = getUserAccount(context);
        if (account != null) {
            AccountManager accountManager = AccountManager.get(context);
            String authToken = accountManager.peekAuthToken(account, BuildConfig.ACCOUNT_TYPE);
            sInstance = new GithubAccount(account.name);
            sInstance.mAuthToken = authToken;
        }
    }

    @NonNull
    public String getAuthToken() {
        return mAuthToken;
    }

    @NonNull
    public static String getLogin(Context context) {
        return AccountManager.get(context).getUserData(getUserAccount(context), LOGIN_KEY);
    }

    @Nullable
    public static Account getUserAccount(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(BuildConfig.ACCOUNT_TYPE);
        if (accounts == null || accounts.length < 1) {
            return null;
        }
        return accounts[0];
    }

    public static boolean addAccount(Context context, @NonNull String login, @NonNull String password) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = new Account(ACCOUNT_NAME, context.getString(R.string.account_token_type));
        Bundle bundle = new Bundle();
        bundle.putString(LOGIN_KEY, login);
        return accountManager.addAccountExplicitly(account, password, bundle);
    }

    @NonNull
    private static GithubAccount stub() {
        GithubAccount account = new GithubAccount("githubaccount.stub");
        account.mAuthToken = "";
        return account;
    }
}
