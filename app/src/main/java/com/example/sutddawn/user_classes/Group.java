package com.example.sutddawn.user_classes;

import com.example.sutddawn.FetchDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {

    private String name;
    private String description = "New Group";
    private String module;
    private String cohortClass;

    private int capacity;
    private int currentCapacity = 0;
    private boolean open = true;

    private ArrayList<String> groupMembers = new ArrayList<String>();

    public Group(){}
    public Group(int capacity, String name, String module){
        this.capacity = capacity;
        this.name = name;
        this.module = module;
    }

    //Add and remove members
    public void addGroupMember(String firebaseID){
        if (!groupMembers.contains(firebaseID) && !isFull() && getOpen()) {
            groupMembers.add(firebaseID);
            currentCapacity += 1;
        }
    }
    public void removeGroupMember(String firebaseID){
        groupMembers.remove(firebaseID);
        currentCapacity -= 1;
    }

    //Gets student data from Firebase & returns as ArrayList of Students
    public ArrayList<Student> retrieveStudentsFromFirebase() {
        ArrayList<Student> result = new ArrayList<>();
        for (String firebaseID:groupMembers){
            Student student = FetchDatabase.getUser(firebaseID);
            result.add(student);
        }
        return result;
    }

    // Misc methods
    public boolean isFull(){
        return currentCapacity == capacity;
    }
    public void changeAccess() {
        open = !open;
    }

    //Setters and getters required for Firebase
    //Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Description
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //Module
    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }

    //Cohort
    public String getCohortClass() {
        return cohortClass;
    }
    public void setCohortClass(String cohortClass) {
        this.cohortClass = cohortClass;
    }

    //Capacity
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity){
        if (currentCapacity < capacity)
            this.capacity = capacity;
    }

    //Current Capacity
    public int getCurrentCapacity() {
        return currentCapacity;
    }
    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    //Open
    public boolean getOpen(){
        return open;
    }
    public void setOpen(boolean bool) {
        this.open = bool;
    }

    //Group Members
    public ArrayList<String> getGroupMembers() {
        return groupMembers;
    }
    public void setGroupMembers(ArrayList<String> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        else if (!(o instanceof Group)) return false;
        Group g = (Group) o;
        return g.name == this.name;
    }

}