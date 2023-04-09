import java.io.FileNotFoundException;
import java.util.List;

public interface ExtratorDeConteudo {

    public List<Conteudo> extraiConteudos(String json) throws FileNotFoundException;

}
