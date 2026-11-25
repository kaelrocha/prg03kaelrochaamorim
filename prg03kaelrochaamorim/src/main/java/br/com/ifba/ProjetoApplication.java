package br.com.ifba;

import br.com.ifba.login.view.TelaLogin;
import java.awt.EventQueue;
import javax.swing.JFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjetoApplication {

    public static void main(String[] args) {
        abrirTela(TelaLogin.class, args);
    }

    static void abrirTela(Class<? extends JFrame> tipoTela, String[] args) {
        ConfigurableApplicationContext contexto =
                new SpringApplicationBuilder(ProjetoApplication.class)
                        .headless(false)
                        .run(args);
        EventQueue.invokeLater(() -> {
            JFrame tela = contexto.getBean(tipoTela);
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);
        });
    }
}
