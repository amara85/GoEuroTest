/**
 * This is the main class. It contains the main(String[] args) method.
 */

package goeurotest;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Create an object of our GoEuroTest class
        GoEuroTest goeurotest = new GoEuroTest();

        // Set the url of the API endpoint
        goeurotest.setApi_endpoint(args[0]);

        // Set the path of the csv output file
        goeurotest.setCsv_out_path("C:\\csv_out.csv");

        // Query the JSON API. Then extract the desired informations:_id, name,
        // type, latitude and longitude of each JSON object. Finally, it creates
        // the CSV output file
        goeurotest.processJSONData();

    }
}
