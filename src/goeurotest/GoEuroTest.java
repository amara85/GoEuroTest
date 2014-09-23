/**
 * This is the class which contains methods that performs these tasks:
 ****  Query the JSON API;
 ****  Extract the desired informations:_id, name,type, latitude and longitude
 ****  of each JSON object;
 ****  Create the CSV output file;
 */
package goeurotest;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GoEuroTest {

    private String api_endpoint; // API endpoint
    private String csv_out_path;// the path of the output csv file

    /*
     * Main method: it query the JSON API. Then, it extracts the desired informations:_id, name,
     * type, latitude and longitude of each JSON object. Finally, it creates
     * the CSV output file
     */
    public void processJSONData() {

        String input_data = null;

        // Return a string containing the JSON data from the JSON API
        input_data = this.getJSONData();


        // Extract the desired data: _id, name, type, latitude,longitude and 
        // put them into a two-dimensional table of strings
        String[][] output_data = this.convertJSONDataToTable(input_data);

        // Write a two-dimensional table of strings to a csv file.
        // Path of this file is stored in the variable "csv_out_path"
        this.writeJSONDateToCsv(output_data);

    }

    // Return a string containing the JSON data from the JSON API
    private String getJSONData() {

        String input_data = "";
        try {

            InputStream is = new URL(api_endpoint).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            input_data = jsonText;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return input_data;

    }

    /** Extract the desired data: _id, name, type, latitude and
     *  longitude of each JSON object and put them into a two-dimensional table
     */
    private String[][] convertJSONDataToTable(String input_data) {

        String[][] output_data = null; //output table containing desired data

        try {
            JSONParser jsonParser = new JSONParser();
            JSONArray array = (JSONArray) jsonParser.parse(input_data);
            output_data = new String[array.size()][5];


            int i = 0;
            while (i < array.size()) {

                JSONObject obji = (JSONObject) array.get(i);
                output_data[i][0] = obji.get("_id").toString();
                output_data[i][1] = obji.get("name").toString();
                output_data[i][2] = obji.get("type").toString();
                JSONObject geoi = (JSONObject) obji.get("geo_position");
                output_data[i][3] = geoi.get("latitude").toString();
                output_data[i][4] = geoi.get("longitude").toString();
                i++;

            }
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return output_data;
    }

    /**  Write a two-dimensional table of strings to a csv file.
     *   Path of this file is stored in the variable "csv_out_path"
     */
    private void writeJSONDateToCsv(String[][] out_data) {

        try {
            FileWriter writer = new FileWriter(this.csv_out_path);

            int i = 0;
            while (i < out_data.length) {
                writer.append(out_data[i][0]);
                writer.append(',');
                writer.append(out_data[i][1]);
                writer.append(',');
                writer.append(out_data[i][2]);
                writer.append(',');
                writer.append(out_data[i][3]);
                writer.append(',');
                writer.append(out_data[i][4]);
                writer.append('\n');
                i++;
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Set the url of the API endpoint
    public void setApi_endpoint(String api_endpoint) {
        this.api_endpoint = api_endpoint;
    }

    // Set the path of the csv output file
    public void setCsv_out_path(String csv_out_path) {
        this.csv_out_path = csv_out_path;
    }
}
