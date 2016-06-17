package com.theironyard;

import org.h2.tools.Server;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by illladell on 6/16/16.
 */
public class MainTest {

    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Main.createTables(conn);
        return conn;
    }

    @Test
    public void testUser() throws SQLException {
        Connection conn = startConnection();
        User user = new User(1, "Alice");
        Main.insertUser(conn, user);
        ArrayList<User> userList = Main.selectUsers(conn);
        conn.close();
        assertTrue(userList.size() > 0);
    }


    @Test
    public void testInsertBuiltPizza () throws SQLException {
        Connection conn = startConnection();
        Main.populateToppings(conn);
        int pizzaId = 1;
        ArrayList<Toppings> toppings = new ArrayList<>();
        Toppings meat = new Toppings(1, "meat");
        Toppings veggie = new Toppings(2, "veggie");
        Toppings cheese = new Toppings(3, "cheese");
        toppings.add(meat);
        toppings.add(veggie);
        toppings.add(cheese);
        Main.insertBuiltPizza(conn, toppings, pizzaId);

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM builtpizza");
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            Integer toppingInt = results.getInt("topping_id");
            assertTrue(toppingInt != null);
        }
    }

    @Test
    public void testDeleteUser() throws SQLException {
        Connection conn = startConnection();
        Pizza pizza = new Pizza("16", "Stuffed", "Original", "Alice");
        Main.insertPizza(conn, pizza);
        Main.deletePizza(conn, 1);
        ArrayList<Pizza> userArrayList = Main.selectPizzas(conn);
        conn.close();
        assertTrue(userArrayList.size() == 0);
    }

    @Test
    public void testSelectPizzas () throws SQLException {
        Connection conn = startConnection();
        Main.populateToppings(conn);
        String size = "16";
        String crust = "thin";
        String sauce = "marinara";
        String orderName = "Ben";
        Toppings meat = new Toppings (0, "meat");
        ArrayList<Toppings> toppings = new ArrayList<>();
        toppings.add(meat);
        Pizza pizza = new Pizza (size, crust, sauce, orderName, toppings);
        int id = Main.insertPizza(conn, pizza);
        Main.insertBuiltPizza(conn, toppings,id);

        Main.selectPizzas(conn);

        ArrayList<Pizza> pizzaTest = Main.selectPizzas(conn);

        assertTrue(pizzaTest != null);

    }
}