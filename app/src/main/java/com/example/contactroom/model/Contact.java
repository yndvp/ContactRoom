package com.example.contactroom.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contact {

    @PrimaryKey(autoGenerate = true)
//    @NonNull -> redundant(as it's implied)
//    @ColumnInfo(name = "id") -> Column name not necessary since it's the same as the filed name
    private int id;

//    @ColumnInfo(name = "name")
    private String name;

//    @ColumnInfo(name = "occupation")
    private String occupation;

    public Contact(@NonNull String name, @NonNull String occupation) {
        this.name = name;
        this.occupation = occupation;
    }

    public Contact(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOccupation() {
        return occupation;
    }
}
