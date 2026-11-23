package br.com.ifba.usuario.view;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;
import javax.swing.table.DefaultTableModel;

final class UsuarioTabelaModelo extends DefaultTableModel {

    static final int COL_EDITAR = 3;
    static final int COL_REMOVER = 4;

    private List<Usuario> usuarios = List.of();

    UsuarioTabelaModelo() {
        super(new Object[]{"NOME", "LOGIN", "E-MAIL", "EDITAR", "REMOVER"}, 0);
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }

    void atualizarUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        setRowCount(0);
        for (Usuario usuario : usuarios) {
            // Exibe apenas os campos necessários para identificar o usuário na lista.
            addRow(new Object[]{
                usuario.getNome(), usuario.getLogin(), usuario.getEmail(), "Editar", "Remover"
            });
        }
    }

    Usuario usuarioNaLinha(int linha) {
        return usuarios.get(linha);
    }
}
