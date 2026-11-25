package br.com.ifba.usuario.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.entity.Usuario;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

@Component
@Scope("prototype")
public class UsuarioListar extends JFrame {

    private final UsuarioIController controller;
    private final ObjectProvider<UsuarioFormulario> formularios;
    private final JTextField txtBusca = new JTextField(20);
    private final JButton btnAdicionar = new JButton("+ Novo usuário");
    private final UsuarioTabelaModelo modelo;
    private final JTable tabela;

public UsuarioListar(UsuarioIController controller) {
    this(controller, null);
}

@Autowired
public UsuarioListar(UsuarioIController controller, ObjectProvider<UsuarioFormulario> formularios) {
    super("Usuários");
    this.controller = controller;
    this.formularios = formularios;
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(700, 420);
    setLocationRelativeTo(null);

    modelo = new UsuarioTabelaModelo();
    tabela = new JTable(modelo);
    tabela.setRowHeight(28);
    tabela.setRowSorter(new TableRowSorter<>(modelo));

    JPanel topo = new JPanel(new BorderLayout(10, 10));
    topo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
    topo.add(txtBusca, BorderLayout.WEST);
    topo.add(btnAdicionar, BorderLayout.EAST);

    JPanel centro = new JPanel(new BorderLayout());
    centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    centro.add(new JScrollPane(tabela), BorderLayout.CENTER);

    add(topo, BorderLayout.NORTH);
    add(centro, BorderLayout.CENTER);

    configurarEventos();
    recarregar();
}

// Lista todos, ou filtra por nome, e atualiza a tabela.
// É público porque o formulário chama depois de salvar.
public void recarregar() {
    try {
        String texto = txtBusca.getText().trim();
        modelo.atualizarUsuarios(texto.isEmpty() ? controller.findAll() : controller.findByNome(texto));
    } catch (PersistenciaException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

private void abrirFormulario(Usuario usuario) {
    UsuarioFormulario formulario = formularios == null
            ? new UsuarioFormulario(this, controller, usuario)
            : formularios.getObject(this, controller, usuario);
    formulario.setVisible(true);
}

private void configurarEventos() {
    txtBusca.getDocument().addDocumentListener(new DocumentListener() {
        @Override public void insertUpdate(DocumentEvent e) { recarregar(); }
        @Override public void removeUpdate(DocumentEvent e) { recarregar(); }
        @Override public void changedUpdate(DocumentEvent e) { recarregar(); }
    });

    btnAdicionar.addActionListener(e ->
        abrirFormulario(null));

    tabela.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int linhaVisual = tabela.rowAtPoint(e.getPoint());
            int coluna = tabela.columnAtPoint(e.getPoint());
        if (linhaVisual < 0) return;
            int linha = tabela.convertRowIndexToModel(linhaVisual);
            Usuario selecionado = modelo.usuarioNaLinha(linha);

        if (coluna == UsuarioTabelaModelo.COL_EDITAR) {
                abrirFormulario(selecionado);
            } else if (coluna == UsuarioTabelaModelo.COL_REMOVER) {
            int opcao = JOptionPane.showConfirmDialog(
                    UsuarioListar.this,
                    "Deseja excluir o usuário " + selecionado.getNome() + "?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
                try {
                    controller.delete(selecionado);
                    recarregar();
                    } catch (PersistenciaException ex) {
                    JOptionPane.showMessageDialog(UsuarioListar.this, ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    });
}
}