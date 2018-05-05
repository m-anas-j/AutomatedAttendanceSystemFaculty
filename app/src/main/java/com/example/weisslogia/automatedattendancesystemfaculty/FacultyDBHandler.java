package com.example.weisslogia.automatedattendancesystemfaculty;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class FacultyDBHandler extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "faculty_side.db";

    //faculty table
    private static final String TABLE_FACULTY = "faculty";
    private static final String FACULTY_COLUMN_1_ID = "_id";
    private static final String FACULTY_COLUMN_2_NAME = "faculty_name";
    private static final String FACULTY_COLUMN_3_PASSWORD = "faculty_password";
    //student table
    private static final String TABLE_STUDENT = "student";
    private static final String STUDENT_COLUMN_1_ID = "_id";
    private static final String STUDENT_COLUMN_2_NAME = "student_name";
    private static final String STUDENT_COLUMN_3_PASSWORD = "student_password";
    //Attendance table
    private static final String TABLE_ATTENDANCE = "attendance";
    private static final String ATTENDANCE_COLUMN_1_ID = "_id";
    private static final String ATTENDANCE_COLUMN_2_DATE = "date";
    private static final String ATTENDANCE_COLUMN_3_STUDENT_ID = "student_id";
    private static final String ATTENDANCE_COLUMN_4_STATUS= "status";
    private static final String ATTENDANCE_COLUMN_5_TOTALCLASSES = "total classes";
    private static final String ATTENDANCE_COLUMN_6_PERCENTAGE = "percentage";
    //Course info table
    private static final String TABLE_COURSEINFO = "attendance";
    private static final String COURSEINFO_COLUMN_1_ID = "_id";
    private static final String COURSEINFO_COLUMN_2_NAME = "course_name";
    private static final String COURSEINFO_COLUMN_3_FACULTY_ID = "faculty_id";


    public FacultyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create student table
        String createStudentTable = "CREATE TABLE " + TABLE_STUDENT + "( " +
                STUDENT_COLUMN_1_ID + " TEXT PRIMARY KEY, " +
                STUDENT_COLUMN_2_NAME + " TEXT, " +
                STUDENT_COLUMN_3_PASSWORD + " TEXT )"
                ;
        db.execSQL(createStudentTable);

        //create faculty table
        String createFacultyTable = "CREATE TABLE " + TABLE_FACULTY + "( " +
                FACULTY_COLUMN_1_ID + " TEXT PRIMARY KEY, " +
                FACULTY_COLUMN_2_NAME + " TEXT, " +
                FACULTY_COLUMN_3_PASSWORD + " TEXT )"
                ;
        db.execSQL(createFacultyTable);

        //create course info table
        String createCourseInfoTable = "CREATE TABLE " + TABLE_COURSEINFO + "( " +
                COURSEINFO_COLUMN_1_ID + " TEXT PRIMARY KEY, " +
                COURSEINFO_COLUMN_2_NAME + " TEXT, " +
                COURSEINFO_COLUMN_3_FACULTY_ID + " TEXT )"
                ;
        db.execSQL(createCourseInfoTable);

        //create attendance table
        String createAttendanceTable = "CREATE TABLE " + TABLE_ATTENDANCE + "( " +
                ATTENDANCE_COLUMN_1_ID + " TEXT PRIMARY KEY, " +
                ATTENDANCE_COLUMN_2_DATE + " TEXT, " +
                ATTENDANCE_COLUMN_3_STUDENT_ID + " TEXT, " +
                ATTENDANCE_COLUMN_4_STATUS + " INTEGER, " +
                ATTENDANCE_COLUMN_5_TOTALCLASSES + "INTEGER, " +
                ATTENDANCE_COLUMN_6_PERCENTAGE + " DECIMAL(10,5) )"
                ;
        db.execSQL(createAttendanceTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACULTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSEINFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);

    }


}
