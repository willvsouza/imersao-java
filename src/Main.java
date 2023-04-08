import java.io.File;
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

        // cria a pasta "diretorio" se ela já não existir
        File diretorio = new File("posters/");
        diretorio.mkdir();

        // exibir e manipular os dados
        GeradorDeStickers geradora = new GeradorDeStickers();
        for (Map<String,String> filme : listaDeFilmes) {

            // ajusta a url da imagem para acessar a versão em maior qualidade
            String urlImagemPequena = filme.get("image");
            String urlImagem = urlImagemPequena.replace("._V1_UX128_CR0,12,128,176_AL_", "");

            String titulo = filme.get("title");
            String nomeArquivo = "posters/" + titulo + ".png";

            InputStream inputStream = new URL(urlImagem).openStream();
            geradora.cria(inputStream, nomeArquivo);

            System.out.println("Título: "+filme.get("title"));
            System.out.println();
        }
    }

}