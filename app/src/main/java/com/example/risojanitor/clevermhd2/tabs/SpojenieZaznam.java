package com.example.risojanitor.clevermhd2.tabs;

/**
 * Trieda pre spracovanie dát pre uloženie spojenia
 */
public class SpojenieZaznam {
    public String nazov;
    public String odk1;
    public String odk2;
    public String odk3;

    public String km1;
    public String km2;
    public String km3;

    public SpojenieZaznam (String nazov, String odk1, String odk2, String odk3, String km1, String km2, String km3){
        this.nazov=nazov;
        this.odk1=odk1;
        this.odk2=odk2;
        this.odk3=odk3;

        this.km1=km1;
        this.km2=km2;
        this.km3=km3;

    }
}
