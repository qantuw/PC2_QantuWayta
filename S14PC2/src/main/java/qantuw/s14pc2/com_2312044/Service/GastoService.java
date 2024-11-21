/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qantuw.s14pc2.com_2312044.Service;

import java.time.LocalDate;
import java.util.List;
import qantuw.s14pc2.com_2312044.Model.Gasto;
import qantuw.s14pc2.com_2312044.Repository.GastoRepository;
/**
 *
 * @author ASUS
 */
public class GastoService {


    public final GastoRepository repository;
    private static final double LIMITE_MENSUAL = 5000.0;

    public GastoService(GastoRepository repository) {
        this.repository = repository;
    }

    public void registrarGasto(String descripcion, String categoria, double monto, LocalDate fechaGasto) {
        validarCategoria(categoria);
        validarMonto(monto);
        validarLimiteMensual(monto, fechaGasto);

        Gasto gasto = new Gasto.Builder()
                .setDescripcion(descripcion)
                .setCategoria(categoria)
                .setMonto(monto)
                .setFechaGasto(fechaGasto)
                .build();
        repository.save(gasto);
    }

    private void validarCategoria(String categoria) {
        if (!List.of("Alimentación", "Transporte", "Entretenimiento", "Salud", "Otros").contains(categoria)) {
            throw new IllegalArgumentException("Categoría no válida: " + categoria);
        }
    }

    private void validarMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0.");
        }
    }

    private void validarLimiteMensual(double monto, LocalDate fechaGasto) {
        double totalMensual = repository.findAll().stream()
                .filter(g -> g.getFechaGasto().getMonth().equals(fechaGasto.getMonth()))
                .mapToDouble(Gasto::getMonto)
                .sum();

        if (totalMensual + monto > LIMITE_MENSUAL) {
            throw new IllegalArgumentException("El total mensual excede el límite de S/5000.");
        }
    }

    public List<Gasto> obtenerResumenMensual() {
        return repository.findAll();
    }
}

