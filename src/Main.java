import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
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
        //String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json"; // usando endereço alternativo
        //String url = "https://imdb-api.com/en/API/Top250Movies/k_63q3moh2";
        //ExtratorDeConteudoDoIMDB extrator = new ExtratorDeConteudoDoIMDB();

        // faz uma conexão HTTP e busca imagens da NASA
        String url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2022-06-12&end_date=2022-06-14";
        ExtratorDeConteudoDaNasa extrator = new ExtratorDeConteudoDaNasa();

        ClienteHttp http = new ClienteHttp();
        String json = http.buscaDados(url);

        // cria a pasta "diretorio" se ela já não existir
        File diretorio = new File("posters/");
        diretorio.mkdir();

        // exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        GeradorDeStickers geradora = new GeradorDeStickers();

        //limitando em 2 conteúdos para agilizar os testes
        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo = "posters/" + conteudo.getTitulo() + ".png";
            InputStream inputStreamSobreposicao = new FileInputStream(new File("sobreposicao/top.png"));;

            geradora.cria(inputStream, nomeArquivo, "TESTE", inputStreamSobreposicao);

            System.out.println("Título -> "+conteudo.getTitulo());
            System.out.println();
        }
    }

}