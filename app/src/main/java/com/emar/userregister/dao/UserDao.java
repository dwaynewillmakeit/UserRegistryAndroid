package com.emar.userregister.dao;

import com.emar.userregister.entities.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    long insert(User user);

    @Query("SELECT id,name,email,phone_number FROM users ")
    LiveData<List<User>> getUsersAll();



}
