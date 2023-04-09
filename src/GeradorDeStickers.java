import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GeradorDeStickers {

    public void cria(InputStream inputStream, String nomeArquivo, String texto, InputStream inputStreamSobreposicao) throws IOException {

        // fazer a leitura da imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // criar nova imagem em memória com transparência e redimensionada
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original para nova imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // extraindo a imagem para sobreposição
        BufferedImage imagemSobreposicao = ImageIO.read(inputStreamSobreposicao);
        int posicaoImagemSobreposicaoY = novaAltura -imagemSobreposicao.getHeight();
        graphics.drawImage(imagemSobreposicao, 0, posicaoImagemSobreposicaoY, null);

        // configurar a fonte
        Font fonte = new Font(Font.SANS_SERIF, Font.BOLD, 80);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        // escrever uma frase na nova imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
        int larguraTexto = (int) retangulo.getWidth();
        int posicaoTextoX = (largura - larguraTexto) / 2;
        int posicaoTextoY = novaAltura - 60;
        graphics.drawString(texto, posicaoTextoX, posicaoTextoY);

        // faz o contorno no texto
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        TextLayout textLayout = new TextLayout(texto, fonte, fontRenderContext);

        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        BasicStroke outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);

        // escrever a nova imagem num arquivo
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));
    }
}