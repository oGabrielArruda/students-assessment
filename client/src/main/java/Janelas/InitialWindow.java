package Janelas;

import Aluno.Aluno;
import WebService.ClienteWS;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class InitialWindow {
    private JTextField RATextField;
    private JTextField CodigoDaDisciplinaTextField;
    private JTextField notaTextField;
    private JTextField frequênicaTextField;
    private JTextField nomeDoAlunoTextField;
    private JButton adicionarButton;
    private JButton salvarButton;
    public JPanel panelMain;

    public InitialWindow()
    {
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
                }
            }
        });
    }
}
