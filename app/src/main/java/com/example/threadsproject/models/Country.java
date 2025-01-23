package com.example.threadsproject.models;

public class Country
{
    private String name;
    private String flag;
    private String population;

    public Country(String name, String popoulation, String flag)
    {
        this.name = name;
        this.population = popoulation;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public String getPopulation() {
        return population;
    }

    public String getFlag() {
        return flag;
    }
}

