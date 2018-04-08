package com.example.tdw.non_cablecarvalidator.Activities.Models;

/**
 * Created by lanliang on 9/9/16.
 */
public class IdNamePair {
    public String id;
    public String name;
    public String lineNum;

    public IdNamePair(String id, String name, String lineNum){

        this.id = id;
        this.name = name;
        this.lineNum = lineNum;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
