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
import javax.swing.border.EmptyBorder;
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
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255)); // Fondo claro

        // Espaciado general
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(getContentPane().getBackground());
        add(mainPanel, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Registrar Gasto"));
        formPanel.setBackground(Color.WHITE);

        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(descripcionLabel);
        descripcionField = new JTextField();
        formPanel.add(descripcionField);

        JLabel categoriaLabel = new JLabel("Categoría:");
        categoriaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(categoriaLabel);
        categoriaCombo = new JComboBox<>(new String[]{"Alimentación", "Transporte", "Entretenimiento", "Salud", "Otros"});
        formPanel.add(categoriaCombo);

        JLabel montoLabel = new JLabel("Monto (S/):");
        montoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(montoLabel);
        montoField = new JTextField();
        formPanel.add(montoField);

        JLabel fechaLabel = new JLabel("Fecha (YYYY-MM-DD):");
        fechaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(fechaLabel);
        fechaField = new JTextField();
        formPanel.add(fechaField);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(getContentPane().getBackground());

        JButton registrarButton = createStyledButton("Registrar Gasto", Color.GREEN.darker());
        registrarButton.addActionListener(e -> registrarGasto());
        buttonPanel.add(registrarButton);

        JButton listarButton = createStyledButton("Listar Gastos", Color.BLUE.darker());
        listarButton.addActionListener(e -> listarGastos());
        buttonPanel.add(listarButton);

        JButton limpiarButton = createStyledButton("Limpiar Campos", Color.ORANGE.darker());
        limpiarButton.addActionListener(e -> limpiarCampos());
        buttonPanel.add(limpiarButton);

        JButton resumenButton = createStyledButton("Mostrar Resumen", Color.MAGENTA.darker());
        resumenButton.addActionListener(e -> mostrarResumen());
        buttonPanel.add(resumenButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Listado de Gastos"));
        tablePanel.setBackground(Color.WHITE);

        String[] columnNames = {"ID", "Descripción", "Categoría", "Monto (S/)", "Fecha"};
        tableModel = new DefaultTableModel(columnNames, 0);
        gastosTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(gastosTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        filtroCategoriaCombo = new JComboBox<>(new String[]{"Todas", "Alimentación", "Transporte", "Entretenimiento", "Salud", "Otros"});
        filtroCategoriaCombo.addActionListener(e -> filtrarGastosPorCategoria());
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filtrar por categoría:"));
        filterPanel.add(filtroCategoriaCombo);
        filterPanel.setBackground(Color.WHITE);
        tablePanel.add(filterPanel, BorderLayout.NORTH);

        mainPanel.add(tablePanel, BorderLayout.EAST);

        JPanel resumenPanel = new JPanel(new BorderLayout());
        resumenPanel.setBorder(BorderFactory.createTitledBorder("Resumen de Gastos"));
        resumenPanel.setBackground(Color.WHITE);
        resumenArea = new JTextArea(5, 30);
        resumenArea.setEditable(false);
        resumenArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resumenArea.setBackground(new Color(245, 245, 245));
        resumenPanel.add(new JScrollPane(resumenArea), BorderLayout.CENTER);

        mainPanel.add(resumenPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker(), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        return button;
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
            listarGastos();
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
        tableModel.setRowCount(0);
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
