package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Laboratory {
    // Ambiguous names : number and problem number
    private int number;
    private Date date;
    private int problemNumber;
    private float grade = 1;
    private String studentRegNumber;

    public Laboratory(int number, String dateString, int problemNumber,
                      String studentRegNumber) throws ParseException {
        this.number = number; // problemNumber assigned to Number
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.date = format.parse(dateString);
        this.problemNumber = problemNumber; // number assigned to problem number
        this.studentRegNumber = studentRegNumber;
    }

    public Laboratory(int number, String date, int problemNumber, Float grade,
                      String studentRegNumber) throws ParseException {
        this.number = number; // problemNumber assigned to Number
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        this.date = format.parse(date);
        this.problemNumber = problemNumber; // number assigned to problem number
        this.grade = grade;
        this.studentRegNumber = studentRegNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProblemNumber() {
        return problemNumber;
    }

    public void setProblemNumber(int problemNumber) {
        this.problemNumber = problemNumber;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getStudentRegNumber() {
        return studentRegNumber;
    }

    public void setStudentRegNumber(String studentRegNumber) {
        this.studentRegNumber = studentRegNumber;
    }

    @Override
    public String toString() {
        int month = date.getMonth() + 1;
        int year = date.getYear() + 1900;
        return number + " " + date.getDate() + "/" + month + "/" + year + " "
                + problemNumber + " " + grade + " " + studentRegNumber;
    }

} 