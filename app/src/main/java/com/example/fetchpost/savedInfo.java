package com.example.fetchpost;

public class savedInfo {

    public static String baseUrl = "http://192.168.137.1/fetchpost/";
    //192.168.137.1

    public static String add_member = baseUrl+"register.php";
    public static String login = baseUrl+"login.php";
    public static String forgot = baseUrl+"forgot.php";
    public static String validate = baseUrl+"validate.php";
    public static String memberDet = baseUrl+"member.php";


    public static String success = "1";
    public static String memDontExist = "2";
    public static String passDontMatch = "3";
    public static String postNotPassed = "10";




}
