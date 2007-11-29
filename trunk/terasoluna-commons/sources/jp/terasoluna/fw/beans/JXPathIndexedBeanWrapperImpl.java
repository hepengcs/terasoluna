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

package jp.terasoluna.fw.beans;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.terasoluna.fw.beans.jxpath.BeanPointerFactoryEx;
import jp.terasoluna.fw.beans.jxpath.DynamicPointerFactoryEx;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathException;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JXPath��p����IndexBeanWrapper�̎����B
 * 
 * <p>JavaBean�AMap�ADynaBean����v���p�e�B�����w�肷�邱�Ƃɂ��A
 * �����l���擾���邱�Ƃ��ł���B
 * �������z��EList�^�̏ꍇ�A�Y�����鑮���l��S�Ď擾����B
 * <h5>�擾�ł��鑮���̌^</h5>
 * <ul>
 *   <li>�v���~�e�B�u�^</li>
 *   <li>�v���~�e�B�u�^�̔z��</li>
 *   <li>JavaBean</li>
 *   <li>JavaBean�̔z��EList�^</li>
 *   <li>Map�^</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Map�I�u�W�F�N�g�A�܂���Map�^�������g�p����ꍇ�A
 * �ȉ��̕�����Map�L�[�Ɏg�p�ł��Ȃ��B
 * <ul>
 *   <li>/ �c�X���b�V��</li>
 *   <li>[ �c�p�������i�J���j</li>
 *   <li>] �c�p�������i���j</li>
 *   <li>. �c�h�b�g</li>
 *   <li>' �c�V���O���N�H�[�g</li>
 *   <li>" �c�_�u���N�H�[�g</li>
 *   <li>( �c�������i�J���j</li>
 *   <li>) �c�������i���j</li>
 * </ul>
 * </p>
 * 
 * <hr>
 *
 * <h4>�ȒP�Ȏg�p��</h4>
 *
 * <p>�ȉ��̂悤��Employee�I�u�W�F�N�g��firstName�����ɃA�N�Z�X����B
 * <pre>
 * public class Employee {
 *     private String firstName;
 *
 *     public void setFirstName(String firstName) {
 *         this.firstName = firstName;
 *     }
 *     public String getFirstName() {
 *         return firstName;
 *     }
 * }
 * </pre>
 * </p>
 *
 * <p><u>�P�D�R���X�g���N�^�ŃA�N�Z�X�Ώۂ�JavaBean�����b�v����B</u>
 * <pre>
 * // �A�N�Z�X�ΏۂƂȂ�Employee�I�u�W�F�N�g
 * Employee emp = new Employee();
 * emp.setFirstName("�߂�");
 * 
 * IndexedBeanWrapper bw = new JXPathIndexedBeanWrapperImpl(emp);
 * </pre>
 * </p>
 *
 * <p><u>�Q�DfirstName�����ɃA�N�Z�X����B</u>
 * ������String�ɂ͑��������w�肷��B
 * <pre>
 * Map&lt;String, Object&gt; map = bw.getIndexedPropertyValues("<strong>firstName</strong>");
 * </pre>
 * 
 * �L�[���v���p�e�B���A�l�������l��Map�C���X�^���X���Ԃ����B
 * �ȉ��̃R�[�h�ł͑S�Ă̗v�f���R���\�[���ɏo�͂��Ă���B
 * <pre>
 * System.out.println("Map�̃L�[�FMap�̒l");
 * System.out.println("========================");
 * Set&lt;String&gt; keyset = map.keySet();
 * for (String key : keyset) {
 *     System.out.print(key + ":");
 *     System.out.println(map.get(key).toString());
 * }
 * </pre>
 * ���ʂ͈ȉ��̂悤�ɏo�͂����B
 * <pre>
 * Map�̃L�[�FMap�̒l
 * ========================
 * firstName:�߂�
 * </pre>
 * </p>
 * 
 * <hr>
 *
 * <h4>�z�񑮐��ւ̃A�N�Z�X</h4>
 *
 * <p>�ȉ��̂悤��Address�I�u�W�F�N�g�̔z��^����numbers�ɃA�N�Z�X����B
 * <pre>
 * public class Address {
 *     private int[] numbers;
 *
 *     public void setNumbers(int[] numbers) {
 *         this.numbers = numbers;
 *     }
 *     public int[] getNumbers() {
 *         return numbers;
 *     }
 * }
 * </pre>
 * </p>
 *
 * <p><u>�P�D�R���X�g���N�^�ŃA�N�Z�X�Ώۂ�JavaBean�����b�v����B</u>
 * <pre>
 * // Employee�̑����ƂȂ�Address�I�u�W�F�N�g
 * Address address = new Address();
 * address.setNumbers(new int[]{1, 2, 3});
 * 
 * IndexedBeanWrapper bw = new JXPathIndexedBeanWrapperImpl(address);
 * </pre>
 * </p>
 *
 * <p><u>�Q�Dnumbers�����ɃA�N�Z�X����B</u>
 * <em>'numbers[]'�̂悤�ɔz��L����t����K�v�͂Ȃ��A
 * ���������w�肷��΂悢���Ƃɒ��ӂ��邱�ƁB</em>
 * <pre>
 * Map&lt;String, Object&gt; map = bw.getIndexedPropertyValues("<strong>numbers</strong>");
 * </pre>
 * </p>
 *
 * �L�[���v���p�e�B���A�l�������l��Map�C���X�^���X���Ԃ����B
 * �ȉ��̃R�[�h�ł͑S�Ă̗v�f���R���\�[���ɏo�͂��Ă���B
 * <pre>
 * System.out.println("Map�̃L�[�FMap�̒l");
 * System.out.println("========================");
 * Set&lt;String&gt; keyset = map.keySet();
 * for (String key : keyset) {
 *     System.out.print(key + ":");
 *     System.out.println(map.get(key).toString());
 * }
 * </pre>
 * ���ʂ͈ȉ��̂悤�ɏo�͂����B
 * <pre>
 * Map�̃L�[�FMap�̒l
 * ========================
 * numbers[0]:1
 * numbers[1]:2
 * numbers[2]:3
 * </pre>
 * List�^�̃I�u�W�F�N�g�ɑ΂��Ă��A���l�̕��@�Œl���擾�ł���B
 * </p>
 * 
 * <hr>
 *
 * <h4>�l�X�g���������ւ̃A�N�Z�X</h4>
 *
 * <p>���L�̂悤��Employee�I�u�W�F�N�g����A
 * �l�X�g���ꂽAddress�N���X��streetNumber�����ɃA�N�Z�X����B
 * <pre>
 * public class Employee {
 *     private Address homeAddress;
 *
 *     public void setHomeAddress(Address homeAddress) {
 *         this.homeAddress = homeAddress;
 *     }
 *     public Address getHomeAddress() {
 *         return homeAddress;
 *     }
 * }
 * public class Address {
 *     private String streetNumber;
 *
 *     public void setStreetNumber(String streetNumber) {
 *         this.streetNumber = streetNumber;
 *     }
 *     public String getStreetNumber() {
 *         return streetNumber;
 *     }
 * }
 * </pre>
 * </p>
 * 
 * <p><u>�P�D�R���X�g���N�^�ŃA�N�Z�X�Ώۂ�JavaBean�����b�v����B</u>
 * <pre>
 * // Employee�̑����ƂȂ�Address�I�u�W�F�N�g
 * Address address = new Address();
 * address.setStreetNumber("�Z��");
 * 
 * // Employee
 * Employee emp = new Employee();
 * emp.setHomeAddress(address);
 * 
 * IndexedBeanWrapper bw = new JXPathIndexedBeanWrapperImpl(emp);
 * </pre>
 * </p>
 *
 * <p><u>�Q�DEmployee�I�u�W�F�N�g��homeAddress�����Ƀl�X�g���ꂽ�A
 * streetNumber�����ɃA�N�Z�X����B</u>
 * �l�X�g�����������w�肷��ꍇ�A�ȉ��̃R�[�h�̂悤��'.'�i�h�b�g�j��
 * ��������A������B
 * <pre>
 * Map&lt;String, Object&gt; map = bw.getIndexedPropertyValues("<strong>homeAddress.streetNumber</strong>");
 * </pre>
 * </p>
 * 
 * �L�[���v���p�e�B���A�l�������l��Map�C���X�^���X���Ԃ����B
 * �ȉ��̃R�[�h�ł͑S�Ă̗v�f���R���\�[���ɏo�͂��Ă���B
 * <pre>
 * System.out.println("Map�̃L�[�FMap�̒l");
 * System.out.println("========================");
 * Set&lt;String&gt; keyset = map.keySet();
 * for (String key : keyset) {
 *     System.out.print(key + ":");
 *     System.out.println(map.get(key).toString());
 * }
 * </pre>
 * ���ʂ͈ȉ��̂悤�ɏo�͂����B
 * <pre>
 * Map�̃L�[�FMap�̒l
 * ========================
 * homeAddress.streetNumber:�Z��
 * </pre>
 * �l�X�g�����������z��EList�^�ł����Ă��A�l���擾���邱�Ƃ��ł���B
 * </p>
 * 
 * <hr>
 *
 * <h4>Map�^�����ւ̃A�N�Z�X</h4>
 *
 * <p>���L�̂悤��Employee�I�u�W�F�N�g��Map����addressMap�ɃA�N�Z�X����B
 * <pre>
 * public class Employee {
 *     private Map addressMap;
 *
 *     public void setAddressMap(Map addressMap) {
 *         this.addressMap = addressMap;
 *     }
 *     public Map getAddressMap() {
 *         return addressMap;
 *     }
 * }
 * </pre>
 * </p>
 * 
 * <p><u>�P�D�R���X�g���N�^�ŃA�N�Z�X�Ώۂ�JavaBean�����b�v����B</u>
 * <pre>
 * // Employee�̑����ƂȂ�Map
 * Map addressMap = new HashMap();
 * addressMap.put("home", "address1");
 * 
 * // Employee
 * Employee emp = new Employee();
 * emp.setAddressMap(addressMap);
 * 
 * IndexedBeanWrapper bw = new JXPathIndexedBeanWrapperImpl(emp);
 * </pre>
 * </p>
 *
 * <p><u>�Q�DEmployee��addressMap�������ɃZ�b�g����home�L�[�ɃA�N�Z�X����B</u>
 * Map�^�����̃L�[���w�肷��ꍇ�A�ȉ��̃R�[�h�̂悤�ɂ������ŃL�[����A������B
 * <pre>
 * Map&lt;String, Object&gt; map = bw.getIndexedPropertyValues("<strong>addressMap(home)</strong>");
 * </pre>
 * </p>
 * 
 * �L�[���v���p�e�B���A�l�������l��Map�C���X�^���X���Ԃ����B
 * �ȉ��̃R�[�h�ł͑S�Ă̗v�f���R���\�[���ɏo�͂��Ă���B
 * <pre>
 * System.out.println("Map�̃L�[�FMap�̒l");
 * System.out.println("========================");
 * Set&lt;String&gt; keyset = map.keySet();
 * for (String key : keyset) {
 *     System.out.print(key + ":");
 *     System.out.println(map.get(key).toString());
 * }
 * </pre>
 * ���ʂ͈ȉ��̂悤�ɏo�͂����B
 * <pre>
 * Map�̃L�[�FMap�̒l
 * ========================
 * addressMap(home):address1
 * </pre>
 * Map�^�����̃L�[����()�i���ʁj�ň͂��邱�Ƃɒ��ӂ��邱�ƁB
 * </p>
 *
 * <hr>
 *
 * <h4>Map�I�u�W�F�N�g�ւ̃A�N�Z�X</h4>
 *
 * <p>�{�N���X��JavaBean�����ł͂Ȃ��AMap�I�u�W�F�N�g�ւ̃A�N�Z�X���\�ł���B
 * 
 * <p><u>�P�D�R���X�g���N�^�ŃA�N�Z�X�Ώۂ�Map�����b�v����B</u>
 * <pre>
 * // Employee�̑����ƂȂ�Map
 * Map addressMap = new HashMap();
 * addressMap.put("home", "address1");
 * 
 * IndexedBeanWrapper bw = new JXPathIndexedBeanWrapperImpl(addressMap);
 * </pre>
 * </p>
 *
 * <p><u>�Q�DaddressMap�ɃZ�b�g����home�L�[�ɃA�N�Z�X����B</u>
 * <pre>
 * Map&lt;String, Object&gt; map = bw.getIndexedPropertyValues("<strong>home</strong>");
 * </pre>
 * </p>
 * 
 * �L�[���v���p�e�B���A�l�������l��Map�C���X�^���X���Ԃ����B
 * �ȉ��̃R�[�h�ł͑S�Ă̗v�f���R���\�[���ɏo�͂��Ă���B
 * <pre>
 * System.out.println("Map�̃L�[�FMap�̒l");
 * System.out.println("========================");
 * Set&lt;String&gt; keyset = map.keySet();
 * for (String key : keyset) {
 *     System.out.print(key + ":");
 *     System.out.println(map.get(key).toString());
 * }
 * </pre>
 * ���ʂ͈ȉ��̂悤�ɏo�͂����B
 * <pre>
 * Map�̃L�[�FMap�̒l
 * ========================
 * home:address1
 * </pre>
 * Map�I�u�W�F�N�g�ɑ΂��Ă��A�z��EList�^�����A
 * �l�X�g���������̎擾���\�ł���B
 * </p>
 * 
 * <hr>
 *
 * <h4>DynaBean�ւ̃A�N�Z�X</h4>
 *
 * <p>�{�N���X��JavaBean�����ł͂Ȃ��ADynaBean�ւ̃A�N�Z�X���\�ł���B
 * 
 * <p><u>�P�D�R���X�g���N�^�ŃA�N�Z�X�Ώۂ�DynaBean�����b�v����B</u>
 * <pre>
 * // DynaBean�Ƀ��b�v�����JavaBean
 * Address address = new Address();
 * address.setStreetNumber("�Z��");
 * 
 * // DynaBean
 * DynaBean dynaBean = new WrapDynaBean(address);
 * 
 * IndexedBeanWrapper bw = new JXPathIndexedBeanWrapperImpl(dynaBean);
 *     
 * --------------------------------------------------------
 * ��L�̃R�[�h�Ŏg�p���Ă���Address�I�u�W�F�N�g�͈ȉ��̂悤�ȃN���X�ł���B
 * 
 * public class Address {
 *     private String streetNumber;
 *
 *     public void setStreetNumber(String streetNumber) {
 *         this.streetNumber = streetNumber;
 *     }
 *     public String getStreetNumber() {
 *         return streetNumber;
 *     }
 * }
 * </pre>
 * 
 * </p>
 *
 * <p><u>�Q�DDynaBean��streetNumber�����ɃA�N�Z�X����B</u>
 * <pre>
 * Map&lt;String, Object&gt; map = bw.getIndexedPropertyValues("<strong>streetNumber</strong>");
 * </pre>
 * </p>
 * 
 * �L�[���v���p�e�B���A�l�������l��Map�C���X�^���X���Ԃ����B
 * �ȉ��̃R�[�h�ł͑S�Ă̗v�f���R���\�[���ɏo�͂��Ă���B
 * <pre>
 * System.out.println("Map�̃L�[�FMap�̒l");
 * System.out.println("========================");
 * Set&lt;String&gt; keyset = map.keySet();
 * for (String key : keyset) {
 *     System.out.print(key + ":");
 *     System.out.println(map.get(key).toString());
 * }
 * </pre>
 * ���ʂ͈ȉ��̂悤�ɏo�͂����B
 * <pre>
 * Map�̃L�[�FMap�̒l
 * ========================
 * streetNumber:�Z��
 * </pre>
 * </p>
 * 
 */
public class JXPathIndexedBeanWrapperImpl implements IndexedBeanWrapper {
    /**
     * ���O�N���X�B
     */
    private static Log log 
        = LogFactory.getLog(JXPathIndexedBeanWrapperImpl.class);
    
    /**
     * JXPath�R���e�L�X�g�B
     */
    protected JXPathContext context = null;
    
    /**
     * �����������B
     * 
     * <p>�g������NodePointer�t�@�N�g����ǉ�����B
     * NodePointer�t�@�N�g����static���\�b�h�ŁA��x�����Ăяo���B
     * ���s����NodePointer�t�@�N�g���ǉ����s�Ȃ��ƁA
     * �}���`�X���b�h���ɂ�NullPointerException����������\��������B</p>
     */
    static {
    	JXPathContextReferenceImpl.addNodePointerFactory(
                new BeanPointerFactoryEx());
        JXPathContextReferenceImpl.addNodePointerFactory(
                new DynamicPointerFactoryEx());
    }
    
    /**
     * �R���X�g���N�^�B
     * @param target �Ώۂ̃I�u�W�F�N�g
     */
    public JXPathIndexedBeanWrapperImpl(Object target) {
        // �^�[�Q�b�g�ƂȂ�JavaBean��Null�̏ꍇ�͗�O
        if (target == null) {
            log.error("TargetBean is null!");
            throw new IllegalArgumentException("TargetBean is null!");
        }
        context = JXPathContext.newContext(target);
    }
    
    /**
     * �w�肵���v���p�e�B���Ɉ�v���鑮���l��Ԃ��B
     *
     * @param propertyName �v���p�e�B��
     * @return �v���p�e�B���Ɉ�v���鑮���l���i�[����Map�i�ʒu���A�����l�j
     */
    public Map<String, Object> getIndexedPropertyValues(String propertyName) {
        
        // �v���p�e�B����Null�E�󕶎�
        if (StringUtils.isEmpty(propertyName)) {
            String message = "PropertyName is empty!";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        
        // �v���p�e�B���ɕs���ȕ���
        if (StringUtils.indexOfAny(propertyName,
                new char[]{'/', '"', '\''}) != -1) { 
            String message = "Invalid character has found within property name."
                + " '" + propertyName + "' " + "Cannot use [ / \" ' ]";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        
        // �z���[]�ȊO��[]���g���Ă���
        String stringIndex = extractIndex(propertyName);
        if (stringIndex.length() > 0) {
            try {
                Integer.parseInt(stringIndex);
            } catch (NumberFormatException e) {
                String message = "Invalid character has found within property name."
                    + " '" + propertyName + "' " + "Cannot use [ [] ]";
                log.error(message);
                throw new IllegalArgumentException(message);
            }
        }
        
        Map<String, Object> result
            = new LinkedHashMap<String, Object>();
        String requestXpath = toXPath(propertyName);
        
        // JXPath����v���p�e�B�擾
        Iterator ite = null;
        try {
            ite = context.iteratePointers(requestXpath);
        } catch (JXPathException e) {
            // �v���p�e�B�����s��
            String message = 
                "Invalid property name. "
                + "PropertyName: '" + propertyName + "'"
                + "XPath: '" + requestXpath + "'";
            log.error(message, e);
            throw new IllegalArgumentException(message, e);
        }
        
        // XPath �� Java property
        while (ite.hasNext()) {
            Pointer p = (Pointer) ite.next();
            result.put(this.toPropertyName(p.asPath()), p.getValue());
        }
        return result;
    }
    
    /**
     * �v���p�e�B�`���̕������XPath�`���̕�����ɕϊ�����B
     * @param propertyName �v���p�e�B�`��������
     * @return XPath�`��������
     */
    protected String toXPath(String propertyName) {
        StringBuilder builder = new StringBuilder("/");
        String[] properties = StringUtils.split(propertyName, '.');
        
        if (properties == null || properties.length == 0) {
            String message = "Property name is null or blank.";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        
        for (String property : properties) {
            // �l�X�g
            if (builder.length() > 1) {
                builder.append('/');
            }
            
            // Map����
            if (isMapProperty(property)) {
                builder.append(escapeMapProperty(property));
                
            // JavaBean �܂��� Primitive
            } else {
                builder.append(extractAttributeName(property));
            }
           
            // �z��C���f�b�N�X
            builder.append(extractIncrementIndex(property));
        }
        return builder.toString();
    }
    
    /**
     * �C���N�������g���ꂽ�Y���������o���B
     * @param property Java�v���p�e�B���B
     * @return String XPath�`���̓Y�����B 
     */
    protected String extractIncrementIndex(String property) {
        return extractIncrementIndex(property, 1);
    }

    /**
     * �C���N�������g���ꂽ�Y���������o���B
     * @param property �v���p�e�B���B
     * @param increment �C���N�������g����l�B
     * @return String �C���N�������g���ꂽ�Y�����B
     */
    protected String extractIncrementIndex(String property, int increment) {
        String stringIndex = extractIndex(property);
        if ("".equals(stringIndex)) {
            return "";
        }
        
        // �Y�������擾�ł����ꍇ�A�C���N�������g����
        try {
            int index = Integer.parseInt(stringIndex);
            return new StringBuilder().append('[')
                .append(index + increment).append(']').toString();
        } catch (NumberFormatException e) {
            // �z���[]�ł͂Ȃ�
            return "";
        }
    }

    /**
     * �z��C���f�b�N�X���擾����B
     * @param property �v���p�e�B���B
     * @return �z��C���f�b�N�X�B
     */
    protected String extractIndex(String property) {
        int start = property.lastIndexOf('[');
        int end = property.lastIndexOf(']');
        
        // []���Ȃ��̂Ŕz��ł͂Ȃ�
        if (start == -1 && end == -1) {
            return "";
        }
        
        // ']aaa[' �̂悤��[]�̈ʒu���s���A�܂���[]�̂ǂ��炩�����Ȃ�
        if (start == -1 || end == -1 || start > end) {
            String message = "Cannot get Index. "
                + "Invalid property name. '" + property + "'";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        return property.substring(start + 1, end);
    }
    
    /**
     * Map�v���p�e�B��XPath�`���ɃG�X�P�[�v����B
     * @param property Java�v���p�e�B���B
     * @return String XPath�B 
     */
    protected String escapeMapProperty(String property) {
        // aaa(bbb) �� aaa/bbb
        String mapPropertyName = extractMapPropertyName(property);
        String mapKey = extractMapPropertyKey(property);
        return mapPropertyName + "/" + mapKey;
    }

    /**
     * Map�^�����̃v���p�e�B�������o���B
     * @param property Java�v���p�e�B���B
     * @return String XPath�B 
     */
    protected String extractMapPropertyName(String property) {
        int pos = property.indexOf('(');
        
        // '('���Ȃ��ꍇ�͗�O
        if (pos == -1) {
            String message = "Cannot get Map attribute. "
                + "Invalid property name. '" + property + "'";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        return property.substring(0, pos);
    }

    /**
     * Map�^�����̃L�[�������o���B
     * @param property Java�v���p�e�B���B
     * @return String XPath�B 
     */
    protected String extractMapPropertyKey(String property) {
        // aaa(bbb) �� bbb
        int start = property.indexOf('(');
        int end = property.indexOf(')');
        
        // '()'���Ȃ��A�܂���()�̈ʒu���s���ȏꍇ�͗�O
        if (start == -1 || end == -1 || start > end) {
            String message = "Cannot get Map key. "
                + "Invalid property name. '" + property + "'";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        return property.substring(start + 1, end);
    }

    /**
     * Map�^�������ǂ������f����B
     * @param property Java�v���p�e�B���B
     * @return boolean Map�^�����Ȃ��true�A����ȊO��false��Ԃ��B 
     */
    protected boolean isMapProperty(String property) {
        // '()'�������Map�^����
        if (property.indexOf('(') != -1 && property.indexOf(')') != -1) {
            return true;
        }
        return false;
    }
    
    /**
     * XPath�`���̕�������v���p�e�B�`���̕�����ɕϊ�����B
     * @param xpath XPath�`��������
     * @return �v���p�e�B�`��������
     */
    protected String toPropertyName(String xpath) {
        StringBuilder builder = new StringBuilder("");
        String[] nodes = StringUtils.split(xpath, '/');
        
        if (nodes == null || nodes.length == 0) {
            String message = "XPath is null or blank.";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        
        for (int i = 0; i < nodes.length; i++) {
            String node = nodes[i];
            
            // Map�I�u�W�F�N�g
            if (i == 0 && isMapObject(node)) {
                builder.append(extractMapKey(node));
                builder.append(extractDecrementIndex(node));
                continue;
            }
            
            // �l�X�g
            if (builder.length() > 0) {
                builder.append('.');
            }
            
            // Map����
            if (isMapAttribute(node)) {
                builder.append(extractMapAttributeName(node));
                builder.append('(');
                builder.append(extractMapKey(node));
                builder.append(')');
                
            // JavaBean �܂��� primitive
            } else {
                builder.append(extractAttributeName(node));
            }
            
            // �z��C���f�b�N�X
            builder.append(extractDecrementIndex(node));
        }
        return builder.toString();
    }

    /**
     * �����������o���B
     * �z��̏ꍇ�A�Y�����̓J�b�g�����B
     * @param node XPath�̃m�[�h�B
     * @return �������B
     */
    protected String extractAttributeName(String node) {
        int pos = node.lastIndexOf('[');
        if (pos == -1) {
            return node;
        }
        // �z��̓Y�����̓J�b�g
        return node.substring(0, pos);
    }

    /**
     * Map�̑����������o���B
     * @param node XPath�̃m�[�h�B
     * @return �������B
     */
    protected String extractMapAttributeName(String node) {
        // �ŏ���'['�܂ł̕������Map�̑������Ƃ���
        int pos = node.indexOf('[');
        
        // '['���Ȃ��ꍇ�͗�O
        if (pos == -1) {
            String message = "Cannot get Map attribute. "
                + "Invalid property name. '" + node + "'";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        return node.substring(0, pos);
    }

    /**
     * Map�L�[�����o���B
     * @param node XPath�̃m�[�h�B
     * @return �������B
     */
    protected String extractMapKey(String node) {
        // aaa[@name='bbb'] �� bbb 
        int start = node.indexOf('[');
        int end = node.indexOf(']');
        
        // '[]'���Ȃ��A�܂���[]�̈ʒu���s���ȏꍇ�͗�O
        if (start == -1 || end == -1 || start > end) {
            String message = "Cannot get Map key. "
                + "Invalid property name. '" + node + "'";
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        return node.substring(start + "[@name='".length(), end - "'".length());
    }

    /**
     * �f�N�������g�����Y���������o���B
     * @param node XPath�̃m�[�h�B
     * @return �������B
     */
    protected String extractDecrementIndex(String node) {
        return extractIncrementIndex(node, -1);
    }

    /**
     * Map���������I�u�W�F�N�g���ǂ������f����B
     * @param node XPath�̃m�[�h�B
     * @return Map�����Ȃ��true�A����ȊO��false��Ԃ��B
     */
    protected boolean isMapAttribute(String node) {
        // '[@name'�������Map����
        if (node.indexOf("[@name") != -1) {
            return true;
        }
        return false;
    }

    /**
     * Map�I�u�W�F�N�g���ǂ������f����B
     * @param node XPath�̃m�[�h�B
     * @return Map�I�u�W�F�N�g�Ȃ��true�A����ȊO��false��Ԃ��B
     */
    protected boolean isMapObject(String node) {
        // '.[@name'�c�Ŏn�܂�Ȃ��Map�I�u�W�F�N�g
        if (node.startsWith(".[@name")) {
            return true;
        }
        return false;
    }
}
