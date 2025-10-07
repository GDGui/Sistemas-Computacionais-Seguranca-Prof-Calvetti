import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class CriptoAES {

    // Método para cifrar um texto usando AES
    public static String cifrar(String textoPlano, SecretKey chave, byte[] iv) throws Exception {
        System.out.println("\n--- CIFRANDO MENSAGEM ---");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, chave, ivParameterSpec);
        byte[] textoCifradoBytes = cipher.doFinal(textoPlano.getBytes("UTF-8"));
        System.out.println("Mensagem cifrada com sucesso!");
        return Base64.getEncoder().encodeToString(textoCifradoBytes);
    }

    // Método para decifrar um texto usando AES
    public static String decifrar(String textoCifrado, SecretKey chave, byte[] iv) throws Exception {
        System.out.println("\n--- DECIFRANDO MENSAGEM ---");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, chave, ivParameterSpec);
        byte[] textoCifradoBytes = Base64.getDecoder().decode(textoCifrado);
        byte[] textoDecifradoBytes = cipher.doFinal(textoCifradoBytes);
        System.out.println("Mensagem decifrada com sucesso!");
        return new String(textoDecifradoBytes, "UTF-8");
    }

    public static void main(String[] args) {
        System.out.println("Nosso programa de Criptografia AES comecou!");

        try {
            // 1. Gerar a Chave AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey chaveSecreta = keyGen.generateKey();

            // 2. Gerar o Vetor de Inicialização (IV)
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            
            // --- Mensagem Original ---
            String mensagemOriginal = "O conhecimento gera a chave para a liberdade.";
            System.out.println("\nMensagem Original: " + mensagemOriginal);

            // 3. Cifrar a mensagem
            String mensagemCifrada = cifrar(mensagemOriginal, chaveSecreta, iv);
            System.out.println("Mensagem Cifrada (em Base64): " + mensagemCifrada);

            // 4. Decifrar a mensagem
            String mensagemDecifrada = decifrar(mensagemCifrada, chaveSecreta, iv);
            System.out.println("\nMensagem Decifrada: " + mensagemDecifrada);
            
            // Verificação Final
            System.out.println("\nA mensagem original: igual a decifrada? " + mensagemOriginal.equals(mensagemDecifrada));

        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante a execução.");
            e.printStackTrace();
        }
    }
}