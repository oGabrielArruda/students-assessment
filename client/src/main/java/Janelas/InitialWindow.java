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
                    short ra = getRa(RATextField.getText());
                    int codigo = getCod(CodigoDaDisciplinaTextField.getText());
                    double nota = getNota(notaTextField.getText());
                    double frequencia = getFreq(frequenciaTextField.getText());
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

    private short getRa(String raStr) throws Exception {
        try {
            short ret = Short.parseShort(raStr);
            return ret;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "RA inválido");
            RATextField.setText("");
            RATextField.grabFocus();
            throw new Exception("Erro ao converter RA");
        }
    }

    private int getCod(String codStr) throws Exception {
        try {
            int ret = Integer.parseInt(codStr);
            return ret;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código de disciplina inválido inválido");
            CodigoDaDisciplinaTextField.setText("");
            CodigoDaDisciplinaTextField.grabFocus();
            throw new Exception("Erro ao converter COD de Disciplina");
        }
    }

    private double getNota(String notaStr) throws Exception {
        try {
            double ret = Integer.parseInt(notaStr);
            return ret;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Nota inválida");
            notaTextField.setText("");
            notaTextField.grabFocus();
            throw new Exception("Erro ao converter a nota");
        }
    }

    private double getFreq(String freqStr) throws Exception {
        try {
            double ret = Integer.parseInt(freqStr);
            return ret;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Frequencia inválida");
            frequenciaTextField.setText("");
            frequenciaTextField.grabFocus();
            throw new Exception("Erro ao converter COD de Disciplina");
        }
    }
}
