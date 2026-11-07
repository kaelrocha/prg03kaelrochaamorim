package br.com.ifba.usuario.view;

import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.dao.UsuarioIDao;

import br.com.ifba.usuario.entity.Usuario;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

// Tela de listagem de Usuario
public class UsuarioListar extends JFrame {

    private static final int COL_EDITAR = 3;
    private static final int COL_REMOVER = 4;

    private List<Usuario> usuarios = new ArrayList<>();
    private final UsuarioIDao dao = new UsuarioDao();
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

    // Busca no banco (todos, ou filtrando por nome com JPQL) e atualiza a tabela.
    // É público porque o formulário chama depois de salvar.
    public void recarregar() {
        try {
            String texto = txtBusca.getText().trim();
            usuarios = texto.isEmpty() ? dao.findAll() : dao.findByNome(texto);
            carregarTabela();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar usuários: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
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
        // Busca: a cada alteração do texto, consulta o banco (JPQL com where)
        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { recarregar(); }
            @Override public void removeUpdate(DocumentEvent e) { recarregar(); }
            @Override public void changedUpdate(DocumentEvent e) { recarregar(); }
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
                          try {
                              dao.delete(selecionado);
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

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new UsuarioListar().setVisible(true));
    }
}