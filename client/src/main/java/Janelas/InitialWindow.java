package Janelas;

import Aluno.Aluno;
import Resultado.Resultado;
import WebService.ClienteWS;
import Fila.Fila;
import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;

public class InitialWindow {
    private JTextField RATextField;
    private JTextField CodigoDaDisciplinaTextField;
    private JTextField notaTextField;
    private JTextField frequenciaTextField;
    private JTextField nomeDoAlunoTextField;
    private JButton adicionarButton;
    private JButton salvarButton;
    public JPanel panelMain;

    public InitialWindow() {
        Fila<Resultado> filaResultado = new Fila<Resultado>();

        RATextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    Aluno aluno = (Aluno) ClienteWS.getObjeto(Aluno.class, "http://localhost:3000/alunos", RATextField.getText());
                    nomeDoAlunoTextField.setText(aluno.getNome());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    nomeDoAlunoTextField.setText("");
                }
            }
        });
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    short ra = Short.parseShort(RATextField.getText());
                    int codigo = Integer.parseInt(CodigoDaDisciplinaTextField.getText());
                    double nota = Double.parseDouble(notaTextField.getText());
                    double frequencia = Double.parseDouble(frequenciaTextField.getText());
                    limparCampos();
                    Resultado novoResultado = new Resultado(ra, codigo, nota, frequencia);
                    filaResultado.guardeUmItem(novoResultado);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


        });
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                while (!filaResultado.isVazia()) {
                    try {
                        Resultado resultado = (Resultado) ClienteWS.postObjeto(filaResultado.recupereUmItem(), Resultado.class, "http://localhost:3000/avaliar");
                        filaResultado.removaUmItem();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void limparCampos() {
        this.RATextField.setText("");
        this.frequenciaTextField.setText("");
        this.CodigoDaDisciplinaTextField.setText("");
        this.nomeDoAlunoTextField.setText("");
        this.notaTextField.setText("");
    }
}
