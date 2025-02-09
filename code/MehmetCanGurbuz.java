import java.awt.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;
/**
 * Application to find the shortest route between cities
 * @author Mehmet Can Gürbüz , Student ID: 2022400177
 * @since Date: 04.04.2024
 */
public class MehmetCanGurbuz {
    public static void main(String[] args) throws FileNotFoundException {
        // reading the file for learn how many city are there
        File file = new File("city_coordinates.txt");
        Scanner scanner = new Scanner(file);
        int counter = 0;
        while (scanner.hasNextLine()){
            scanner.nextLine();
            counter = counter + 1;
        }
        int num_cities = counter;
        // reading the file and creates objects from the city class for all cities in the file
        Scanner scanner3 = new Scanner(file);
        City[] cities = new City[num_cities];
        for(int i = 0;i<num_cities;i++) {
                String line = scanner3.nextLine();
                String[] lineSplit = line.split(",");
                cities[i] = new City(lineSplit[0], Double.parseDouble(lineSplit[1]), Double.parseDouble(lineSplit[2]));
            }
        double[][] distances = new double[num_cities][num_cities];// creating distances array for my Floyd Warshall Matrix
        for (int i = 0 ; i < num_cities ; i++){
            for (int j = 0 ; j < num_cities ; j++){
                distances[i][j] = Double.POSITIVE_INFINITY;// filling my matrix with infinity numbers for my Floyd Warshall algorithms
            }
        }
        File file1 = new File("city_connections.txt");// reading the connections file to calculates roads between cities
        Scanner sc = new Scanner(file1);
        while (sc.hasNextLine()){
            double x0 = 0;
            double y0 = 0;
            double x1 = 0;
            double y1 = 0;
            String line = sc.nextLine();
            String[] lineSplit = line.split(",");
            // create a and b variable to hold my distances values in my distances array
            int a = 0;
            int b = 0;
            for(int i = 0 ; i < num_cities ; i++){
                if(Objects.equals(lineSplit[0], cities[i].getName())){
                    x0 = cities[i].getX();
                    y0 = cities[i].getY();
                    a = i;
                    break;
                }
            }
            for(int j = 0 ; j < num_cities ; j++) {
                if (Objects.equals(lineSplit[1], cities[j].getName())) {
                    x1 = cities[j].getX();
                    y1 = cities[j].getY();
                    b = j;
                    break;
                }
            }
            // fill my distances array with proper road distances calculations between all roads in one way
            distances[a][b] = Math.sqrt(Math.pow(Math.abs(x0-x1),2) + Math.pow(Math.abs(y0-y1),2));
        }
        File file2 = new File("city_connections.txt");// reading the connections file to calculates roads between cities
        Scanner scanner1 = new Scanner(file2);
        while (scanner1.hasNextLine()){
            double x0 = 0;
            double y0 = 0;
            double x1 = 0;
            double y1 = 0;
            String line = scanner1.nextLine();
            String[] lineSplit = line.split(",");
            // create a and b variable to hold my distances values in my distances array
            int a = 0;
            int b = 0;
            for(int i = 0 ; i < num_cities ; i++){
                if(Objects.equals(lineSplit[1], cities[i].getName())){
                    x0 = cities[i].getX();
                    y0 = cities[i].getY();
                    a = i;
                    break;
                }
            }
            for(int j = 0 ; j < num_cities ; j++) {
                if (Objects.equals(lineSplit[0], cities[j].getName())) {
                    x1 = cities[j].getX();
                    y1 = cities[j].getY();
                    b = j;
                    break;
                }
            }
            // fill my distances array with proper road distances calculations between all roads in composite ways
            distances[a][b] = Math.sqrt(Math.pow(Math.abs(x0-x1),2) + Math.pow(Math.abs(y0-y1),2));

        }
        // arrange my array values by multiply with 1000 and turn into longs for calculation mistakes
        for(int i = 0 ; i < num_cities ; i++){
            for (int j = 0 ; j < num_cities ; j++){
                if (distances[i][j] != Double.POSITIVE_INFINITY){
                    distances[i][j] = (long)(Math.round((distances[i][j] * 1000)) );

                }
                if(i == j){
                    distances[i][j] = 0; // arrange my array variable for same cities to zero
                }
            }
        }
        findTheShortest(cities,distances); // go into Floyd Warshall Alghorithm
    }
    public static void findTheShortest(City[] cities ,double[][] dist ) throws FileNotFoundException {
        // create a method for calculating all distances between every city by using our road distances values and create a new distances array
        // reading the file for learn how many city are there
        File file = new File("city_coordinates.txt");
        Scanner scanner = new Scanner(file);
        int counter = 0;
        while (scanner.hasNextLine()){
            scanner.nextLine();
            counter = counter + 1;
        }
        int num_cities = counter;

        for (int k = 0; k < num_cities; k++) {// Iterate through all vertices in the matrix and consider each vertex as a possible intermediate vertex in the paths being examined.
            // For each pair of vertices (a,b), if the shortest path from a to b passing through the current intermediate vertex is shorter than the previously known the shortest path, update the shortest distance.
            for (int i = 0; i < num_cities; i++) {
                // Repeat the main loop for all vertices until all intermediate vertices have been considered.
                for (int j = 0; j < num_cities; j++) {
                    // The resulting matrix contains the shortest distances between all pairs of vertices in the graph.
                    if (dist[i][k] != Double.POSITIVE_INFINITY && dist[k][j] != Double.POSITIVE_INFINITY) {
                        double newDistance = dist[i][k] + dist[k][j];
                        if (newDistance < dist[i][j]) {
                            dist[i][j] = newDistance ;
                        }
                    }
                }
            }
        }
        // take the input for starting city
        Scanner input = new Scanner(System.in);
        String initialcity ;
        String finalcity;
        int first_city = 0; // create an integer variable for learn the starting city index in our matrix array
        int last_city = 0; // create an integer variable for learn the  destination city index in our matrix array
        System.out.print("Enter starting city: ");
        initialcity = input.nextLine();
        while (true) {// create a while loop for entering non exist city name and repeating the input
            boolean isExist = false;
            for (int i = 0; i < num_cities; i++) {
                if (Objects.equals(initialcity.toLowerCase(), cities[i].getName().toLowerCase())) {
                    first_city = i;
                    isExist = true;
                }
            }
            if (!isExist) {
                System.out.println( "City named '" + initialcity + "' not found. Please enter a valid city name.");
                System.out.print("Enter starting city: ");
                initialcity = input.nextLine();
            }else {
                break;
            }
        }
        System.out.print("Enter destination city: ");
        finalcity = input.nextLine();
        while (true) {// create a while loop for entering non exist city name and repeating the input
            boolean isExist = false;
            for (int j = 0; j < num_cities; j++) {
                if (Objects.equals(finalcity.toLowerCase(), cities[j].getName().toLowerCase())) {
                    last_city = j;
                    isExist = true;
                }
            }
            if (!isExist) {
                System.out.println( "City named '" + finalcity + "' not found. Please enter a valid city name.");
                System.out.print("Enter destination city: ");
                finalcity = input.nextLine();
            }else {
                break;
            }
        }
        if (dist[first_city][last_city] == Double.POSITIVE_INFINITY){// if the matrix value is infinity, there is no path to the destination city which is entered
            System.out.print("No path could be found.");
        }else {// make proper adjustment for our path distance among cities for printing
            System.out.print("Total distance:");
            System.out.printf("%.2f", dist[first_city][last_city] / 1000.);
            System.out.print(" ");
            // create a Canvas and set the x and y scales of that canvas
            StdDraw.setCanvasSize(2377/2,1055/2);
            StdDraw.setXscale(0,2377);
            StdDraw.setYscale(0,1055);
            // printing a map png on the background
            StdDraw.picture(2377/2.0,1055/2.0,"map.png",  2377,1055);
            StdDraw.setPenColor(Color.gray);
            // printing the city names and city coordination points
            StdDraw.setFont( new Font("Helvetica", Font.BOLD, 11));
            for(int i =0 ; i < num_cities ; i++){
                StdDraw.filledCircle(cities[i].getX(),cities[i].getY(),5);
                StdDraw.text(cities[i].getX(),cities[i].getY()+14,cities[i].getName());
            }
            // reading the connections file to print the line between the cities
            File file1 = new File("city_connections.txt");
            Scanner sc = new Scanner(file1);
            while (sc.hasNextLine()) {
                double x0 = 0;
                double y0 = 0;
                double x1 = 0;
                double y1 = 0;
                String line = sc.nextLine();
                String[] lineSplit = line.split(",");
                for (int i = 0; i < num_cities; i++) {
                    if (Objects.equals(lineSplit[0], cities[i].getName())) {
                        x0 = cities[i].getX();
                        y0 = cities[i].getY();
                        break;
                    }
                }
                for (int j = 0; j < num_cities; j++) {
                    if (Objects.equals(lineSplit[1], cities[j].getName())) {
                        x1 = cities[j].getX();
                        y1 = cities[j].getY();
                        break;
                    }
                }
                StdDraw.line(x0, y0, x1, y1); // printing the lines between the city coordinates
            }
            printShortestPath(cities, dist, first_city, last_city); // go into the path printing algorithm
        }
    }
    public static void printShortestPath(City[] cities, double[][] distances, int source, int destination) throws FileNotFoundException {
        // create the shortest path method by using our city class , dist array , first city and last city parameters
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE); // arrange our starting city writing and point to blue
        StdDraw.filledCircle(cities[source].getX(),cities[source].getY(),5);
        StdDraw.text(cities[source].getX(),cities[source].getY()+14,cities[source].getName());
        System.out.print("Path: ");
        System.out.print(cities[source].getName()); // print the starting city name

        while (source != destination) { // print next city unless starting city equals destination city
            int nextCity = getNextCity(cities,distances, source, destination); // go into the next city method and equalize return value to next city variable
            System.out.print(" -> " + cities[nextCity].getName()); // print the next city name
            source = nextCity; // equalize the current city as next city
        }
    }

    public static int  getNextCity(City[] cities,double[][] distances, int currentCity, int destination) throws FileNotFoundException {
        // create a method for find the next city for our shortest path
        for (int nextCity = 0; nextCity < distances.length ; nextCity++) {
            // if our next city is not equal to our current and destination city
            // check the distance between current city and destination city
            // if the distance equals the distance between current city and next city plus next city and destination city
            // we can check if the next city is neighbour to the current one.
            if (nextCity != currentCity && nextCity != destination && distances[currentCity][destination] == distances[currentCity][nextCity] +
                    distances[nextCity][destination]) {
                boolean isNeighbour = false; // create a boolean for neighbour cities
                // reading the file to check the next city is neighbour to the current one
                File file2 = new File("city_connections.txt");
                Scanner scanner1 = new Scanner(file2);
                while (scanner1.hasNextLine()) {
                    String line = scanner1.nextLine();
                    String[] lineSplit = line.split(",");
                    if (Objects.equals(lineSplit[1], cities[currentCity].getName())) {
                        if(Objects.equals(lineSplit[0], cities[nextCity].getName()))
                            isNeighbour = true;


                        }
                    if (Objects.equals(lineSplit[0], cities[currentCity].getName())) {
                        if(Objects.equals(lineSplit[1], cities[nextCity].getName()))
                            isNeighbour = true;

                    }
                }

                if(isNeighbour){ // if the cities are neighbour we can draw them to our map
                    // create points lines and writing on our StdDraw map
                    StdDraw.filledCircle(cities[currentCity].getX(),cities[currentCity].getY(),5);
                    StdDraw.text(cities[currentCity].getX(),cities[currentCity].getY()+14,cities[currentCity].getName());
                    StdDraw.filledCircle(cities[nextCity].getX(),cities[nextCity].getY(),5);
                    StdDraw.text(cities[nextCity].getX(),cities[nextCity].getY()+14,cities[nextCity].getName());
                    StdDraw.filledCircle(cities[destination].getX(),cities[destination].getY(),5);
                    StdDraw.text(cities[destination].getX(),cities[destination].getY()+14,cities[destination].getName());
                    StdDraw.setPenRadius(0.007);
                    // drawing lines between current city and next city
                    StdDraw.line(cities[currentCity].getX() , cities[currentCity].getY() , cities[nextCity].getX() ,cities[nextCity].getY()  );

                    return nextCity; // return the value for our printTheShortestPath method above
                }
            }
            // check the next city and destination city is equal and is neighbour to each other
            if(nextCity != currentCity && nextCity == destination && distances[currentCity][destination] == distances[currentCity][nextCity] +
                    distances[nextCity][destination]){
                boolean isNeighbour1 = false;
                File file2 = new File("city_connections.txt");
                Scanner scanner1 = new Scanner(file2);
                while (scanner1.hasNextLine()) {
                    String line = scanner1.nextLine();
                    String[] lineSplit = line.split(",");
                    if (Objects.equals(lineSplit[1], cities[currentCity].getName())) {
                        if(Objects.equals(lineSplit[0], cities[destination].getName()))
                            isNeighbour1 = true;
                    }
                    if (Objects.equals(lineSplit[0], cities[currentCity].getName())) {
                        if(Objects.equals(lineSplit[1], cities[destination].getName()))
                            isNeighbour1 = true;
                    }
                }
                // if the next city is equal to the destination city we draw line for current city between destination city
                if(isNeighbour1){
                    StdDraw.line(cities[currentCity].getX() , cities[currentCity].getY() , cities[destination].getX() ,cities[destination].getY());
                }
            }
        }
        return destination;// if there are no next city to go, the method return the destination city
    }
}
