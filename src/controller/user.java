package controller;

import model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface user {
    boolean saveUser(User user) throws SQLException, ClassNotFoundException;
    ArrayList<User> getAllUsers() throws SQLException, ClassNotFoundException;
    List<String> getUserIds() throws SQLException, ClassNotFoundException;
    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

}
