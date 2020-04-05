package com.example.fetchpost;

public class savedInfo {

    private static String baseUrl = "http://192.168.43.29/fetchpost/";
    //192.168.43.29 - phone's hotspot
    //192.168.137.1 - laptop's hotspot
    static String add_member = baseUrl+"register.php";
    static String login = baseUrl+"login.php";
    static String forgot = baseUrl+"forgot.php";
    static String validate = baseUrl+"validate.php";
    static String memberFetch = baseUrl+"memFetch.php";
    static String memberFetchAll = baseUrl+"memFetchAll.php";



    static String success = "1";
    static String memDontExist = "2";
    static String passDontMatch = "3";
    public static String postNotPassed = "10";




}
