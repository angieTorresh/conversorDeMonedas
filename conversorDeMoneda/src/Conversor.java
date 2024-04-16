import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Conversor {
    public static void main(String[] args) {
        List<Moneda> historial = new ArrayList<>();

        while(true) {
            String entrada = JOptionPane.showInputDialog(null, """
                Bienvenido al conversor de Moneda.
                Por favor ingrese los códigos de la moneda a convertir, la moneda resultante, y el valor a convertir.
                Ejemplo: USD COP 1.0
                Para salir, escriba 'salir'.
                Para ver el historial de conversiones, escriba 'historial'.
                    """);

            if (entrada.equalsIgnoreCase("salir")) {
                break;
            } else if (entrada.equalsIgnoreCase("historial")) {
                if (historial.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay conversiones en el historial.");
                } else {
                    StringBuilder historialString = new StringBuilder();
                    for (Moneda moneda : historial) {
                        historialString.append(moneda.monedaOrigen()).append(" a ").append(moneda.monedaDestino()).append(": ")
                                .append(moneda.valor()).append(" = ").append(moneda.conversion_result()).append(" el ")
                                .append(moneda.fechaHora()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, historialString);
                }
            } else {
                try {
                String[] datos = entrada.toUpperCase(Locale.ROOT).split(" ");
                String monedaOrigen = datos[0];
                String monedaDestino = datos[1];
                float cantidad = Float.parseFloat(datos[2]);
                if (monedaOrigen.length() != 3 || monedaDestino.length() != 3) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese códigos de moneda válidos.");
                    continue;
                }
                ConvertirMoneda busqueda = new ConvertirMoneda();
                Moneda moneda = busqueda.convertirMoneda(monedaOrigen, monedaDestino, cantidad);
                historial.add(moneda);
                if (moneda.conversion_result() == 0.0f && cantidad != 0.0f) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error. Por favor verifique los datos ingresados.");
                } else {
                    JOptionPane.showMessageDialog(null, cantidad + " " + monedaOrigen + " = " + moneda.conversion_result()+ " " + monedaDestino);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error. Por favor verifique los datos ingresados.");
            }
            }
        }
    }
}