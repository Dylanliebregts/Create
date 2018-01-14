package com.example.dlieb.scanner;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity { //extends   allows one class to "inherit" the properties of another class.

    //variables
    private KeyStore keystore;
    private static final String KEY_NAME="EDMTDev";
    private Cipher cipher;
    private TextView textView;

    //Oncreate methode
    @Override //when using override you make a change to the existing class that you inherit from another class.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     //layout van de activity_main xml

        KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);  //Keyguard is unlocking your phone, I need this for my unlock method
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE); //class that coordinates access to the fingerprint hardware.

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) //checking if manifest has the permissions for finger scan
        {
            return;
        }

        if(!fingerprintManager.isHardwareDetected())
            Toast.makeText(this, "Fingerprint authentication permission not enable", Toast.LENGTH_SHORT).show(); //if finger detected but permissions is off give this message
        else
        {
            if(!fingerprintManager.hasEnrolledFingerprints())
                Toast.makeText(this, "Register at least one fingerprint in Settings", Toast.LENGTH_SHORT).show(); //if there is no fingerprint in the mobile stored give this message
            else {
                if (!keyguardManager.isKeyguardSecure())
                    Toast.makeText(this, "Lock screen security not enabled in Settings", Toast.LENGTH_SHORT).show(); //if lock screen security is not enabled in the settings give this message
                else
                    genKey(); //else if everything is good generate a key to login and continue

                if(cipherInit()) //this is where the user logs in after this if statement
                { //finger print handler executes the finger scan for me.
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher); //cryptoObject is needed for fingerprint authentication
                    FingerprintHandler helper = new FingerprintHandler(this); //helper = fingerprint handler
                    helper.StartAuthentication(fingerprintManager, cryptoObject); //checking
                }
            }
        }



    }
        //functionality of a cryptographic cipher for encryption and decryption
    private boolean cipherInit() {
        try {   //get instance is needed to create a cipher method, in the instance are methods to make the key safer.
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {      //catch errors
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();

        }

            try {
                keystore.load(null); // in the keystore is my encrypted private key
                SecretKey key = (SecretKey)keystore.getKey(KEY_NAME,null); //secretkey also a method to encrypt the key
                cipher.init(Cipher.ENCRYPT_MODE,key);   //Constant used to initialize cipher to encryption mode.
                return true;    //expects it to be true, returns true
            } catch (IOException e1) {      //cathing different errors (printstacktrace displays the error to my "system.err"

                e1.printStackTrace();
                return false;
            } catch (NoSuchAlgorithmException e1) {

                e1.printStackTrace();
                return false;
            } catch (CertificateException e1) {

                e1.printStackTrace();
                return false;
            } catch (UnrecoverableKeyException e1) {

                e1.printStackTrace();
                return false;
            } catch (KeyStoreException e1) {
                e1.printStackTrace();
                return false;

            } catch (InvalidKeyException e1) {
                e1.printStackTrace();
                return false;

            }



    }
    //method for generating a key if everything is succeed
//I have encrtyped the key so now it's time t generate the key.
    private void genKey() {         //method to generate the key
        try {
            keystore = KeyStore.getInstance("AndroidKeyStore"); //opslaan key
        } catch (KeyStoreException e) {     //catch error
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"); //get instance needed to create a keygenerator method, encrypt it with KEY_ALGORITHM_AES, "android keystore is the name given to the instance"
        } catch (NoSuchAlgorithmException e) {  //catch errors
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            keystore.load(null);    //keystore wit the key loads
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC
                    ).setUserAuthenticationRequired(true) //KeyGenParameterSpec to generate a key, key needs to get decrypted here

                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build()
            );
            keyGenerator.generateKey();
        } catch (IOException e) { //catch errors
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        catch (InvalidAlgorithmParameterException e )
        {
            e.printStackTrace();
        }

    }
}