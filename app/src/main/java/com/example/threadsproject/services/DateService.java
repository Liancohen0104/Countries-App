package com.example.threadsproject.services;

import android.os.StrictMode;
import com.example.threadsproject.models.Country;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.net.URL;

public class DateService
{
     public static ArrayList<Country> getAllCountries () // שליפת המידע מהאינטרנט והכנסתה לרשימה
     {
         ArrayList<Country> arrCountry = new ArrayList<>(); //רשימה שתחזיק אובייקטים של מדינות ששלפנו מהאינטרנט
         URL url; // הנתיב עצמו מטיפוס URL
         String sUrl = "https://restcountries.com/v2/all?fields=name,flags,population"; // הכתובת ממנה נשלוף את הנתונים ואז סימן שאלה מה שנרצה לשלוף

         StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build(); // יצירת התהליכון שיעשה את השליפה ברקע
         StrictMode.setThreadPolicy(policy);

         try //נסה לעשות השמה של מחרוזת הURL לאובייקט מטיפוס URL
         {
             url = new URL(sUrl);
         }
         catch (MalformedURLException e) // תפוס את החריגה אם לא הצליח
         {
             throw new RuntimeException(e);
         }

         HttpURLConnection request = null;
         try // נסה לשלוף ולהמיר את המידע מהURL עם בקשת Http
         {
             request = (HttpURLConnection) url.openConnection(); // בקשת Http
             request.connect();
             JsonParser jp = new JsonParser(); // אובייקט להמרת הנתונים
             JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //  המרת הנתונים לJsonElement
             JsonArray rootArray = root.getAsJsonArray(); // המרת הנתונים מJsonElement לJsonArray

             for (JsonElement je : rootArray) // לולאה על מערך הJson והוספת כל איבר (JsonObject) לרשימת המדינות
             {
                 JsonObject obj = je.getAsJsonObject();

                 JsonElement nameElement = obj.get("name"); // שליפת שדה השם
                 JsonElement populationElement = obj.get("population"); // שליפת שדה האוכלוסיה
                 JsonElement flagElement = obj.get("flags").getAsJsonObject().get("png");  // הוספת שדה הדגל ושליפתו png ולא svg

                 String name = nameElement.toString().replace("\"","").trim();
                 String population = populationElement.toString().replace("\"","").trim();
                 String flag = flagElement.toString().replace("\"","").trim();

                 arrCountry.add(new Country(name,population,flag));
             }
         }
         catch (IOException e)
         {
             throw new RuntimeException(e);
         }
         return arrCountry;
     }
}
