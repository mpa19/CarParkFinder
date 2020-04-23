package com.example.marc.carparkfinder.ui.Home.ListsAdapter;

public class Campus {
    int image;
    String campus, car, moto;


    public Campus(int image, String name, String car, String moto) {
        this.image = image;
        this.campus = name;
        this.car = car;
        this.moto = moto;
    }

    public int getImage() {
        return image;
    }

    public String getCampus() {
        return campus;
    }

    public String getCar() {
        return car;
    }

    public String getMoto() {
        return moto;
    }
}
