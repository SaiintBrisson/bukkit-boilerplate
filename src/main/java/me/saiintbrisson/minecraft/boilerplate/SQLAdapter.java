package me.saiintbrisson.minecraft.boilerplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQLAdapter<V> {

    V read(ResultSet resultSet) throws SQLException;

    void insert(V element, PreparedStatement statement) throws SQLException;

    void update(V element, PreparedStatement statement) throws SQLException;

}
