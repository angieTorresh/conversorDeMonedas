import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertirMoneda {
    Moneda convertirMoneda(String monedaOrigen, String monedaDestino, float valor) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/6dc66471e0a1e2d02a82ad97/pair/" +
                        monedaOrigen + "/" + monedaDestino + "/" + valor))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        LocalDateTime fechaHoraConversion = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fechaHoraFormateada = fechaHoraConversion.format(formatter);

        Moneda moneda = new Gson().fromJson(response.body(), Moneda.class);
        return new Moneda(monedaOrigen, monedaDestino, valor, moneda.conversion_result(), fechaHoraFormateada);
    }
}
