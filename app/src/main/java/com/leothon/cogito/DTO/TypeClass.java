package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.SelectClass;

import java.util.ArrayList;

public class TypeClass {
    private String typeClassCount;
    private ArrayList<SelectClass> typeClass;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<SelectClass> getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(ArrayList<SelectClass> typeClass) {
        this.typeClass = typeClass;
    }

    public String getTypeClassCount() {
        return typeClassCount;
    }

    public void setTypeClassCount(String typeClassCount) {
        this.typeClassCount = typeClassCount;
    }
}
