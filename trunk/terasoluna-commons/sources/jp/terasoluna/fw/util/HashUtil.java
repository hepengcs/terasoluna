/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �n�b�V���l���v�Z���郆�[�e�B���e�B�N���X�B
 * 
 * <p>
 *  java.security.MessageDigest��p���āA������̃n�b�V���l��
 *  �擾����B<br>
 *  MD5�ASHA1�A���̑��A���S���Y�����g�p���Ď擾����B
 *  <strong>�g�p��</strong><br>
 *  <code><pre>
 *   �E�E�E
 *    // DB�ɓo�^���郆�[�U�p�X���[�h�̃n�b�V���l���v�Z����B
 *    byte[] hash = HashUtil.hashMD5(userPassword);
 *   �E�E�E
 *  </pre></code>
 * </p>
 *
 */
public class HashUtil {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(HashUtil.class);

    /**
     * �w�肳�ꂽ�A���S���Y���ŕ�������n�b�V���l���擾����B
     *
     * @param algorithm �n�b�V���A���S���Y��
     * @param str �n�b�V���l�̎擾�Ώۂ̕�����
     * @return �n�b�V���l
     * @throws NoSuchAlgorithmException �n�b�V���A���S���Y�����s���ȂƂ�
     * 
     */
    public static byte[] hash(String algorithm, String str)
            throws NoSuchAlgorithmException {
        if (algorithm == null || str == null) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance(algorithm.toUpperCase());
        return md.digest(str.getBytes());
    }

    /**
     * MD5�A���S���Y���ŕ�����̃n�b�V���l���擾����B
     * 
     * @param str �n�b�V���l�̎擾�Ώۂ̕�����
     * @return �n�b�V���l
     */
    public static byte[] hashMD5(String str) {
        try {
            return hash("MD5", str);
        } catch (NoSuchAlgorithmException e) {
            log.error("The algorithm is not available"
                    + " in the caller's environment.", e);
            return null; // can't happen
        }
    }

    /**
     * SHA1�A���S���Y���ŕ�����̃n�b�V���l���擾����B
     * 
     * @param str �n�b�V���l�̎擾�Ώۂ̕�����
     * @return �n�b�V���l
     */
    public static byte[] hashSHA1(String str) {
        try {
            return hash("SHA1", str);
        } catch (NoSuchAlgorithmException e) {
            log.error("The algorithm is not available"
                    + " in the caller's environment.", e);
            return null; // can't happen
        }
    }
}
