package br.com.ifba.usuario.view;

import br.com.ifba.usuario.entity.Usuario;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// Tela de cadastro e edição de Usuario (apenas componentes, sem persistência)
public class UsuarioFormulario extends JDialog {

    private final Usuario usuario; // null = cadastro, preenchido = edição

    private final JTextField txtNome = new JTextField(20);
    private final JTextField txtCpf = new JTextField(20);
    private final JComboBox<String> cbGenero =
            new JComboBox<>(new String[]{"Masculino", "Feminino", "Outro"});
    private final JTextField txtNascimento = new JTextField(20);
    private final JTextField txtTelefone = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtLogin = new JTextField(20);
    private final JPasswordField txtSenha = new JPasswordField(20);

    // Cadastro
    public UsuarioFormulario(Frame pai) {
        this(pai, null);
    }

    // Edição (ou cadastro, se usuario for null)
    public UsuarioFormulario(Frame pai, Usuario usuario) {
        super(pai, usuario == null ? "Novo usuário" : "Editar usuário", true);
        this.usuario = usuario;
        montarTela();
        if (usuario != null) {
            preencherCampos();
        }
        pack();
        setLocationRelativeTo(pai);
    }

    private void montarTela() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 10, 5, 10);
        g.anchor = GridBagConstraints.WEST;

        String[] rotulos = {"Nome", "CPF", "Gênero", "Nascimento", "Telefone",
                            "E-mail", "Login", "Senha"};
        Component[] campos = {txtNome, txtCpf, cbGenero, txtNascimento,
                              txtTelefone, txtEmail, txtLogin, txtSenha};

        for (int i = 0; i < rotulos.length; i++) {
            g.gridx = 0;
            g.gridy = i;
            painel.add(new JLabel(rotulos[i] + ":"), g);
            g.gridx = 1;
            painel.add(campos[i], g);
        }

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        JPanel botoes = new JPanel();
        botoes.add(btnSalvar);
        botoes.add(btnCancelar);

        g.gridx = 0;
        g.gridy = rotulos.length;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        painel.add(botoes, g);

        setContentPane(painel);

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void preencherCampos() {
        txtNome.setText(usuario.getNome());
        txtCpf.setText(usuario.getCpf());
        cbGenero.setSelectedItem(usuario.getGenero());
        txtNascimento.setText(usuario.getDataNascimento());
        txtTelefone.setText(usuario.getTelefone());
        txtEmail.setText(usuario.getEmail());
        txtLogin.setText(usuario.getLogin());
        // A senha não é exibida na edição
    }

    private void salvar() {
        if (txtNome.getText().isBlank() || txtLogin.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Nome e login são obrigatórios.");
            return;
        }
        // TODO: a persistência (salvar/atualizar no banco) fica para a Parte 2
        JOptionPane.showMessageDialog(this, "Tela pronta. Persistência na Parte 2.");
        dispose();
    }
}