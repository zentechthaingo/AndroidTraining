package ru.arturvasilov.sqlite.query;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author Artur Vasilov
 */
public interface QueryList<T> {

    @NonNull
    QueryList<T> where(@Nullable String query);

    @NonNull
    QueryList<T> whereArgs(@Nullable String[] args);

    @NonNull
    List<T> execute();

}
