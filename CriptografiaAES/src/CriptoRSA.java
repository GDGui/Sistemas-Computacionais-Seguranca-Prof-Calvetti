import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class CriptoRSA {

    // Método para cifrar com RSA usando a Chave Pública
    public static String cifrarRSA(String textoPlano, PublicKey chavePublica) throws Exception {
        System.out.println("\n--- CIFRANDO MENSAGEM COM CHAVE PUBLICA ---");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
        byte[] textoCifradoBytes = cipher.doFinal(textoPlano.getBytes("UTF-8"));
        System.out.println("Mensagem cifrada com sucesso!");
        return Base64.getEncoder().encodeToString(textoCifradoBytes);
    }

    // Método para decifrar com RSA usando a Chave Privada
    public static String decifrarRSA(String textoCifrado, PrivateKey chavePrivada) throws Exception {
        System.out.println("\n--- DECIFRANDO MENSAGEM COM CHAVE PRIVADA ---");
        byte[] textoCifradoBytes = Base64.getDecoder().decode(textoCifrado);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
        byte[] textoDecifradoBytes = cipher.doFinal(textoCifradoBytes);
        System.out.println("Mensagem decifrada com sucesso!");
        return new String(textoDecifradoBytes, "UTF-8");
    }

    public static void main(String[] args) {
        System.out.println("Iniciando nosso programa de Criptografia RSA!");

        try {
            // 1. Gerar o par de chaves (pública e privada).
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair parDeChaves = keyGen.generateKeyPair();
            PublicKey chavePublica = parDeChaves.getPublic();
            PrivateKey chavePrivada = parDeChaves.getPrivate();

            // Mensagem a ser cifrada
            String mensagemOriginal = "Testando a criptografia com chave assimetrica RSA!";
            System.out.println("\nMensagem Original: " + mensagemOriginal);

            // 2. Cifrar a mensagem com a chave pública.
            String mensagemCifrada = cifrarRSA(mensagemOriginal, chavePublica);
            System.out.println("Mensagem Cifrada (Base64): " + mensagemCifrada);

            // 3. Decifrar a mensagem com a chave privada.
            String mensagemDecifrada = decifrarRSA(mensagemCifrada, chavePrivada);
            System.out.println("\nMensagem Decifrada: " + mensagemDecifrada);
            
            // Verificação Final
            System.out.println("\nA mensagem original: igual a decifrada? " + mensagemOriginal.equals(mensagemDecifrada));

        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante a execução do RSA.");
            e.printStackTrace();
        }
    }
}