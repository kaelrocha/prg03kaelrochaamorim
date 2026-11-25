package br.com.ifba.usuario.view;

import org.springframework.context.annotation.Scope;

import br.com.ifba.infrastructure.exception.PersistenciaException;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.exception.UsuarioInvalidoException;
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
@org.springframework.stereotype.Component
@Scope("prototype")
public class UsuarioFormulario extends JDialog {

    private final Usuario usuario; // null = cadastro, preenchido = edição
    private final UsuarioListar listar;
    private final UsuarioIController controller;

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
public UsuarioFormulario(UsuarioListar pai, UsuarioIController controller) {
        this(pai, controller, null);
    }

// Edição (ou cadastro, se usuario for null)
public UsuarioFormulario(UsuarioListar pai, UsuarioIController controller, Usuario usuario) {
    super(pai, usuario == null ? "Novo usuário" : "Editar usuário", true);
    this.usuario = usuario;
    this.listar = pai;
    this.controller = controller;
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

// A tela só monta o objeto e chama o Controller; as regras estão no Service
private void salvar() {
    Usuario alvo = (usuario == null) ? new Usuario() : usuario;
    alvo.setNome(txtNome.getText().trim());
    alvo.setCpf(txtCpf.getText().trim());
    alvo.setGenero((String) cbGenero.getSelectedItem());
    alvo.setDataNascimento(txtNascimento.getText().trim());
    alvo.setTelefone(txtTelefone.getText().trim());
    alvo.setEmail(txtEmail.getText().trim());
    alvo.setLogin(txtLogin.getText().trim());

// na edição, senha em branco = manter a atual
    String senha = new String(txtSenha.getPassword());
if (!senha.isBlank()) {
    alvo.setSenha(senha);
    }

    try {
        if (usuario == null) {
            controller.save(alvo);
        } else {
            controller.update(alvo);
        }
    listar.recarregar();
    dispose();
    } catch (UsuarioInvalidoException | PersistenciaException e) {
// não fecha o diálogo: o usuário vê que não gravou
    JOptionPane.showMessageDialog(this, e.getMessage(),
    "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
}