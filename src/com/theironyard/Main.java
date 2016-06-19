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
        Statement stmtdrop = conn.createStatement();
        stmtdrop.execute("DROP TABLE IF EXISTS toppings");
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, username VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS pizzas (id IDENTITY, ordername VARCHAR, size VARCHAR, crust VARCHAR, sauce VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS toppings (id IDENTITY, topping VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS builtpizza (id IDENTITY, pizza_id INT, topping_id INT)");
    }

    public static void populateToppings(Connection conn) throws SQLException {


        PreparedStatement stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'pepperoni')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'buffalo chicken')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'bacon')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'sausage')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'ham')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'peppers')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'mushroom')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'onion')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'olive')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'pineapple')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'mozzarella')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'cheddar')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'gouda')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'parmesan')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'jack')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'tomato')");
        stmt.execute();
        stmt = conn.prepareStatement("INSERT INTO toppings VALUES(NULL, 'basil')");
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

    public static int insertTopping(Connection conn, Toppings topping) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO toppings VALUES (NULL, ?)");
        stmt.setString(1, topping.topping);
        stmt.execute();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public static int insertPizza(Connection conn, Pizza pizza) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO pizzas VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, pizza.orderName);
        stmt.setString(2, pizza.size);
        stmt.setString(3, pizza.crust);
        stmt.setString(4, pizza.sauce);

        stmt.execute();
        int pizzaId = -1;
        //returns inserted pizza's id
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            pizzaId = rs.getInt(1);
        }
        for (Toppings t : pizza.topping) {
            int toppingId = insertTopping(conn, t);
            insertBuiltPizza(conn, pizzaId, toppingId);
        }


        return pizzaId;
    }

    public static void insertBuiltPizza (Connection conn, int pizzaId, int toppingId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO builtpizza VALUES (NULL, ?, ?)");
        stmt.setInt(1, pizzaId);
        stmt.setInt(2,toppingId);
        stmt.execute();
    }

//    public static void insertBuiltPizza(Connection conn, ArrayList<Toppings> toppings, int pizzaId) throws SQLException {
//
//
//        //select toppings method/get topping ids
//        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM toppings");
//        ResultSet results = stmt1.executeQuery();
//        while (results.next()) {
//            int toppingId = results.getInt("id");
//            String name = results.getString("topping");
//
//            int size = toppings.size();
//
//            for (int i = 0; i <size; i++) {
//                Toppings temp = toppings.get(i);
//                if (name.equalsIgnoreCase(temp.topping)) {
//                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO builtpizza VALUES (NULL, ?, ?)");
//
//                    stmt.setInt(1, pizzaId);
//                    stmt.setInt(2, toppingId);
//                    stmt.execute();
//                }
//            }











            /*
            switch (topping) {
                case ("meat"):
                    toppingTemp = new Toppings(1, "meat");
                    break;
                case ("veggie"):
                    toppingTemp = new Toppings(2, "veggie");
                    break;
                case ("cheese"):
                    toppingTemp = new Toppings(3, "cheese");
                    break;
            }
        }
            Integer toppingInt = null;
            int size = toppings.size();

        for (int i = 0; i<size; i++) {
            Toppings name = toppings.get(i);
            if (name.topping.equals(toppingTemp.topping)) {
                toppingInt = 1;
            }
            else if (name.topping.equals(toppingTemp.topping)) {
                toppingInt = 2;
            }
            else if (name.topping == toppingTemp.topping) {
                toppingInt = 3;
            }*/




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
            ArrayList<Toppings> toppings = new ArrayList<>();

            stmt = conn.prepareStatement("SELECT * FROM toppings INNER JOIN builtpizza ON builtpizza.topping_id = toppings.id WHERE builtpizza.pizza_id =?");
            stmt.setInt(1, id);
            ResultSet resultsTop = stmt.executeQuery();
            while (resultsTop.next()) {
                int topId = resultsTop.getInt("id");
                String topping = resultsTop.getString("topping");
                toppings.add(new Toppings(topId, topping));
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

        deleteBuiltPizza(conn, id);
    }

    public static void deleteBuiltPizza(Connection conn, int pizzaId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM builtpizza WHERE pizza_id = ?");
        stmt.setInt(1, pizzaId);
        stmt.execute();
    }

    public static void updatePizza (Connection conn, Pizza pizza) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE pizzas SET size = ?, crust = ?, sauce = ?, ordername = ? WHERE id = ?");
        stmt.setString(1, pizza.orderName);
        stmt.setString(2, pizza.size);
        stmt.setString(3, pizza.crust);
        stmt.setString(4, pizza.sauce);
        stmt.setInt(5, pizza.id);
        stmt.execute();

        deleteBuiltPizza(conn, pizza.id);

        //insertBuiltPizza(conn, pizza.topping, pizza.id);


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

        Spark.put(
                "/pizza/:id",
                (request, response) -> {
                    int id = Integer.valueOf(request.params(":id"));
                    String body = request.body();
                    JsonParser p = new JsonParser();
                    Pizza pizza = p.parse(body, Pizza.class);
                    updatePizza(conn, pizza);
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

        Spark.put(
                "/pizza/:id",
                (request, response) -> {
                    int id = Integer.valueOf(request.params(":id"));
                    String body = request.body();
                    JsonParser p = new JsonParser();
                    Pizza pizza = p.parse(body, Pizza.class);
                    updatePizza(conn, pizza);
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
