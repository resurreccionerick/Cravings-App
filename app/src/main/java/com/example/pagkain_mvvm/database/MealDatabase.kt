package com.example.pagkain_mvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pagkain_mvvm.models.random.MealsItem

@Database(entities = [MealsItem::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class MealDatabase : RoomDatabase() {

    abstract fun dao(): MealDAO

    companion object {
        @Volatile
        var INSTANCE: MealDatabase? = null //will be visible to any other thread

        @Synchronized //only one thread can have instance of this db
        fun getInstance(context: Context): MealDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration() //rebuild db but keeping the data inside the db
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }

}