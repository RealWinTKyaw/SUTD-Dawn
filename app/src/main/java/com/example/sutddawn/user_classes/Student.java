package com.example.sutddawn.user_classes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Student implements Serializable {

    private String ID;
    private String name;
    private String email;
    private String password;
    private String cohortClass;
    private String firebaseID;
    private String studentBio = "Hello World";
    //default modules, can be changed each term
    private ArrayList<String> modules = new ArrayList<String>(){{
        add("50.001");
        add("50.002");
        add("50.004");
    }};
    private String overallIndividualRating = "0.0"; //value to display
    private HashMap<String, String> overallRatings= new HashMap<String, String>();
    private HashMap<String, String> skillRatings = new HashMap<String, String>();
    //averageSkillRatings are the values to display
    private HashMap<String, String> averageSkillRatings= new HashMap<String, String>(){{
        put("Programming", "0.0");
        put("CAD Modeling", "0.0");
        put("Premier Pro", "0.0");
    }};

    public Student(){}
    public Student(String ID, String name, String email, String password) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //Add and remove a skill
    public void addCustomSkill(String skill) {
        averageSkillRatings.put(skill, "0.0");
    }
    public void removeCustomSkill(String skill) {
        averageSkillRatings.remove(skill);
    }

    //Add and remove modules
    public void addCustomModule(String module) {
        modules.add(module);
    }
    public void removeModule(String module) {
        modules.remove(module);
    }

    //rateOverall will be the method called
    public void rateOverall(Student studentToRate, String rating) {
        HashMap<String, String> ratings = studentToRate.getOverallRatings();
        ratings.put(this.name, rating);
        studentToRate.setOverallRatings(ratings);
        studentToRate.calculateOverallRating();
    }
    public void calculateOverallRating() {
        double sum = 0;
        int count = 0;
        for (String i:overallRatings.values()){
            sum+=Double.parseDouble(i);
            count+=1;
        }
        BigDecimal result = BigDecimal.valueOf(sum/count);
        result = result.setScale(1, RoundingMode.HALF_UP);
        overallIndividualRating = String.valueOf(result.doubleValue());
    }

    //rateSkill and returnSpecificSkillRating will be the methods called
    public void rateSkill(String skillToRate, Student studentToRate, String rating) {
        HashMap<String, String> ratings = studentToRate.getSkillRatings();
        ratings.put(skillToRate+this.name, rating);
        studentToRate.setSkillRatings(ratings);
        studentToRate.calculateSkillRating(skillToRate);
    }
    public void calculateSkillRating(String skillToCalc) {
        double sum = 0;
        int count = 0;
        int skillOnly = skillToCalc.length();
        for (String skillAndRater:skillRatings.keySet()) {
            if (skillAndRater.substring(0,skillOnly).equals(skillToCalc)){
                sum+=Double.parseDouble(skillRatings.get(skillAndRater));
                count+=1;
            }
        }
        BigDecimal result = BigDecimal.valueOf(sum/count);
        result = result.setScale(1, RoundingMode.HALF_UP);
        averageSkillRatings.put(skillToCalc, String.valueOf(result.doubleValue()));
    }

    //Gets rating for specified skill
    public String returnSpecificSkillRating(String skill) {
        return averageSkillRatings.get(skill);
    }

    //Gets set of skills
    public Set<String> returnSkills() {
        return averageSkillRatings.keySet();
    }

    //Convert modules to string
    public String modulesToString(ArrayList<String> modules) {
        StringBuilder result = new StringBuilder();
        for (String m:modules) {
            result.append(" ").append(m).append(",");
        }
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }

    //Setters and getters required for Firebase
    //ID
    public String getID(){
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    //Name
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Email
    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //Password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //Firebase UID
    public String getFirebaseID() {
        return firebaseID;
    }
    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }

    //Cohort Class
    public String getCohortClass() {
        return cohortClass;
    }
    public void setCohortClass(String cohortClass) {
        this.cohortClass = cohortClass;
    }

    //Student Bio
    public String getStudentBio() {
        return studentBio;
    }
    public void setStudentBio(String studentBio) {
        this.studentBio = studentBio;
    }

    //Modules
    public ArrayList<String> getModules() {
        return modules;
    }
    public void setModules(ArrayList<String> modules) {
        this.modules = modules;
    }

    //Overall Indiv Rating
    public String getOverallIndividualRating() {
        return overallIndividualRating;
    }
    public void setOverallIndividualRating(String overallIndividualRating) {
        this.overallIndividualRating = overallIndividualRating;
    }

    //All overall ratings
    public HashMap<String, String> getOverallRatings() {
        return overallRatings;
    }
    public void setOverallRatings(HashMap<String, String> overallRatings) {
        this.overallRatings = overallRatings;
    }

    //All skill ratings
    public HashMap<String, String> getSkillRatings() {
        return skillRatings;
    }
    public void setSkillRatings(HashMap<String, String> skillRatings) {
        this.skillRatings = skillRatings;
    }

    //Average rating of each skill
    public HashMap<String, String> getAverageSkillRatings() {
        return averageSkillRatings;
    }
    public void setAverageSkillRatings(HashMap<String, String> averageSkillRatings) {
        this.averageSkillRatings = averageSkillRatings;
    }
  
    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        else if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return s.name == this.name;
    }

}