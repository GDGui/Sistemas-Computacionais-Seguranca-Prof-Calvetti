import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class FuncaoHash {

    // Método auxiliar para converter um array de bytes para uma string Hexadecimal
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Nosso método principal para gerar o hash SHA-256 de uma string
    public static String gerarHash(String input) throws Exception {
        // 1. Obter uma instância do MessageDigest para SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // 2. Gerar o hash dos bytes da nossa string de entrada
        // Usamos StandardCharsets.UTF_8 para garantir a consistência entre sistemas
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        // 3. Converter o resultado para Hexadecimal usando nosso método auxiliar
        return bytesToHex(hashBytes);
    }

    public static void main(String[] args) {
        System.out.println("Iniciando nosso programa de Funcao Hash (SHA-256)!");

        try {
            String mensagemOriginal = "O conhecimento protege.";
            
            // --- 1. Testando a Propriedade Determinística ---
            System.out.println("\n--- Teste 1: Determinismo ---");
            System.out.println("Mensagem Original: " + mensagemOriginal);
            
            String hash1 = gerarHash(mensagemOriginal);
            String hash2 = gerarHash(mensagemOriginal); // Gerando o hash da MESMA mensagem de novo

            System.out.println("Hash 1: " + hash1);
            System.out.println("Hash 2: " + hash2);
            System.out.println("Os hashes sao identicos? " + hash1.equals(hash2));

            // --- 2. Testando o Efeito Avalanche ---
            System.out.println("\n--- Teste 2: Efeito Avalanche ---");
            // Vamos mudar apenas um caractere: o ponto final por uma exclamação.
            String mensagemAlterada = "O conhecimento protege!";
            System.out.println("Mensagem Alterada: " + mensagemAlterada);

            String hash3 = gerarHash(mensagemAlterada);
            System.out.println("Hash da mensagem original: " + hash1);
            System.out.println("Hash da mensagem alterada: " + hash3);
            System.out.println("Os hashes sao identicos? " + hash1.equals(hash3));
            
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante a execução do Hash.");
            e.printStackTrace();
        }
    }
}