package com.theironyard;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import org.h2.tools.Server;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, username VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS pizzas (id IDENTITY, size VARCHAR, crust VARCHAR, sauce VARCHAR, ordername VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS toppings (id IDENTITY, topping VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS builtpizza (id IDENTITY, pizza_id INT, topping_id INT)");
    }

    public static void populateToppings(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'meat')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'veggie')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'cheese')");
        stmt.execute();
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
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO pizzas VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, pizza.size);
        stmt.setString(2, pizza.crust);
        stmt.setString(3, pizza.sauce);
        stmt.setString(4, pizza.orderName);
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
        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM toppings");
        ResultSet results = stmt1.executeQuery();
        while (results.next()) {
            String topping = results.getString("topping");
            switch (topping) {
                case ("meat"):
                    meat = new Toppings(1, "meat");
                    break;
                case ("veggie"):
                    veggie = new Toppings(2, "veggie");
                    break;
                case ("cheese"):
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
        ArrayList<Toppings> toppings = new ArrayList<>();
        Toppings topping = new Toppings();
        while (results.next()) {
            int id = results.getInt("id");
            String size = results.getString("size");
            String crust = results.getString("crust");
            String sauce = results.getString("sauce");

            stmt = conn.prepareStatement("SELECT * FROM builtpizza INNER JOIN toppings ON builtpizza.topping_id = toppings.id WHERE builtpizza.pizza_id =?");
            stmt.setInt(1, id);
            ResultSet resultsTop = stmt.executeQuery();
            while (resultsTop.next()) {
                int topId = results.getInt("toppings_id");
                if (topId == 1) {
                    topping = new Toppings(1, "meat");
                }
                else if (topId == 2) {
                    topping = new Toppings(2, "veggie");
                }
                else if (topId == 3) {
                    topping =  new Toppings(3, "cheese");
                }
                toppings.add(topping);

            }



            Pizza p = new Pizza(id, size, crust, sauce, "", toppings);
            pizzas.add(p);
        }
        return pizzas;
    }


    public static void deletePizza(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM pizzas WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();

        stmt = conn.prepareStatement("DELETE FROM builtpizza WHERE pizza_id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);
        populateToppings(conn);

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

        Spark.post(
                "/pizza",
                (request, response) -> {
                    String body = request.body();
                    JsonParser p = new JsonParser();
                    Pizza pizza = p.parse(body, Pizza.class);
                    insertPizza(conn, pizza);
                    return "";
                }
        );

        Spark.delete(
                "/pizza/:id",
                (request, response) -> {
                    int id = Integer.valueOf(request.params(":id"));
                    deletePizza(conn, id);
                    return "";
                }
        );
    }
}
