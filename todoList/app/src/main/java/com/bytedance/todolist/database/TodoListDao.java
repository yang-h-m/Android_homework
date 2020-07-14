package com.bytedance.todolist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
@Dao
public interface TodoListDao {
    @Query("SELECT * FROM todo")
    List<TodoListEntity> loadAll();

    @Insert
    long addTodo(TodoListEntity entity);

    @Query("UPDATE todo SET state=:mState WHERE id=:mId")
    int update(int mState,long mId);

    @Query("DELETE FROM todo")
    void deleteAll();

    @Delete
    void deleteItem(TodoListEntity entity);
}
