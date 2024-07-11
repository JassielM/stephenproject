package com.example.stephen_app;

public class LowerClass {
    String id,name,std_class;
    int math,english,lit_1,lit_2,re,reading,agg,average;
    String division;

    public LowerClass(String id, String name, String std_class, int math, int english, int lit_1, int lit_2, int re, int reading, int agg, int average, String division) {
        this.id = id;
        this.name = name;
        this.std_class = std_class;
        this.math = math;
        this.english = english;
        this.lit_1 = lit_1;
        this.lit_2 = lit_2;
        this.re = re;
        this.reading = reading;
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

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getLit_1() {
        return lit_1;
    }

    public void setLit_1(int lit_1) {
        this.lit_1 = lit_1;
    }

    public int getLit_2() {
        return lit_2;
    }

    public void setLit_2(int lit_2) {
        this.lit_2 = lit_2;
    }

    public int getRe() {
        return re;
    }

    public void setRe(int re) {
        this.re = re;
    }

    public int getReading() {
        return reading;
    }

    public void setReading(int reading) {
        this.reading = reading;
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
