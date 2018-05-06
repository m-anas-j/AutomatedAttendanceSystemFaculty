package com.example.weisslogia.automatedattendancesystemfaculty;

public class Faculty {

    private int facultyId;
    private String facultyName;
    private String facultyPassword;
    private String facultyUsername;

    public Faculty()
    {

    }

    public Faculty(String _facultyName, String _facultyUsername, int _facultyId, String _facultyPassword)
    {
        this.facultyName = _facultyName;
        this.facultyUsername = _facultyUsername;
        this.facultyId = _facultyId;
        this.facultyPassword = _facultyPassword;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyPassword() {
        return facultyPassword;
    }

    public void setFacultyPassword(String facultyPassword) {
        this.facultyPassword = facultyPassword;
    }

    public String getFacultyUsername() {
        return facultyUsername;
    }

    public void setFacultyUsername(String facultyUsername) {
        this.facultyUsername = facultyUsername;
    }
}
