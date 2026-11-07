package br.com.ifba.usuario.view;

import br.com.ifba.usuario.dao.UsuarioDao;
import br.com.ifba.usuario.dao.UsuarioIDao;
import br.com.ifba.infrastructure.exception.PersistenciaException;

import br.com.ifba.usuario.entity.Usuario;
import java.awt.Component;
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

// Tela de cadastro e edição de Usuario
public class UsuarioFormulario extends JDialog {

    private final Usuario usuario; // null = cadastro, preenchido = edição
    private final UsuarioListar listar;
    private final UsuarioIDao dao = new UsuarioDao();

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
    public UsuarioFormulario(UsuarioListar pai) {
        this(pai, null);
    }

    // Edição (ou cadastro, se usuario for null)
    public UsuarioFormulario(UsuarioListar pai, Usuario usuario) {
        super(pai, usuario == null ? "Novo usuário" : "Editar usuário", true);
        this.usuario = usuario;
        this.listar = pai;
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
        String senha = new String(txtSenha.getPassword());

        // no cadastro a senha é obrigatória; na edição, vazio = manter a atual
        if (txtNome.getText().isBlank() || txtLogin.getText().isBlank()
                || (usuario == null && senha.isBlank())) {
            JOptionPane.showMessageDialog(this,
                    "Nome e login são obrigatórios (e a senha, no cadastro).");
            return;
        }

        Usuario alvo = (usuario == null) ? new Usuario() : usuario;
        alvo.setNome(txtNome.getText().trim());

        String cpf = txtCpf.getText().trim();
        alvo.setCpf(cpf.isEmpty() ? null : cpf); 

        alvo.setGenero((String) cbGenero.getSelectedItem());
        alvo.setDataNascimento(txtNascimento.getText().trim());
        alvo.setTelefone(txtTelefone.getText().trim());
        String email = txtEmail.getText().trim();
        alvo.setEmail(email.isEmpty() ? null : email); 
        alvo.setLogin(txtLogin.getText().trim());
        if (!senha.isBlank()) {
            alvo.setSenha(senha);
        }

        try {
            if (usuario == null) {
                dao.save(alvo);      // persist
            } else {
                dao.update(alvo);   // merge
            }
            listar.recarregar();
            dispose();
          } catch (PersistenciaException e) {
      // não fecha o diálogo: o usuário vê que não gravou
              JOptionPane.showMessageDialog(this, e.getMessage(),
              "Erro", JOptionPane.ERROR_MESSAGE);
          }
    }
}