package com.example.stephen_app;

public class UpperClass {
    String id, name, std_class;
    int math, science, sst, english, agg, average;
    String division;

    public UpperClass(String id, String name, String std_class, int math, int science, int sst, int english, int agg, int average, String division) {
        this.id = id;
        this.name = name;
        this.std_class = std_class;
        this.math = math;
        this.science = science;
        this.sst = sst;
        this.english = english;
        this.agg = agg;
        this.average = average;
        this.division = division;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStd_class() {
        return std_class;
    }

    public void setStd_class(String std_class) {
        this.std_class = std_class;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public int getSst() {
        return sst;
    }

    public void setSst(int sst) {
        this.sst = sst;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getAgg() {
        return agg;
    }

    public void setAgg(int agg) {
        this.agg = agg;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
