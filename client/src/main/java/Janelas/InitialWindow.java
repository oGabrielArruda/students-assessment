package Janelas;

import Aluno.Aluno;
import Resultado.Resultado;
import WebService.ClienteWS;
import Fila.Fila;
import MensagemResultado.MensagemResultado;

import javax.swing.*;
import java.awt.event.*;

public class InitialWindow {
    private JTextField RATextField;
    private JTextField CodigoDaDisciplinaTextField;
    private JTextField notaTextField;
    private JTextField frequenciaTextField;
    private JTextField nomeDoAlunoTextField;
    private JButton adicionarButton;
    private JButton salvarButton;
    public JPanel panelMain;
    private JList list1;
    Fila<Resultado> filaResultado = new Fila<Resultado>();

    public InitialWindow() {


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
                    Resultado novoResultado = new Resultado(ra, codigo, nota, frequencia);
                    filaResultado.guardeUmItem(novoResultado);
                    limparCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    ex.printStackTrace();
                }
            }


        });
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sendToApi();
                limparFila();
            }
        });
    }

    private void sendToApi() {
        String[] vetorMensagens = new String[this.filaResultado.qtd()];
        int indice = 0;

        while (!this.filaResultado.isVazia()) {
            Resultado resultadoParaEnvio = pegarResultado();
            try {
                MensagemResultado resultadoRecebido = (MensagemResultado) ClienteWS.postObjeto(resultadoParaEnvio, MensagemResultado.class, "http://localhost:3000/avaliar");
                if(resultadoRecebido.getMensagem().equals("Sucesso"))
                    vetorMensagens[indice] = "Sucesso ao avaliar o aluno do RA " + resultadoParaEnvio.getRA() + ", na disciplina " + resultadoParaEnvio.getCod();
                else
                    vetorMensagens[indice] = "Erro ao avaliar o aluno do RA "+ resultadoParaEnvio.getRA() + ": " + resultadoRecebido.getMensagem();
            } catch (Exception ex) {
                ex.printStackTrace();
                vetorMensagens[indice] = "Erro ao avaliar o aluno do RA "+ resultadoParaEnvio.getRA() + ": " + ex;
            } finally {
                removerResultado();
                indice++;
            }
        }

        list1.setListData(vetorMensagens);
    }

    private Resultado pegarResultado() {
        Resultado ret = null;
        try {
            ret = this.filaResultado.recupereUmItem();
        } catch (Exception ex) { ex.printStackTrace(); }
        return ret;
    }

    private void removerResultado() {
        try {
            this.filaResultado.removaUmItem();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void limparFila() {
        this.filaResultado = new Fila<Resultado>();
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
            double ret = Double.parseDouble(freqStr);
            return ret;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Frequencia inválida");
            frequenciaTextField.setText("");
            frequenciaTextField.grabFocus();
            throw new Exception("Erro ao converter Frequencia");
        }
    }
}
