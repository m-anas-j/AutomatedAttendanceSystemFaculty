package com.example.weisslogia.automatedattendancesystemfaculty;

import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

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
    private static final String TABLE_ATTENDANCE = "attendance";
    private static final String ATTENDANCE_COLUMN_1_ID = "_id";
    private static final String ATTENDANCE_COLUMN_2_DATE = "date";
    private static final String ATTENDANCE_COLUMN_3_STUDENT_ID = "student_id";
    private static final String ATTENDANCE_COLUMN_4_STATUS= "status";
    private static final String ATTENDANCE_COLUMN_6_TOTALCLASSES = "total_classes";
    private static final String ATTENDANCE_COLUMN_7_PERCENTAGE = "percentage";
    private static final String ATTENDANCE_COLUMN_5_DAYSPRESENT = "days_present";
    //Course info table
    private static final String TABLE_COURSEINFO = "course_info";
    private static final String COURSEINFO_COLUMN_1_ID = "_id";
    private static final String COURSEINFO_COLUMN_2_NAME = "course_name";
    private static final String COURSEINFO_COLUMN_3_FACULTY_ID = "faculty_id";
    private static boolean createdCourseInfoTable = false;


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
                COURSEINFO_COLUMN_3_FACULTY_ID + " NUMERIC )"
                ;
        //db.execSQL(createCourseInfoTable);

        //create attendance table
        String createAttendanceTable = "CREATE TABLE " + TABLE_ATTENDANCE + "( " +
                ATTENDANCE_COLUMN_1_ID + " TEXT, " +
                ATTENDANCE_COLUMN_2_DATE + " TEXT, " +
                ATTENDANCE_COLUMN_3_STUDENT_ID + " TEXT, " +
                ATTENDANCE_COLUMN_4_STATUS + " NUMERIC, " +
                ATTENDANCE_COLUMN_5_DAYSPRESENT + " NUMERIC, " +
                ATTENDANCE_COLUMN_6_TOTALCLASSES + " NUMERIC, " +
                ATTENDANCE_COLUMN_7_PERCENTAGE + " NUMERIC )"
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

    public void addNewCourse(ArrayList<String> coursesSelected, int facultyId)
    {
        SQLiteDatabase db = getWritableDatabase();

        /*if (createdCourseInfoTable==false)
        {
            String createCourseInfoTable = "CREATE TABLE " + TABLE_COURSEINFO + "( " +
                    COURSEINFO_COLUMN_1_ID + " TEXT PRIMARY KEY, " +
                    COURSEINFO_COLUMN_2_NAME + " TEXT, " +
                    COURSEINFO_COLUMN_3_FACULTY_ID + " NUMERIC )"
                    ;
            db.execSQL(createCourseInfoTable);
            createdCourseInfoTable=true;
        }*/
        try
        {
            String createCourseInfoTable = "CREATE TABLE " + TABLE_COURSEINFO + "( " +
                    COURSEINFO_COLUMN_1_ID + " TEXT PRIMARY KEY, " +
                    COURSEINFO_COLUMN_2_NAME + " TEXT, " +
                    COURSEINFO_COLUMN_3_FACULTY_ID + " NUMERIC )"
                    ;
            db.execSQL(createCourseInfoTable);
            createdCourseInfoTable=true;
        }catch (SQLException e)
        {

        }

        for (int i=0; i<coursesSelected.size(); i++)
        {
            String name = coursesSelected.get(i);
            String id = coursesSelected.get(i);

            ContentValues newCourseValues = new ContentValues();
            newCourseValues.put(COURSEINFO_COLUMN_1_ID, id);
            newCourseValues.put(COURSEINFO_COLUMN_2_NAME, name);
            newCourseValues.put(COURSEINFO_COLUMN_3_FACULTY_ID, facultyId);
            db.insert(TABLE_COURSEINFO, null, newCourseValues);
        }


        db.close();

    }

    public Cursor viewAllCourse()
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSEINFO;
        Cursor ret = db.rawQuery(query, null);
        ret.moveToFirst();
        return ret;
    }

    public Cursor getFacultysCourses(int loggedInFacultyId)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COURSEINFO_COLUMN_1_ID + " FROM " + TABLE_COURSEINFO + " WHERE " + COURSEINFO_COLUMN_3_FACULTY_ID + " IS " + loggedInFacultyId;
        Cursor ret = db.rawQuery(query,null);
        ret.moveToFirst();
        return ret;
    }


    public String searchPassword(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT faculty_username, faculty_password FROM " + TABLE_FACULTY;
        Cursor cursor = db.rawQuery(query, null);

        String facultyUsername, facultyPassword;
        facultyPassword = "PASSWORD NOT FOUND";
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

    public void addNewAttendanceEntry(String receivedCourseId, String receivedCurrentDate, String receivedStudentId)
    {
        SQLiteDatabase db = getWritableDatabase();
        try
        {
            String createAttendanceTable = "CREATE TABLE " + TABLE_ATTENDANCE + "( " +
                    ATTENDANCE_COLUMN_1_ID + " TEXT, " +
                    ATTENDANCE_COLUMN_2_DATE + " TEXT, " +
                    ATTENDANCE_COLUMN_3_STUDENT_ID + " TEXT, " +
                    ATTENDANCE_COLUMN_4_STATUS + " NUMERIC, " +
                    ATTENDANCE_COLUMN_5_DAYSPRESENT + " NUMERIC, " +
                    ATTENDANCE_COLUMN_6_TOTALCLASSES + " NUMERIC, " +
                    ATTENDANCE_COLUMN_7_PERCENTAGE + " NUMERIC )"
                    ;
            db.execSQL(createAttendanceTable);
        }
        catch (SQLException e)
        {

        }

        ContentValues newAttendanceEntry = new ContentValues();
        newAttendanceEntry.put(ATTENDANCE_COLUMN_1_ID, receivedCourseId);
        newAttendanceEntry.put(ATTENDANCE_COLUMN_2_DATE, receivedCurrentDate);
        newAttendanceEntry.put(ATTENDANCE_COLUMN_3_STUDENT_ID, receivedStudentId);
        newAttendanceEntry.put(ATTENDANCE_COLUMN_4_STATUS,1);
        db.insert(TABLE_ATTENDANCE, null, newAttendanceEntry);

    }

    public Cursor viewAttendanceDatabase()
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + ATTENDANCE_COLUMN_1_ID + ", " + ATTENDANCE_COLUMN_2_DATE + ", " + ATTENDANCE_COLUMN_3_STUDENT_ID + ", " + ATTENDANCE_COLUMN_4_STATUS + " FROM " + TABLE_ATTENDANCE;
        Cursor ret = db.rawQuery(query,null);
        ret.moveToFirst();
        return ret;
    }

    public void clearAllDatabases()
    {
        SQLiteDatabase db = getWritableDatabase();
        String deleteFromFaculty = "DELETE FROM " + TABLE_FACULTY;
        String deleteFromCourseInfo = "DELETE FROM " + TABLE_COURSEINFO;
        String deleteFromAttendance = "DELETE FROM " + TABLE_ATTENDANCE;

        db.execSQL(deleteFromFaculty);
        db.execSQL(deleteFromCourseInfo);
        db.execSQL(deleteFromAttendance);
    }

}
