package com.example.weisslogia.automatedattendancesystemfaculty;

public class CourseInfo {

    private String courseId, courseName;
    int facultyId;

    public CourseInfo()
    {

    }

    public CourseInfo(String _courseId, String _courseName, int _facultyId )
    {
        this.courseId = _courseId;
        this.courseName = _courseName;
        this.facultyId = _facultyId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }
}
