package com.example.stephen_app;

public class UpperReport {

    String div = "", math = "", english = "", sst = "", science = "", totalValue = "", aggMath = "", aggEng = "", aggSST = "", aggScience = "", aggTotal = "", commE = "", commS = "", commSST = "", commM = "";
    String div_Bot = "", math_Bot = "", english_Bot = "", sst_Bot = "", science_Bot = "", totalValue_Bot = "";
    String div_Mid = "", math_Mid = "", english_Mid = "", sst_Mid = "", science_Mid = "", totalValue_Mid = "";
    String name;
    String level;
    String std_class;

    public UpperReport(String std_class,String div, String math, String english, String sst, String science, String totalValue, String aggMath, String aggEng, String aggSST, String aggScience, String aggTotal, String commE, String commS, String commSST, String commM, String div_Bot, String math_Bot, String english_Bot, String sst_Bot, String science_Bot, String totalValue_Bot, String div_Mid, String math_Mid, String english_Mid, String sst_Mid, String science_Mid, String totalValue_Mid, String name, String level) {
        this.div = div;
        this.math = math;
        this.english = english;
        this.sst = sst;
        this.science = science;
        this.totalValue = totalValue;
        this.aggMath = aggMath;
        this.aggEng = aggEng;
        this.aggSST = aggSST;
        this.aggScience = aggScience;
        this.aggTotal = aggTotal;
        this.commE = commE;
        this.commS = commS;
        this.commSST = commSST;
        this.commM = commM;
        this.div_Bot = div_Bot;
        this.math_Bot = math_Bot;
        this.english_Bot = english_Bot;
        this.sst_Bot = sst_Bot;
        this.science_Bot = science_Bot;
        this.totalValue_Bot = totalValue_Bot;
        this.div_Mid = div_Mid;
        this.math_Mid = math_Mid;
        this.english_Mid = english_Mid;
        this.sst_Mid = sst_Mid;
        this.science_Mid = science_Mid;
        this.totalValue_Mid = totalValue_Mid;
        this.name = name;
        this.level = level;
        this.std_class = std_class;
    }
}

