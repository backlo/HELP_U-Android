package com.example.help_u.Requester.Adapter;

public class EnrollItem {

    String number = null, name = null;
    boolean selected = false;

    public EnrollItem(String name, String number, boolean selected){
        super();
        this.name = name;
        this.number = number;
        this.selected = selected;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
