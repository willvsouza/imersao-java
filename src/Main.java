import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://imdb-api.com/en/API/Top250Movies/k_63q3moh2";

        // faz uma conexão HTTP e busca as top 250 séries de TV
        //String url = "https://imdb-api.com/en/API/Top250TVs/k_63q3moh2";

        // faz uma conexão HTTP e busca os filmes mais populares
        //String url = "https://imdb-api.com/en/API/MostPopularMovies/k_63q3moh2";

        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        //System.out.println(body);

        // pegar só os dados que interessam (título, poster e classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        //System.out.println("Total de filmes retornados: "+listaDeFilmes.size());
        //System.out.println(listaDeFilmes.get(0));

        // exibir e manipular os dados
        GeradorDeStickers geradora = new GeradorDeStickers();
        for (Map<String,String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            String nomeArquivo = titulo + ".png";

            InputStream inputStream = new URL(urlImagem).openStream();

            geradora.cria(inputStream, nomeArquivo);

            System.out.println("Classificação: "+filme.get("imDbRating"));
            System.out.println();
        }
    }

}