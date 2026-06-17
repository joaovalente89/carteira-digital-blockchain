//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2026   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 //////////////////////////////////////////////////////////////////////////////

package blockchain.helpers;

/**
 * Created on 24/04/2026, 14:46:12
 *
 * @author manso - computer
 */
import java.io.*;
import java.util.Base64;

/**
 * Utility class providing helper methods for object serialization and encoding.
 *
 * <p>
 * This class offers static methods to convert any {@link Serializable} object
 * into a Base64-encoded {@link String} and to restore the original object from
 * such a string. It relies exclusively on the Java Development Kit (JDK)
 * standard library — no third-party dependencies are required.</p>
 *
 * <p>
 * <strong>Serialization contract:</strong> every object passed to
 * {@link #objectToBase64(Serializable)} must implement
 * {@link java.io.Serializable}. If the class (or any of its fields) does not
 * declare a {@code serialVersionUID}, the JVM will generate one automatically;
 * however, this may cause {@link java.io.InvalidClassException} if the class
 * changes between serialization and deserialization.</p>
 *
 * <p>
 * <strong>Security notice:</strong> deserializing data from untrusted sources
 * can be dangerous. Use {@link #base64ToObject(String)} only with data that was
 * produced by a trusted party.</p>
 *
 * <p>
 * <strong>Usage example:</strong></p>
 * <pre>{@code
 * // Serialize
 * String encoded = Utils.objectToBase64(myObject);
 *
 * // Deserialize
 * MyClass restored = (MyClass) Utils.base64ToObject(encoded);
 * }</pre>
 *
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public final class Utils {

    public static <T extends Serializable> byte[] toBytes(T obj) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            return baos.toByteArray();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        }
    }

    public static <T extends Serializable> void save(T obj, String ficheiro) throws IOException {
        byte[] dados = toBytes(obj);
        try (FileOutputStream fos = new FileOutputStream(ficheiro)) {
            fos.write(dados);
        }
    }

// Ler objeto de ficheiro
    public static <T extends Serializable> T load(String ficheiro) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(ficheiro)) {
            byte[] dados = fis.readAllBytes();
            return fromBytes(dados);
        }
    }

    // Objeto → Base64
    public static <T extends Serializable> String toBase64(T obj) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Base64 → Objeto
    public static <T extends Serializable> T fromBase64(String base64) throws IOException, ClassNotFoundException {
        byte[] bytes = Base64.getDecoder().decode(base64);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        }
    }

    public static String cutString(String txt, int lenght) {
        String cuted = txt.substring(0, Math.min(lenght, txt.length()));
        if (cuted.length() == lenght && lenght > 3) {
            cuted = cuted.substring(0, cuted.length() - 3) + "...";
        }
        return cuted;
    }

}
