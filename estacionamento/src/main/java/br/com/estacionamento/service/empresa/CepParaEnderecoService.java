package br.com.estacionamento.service.empresa;

import br.com.estacionamento.domain.empresa.Endereco;
import br.com.estacionamento.domain.empresa.ViaCepResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CepParaEnderecoService {
    public Endereco buscarDadosEndereco(String cep, Integer numero) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI("https://viacep.com.br/ws/"+ cep +"/json/")).GET().build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();

        ViaCepResponse viaCepResponse = objectMapper.readValue(response.body(), ViaCepResponse.class);

        Endereco endereco = viaCepResponse.converterParaEndereco();
        endereco.setNumero(numero);
        return endereco;
    }
}
