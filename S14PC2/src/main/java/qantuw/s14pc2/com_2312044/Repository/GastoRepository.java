/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qantuw.s14pc2.com_2312044.Repository;

/**
 *
 * @author ASUS
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import qantuw.s14pc2.ConexionDataBase;
import qantuw.s14pc2.com_2312044.Model.Gasto;

public class GastoRepository {
    public void save(Gasto gasto) {
        String sql = "INSERT INTO gastos (descripcion, categoria, monto, fecha_gasto) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConexionDataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, gasto.getDescripcion());
            statement.setString(2, gasto.getCategoria());
            statement.setDouble(3, gasto.getMonto());
            statement.setDate(4, Date.valueOf(gasto.getFechaGasto()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el gasto", e);
        }
    }

    public List<Gasto> findAll() {
        List<Gasto> gastos = new ArrayList<>();
        String sql = "SELECT * FROM gastos";
        try (Connection connection = ConexionDataBase.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Gasto gasto = new Gasto.Builder()
                        .setId(resultSet.getInt("id"))
                        .setDescripcion(resultSet.getString("descripcion"))
                        .setCategoria(resultSet.getString("categoria"))
                        .setMonto(resultSet.getDouble("monto"))
                        .setFechaGasto(resultSet.getDate("fecha_gasto").toLocalDate())
                        .build();
                gastos.add(gasto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los gastos", e);
        }
        return gastos;
    }

    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
