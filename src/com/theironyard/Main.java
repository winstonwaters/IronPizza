package com.theironyard;

import jodd.json.JsonSerializer;
import org.h2.tools.Server;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, username VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS pizzas (id IDENTITY, size VARCHAR, crust VARCHAR, sauce VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS toppings (id IDENTITY, topping VARCHAR");
        stmt.execute("CREATE TABLE IF NOT EXISTS builtpizza (id IDENTITY, pizza_id INT, topping_id INT)");
    }

    public static void insertUser(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?)");
        stmt.setString(1, user.username);
        stmt.execute();
    }

    public static ArrayList<User> selectUsers(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
        ResultSet results = stmt.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (results.next()) {
            Integer id = results.getInt("id");
            String username = results.getString("username");
            User user = new User(id, username);
            users.add(user);
        }
        return users;
    }

    public static int insertPizza(Connection conn, Pizza pizza) throws SQLException {
        // return int whenever Zach sends code
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO pizzas VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, pizza.size);
        stmt.setString(2, pizza.crust);
        stmt.setString(3, pizza.sauce);
        stmt.setInt(4, pizza.userId);
        stmt.execute();

        //returns inserted pizza's id
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public static void insertBuiltPizza(Connection conn, ArrayList<Toppings> toppings, int pizzaId) throws SQLException {


        Toppings meat = null;
        Toppings veggie = null;
        Toppings cheese = null;

        //select toppings method/get topping ids
        PreparedStatement stmt1 = conn.prepareStatement("SELECT topping_id FROM toppings");
        ResultSet results = stmt1.executeQuery();
        while (results.next()) {
            int toppingId = results.getInt("topping_id");
            switch (toppingId) {
                case 1:
                    meat = new Toppings(1, "meat");
                    break;
                case 2:
                    veggie = new Toppings(2, "veggie");
                    break;
                case 3:
                    cheese = new Toppings(3, "cheese");
                    break;
            }
        }
            Integer toppingInt = null;
            int size = toppings.size();
        for (int i = 0; i<size; i++) {
            Toppings name = toppings.get(i);
            if (name.topping == meat.topping) {
                toppingInt = 1;
            }
            else if (name.topping == veggie.topping) {
                toppingInt = 2;
            }
            else if (name.topping == cheese.topping) {
                toppingInt = 3;
            }

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO builtpizza VALUES (NULL, ?, ?)");

            stmt.setInt(1, pizzaId);
            stmt.setInt(2, toppingInt);
            stmt.execute();
        }
    }


    //"SELECT * FROM restaurants INNER JOIN users ON restaurants.user_id = users.id WHERE users.id= ?"
    public static ArrayList<Pizza> selectPizzas (Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pizzas");
        ResultSet results = stmt.executeQuery();
        ArrayList<Pizza> pizzas = new ArrayList<>();
        while (results.next()) {
            int id = results.getInt("id");
            String size = results.getString("size");
            String crust = results.getString("crust");
            String sauce = results.getString("sauce");

            stmt = conn.prepareStatement("SELECT topping FROM pizzas INNER JOIN toppings ON pizzas.");

            //remove later*****
            ArrayList<Toppings> toppings = new ArrayList<>();
            // remove ^^

            Pizza p = new Pizza(id, size, crust, sauce, 0, toppings);
            pizzas.add(p);
        }
        return pizzas;
    }




    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);

        Spark.externalStaticFileLocation("public");
        Spark.init();


        Spark.get(
                "/pizza",
                (request, response) ->{
                    ArrayList<Pizza> pizzas = selectPizzas(conn);
                    JsonSerializer s = new JsonSerializer();
                    return s.serialize(pizzas);
                }
        );
    }
}
