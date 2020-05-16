package com.example.fetchpost.Helpers;

public class savedInfo {

    //server details
    private static String baseUrl = "http://192.168.43.29/fetchpost/";
    //192.168.43.29 - phone's hotspot
    //192.168.137.1 - laptop's hotspot
    public static String add_member = baseUrl+"register.php";
    public static String login = baseUrl+"login.php";
    public static String forgot = baseUrl+"forgot.php";
    public static String validate = baseUrl+"validate.php";
    public static String memberFetch = baseUrl+"memFetch.php";
    public static String memberFetchAll = baseUrl+"memFetchAll.php";


    //Logged in details
    public static String loggedUsername = "";
    public static String loggedEmail = "";
    public static String loggedPhoneNumber = "";
    public static String loggedFirstName = "";
    public static String loggedLastName = "";


    //error and success details from database connection
    public static String success = "1";
    public static String memDontExist = "2";
    public static String passDontMatch = "3";
    public static String postNotPassed = "10";




}
