import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDaNasa implements ExtratorDeConteudo {

    public List<Conteudo> extraiConteudos(String json) throws FileNotFoundException {

        // pegar só os dados que interessam (título, poster e classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        List<Conteudo> conteudos = new ArrayList<>();

        InputStream sobreposicao = new FileInputStream(new File("sobreposicao/bom.png"));
        // popular a lista de conteúdos
        for (Map<String, String> atributos : listaDeAtributos) {
            String titulo = atributos.get("title");
            String urlImagem = atributos.get("url");

            Conteudo conteudo = new Conteudo(titulo, urlImagem, titulo, sobreposicao);

            conteudos.add(conteudo);
        }

        return conteudos;
    }
}