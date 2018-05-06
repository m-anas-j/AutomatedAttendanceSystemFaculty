package com.example.weisslogia.automatedattendancesystemfaculty;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.view.View;
import android.widget.Toast;

public class FacultyDBHandler extends SQLiteOpenHelper{


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "faculty_side.db";

    //faculty table
    private static final String TABLE_FACULTY = "faculty";
    private static final String FACULTY_COLUMN_1_ID = "faculty_id";
    private static final String FACULTY_COLUMN_2_NAME = "faculty_name";
    private static final String FACULTY_COLUMN_3_USERNAME = "faculty_username";
    private static final String FACULTY_COLUMN_4_PASSWORD = "faculty_password";
    //student table
    private static final String TABLE_STUDENT = "student";
    private static final String STUDENT_COLUMN_1_ID = "_id";
    private static final String STUDENT_COLUMN_2_NAME = "student_name";
    private static final String STUDENT_COLUMN_3_PASSWORD = "student_password";
    //Attendance table
    private static final String TABLE_ATTENDANCE = "attendanceYa";
    private static final String ATTENDANCE_COLUMN_1_ID = "_id";
    private static final String ATTENDANCE_COLUMN_2_DATE = "date";
    private static final String ATTENDANCE_COLUMN_3_STUDENT_ID = "student_id";
    private static final String ATTENDANCE_COLUMN_4_STATUS= "status";
    private static final String ATTENDANCE_COLUMN_5_TOTALCLASSES = "total_classes";
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
                FACULTY_COLUMN_1_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                FACULTY_COLUMN_2_NAME + " TEXT NOT NULL, " +
                FACULTY_COLUMN_3_USERNAME + " TEXT NOT NULL, " +
                FACULTY_COLUMN_4_PASSWORD + " TEXT NOT NULL)"
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
                ATTENDANCE_COLUMN_5_TOTALCLASSES + " INTEGER, " +
                ATTENDANCE_COLUMN_6_PERCENTAGE + " NUMERIC )"
                ;
        db.execSQL(createAttendanceTable);

        //add entries to course info table
        ContentValues courseInfoValues = new ContentValues();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACULTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSEINFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);

    }

    public void addNewFaculty(Faculty newFaculty)
    {
        String name, userName, passWord;
        int id;
        ContentValues newFacultyValues = new ContentValues();

        id = newFaculty.getFacultyId();
        name = newFaculty.getFacultyName();
        userName = newFaculty.getFacultyUsername();
        passWord = newFaculty.getFacultyPassword();

        SQLiteDatabase db = getWritableDatabase();
        newFacultyValues.put(FACULTY_COLUMN_1_ID,id);
        newFacultyValues.put(FACULTY_COLUMN_2_NAME,name);
        newFacultyValues.put(FACULTY_COLUMN_3_USERNAME,userName);
        newFacultyValues.put(FACULTY_COLUMN_4_PASSWORD,passWord);
        db.insert(TABLE_FACULTY,null,newFacultyValues);
        sortFacultyTable();
    }

    public void sortFacultyTable()
    {
        //sort the table every time a new entry is added
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("CREATE TABLE ORDERED_FACULTY_TABLE AS SELECT * FROM " + TABLE_FACULTY + " ORDER BY " + FACULTY_COLUMN_1_ID + " ASC");
        db.execSQL("DROP TABLE " + TABLE_FACULTY);
        db.execSQL("ALTER TABLE ORDERED_FACULTY_TABLE RENAME TO " + TABLE_FACULTY);
    }

    public String getNewlyCreatedFacultyInfo(int newFacultyId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String nameQuery = "SELECT *" + " FROM " + TABLE_FACULTY + " WHERE " + FACULTY_COLUMN_1_ID + " IS " + newFacultyId;
        //String nameQuery = "SELECT *" + " FROM " + TABLE_FACULTY;
        Cursor yay = db.rawQuery(nameQuery,null);
        yay.moveToFirst();
        String name = yay.getString(yay.getColumnIndex(FACULTY_COLUMN_2_NAME));
        return name;
    }

    public Cursor viewAllFaculty()
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FACULTY;
        Cursor ret = db.rawQuery(query, null);
        ret.moveToFirst();
        return ret;
    }


    public String searchPassword(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT faculty_username, faculty_password FROM " + TABLE_FACULTY;
        Cursor cursor = db.rawQuery(query, null);

        String facultyUsername, facultyPassword;
        facultyPassword = "NOT FOUND";
        if(cursor.moveToFirst()){
            do{
                facultyUsername = cursor.getString(0);
                facultyPassword = cursor.getString(1);

                if(facultyUsername.equals(username)){
                    facultyPassword = cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        return facultyPassword;
    }
}
