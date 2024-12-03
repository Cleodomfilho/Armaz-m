import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Armazem {

    // Matriz 3D para armazenar os dados do armazém
    private String[][][] armazem = new String[3][4][5];
    private JTextArea textArea;

    public Armazem() {
        // Inicializa a matriz do armazém
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 5; k++) {
                    armazem[i][j][k] = ""; // Inicializa todas as posições como vazias
                }
            }
        }
        // Configuração da interface gráfica
        JFrame frame = new JFrame("Sistema de Armazém");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        // Área de texto para exibir o estado do armazém
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        container.add(scrollPane, BorderLayout.CENTER);

        // Painel para os botões e entradas
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JButton btnExibirEstado = new JButton("Exibir Estado");
        JButton btnAdicionarProduto = new JButton("Adicionar Produto");
        JButton btnRemoverProduto = new JButton("Remover Produto");
        JButton btnMoverProduto = new JButton("Mover Produto");
        JButton btnListarPosicoes = new JButton("Listar Abaixo de Mínimo");
        JButton btnSair = new JButton("Sair");

        panel.add(btnExibirEstado);
        panel.add(btnAdicionarProduto);
        panel.add(btnRemoverProduto);
        panel.add(btnMoverProduto);
        panel.add(btnListarPosicoes);
        panel.add(btnSair);

        container.add(panel, BorderLayout.SOUTH);

        // Ação dos botões
        btnExibirEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirEstado();
            }
        });

        btnAdicionarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProduto();
            }
        });

        btnRemoverProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProduto();
            }
        });

        btnMoverProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverProduto();
            }
        });

        btnListarPosicoes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPosicoesAbaixoMinimo();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    // Exibe o estado atual do armazém
    public void exibirEstado() {
        StringBuilder sb = new StringBuilder("Estado atual do armazém:\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 5; k++) {
                    String posicao = armazem[i][j][k];
                    if (!posicao.isEmpty()) {
                        String[] partes = posicao.split(",");
                        String produto = partes[0];
                        int quantidade = Integer.parseInt(partes[1]);
                        sb.append("Andar ").append(i + 1).append(", Corredor ").append(j + 1)
                                .append(", Seção ").append(k + 1).append(": ").append(produto)
                                .append(", Quantidade: ").append(quantidade).append("\n");
                    }
                }
            }
        }
        textArea.setText(sb.toString());
    }

    // Adiciona um produto em uma posição específica
    public void adicionarProduto() {
        String andar = JOptionPane.showInputDialog("Digite o andar (1-3): ");
        String corredor = JOptionPane.showInputDialog("Digite o corredor (1-4): ");
        String secao = JOptionPane.showInputDialog("Digite a seção (1-5): ");
        String produto = JOptionPane.showInputDialog("Digite o nome do produto: ");
        String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade: ");

        int andarInt = Integer.parseInt(andar) - 1;
        int corredorInt = Integer.parseInt(corredor) - 1;
        int secaoInt = Integer.parseInt(secao) - 1;
        int quantidade = Integer.parseInt(quantidadeStr);

        String posicaoAtual = armazem[andarInt][corredorInt][secaoInt];
        if (posicaoAtual.isEmpty()) {
            armazem[andarInt][corredorInt][secaoInt] = produto + "," + quantidade; // Adiciona o produto
        } else {
            String[] partes = posicaoAtual.split(",");
            int quantidadeAtual = Integer.parseInt(partes[1]);
            armazem[andarInt][corredorInt][secaoInt] = produto + "," + (quantidadeAtual + quantidade); // Incrementa a quantidade
        }
        JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
    }

    // Remove uma quantidade específica de um produto de uma posição
    public void removerProduto() {
        String andar = JOptionPane.showInputDialog("Digite o andar (1-3): ");
        String corredor = JOptionPane.showInputDialog("Digite o corredor (1-4): ");
        String secao = JOptionPane.showInputDialog("Digite a seção (1-5): ");
        String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade a ser removida: ");

        int andarInt = Integer.parseInt(andar) - 1;
        int corredorInt = Integer.parseInt(corredor) - 1;
        int secaoInt = Integer.parseInt(secao) - 1;
        int quantidade = Integer.parseInt(quantidadeStr);

        String posicaoAtual = armazem[andarInt][corredorInt][secaoInt];
        if (!posicaoAtual.isEmpty()) {
            String[] partes = posicaoAtual.split(",");
            int quantidadeAtual = Integer.parseInt(partes[1]);
            if (quantidadeAtual >= quantidade) {
                quantidadeAtual -= quantidade;
                if (quantidadeAtual == 0) {
                    armazem[andarInt][corredorInt][secaoInt] = ""; // Remove o produto se a quantidade for zero
                } else {
                    armazem[andarInt][corredorInt][secaoInt] = partes[0] + "," + quantidadeAtual; // Atualiza a quantidade
                }
                JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Quantidade insuficiente para remover.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Não há produto nesta posição.");
        }
    }

    // Move um produto de uma posição para outra
    public void moverProduto() {
        String andarOrigem = JOptionPane.showInputDialog("Digite o andar de origem (1-3): ");
        String corredorOrigem = JOptionPane.showInputDialog("Digite o corredor de origem (1-4): ");
        String secaoOrigem = JOptionPane.showInputDialog("Digite a seção de origem (1-5): ");
        String andarDestino = JOptionPane.showInputDialog("Digite o andar de destino (1-3): ");
        String corredorDestino = JOptionPane.showInputDialog("Digite o corredor de destino (1-4): ");
        String secaoDestino = JOptionPane.showInputDialog("Digite a seção de destino (1-5): ");
        String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade a ser movida: ");

        int andarOrigemInt = Integer.parseInt(andarOrigem) - 1;
        int corredorOrigemInt = Integer.parseInt(corredorOrigem) - 1;
        int secaoOrigemInt = Integer.parseInt(secaoOrigem) - 1;
        int andarDestinoInt = Integer.parseInt(andarDestino) - 1;
        int corredorDestinoInt = Integer.parseInt(corredorDestino) - 1;
        int secaoDestinoInt = Integer.parseInt(secaoDestino) - 1;
        int quantidade = Integer.parseInt(quantidadeStr);

        String posicaoOrigem = armazem[andarOrigemInt][corredorOrigemInt][secaoOrigemInt];
        if (!posicaoOrigem.isEmpty()) {
            String[] partesOrigem = posicaoOrigem.split(",");
            String produto = partesOrigem[0];
            int quantidadeOrigem = Integer.parseInt(partesOrigem[1]);
            if (quantidadeOrigem >= quantidade) {
                removerProduto(andarOrigemInt, corredorOrigemInt, secaoOrigemInt, quantidade); // Remove da origem
                adicionarProduto(andarDestinoInt, corredorDestinoInt, secaoDestinoInt, produto, quantidade); // Adiciona no destino
                JOptionPane.showMessageDialog(null, "Produto movido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Quantidade insuficiente para mover.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Não há produto na posição de origem.");
        }
    }

    private void adicionarProduto(int andarDestinoInt, int corredorDestinoInt, int secaoDestinoInt, String produto, int quantidade) {
    }

    private void removerProduto(int andarOrigemInt, int corredorOrigemInt, int secaoOrigemInt, int quantidade) {

    }

    // Lista as posições com produtos abaixo de uma quantidade mínima
    public void listarPosicoesAbaixoMinimo() {
        String minimoStr = JOptionPane.showInputDialog("Digite a quantidade mínima: ");
        int minimo = Integer.parseInt(minimoStr);

        StringBuilder sb = new StringBuilder("Posições com produtos abaixo de " + minimo + ":\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 5; k++) {
                    String posicao = armazem[i][j][k];
                    if (!posicao.isEmpty()) {
                        String[] partes = posicao.split(",");
                        String produto = partes[0];
                        int quantidade = Integer.parseInt(partes[1]);
                        if (quantidade < minimo) {
                            sb.append("Andar ").append(i + 1).append(", Corredor ").append(j + 1)
                                    .append(", Seção ").append(k + 1).append(": ").append(produto)
                                    .append(", Quantidade: ").append(quantidade).append("\n");
                        }
                    }
                }
            }
        }
        textArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Armazem(); // Inicializa a interface gráfica
            }
        });
    }
}
