package de.miij.util;

/*
 * -- $Id: Blowfish.java,v 1.4 2001/06/28 10:45:43 nikhil Exp $ --
 * ============================================================================
 * GNU LESSER GENERAL PUBLIC LICENSE Version 2.1, February 1999
 * ============================================================================
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA -- Copyright (C) 2001 Auriga Logic Pvt. Ltd.
 * <http://www.aurigalogic.com> Author: Nikhil Gupte <ngupte@aurigalogic.com>
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * Provides Blowfish encryption and decryption. This class uses Sun
 * Microsystem's JCE 1.2.1 which is available from <a
 * href="http://java.sun.com/">http://java.sun.com/</a>.
 * <p>
 * <b>Example:</b> <br />
 * <code>
* &nbsp;&nbsp;String ciphertext = Blowfish.encrypt("encryptme", "my key");
* &nbsp;&nbsp;String cleartext = Blowfish.encrypt(ciphertext, "my key");
* </code>
 * </p>
 * 
 * @author <a href="mailto:ngupte@aurigalogic.com">Nikhil Gupte</a>
 * @version $Revision: 1.4 $ $Date: 2001/06/28 10:45:43 $
 */
public class BlowfishUtil
{

   /**
	 * Encrypts a string using the passed key.
	 * 
	 * @param cleartext
	 *           The clear text string to encrypt.
	 * @param key
	 *           The key to use for the encryption.
	 * @return a string representing the resulting ciphertext.
	 */

   public static String encrypt(String cleartext, String key)
           throws Exception {
       return crypt(cleartext, key, Cipher.ENCRYPT_MODE);    
   }

   /**
	 * Decrypts a string using the passed key.
	 * 
	 * @param ciphertext
	 *           The encrypted string.
	 * @param key
	 *           The key to use for the decryption.
	 * @return a string representing the resulting cleartext.
	 */
   public static String decrypt(String ciphertext, String key)
           throws Exception {
       return crypt(ciphertext, key, Cipher.DECRYPT_MODE);    
   }

   /*
	 * This actuall does the encryption/decryption.
	 */
   private static String crypt(String input, String key, int mode) 
           throws Exception {

       // Install SunJCE provider
       Provider sunJce = new com.sun.crypto.provider.SunJCE();
       Security.addProvider(sunJce);
        
       KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
       kgen.init(448);
//       SecretKey skey = kgen.generateKey();

       byte[] raw = key.getBytes();
       SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
           
       Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
       cipher.init(mode, skeySpec);

       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       ByteArrayInputStream bis = new ByteArrayInputStream(input.getBytes());
       CipherOutputStream cos = new CipherOutputStream(bos, cipher);

       int length = 0;
       byte[] buffer =  new byte[8192];

       while ((length = bis.read(buffer)) != -1) {
          cos.write(buffer, 0, length);
       }

       bis.close();
       cos.close();

       return bos.toString();
   }
}