/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qantuw.s14pc2.com_2312044.View;

/**
 *
 * @author ASUS
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import qantuw.s14pc2.com_2312044.Controller.Controller;
import qantuw.s14pc2.com_2312044.Model.Gasto;

public class GastoView extends JFrame  {

    private Controller controller;

    // Componentes del formulario
    private JTextField descripcionField;
    private JComboBox<String> categoriaCombo;
    private JTextField montoField;
    private JTextField fechaField;
    private JTable gastosTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filtroCategoriaCombo;
    private JTextArea resumenArea;

    public GastoView(Controller controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Gestión de Gastos Personales");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel superior con el formulario de registro
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        formPanel.add(new JLabel("Descripción:"));
        descripcionField = new JTextField();
        formPanel.add(descripcionField);

        formPanel.add(new JLabel("Categoría:"));
        categoriaCombo = new JComboBox<>(new String[]{"Alimentación", "Transporte", "Entretenimiento", "Salud", "Otros"});
        formPanel.add(categoriaCombo);

        formPanel.add(new JLabel("Monto (S/):"));
        montoField = new JTextField();
        formPanel.add(montoField);

        formPanel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        fechaField = new JTextField();
        formPanel.add(fechaField);

        add(formPanel, BorderLayout.NORTH);

        // Panel con botones de acciones
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton registrarButton = new JButton("Registrar Gasto");
        registrarButton.addActionListener(e -> registrarGasto());
        buttonPanel.add(registrarButton);

        JButton listarButton = new JButton("Listar Gastos");
        listarButton.addActionListener(e -> listarGastos());
        buttonPanel.add(listarButton);

        JButton limpiarButton = new JButton("Limpiar");
        limpiarButton.addActionListener(e -> limpiarCampos());
        buttonPanel.add(limpiarButton);

        JButton resumenButton = new JButton("Mostrar Resumen");
        resumenButton.addActionListener(e -> mostrarResumen());
        buttonPanel.add(resumenButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Panel para mostrar la tabla de gastos
        JPanel tablePanel = new JPanel(new BorderLayout());

        String[] columnNames = {"ID", "Descripción", "Categoría", "Monto (S/)", "Fecha"};
        tableModel = new DefaultTableModel(columnNames, 0);
        gastosTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(gastosTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Filtro de Categoría para la tabla
        filtroCategoriaCombo = new JComboBox<>(new String[]{"Todas", "Alimentación", "Transporte", "Entretenimiento", "Salud", "Otros"});
        filtroCategoriaCombo.addActionListener(e -> filtrarGastosPorCategoria());
        tablePanel.add(filtroCategoriaCombo, BorderLayout.NORTH);

        add(tablePanel, BorderLayout.WEST);

        // Área de resumen de gastos
        resumenArea = new JTextArea(5, 30);
        resumenArea.setEditable(false);
        JScrollPane resumenScrollPane = new JScrollPane(resumenArea);
        add(resumenScrollPane, BorderLayout.SOUTH);
    }

    private void registrarGasto() {
        try {
            String descripcion = descripcionField.getText();
            String categoria = (String) categoriaCombo.getSelectedItem();
            double monto = Double.parseDouble(montoField.getText());
            LocalDate fecha = LocalDate.parse(fechaField.getText());

            controller.registrarGasto(descripcion, categoria, monto, fecha);
            JOptionPane.showMessageDialog(this, "Gasto registrado con éxito", "Registro de Gasto", JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
            listarGastos();  // Actualizamos la lista de gastos
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarGastos() {
        List<Gasto> gastos = controller.listarGastos();
        actualizarTablaGastos(gastos);
    }

    private void filtrarGastosPorCategoria() {
        String categoriaFiltro = (String) filtroCategoriaCombo.getSelectedItem();
        if ("Todas".equals(categoriaFiltro)) {
            listarGastos();
        } else {
            List<Gasto> gastosFiltrados = controller.listarGastosPorCategoria(categoriaFiltro);
            actualizarTablaGastos(gastosFiltrados);
        }
    }

    private void actualizarTablaGastos(List<Gasto> gastos) {
        tableModel.setRowCount(0); // Limpiar la tabla antes de actualizar

        for (Gasto gasto : gastos) {
            tableModel.addRow(new Object[]{
                    gasto.getId(), gasto.getDescripcion(), gasto.getCategoria(),
                    gasto.getMonto(), gasto.getFechaGasto()
            });
        }
    }

    private void limpiarCampos() {
        descripcionField.setText("");
        montoField.setText("");
        fechaField.setText("");
    }

    private void mostrarResumen() {
        double totalGastos = controller.obtenerMontoTotalMensual();
        List<String> resumenPorCategoria = controller.obtenerResumenPorCategoria();

        StringBuilder resumen = new StringBuilder();
        resumen.append("Total de Gastos: S/").append(totalGastos).append("\n\n");
        resumen.append("Gastos por Categoría:\n");

        for (String categoria : resumenPorCategoria) {
            resumen.append(categoria).append("\n");
        }

        resumenArea.setText(resumen.toString());
    }
}


