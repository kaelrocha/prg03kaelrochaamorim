package br.com.ifba.usuario.view;

import br.com.ifba.usuario.entity.Usuario;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

// Tela de listagem de Usuario (apenas componentes, sem persistência)
public class UsuarioListar extends JFrame {

    private static final int COL_EDITAR = 3;
    private static final int COL_REMOVER = 4;

    private final List<Usuario> usuarios = new ArrayList<>();
    private final JTextField txtBusca = new JTextField(20);
    private final JButton btnAdicionar = new JButton("+ Novo usuário");
    private final DefaultTableModel modelo;
    private final JTable tabela;
    private final TableRowSorter<DefaultTableModel> sorter;

    public UsuarioListar() {
        super("Usuários");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 420);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(
                new Object[]{"NOME", "LOGIN", "E-MAIL", "EDITAR", "REMOVER"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        tabela.setRowHeight(28);
        sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        // Dados fictícios (sem persistência ainda)
        usuarios.add(criar("Ana Souza", "ana", "ana@email.com"));
        usuarios.add(criar("Bruno Lima", "bruno", "bruno@email.com"));
        usuarios.add(criar("Carla Dias", "carla", "carla@email.com"));
        carregarTabela();

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
    }

    private Usuario criar(String nome, String login, String email) {
        Usuario u = new Usuario(nome, null, login, "senha-ficticia");
        u.setEmail(email);
        return u;
    }

    // Senha, CPF, telefone e nascimento ficam de fora da listagem
    private void carregarTabela() {
        modelo.setRowCount(0);
        for (Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                u.getNome(), u.getLogin(), u.getEmail(), "Editar", "Remover"});
        }
    }

    private void configurarEventos() {
        // Busca: filtra as linhas exibidas
        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            private void filtrar() {
                String texto = txtBusca.getText().trim();
                sorter.setRowFilter(texto.isEmpty() ? null
                        : RowFilter.regexFilter("(?i)" + Pattern.quote(texto)));
            }
            @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrar(); }
        });

        // Abre o formulário de cadastro
        btnAdicionar.addActionListener(e ->
                new UsuarioFormulario(this).setVisible(true));

        // Cliques nas colunas Editar / Remover
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaVisual = tabela.rowAtPoint(e.getPoint());
                int coluna = tabela.columnAtPoint(e.getPoint());
                if (linhaVisual < 0) return;
                int linha = tabela.convertRowIndexToModel(linhaVisual);
                Usuario selecionado = usuarios.get(linha);

                if (coluna == COL_EDITAR) {
                    new UsuarioFormulario(UsuarioListar.this, selecionado).setVisible(true);
                } else if (coluna == COL_REMOVER) {
                    int opcao = JOptionPane.showConfirmDialog(
                            UsuarioListar.this,
                            "Deseja excluir o usuário " + selecionado.getNome() + "?",
                            "Confirmar exclusão",
                            JOptionPane.YES_NO_OPTION);
                    if (opcao == JOptionPane.YES_OPTION) {
                        // TODO: exclusão real fica para a Parte 2
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new UsuarioListar().setVisible(true));
    }
}