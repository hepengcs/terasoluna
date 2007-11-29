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

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �v���p�e�B�t�@�C������v���p�e�B���擾���郆�[�e�B���e�B�N���X�B
 *
 * <p>�f�t�H���g�ł� ApplicationResources �t�@�C����ǂݍ��ނ��A
 * ApplicationResources �t�@�C���ňȉ��̂悤�Ɏw�肷�邱�Ƃɂ��A
 * ���̃v���p�e�B�t�@�C����ǉ��œǂݍ��ނ��Ƃ��ł���B</p>
 * <strong>ApplicationResources.properties�̐ݒ菑��</strong><br>
 * <code><pre>
 *   add.property.file.1 = <i>&lt;�ǉ��v���p�e�B�t�@�C����1&gt;</i>
 *   add.property.file.2 = <i>&lt;�ǉ��v���p�e�B�t�@�C����2&gt;</i>
 *   ...
 * </pre></code>
 * 
 * <p>
 * �܂��A�v���p�e�B�t�@�C�����ʂɎw�肵���ȉ��̋@�\������
 * <ol>
 *  <li>�����L�[�����ɂ��l�擾</li>
 *  <li>�����L�[�擾</li>
 * </ol>
 * �ڍׂ́A
 * getPropertyNames() ���\�b�h�A
 * getPropertiesValues() ���\�b�h���Q�ƁB
 * </p>
 *
 */
public class PropertyUtil {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(PropertyUtil.class);

    /**
     * �f�t�H���g�v���p�e�B�t�@�C�����B
     */
    public static final String DEFAULT_PROPERTY_FILE
        = "ApplicationResources.properties";

    /**
     * �ǉ��v���p�e�B�t�@�C���w��̃v���t�B�b�N�X�B
     */
    private static final String ADD_PROPERTY_PREFIX = "add.property.file.";
    
    /**
     * �v���p�e�B�t�@�C���̊g���q�B
     */
    private static final String PROPERTY_EXTENSION = ".properties";

    /**
     * �v���p�e�B�̃L�[�ƒl��ێ�����I�u�W�F�N�g�B
     */
    private static TreeMap<String, String> props =
            new TreeMap<String, String>();
    
    /**
     * �ǂݍ��񂾃v���p�e�B�t�@�C�������X�g�B
     */
    private static Set<String> files = new HashSet<String>();

    /**
     * �N���X���[�h���Ƀv���p�e�B�t�@�C����ǂݍ��ݏ���������B
     */
    static {
        StringBuilder key = new StringBuilder();
        load(DEFAULT_PROPERTY_FILE);
        if (props != null) {
            for (int i = 1; ; i++) {
                key.setLength(0);
                key.append(ADD_PROPERTY_PREFIX);
                key.append(i);
                String path = getProperty(key.toString());
                if (path == null) {
                    break;
                }
                addPropertyFile(path);
            }
        }
        overrideProperties();
    }

    /**
     * �w�肳�ꂽ�v���p�e�B�t�@�C����ǂݍ��ށB
     * 
     * <p>
     *  �ǂݍ��܂ꂽ�v���p�e�B�t�@�C���́A
     *  �ȑO�ǂݍ��񂾓��e�ɒǉ������B
     * </p>
     *
     * @param name �v���p�e�B�t�@�C����
     */
    private static void load(String name) {
        StringBuilder key = new StringBuilder();
        Properties p = readPropertyFile(name);
        for (Map.Entry e : p.entrySet()) {
            // �ǂݍ��񂾂��̂����ׂ�props�ɒǉ�����B
            props.put((String) e.getKey(), (String) e.getValue());
        }

        if (p != null) {
            for (int i = 1; ; i++) {
                key.setLength(0);
                key.append(ADD_PROPERTY_PREFIX);
                key.append(i);
                String addfile = p.getProperty(key.toString());
                if (addfile == null) {
                    break;
                }
                String path = getPropertiesPath(name, addfile);
                addPropertyFile(path);
            }
        }
    }

    /**
     * �w�肳�ꂽ�v���p�e�B�t�@�C����ǂݍ��ށB
     * 
     * <p>
     * �ȑO�ǂݍ��񂾓��e�ɒǉ������B
     * </p>
     * @param name �v���p�e�B�t�@�C����
     * @return �v���p�e�B���X�g
     */
    private static Properties readPropertyFile(String name) {
        // �J�����g�X���b�h�̃R���e�L�X�g�N���X���[�_���g�p�����
        // WEB-INF/classes�̃v���p�e�B�t�@�C����ǂނ��Ƃ��ł��Ȃ��ꍇ������B
        // ����JNLP�Ń��\�[�X���擾����ɂ́A���C���X���b�h�̃R���e�L�X�g
        // �N���X���[�_�𗘗p���Ȃ���΂Ȃ�Ȃ����ߗ����𕹗p����B
        InputStream is = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(name);
        if (is == null) {
            is = PropertyUtil.class.getResourceAsStream("/" + name);
        }
        
        Properties p = new Properties();
        try {
            try {
                p.load(is);
                files.add(name);

            } catch (NullPointerException e) {
                System.err.println("!!! PANIC: Cannot load " + name + " !!!");
                System.err.println(ExceptionUtil.getStackTrace(e));
            } catch (IOException e) {
                System.err.println("!!! PANIC: Cannot load " + name + " !!!");
                System.err.println(ExceptionUtil.getStackTrace(e));
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return p;
    }

    /**
     * �v���p�e�B�t�@�C������ǂݍ��܂ꂽ���e���A
     * �R�}���h���C���� &quot;-D&quot; �I�v�V�������Ŏw�肳�ꂽ
     * �V�X�e���v���p�e�B�ŏ㏑������B
     */
    private static void overrideProperties() {
        Enumeration<String> enumeration = 
            Collections.enumeration(props.keySet());
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = System.getProperty(name);
            if (value != null) {
                props.put(name, value);
            }
        }
    }

    /**
     * �w�肳�ꂽ�v���p�e�B�t�@�C����ǉ��œǂݍ��ށB
     * 
     * <p>
     *  ������Ăяo���Ă�1�x�����ǂݍ��܂�Ȃ��B
     *  �v���p�e�B�t�@�C������ ".properties" �͏ȗ��ł���B
     * </p>
     * 
     * @param name �v���p�e�B�t�@�C����
     */
    public static void addPropertyFile(String name) {
        if (!name.endsWith(PROPERTY_EXTENSION)) {
            StringBuilder nameBuf = new StringBuilder();
            nameBuf.append(name);
            nameBuf.append(PROPERTY_EXTENSION);
            name = nameBuf.toString();
        }
        if (!files.contains(name)) {
            load(name);
        }
    }

    /**
     * �w�肳�ꂽ�L�[�̃v���p�e�B���擾����B
     *
     * <p>
     *  �Q�ƒl�� &quot;@&quot; �t���̕�����ł��鎞�A�ԐڃL�[�Ƃ݂Ȃ�
     *  ������x &quot;@&quot; ���O������������L�[�Ƃ��Č�������B
     *  <code>key=@key</code>
     *  �Ƃ����`�Œ�`����Ă��鎞�A�������[�v��������邽�߁A
     *  <code>@key</code>�𒼐ڕԋp����B
     *  �擪�� &quot;@&quot; �ł��镶�����l�Ƃ��Đݒ肷��ۂɂ�
     *  �擪�� &quot;@@&quot; �� &quot;@&quot; �ɕύX���v���p�e�B�t�@�C��
     *  �ɐݒ肷�鎖�ŁA�ԐڃL�[�����̋@�\������ł���B
     * </p>
     * 
     * @param key �v���p�e�B�̃L�[
     * @return �w�肳�ꂽ�L�[�̃v���p�e�B�̒l
     */
    public static String getProperty(String key) {
        String result = props.get(key);
            
        // (�L�[)=@(�L�[)�̎��A�������[�v���
        if (result != null && result.equals("@" + key)) {
            return result;
        }
        // @@�̏ꍇ�͊ԐڃL�[������������A@�ƌ��Ȃ��B
        if (result != null && result.startsWith("@@")) {
            return result.substring(1);
        }
        if (result != null && result.startsWith("@")) {
            result = getProperty(result.substring(1));
        }

        return result;
    }

    /**
     * �w�肳�ꂽ�L�[�̃v���p�e�B���擾����B
     * 
     * <p>
     *  �v���p�e�B��������Ȃ������ꍇ�ɂ́A�w�肳�ꂽ�f�t�H���g���Ԃ����B
     * </p>
     * 
     * @param key �v���p�e�B�̃L�[
     * @param defaultValue �v���p�e�B�̃f�t�H���g�l
     * @return �w�肳�ꂽ�L�[�̃v���p�e�B�̒l
     */
    public static String getProperty(String key, String defaultValue) {
        String result = props.get(key);
        if (result == null) {
            return defaultValue;
        }
        return result;
    }

    /**
     * �v���p�e�B�̂��ׂẴL�[�̃��X�g���擾����B
     *
     * @return �v���p�e�B�̂��ׂẴL�[�̃��X�g
     */
    public static Enumeration getPropertyNames() {
        return Collections.enumeration(props.keySet());
    }

    /**
     * �w�肳�ꂽ�v���t�B�b�N�X����n�܂�L�[�̃��X�g���擾����B
     * 
     * @param keyPrefix �L�[�̃v���t�B�b�N�X
     * @return �w�肳�ꂽ�v���t�B�b�N�X����n�܂�L�[�̃��X�g
     */
    public static Enumeration<String> getPropertyNames(String keyPrefix) {
        Map<String, String> map = props.tailMap(keyPrefix);
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String name = iter.next();
            if (!name.startsWith(keyPrefix)) {
                return Collections.enumeration(
                            props.subMap(keyPrefix, name).keySet());
            }
        }
        return Collections.enumeration(map.keySet());  
    }

    /**
     * �v���p�e�B�t�@�C�����A�����L�[��������w�肷�邱�Ƃɂ��
     * �l�Z�b�g���擾����B
     * 
     * @param propertyName �v���p�e�B�t�@�C����
     * @param keyPrefix �����L�[������
     * @return �l�Z�b�g
     */
    public static Set getPropertiesValues(String propertyName ,
            String keyPrefix) {

        Properties localProps = loadProperties(propertyName);
        if (localProps == null) {
            return null;
        }

        Enumeration<String> keyEnum = getPropertyNames(localProps , keyPrefix);
        if (keyEnum == null) {
            return null;
        }

        return getPropertiesValues(localProps , keyEnum);
    }


    /**
     * �v���p�e�B���w�肵�A�����L�[�v���t�B�b�N�X�ɍ��v����
     *  �L�[�ꗗ���擾����B
     * 
     * @param localProps �v���p�e�B
     * @param keyPrefix �����L�[�v���t�B�b�N�X
     * @return �����L�[�v���t�B�b�N�X�ɍ��v����L�[�ꗗ
     */
    public static Enumeration<String> getPropertyNames(
            Properties localProps , String keyPrefix) {

        if (localProps == null || keyPrefix == null) {
            return null;
        }

        Collection<String> matchedNames = new ArrayList<String>();
        Enumeration<?> propNames = localProps.propertyNames();
        while (propNames.hasMoreElements()) {
            String name = (String) propNames.nextElement();
            if (name.startsWith(keyPrefix)) {
                matchedNames.add(name);
            }
        }
        return Collections.enumeration(matchedNames);      
    }

    /**
     * �L�[�ꗗ�ɑ΂��A�v���p�e�B���擾�����l���擾����B
     * 
     * @param localProps �v���p�e�B
     * @param propertyNames �L�[�̈ꗗ
     * @return �l�Z�b�g
     */
    public static Set<String> getPropertiesValues(Properties localProps,
             Enumeration<String> propertyNames) {

         if (localProps == null || propertyNames == null) {
             return null;
         }

         Set<String> retSet = new HashSet<String>();
         while (propertyNames.hasMoreElements()) {
             retSet.add(localProps.getProperty(
               propertyNames.nextElement()));
         }
         return retSet;
    }


    /**
     * �w�肵���v���p�e�B�t�@�C�����ŁA�v���p�e�B�I�u�W�F�N�g���擾����B
     * 
     * @param propertyName �v���p�e�B�t�@�C��
     * @return �v���p�e�B�I�u�W�F�N�g
     */
    public static Properties loadProperties(String propertyName) {
        // propertyName��null�܂��͋󕶎��̎��Anull��ԋp����B
        if (propertyName == null || "".equals(propertyName)) {
            return null;
        }
        Properties retProps = new Properties();
        
        StringBuilder resourceName = new StringBuilder();
        resourceName.append(propertyName);
        resourceName.append(PROPERTY_EXTENSION);
        
        //�J�����g�X���b�h�̃R���e�L�X�g�N���X���[�_���g�p�����
        // WEB-INF/classes�̃v���p�e�B�t�@�C����ǂނ��Ƃ��ł��Ȃ��ꍇ������B
        // ����JNLP�Ń��\�[�X���擾����ɂ́A���C���X���b�h�̃R���e�L�X�g
        // �N���X���[�_�𗘗p���Ȃ���΂Ȃ�Ȃ����ߗ����𕹗p����B
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(resourceName.toString());
        if (is == null) {
            is = PropertyUtil.class.getResourceAsStream(
                    "/" + propertyName + PROPERTY_EXTENSION);
        }

        try {
            retProps.load(is);
        } catch (NullPointerException npe) {
            log.warn("*** Can not find property-file ["
                    + propertyName + ".properties] ***",npe);
            retProps = null;
        } catch (IOException ie) {
            log.error("", ie);
            retProps = null;
        } finally {
            try {
                if (is != null) {
                    is.close();                 
                }
            } catch (IOException ie) {
                log.error("", ie);
                retProps = null;
            }
        }
        return retProps;
    }

    /**
     * �v���p�e�B�t�@�C���̓ǂݏo���p�X���擾����B
     * 
     * �v���p�e�B�t�@�C����ǉ����s�����v���p�e�B�t�@�C����
     * ���݂���f�B���N�g�����x�[�X�ɂ��Ēǉ����ꂽ�v���p�e�B�t�@�C����ǂވׁA
     * �v���p�e�B�t�@�C���̓ǂݏo���f�B���N�g�����擾����B
     * 
     * @param resource �ǉ��w����L�q���Ă���v���p�e�B�t�@�C��
     * @param addFile �ǉ�����v���p�e�B�t�@�C��
     * @return �ǉ�����v���p�e�B�t�@�C���̓ǂݏo���p�X
     */
    private static String getPropertiesPath(String resource, String addFile) {
        File file = new File(resource);
        String dir = file.getParent();
        if (dir != null) {
            StringBuilder dirBuf = new StringBuilder();
            dirBuf.setLength(0);
            dirBuf.append(dir);
            dirBuf.append(File.separator);
            dir = dirBuf.toString();
        } else {
            dir = "";
        }
        StringBuilder retBuf = new StringBuilder();
        retBuf.setLength(0);
        retBuf.append(dir);
        retBuf.append(addFile);
        return retBuf.toString();
    }
}
