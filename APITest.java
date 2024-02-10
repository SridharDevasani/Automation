package com.bdd.actionPages;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class APITest extends BaseClass {
    public static WebDriver driver;

    @Test
    public  void Test_API(){
    	
    	String uri =BaseClass.getProperty();
    	// Set Base URI
        RestAssured.baseURI = uri;
     // Sending request and get response
        Response response = RestAssured.given()
                .when()
                .get("/networks")
                .then()
                .extract().response();
               
        // Log JSON response
        System.out.println("Response Body:");
        System.out.println(response.getBody().asString());

        // Extracting the details of a specific country
        String country = "US";
        String latitude = response.jsonPath().getString("networks.find { it.location.country == '" + country + "' }.location.latitude");
        String longitude = response.jsonPath().getString("networks.find { it.location.country == '" + country + "' }.location.longitude");

        //Printing the country, latitude and longitude details
        System.out.println("Country: " + country);
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
 



    }

public void DBConnection(){

 String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
     String USERNAME = "username";
     String PASSWORD = "password";

    
       Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
           
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to database successfully!");

            
            statement = connection.createStatement();

            
            String sqlQuery = "SELECT * FROM my_table";
            resultSet = statement.executeQuery(sqlQuery);

            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
               
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute SQL query!");
            e.printStackTrace();
        } finally {
            
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("Connection closed successfully!");
            } catch (SQLException e) {
                System.err.println("Failed to close database resources!");
                e.printStackTrace();
            }
        }
    }
	

}







