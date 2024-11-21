/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qantuw.s14pc2.com_2312044.Controller;

/**
 *
 * @author ASUS
 */
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import qantuw.s14pc2.com_2312044.Model.Gasto;
import qantuw.s14pc2.com_2312044.Repository.GastoRepository;
import qantuw.s14pc2.com_2312044.Service.GastoService;

public class Controller {

    private final GastoService service;

    public Controller(GastoService gastoService) {
        GastoRepository repository = new GastoRepository();
        this.service = new GastoService(repository);
    }

    public void registrarGasto(String descripcion, String categoria, double monto, LocalDate fecha) {
        service.registrarGasto(descripcion, categoria, monto, fecha);
    }

    public void eliminarGasto(int id) {
        service.repository.delete(id);
    }

    public List<Gasto> listarGastos() {
        return service.obtenerResumenMensual();
    }

    public List<String> obtenerResumen() {
        return listarGastos().stream()
                .collect(Collectors.groupingBy(Gasto::getCategoria, Collectors.summingDouble(Gasto::getMonto)))
                .entrySet().stream()
                .map(entry -> "Categoría: " + entry.getKey() + ", Total: S/" + entry.getValue())
                .collect(Collectors.toList());
    }

    public List<Gasto> listarGastosPorCategoria(String categoriaFiltro) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public double obtenerMontoTotalMensual() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<String> obtenerResumenPorCategoria() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
