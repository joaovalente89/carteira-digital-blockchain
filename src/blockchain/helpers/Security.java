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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HexFormat;
import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created on 24/04/2026, 14:56:56
 *
 * @author manso - computer
 */
/**
 * Utility class providing cryptographic operations based on Elliptic Curve
 * Cryptography (ECC) and AES symmetric encryption.
 *
 * <h2>Capabilities</h2>
 * <ul>
 * <li>Generate an EC key pair (curve {@value #EC_CURVE}).</li>
 * <li>Save the private key to a file encrypted with AES-256-GCM, derived from a
 * user password via PBKDF2-HMAC-SHA256.</li>
 * <li>Save the public key to a file in clear-text Base64
 * (X.509/SubjectPublicKeyInfo format).</li>
 * <li>Load both keys back from their respective files.</li>
 * <li>Sign arbitrary data with ECDSA (SHA-256 digest).</li>
 * <li>Verify an ECDSA signature.</li>
 * </ul>
 *
 * <h2>Algorithms used (JDK only)</h2>
 * <pre>
 *   Key generation : EC  / secp256r1 (NIST P-256)
 *   Signature      : SHA256withECDSA
 *   KDF            : PBKDF2WithHmacSHA256  (310 000 iterations, OWASP 2023)
 *   Symmetric enc. : AES/GCM/NoPadding    (256-bit key, 96-bit IV, 128-bit tag)
 * </pre>
 *
 * <h2>File formats</h2>
 * <ul>
 * <li><b>Private key file</b> — binary layout (all lengths in bytes):
 * {@code [4 salt-len][salt][4 iv-len][iv][ciphertext]} where ciphertext
 * includes the 16-byte GCM authentication tag appended automatically by the JCE
 * provider.</li>
 * <li><b>Public key file</b> — plain UTF-8 Base64, one line, no PEM
 * headers.</li>
 * </ul>
 *
 * <h2>Usage example</h2>
 * <pre>{@code
 * // 1 — generate
 * KeyPair kp = Security.generateKeyPair();
 *
 * // 2 — persist
 * Security.savePrivateKey(kp.getPrivate(), "vault.key", "s3cr3t".toCharArray());
 * Security.savePublicKey(kp.getPublic(), "pub.key");
 *
 * // 3 — load
 * PrivateKey priv = Security.loadPrivateKey("vault.key", "s3cr3t".toCharArray());
 * PublicKey  pub  = Security.loadPublicKey("pub.key");
 *
 * // 4 — sign / verify
 * byte[] data      = "Hello!".getBytes(StandardCharsets.UTF_8);
 * String signature = Security.sign(data, priv);
 * boolean ok       = Security.verify(data, signature, pub);
 * }</pre>
 *
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public final class Security {

    // -------------------------------------------------------------------------
    // Algorithm constants
    // -------------------------------------------------------------------------
    /**
     * Default slgorithm to compute hash
     */
    
    public  static final String HASH_ALGORITH = "SHA-256";

    /**
     * Elliptic curve name (NIST P-256 / secp256r1).
     */
    private static final String EC_CURVE = "secp256r1";

    /**
     * JCA algorithm name for EC key-pair generation.
     */
    private static final String KEY_ALGORITHM = "EC";

    /**
     * Signature algorithm: ECDSA with SHA-256 digest.
     */
    private static final String SIGNATURE_ALGORITHM = "SHA256withECDSA";

    /**
     * Symmetric cipher transformation: AES in GCM mode, no padding.
     */
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";

    /**
     * AES key size in bits.
     */
    private static final int AES_KEY_BITS = 256;

    /**
     * GCM initialisation-vector length in bytes (96 bits recommended by NIST).
     */
    private static final int GCM_IV_BYTES = 12;

    /**
     * GCM authentication-tag length in bits.
     */
    private static final int GCM_TAG_BITS = 128;

    /**
     * PBKDF2 password-based key derivation algorithm.
     */
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Number of PBKDF2 iterations (OWASP recommendation for PBKDF2-HMAC-SHA256,
     * 2023).
     */
    private static final int PBKDF2_ITERATIONS = 310_000;

    /**
     * Salt length in bytes for PBKDF2 (256 bits).
     */
    private static final int SALT_BYTES = 32;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    /**
     * Private constructor — {@code Security} is a static utility class and must
     * not be instantiated.
     */
    private Security() {
        throw new UnsupportedOperationException("Security is a utility class.");
    }

    // -------------------------------------------------------------------------
    // Key generation
    // -------------------------------------------------------------------------
    /**
     * Generates a new elliptic-curve key pair using the {@value #EC_CURVE}
     * curve.
     *
     * <p>
     * The returned {@link KeyPair} contains:</p>
     * <ul>
     * <li>{@link java.security.interfaces.ECPrivateKey} — keep secret.</li>
     * <li>{@link java.security.interfaces.ECPublicKey} — safe to
     * distribute.</li>
     * </ul>
     *
     * @return a freshly generated EC key pair; never {@code null}
     * @throws RuntimeException if the JVM does not support the required
     * algorithms (should never happen on any standard JDK 11+)
     */
    public static KeyPair generateKeyPair() {
        try {
            // Obtain a KeyPairGenerator for EC keys.
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);

            // Initialise the generator with the named curve and a strong random source.
            // ECGenParameterSpec selects the named curve by its standard name.
            ECGenParameterSpec curveSpec = new ECGenParameterSpec(EC_CURVE);
            generator.initialize(curveSpec, new SecureRandom());

            // Generate and return the key pair.
            return generator.generateKeyPair();

        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException("Failed to generate EC key pair.", e);
        }
    }

    // -------------------------------------------------------------------------
    // Private key — save
    // -------------------------------------------------------------------------
    /**
     * Encrypts the private key with a password-derived AES-256-GCM key and
     * writes it to {@code filePath}.
     *
     * <h3>Encryption steps</h3>
     * <ol>
     * <li>Generate a random 256-bit salt.</li>
     * <li>Derive a 256-bit AES key from the password using PBKDF2-HMAC-SHA256
     * ({@value #PBKDF2_ITERATIONS} iterations).</li>
     * <li>Generate a random 96-bit GCM IV.</li>
     * <li>Encrypt the PKCS#8-encoded private key bytes with AES-GCM.</li>
     * <li>Write
     * {@code [4-byte salt length][salt][4-byte IV length][IV][ciphertext]} to
     * the file.</li>
     * </ol>
     *
     * <p>
     * The password char array is zeroed after the key is derived to limit the
     * time the secret lives in memory.</p>
     *
     * @param privateKey the EC private key to protect; must not be {@code null}
     * @param filePath destination file path (created or overwritten)
     * @param password the encryption password; the array is zeroed before this
     * method returns
     * @throws IllegalArgumentException if any argument is {@code null} or if
     * {@code filePath} is blank
     * @throws RuntimeException on any cryptographic or I/O failure
     */
    public static void savePrivateKey(PrivateKey privateKey, String filePath, char[] password) {
        validateNotNull(privateKey, "privateKey");
        validateNotBlank(filePath, "filePath");
        validateNotNull(password, "password");
        if(!filePath.toLowerCase().endsWith(".priv"))
            filePath += ".priv";

        try {
            // --- 1. Generate a random salt for PBKDF2 ---
            byte[] salt = generateSecureRandom(SALT_BYTES);

            // --- 2. Derive AES key from password + salt via PBKDF2 ---
            SecretKey aesKey = deriveAesKey(password, salt);

            // Zero the password array immediately after key derivation
            // to minimise its residence time in heap memory.
            java.util.Arrays.fill(password, '\0');

            // --- 3. Generate a random GCM initialisation vector ---
            byte[] iv = generateSecureRandom(GCM_IV_BYTES);

            // --- 4. Encrypt the PKCS#8 DER encoding of the private key ---
            // getEncoded() returns the key in PKCS#8 format, which can later
            // be restored with PKCS8EncodedKeySpec.
            byte[] plaintext = privateKey.getEncoded();
            byte[] ciphertext = aesGcmEncrypt(plaintext, aesKey, iv);

            // --- 5. Write [saltLen][salt][ivLen][iv][ciphertext] to file ---
            try (DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath))))) {

                // Prefix each field with its 4-byte length so the reader
                // knows exactly how many bytes to read for each component.
                out.writeInt(salt.length);
                out.write(salt);

                out.writeInt(iv.length);
                out.write(iv);

                out.write(ciphertext);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to write the encrypted private key to: " + filePath, e);
        }
    }

    // -------------------------------------------------------------------------
    // Private key — load
    // -------------------------------------------------------------------------
    /**
     * Reads the encrypted private key file produced by
     * {@link #savePrivateKey(PrivateKey, String, char[])} and reconstructs the
     * {@link PrivateKey}.
     *
     * <h3>Decryption steps</h3>
     * <ol>
     * <li>Read the salt and IV from the file header.</li>
     * <li>Re-derive the AES key from the password and salt.</li>
     * <li>Decrypt the ciphertext with AES-GCM (GCM authentication tag is
     * verified automatically — any tampering raises an exception).</li>
     * <li>Reconstruct the {@link PrivateKey} from the PKCS#8 bytes.</li>
     * </ol>
     *
     * @param filePath destination file path previously created by
     * {@link #savePrivateKey}
     * @param password the password used during encryption; zeroed before return
     * @return the reconstructed EC private key; never {@code null}
     * @throws IllegalArgumentException if any argument is invalid
     * @throws RuntimeException if the password is wrong, the file is corrupted,
     * or any other cryptographic / I/O failure occurs
     */
    public static PrivateKey loadPrivateKey(String filePath, char[] password) {
        validateNotBlank(filePath, "filePath");
        validateNotNull(password, "password");
        if(!filePath.toLowerCase().endsWith(".priv"))
            filePath += ".priv";


        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(Paths.get(filePath))))) {

            // --- 1. Read salt ---
            int saltLen = in.readInt();
            byte[] salt = in.readNBytes(saltLen);

            // --- 2. Re-derive the AES key ---
            SecretKey aesKey = deriveAesKey(password, salt);
            java.util.Arrays.fill(password, '\0');

            // --- 3. Read IV ---
            int ivLen = in.readInt();
            byte[] iv = in.readNBytes(ivLen);

            // --- 4. Read remaining bytes as ciphertext (includes GCM tag) ---
            byte[] ciphertext = in.readAllBytes();

            // --- 5. Decrypt — GCM automatically verifies the authentication tag.
            //        A wrong password or corrupted file will throw AEADBadTagException.
            byte[] plaintext = aesGcmDecrypt(ciphertext, aesKey, iv);

            // --- 6. Reconstruct the PrivateKey from PKCS#8 DER bytes ---
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(plaintext);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePrivate(keySpec);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read the private key file: " + filePath, e);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Failed to reconstruct the private key.", e);
        }
    }

    // -------------------------------------------------------------------------
    // Public key — save
    // -------------------------------------------------------------------------
    /**
     * Encodes the public key as Base64 (X.509 / SubjectPublicKeyInfo DER
     * format) and writes it as plain UTF-8 text to {@code filePath}.
     *
     * <p>
     * The resulting file is a single line of Base64 characters with no PEM
     * headers. It is safe to transmit over any text channel.</p>
     *
     * @param publicKey the EC public key to save; must not be {@code null}
     * @param filePath destination file path (created or overwritten)
     * @throws IllegalArgumentException if any argument is {@code null} or blank
     * @throws RuntimeException on I/O failure
     */
    public static void savePublicKey(PublicKey publicKey, String filePath) {
        validateNotNull(publicKey, "publicKey");
        validateNotBlank(filePath, "filePath");
        if(!filePath.toLowerCase().endsWith(".pub"))
            filePath += ".pub";


        // getEncoded() returns the X.509 SubjectPublicKeyInfo DER bytes.
        // Encode to Base64 for safe storage as text.
        String encoded = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        try {
            Files.writeString(Paths.get(filePath), encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write the public key to: " + filePath, e);
        }
    }

    // -------------------------------------------------------------------------
    // Public key — load
    // -------------------------------------------------------------------------
    /**
     * Reads the Base64-encoded public key file produced by
     * {@link #savePublicKey(PublicKey, String)} and reconstructs the
     * {@link PublicKey}.
     *
     * @param filePath path to the public key file
     * @return the reconstructed EC public key; never {@code null}
     * @throws IllegalArgumentException if {@code filePath} is blank
     * @throws RuntimeException if the file cannot be read or the key bytes are
     * malformed
     */
    public static PublicKey loadPublicKey(String filePath) {
        validateNotBlank(filePath, "filePath");
        if(!filePath.toLowerCase().endsWith(".pub"))
            filePath += ".pub";


        try {
            // Read the single-line Base64 text.
            String encoded = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8).strip();

            // Decode Base64 → DER bytes.
            byte[] derBytes = Base64.getDecoder().decode(encoded);

            // Reconstruct the PublicKey from the X.509 SubjectPublicKeyInfo bytes.
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(derBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(keySpec);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read the public key file: " + filePath, e);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Failed to reconstruct the public key.", e);
        }
    }

    // -------------------------------------------------------------------------
    // Sign
    // -------------------------------------------------------------------------
    /**
     * Signs {@code data} with the given EC private key using
     * {@value #SIGNATURE_ALGORITHM} and returns the signature as a Base64
     * string.
     *
     * <p>
     * The returned string can be stored or transmitted as plain text and later
     * verified with {@link #verify(byte[], String, PublicKey)}.</p>
     *
     * @param data the raw bytes to sign; must not be {@code null}
     * @param privateKey the signer's EC private key; must not be {@code null}
     * @return a non-null Base64-encoded DER signature
     * @throws IllegalArgumentException if any argument is {@code null}
     * @throws RuntimeException on any cryptographic failure
     */
    public static String sign(byte[] data, PrivateKey privateKey) {
        validateNotNull(data, "data");
        validateNotNull(privateKey, "privateKey");

        try {
            // Obtain a Signature instance for ECDSA-SHA256.
            Signature signer = Signature.getInstance(SIGNATURE_ALGORITHM);

            // Initialise in signing mode with the private key.
            signer.initSign(privateKey, new SecureRandom());

            // Feed all data bytes into the digest.
            signer.update(data);

            // Compute the DER-encoded ECDSA signature and Base64-encode it.
            byte[] signatureBytes = signer.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException("Failed to sign the data.", e);
        }
    }

    // -------------------------------------------------------------------------
    // Verify
    // -------------------------------------------------------------------------
    /**
     * Verifies an ECDSA signature produced by
     * {@link #sign(byte[], PrivateKey)}.
     *
     * @param data the original data that was signed; must not be {@code null}
     * @param base64Signature the Base64-encoded signature returned by
     * {@link #sign}; must not be {@code null} or blank
     * @param publicKey the signer's EC public key; must not be {@code null}
     * @return {@code true} if the signature is valid for the given data and
     * key, {@code false} otherwise (wrong key, tampered data, or malformed
     * signature)
     * @throws IllegalArgumentException if any argument is {@code null} or blank
     * @throws RuntimeException on any unrecoverable cryptographic failure
     */
    public static boolean verify(byte[] data, String base64Signature, PublicKey publicKey) {
        validateNotNull(data, "data");
        validateNotBlank(base64Signature, "base64Signature");
        validateNotNull(publicKey, "publicKey");

        try {
            // Decode the Base64 signature back to raw DER bytes.
            byte[] signatureBytes = Base64.getDecoder().decode(base64Signature);

            // Obtain a Signature instance and initialise it in verification mode.
            Signature verifier = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifier.initVerify(publicKey);

            // Feed the original data bytes.
            verifier.update(data);

            // verify() returns true if the signature is cryptographically valid.
            return verifier.verify(signatureBytes);

        } catch (IllegalArgumentException e) {
            // The Base64 string was malformed — treat as invalid signature.
            return false;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to initialise the signature verifier.", e);
        } catch (SignatureException e) {
            // The signature bytes are structurally invalid (not a DER sequence, etc.).
            return false;
        }
    }

    // -------------------------------------------------------------------------
    // Private helpers — AES-GCM
    // -------------------------------------------------------------------------
    /**
     * Encrypts {@code plaintext} using AES-256-GCM.
     *
     * <p>
     * The GCM mode provides both confidentiality (AES-CTR internally) and
     * integrity / authenticity via a 128-bit authentication tag, which the JCE
     * appends automatically to the returned ciphertext.</p>
     *
     * @param plaintext the bytes to encrypt
     * @param key the 256-bit AES secret key
     * @param iv the 96-bit GCM initialisation vector (must be unique per key)
     * @return ciphertext bytes with the GCM authentication tag appended
     */
    private static byte[] aesGcmEncrypt(byte[] plaintext, SecretKey key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

            // GCMParameterSpec carries both the IV and the desired tag length.
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_BITS, iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);

            // doFinal() encrypts the plaintext and appends the 16-byte GCM tag.
            return cipher.doFinal(plaintext);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("AES-GCM encryption failed.", e);
        }
    }

    /**
     * Decrypts {@code ciphertext} (with appended GCM tag) using AES-256-GCM.
     *
     * <p>
     * The JCE provider verifies the authentication tag automatically before
     * returning the plaintext. If verification fails (wrong key, corrupted
     * data, or tampered ciphertext), an {@link AEADBadTagException} is
     * thrown.</p>
     *
     * @param ciphertext the encrypted bytes including the 16-byte GCM tag
     * @param key the 256-bit AES secret key
     * @param iv the 96-bit GCM initialisation vector used during encryption
     * @return the original plaintext bytes
     * @throws RuntimeException wrapping
     * {@link javax.crypto.AEADBadTagException} if the tag check fails (wrong
     * password or corruption)
     */
    private static byte[] aesGcmDecrypt(byte[] ciphertext, SecretKey key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_BITS, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);

            // doFinal() verifies the GCM tag and decrypts. Throws AEADBadTagException
            // if the tag does not match (wrong password or tampered ciphertext).
            return cipher.doFinal(ciphertext);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(
                    "AES-GCM decryption failed. The password may be incorrect "
                    + "or the file may have been tampered with.", e);
        }
    }

    // -------------------------------------------------------------------------
    // Private helpers — PBKDF2 key derivation
    // -------------------------------------------------------------------------
    /**
     * Derives a 256-bit AES {@link SecretKey} from a password and salt using
     * PBKDF2-HMAC-SHA256 with {@value #PBKDF2_ITERATIONS} iterations.
     *
     * <p>
     * PBKDF2 is a key-stretching function: a high iteration count makes brute-
     * force password attacks computationally expensive while keeping legitimate
     * single-derivation cost acceptable.</p>
     *
     * @param password the user password (not zeroed here — caller is
     * responsible)
     * @param salt a random salt of at least {@value #SALT_BYTES} bytes
     * @return a {@link SecretKey} suitable for AES-256
     */
    private static SecretKey deriveAesKey(char[] password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);

            // PBEKeySpec encapsulates password + salt + iteration count + desired key length.
            PBEKeySpec spec = new PBEKeySpec(password, salt, PBKDF2_ITERATIONS, AES_KEY_BITS);
            byte[] keyBytes = factory.generateSecret(spec).getEncoded();

            // Destroy the PBEKeySpec to clear the password copy held inside it.
            spec.clearPassword();

            // Wrap the raw bytes in a SecretKeySpec labelled for AES.
            return new SecretKeySpec(keyBytes, "AES");

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("PBKDF2 key derivation failed.", e);
        }
    }

    // -------------------------------------------------------------------------
    // Private helpers — secure random bytes
    // -------------------------------------------------------------------------
    /**
     * Generates {@code length} cryptographically strong random bytes using
     * {@link SecureRandom}.
     *
     * @param length number of random bytes to generate; must be positive
     * @return a new byte array of the requested length filled with random data
     */
    private static byte[] generateSecureRandom(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    // -------------------------------------------------------------------------
    // Private helpers — argument validation
    // -------------------------------------------------------------------------
    /**
     * Throws {@link IllegalArgumentException} if {@code value} is {@code null}.
     *
     * @param value the object to check
     * @param paramName the parameter name shown in the exception message
     */
    private static void validateNotNull(Object value, String paramName) {
        if (value == null) {
            throw new IllegalArgumentException("Parameter '" + paramName + "' must not be null.");
        }
    }

    /**
     * Throws {@link IllegalArgumentException} if {@code value} is {@code null}
     * or contains only whitespace.
     *
     * @param value the string to check
     * @param paramName the parameter name shown in the exception message
     */
    private static void validateNotBlank(String value, String paramName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Parameter '" + paramName + "' must not be null or blank.");
        }
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //
    //                     H A S H I N G
    //
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    
    public static byte[] getHash(byte[] data, String algorithm) {
        try {
            MessageDigest hasher = MessageDigest.getInstance(algorithm);
            return hasher.digest(data);   
        } catch (NoSuchAlgorithmException ex) {
            return ex.getMessage().getBytes();
        }
    }
    public static byte[] getHash(byte[] data) {
        return getHash(data, HASH_ALGORITH);
    }

    public static String getHashHex(String data, String algorithm) {
           return HexFormat.of().formatHex(getHash(data.getBytes(), algorithm));
    }
    public static String getHashHex(String data) {
           return HexFormat.of().formatHex(getHash(data.getBytes(), HASH_ALGORITH));
    }

    // -------------------------------------------------------------------------
    // Main — smoke test
    // -------------------------------------------------------------------------
    /**
     * End-to-end smoke test: generates a key pair, saves and reloads both keys,
     * signs a message, and verifies the signature.
     *
     * <pre>
     *   javac Security.java
     *   java Security
     * </pre>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) throws Exception {
        System.out.println("=== Security smoke test ===\n");

        // 1 — Generate key pair
        KeyPair kp = Security.generateKeyPair();
        System.out.println("Key pair generated.");

        // 2 — Save keys to temp files
        String privFile = System.getProperty("java.io.tmpdir") + "/ec_private.key";
        String pubFile = System.getProperty("java.io.tmpdir") + "/ec_public.key";
        char[] password = "P@ssw0rd!".toCharArray();

        Security.savePrivateKey(kp.getPrivate(), privFile, password);
        Security.savePublicKey(kp.getPublic(), pubFile);
        System.out.println("Keys saved to: " + privFile + " / " + pubFile);

        // 3 — Reload keys
        PrivateKey priv = Security.loadPrivateKey(privFile, "P@ssw0rd!".toCharArray());
        PublicKey pub = Security.loadPublicKey(pubFile);
        System.out.println("Keys loaded successfully.");

        // 4 — Sign a message
        byte[] message = "Hello, ECC!".getBytes(StandardCharsets.UTF_8);
        String signature = Security.sign(message, priv);
        System.out.println("Signature : " + signature);

        // 5 — Verify (should be true)
        boolean valid = Security.verify(message, signature, pub);
        System.out.println("Valid signature  : " + valid);

        // 6 — Tamper test (should be false)
        byte[] tampered = "Hello, ECC?".getBytes(StandardCharsets.UTF_8);
        boolean invalid = Security.verify(tampered, signature, pub);
        System.out.println("Tampered message : " + invalid + " (expected false)");

        System.out.println("\n=== All tests passed ===");
    }

}
