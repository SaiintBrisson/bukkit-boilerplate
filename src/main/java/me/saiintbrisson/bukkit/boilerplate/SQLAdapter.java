package me.saiintbrisson.bukkit.boilerplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQLAdapter<V> {

    V read(ResultSet resultSet) throws SQLException;

    void write(V element, PreparedStatement statement) throws SQLException;

}
