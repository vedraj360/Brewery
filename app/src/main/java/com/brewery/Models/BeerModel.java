package com.brewery.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class BeerModel  {

    @SerializedName("abv")
    @Expose
    private String abv;
    @SerializedName("ibu")
    @Expose
    private String ibu;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("style")
    @Expose
    private String style;
    @SerializedName("ounces")
    @Expose
    private Double ounces;

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getIbu() {
        return ibu;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Double getOunces() {
        return ounces;
    }

    public void setOunces(Double ounces) {
        this.ounces = ounces;
    }

    public static final Comparator<BeerModel> BY_ASV = new Comparator<BeerModel>() {
        @Override
        public int compare(BeerModel o1, BeerModel o2) {
            double a1 = Double.parseDouble(o1.getAbv());
            double a2 = Double.parseDouble(o2.getAbv());
            return Double.compare(a1, a2);
        }
    };
}
