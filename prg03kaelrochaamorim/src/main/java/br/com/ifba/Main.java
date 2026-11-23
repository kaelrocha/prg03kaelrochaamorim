package br.com.ifba;

import br.com.ifba.usuario.controller.UsuarioController;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.usuario.service.UsuarioIService;
import br.com.ifba.usuario.service.UsuarioService;
import br.com.ifba.usuario.view.UsuarioListar;

public class Main {

    public static void main(String[] args) {
        UsuarioIDao dao = new UsuarioDao();
        UsuarioIService service = new UsuarioService(dao);
        UsuarioIController controller = new UsuarioController(service);

        java.awt.EventQueue.invokeLater(() -> new UsuarioListar(controller).setVisible(true));
    }
}