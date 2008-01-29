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

package jp.terasoluna.fw.oxm.xsd.xerces;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Stack;

import jp.terasoluna.fw.oxm.xsd.exception.IllegalSchemaDefinitionException;
import jp.terasoluna.fw.oxm.xsd.exception.UnknownXMLDataException;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XNIException;

/**
 * XML�f�[�^�̃p�[�X���̏ڍׂȃG���[���Ƃ��āA�t�B�[���h�����������߁AXMLErrorReporter�̊g�����s�����N���X�B
 * <p>
 * �t�B�[���h���Ƃ́A�`���`�F�b�N�̃G���[�����������ӏ�����肷�邽�߂̏��ł���B<br>
 * �X�^�b�N�Ɋi�[����Ă���v�f���ɃC���f�b�N�X��t��������������h�b�g�i"."�j�ŘA�����A �t�B�[���h���𐶐�����B<br>
 * �v�f�̐����P�̏ꍇ�ł��A�K���C���f�b�N�X��t������B �����ɂ̓C���f�b�N�X��t�����Ȃ��B<br>
 * �G���[�����������ꍇ�ɐ��������t�B�[���h���̃T���v�����ȉ��ɋL���B
 * </p>
 * <p>
 * �yXML�f�[�^�̃T���v���z <br>
 * <code><pre>
 *   &lt;sample-dto param-d=&quot;...&quot;&gt;
 *     &lt;param-a&gt;
 *       &lt;param-b&gt;
 *         &lt;param-c&gt;...&lt;/param-c&gt;
 *       &lt;/param-b&gt;
 *     &lt;/param-a&gt;
 *   &lt;/sample-dto&gt;
 * </pre></code>
 * </p>
 * <p>
 * ��L��XML�f�[�^��param-c�v�f�ɕs���Ȓl�����͂��ꂽ�ꍇ�ɐ��������t�B�[���h�����ȉ��ɋL���B<br>
 * �t�B�[���h���F<code><b>sample-dto[0].param-a[0].param-b[0].param-c[0]</code></b><br>
 * ���v�f�̐����P�ł��C���f�b�N�X���t������Ă���B
 * </p>
 * <p>
 * ��L��XML�f�[�^��param-d�����ɕs���Ȓl�����͂��ꂽ�ꍇ�ɐ��������t�B�[���h�����ȉ��ɋL���B<br>
 * �t�B�[���h���F<code><b>sample-dto[0].param-a[0].param-b[0].param-d</code></b><br>
 * �������ɂ̓C���f�b�N�X���t������Ă��Ȃ��B
 * </p>
 * <p>
 * <b>�`���`�F�b�N�Ő��������G���[�R�[�h</b>
 * <p>
 * �`���`�F�b�N�Ŕ�������G���[�R�[�h�̈ꗗ���ȉ��ɋL���B<br>
 * <table border="1" CELLPADDING="8">
 * <tr>
 * <th>�G���[�R�[�h</th>
 * <th>�u��������</th>
 * <th>���������</th>
 * </tr>
 * <tr>
 * <td>typeMismatch.number</td>
 * <td>{���͂��ꂽ�l, �f�[�^�^}</td>
 * <td>�s���Ȑ��l�����͂��ꂽ�ꍇ</td>
 * </tr>
 * <tr>
 * <td>typeMismatch.boolean</td>
 * <td>{���͂��ꂽ�l, �f�[�^�^}</td>
 * <td>�s����boolean�l�����͂��ꂽ�ꍇ</td>
 * </tr>
 * <tr>
 * <td>typeMismatch.date</td>
 * <td>{���͂��ꂽ�l, �f�[�^�^}</td>
 * <td>�s���ȓ��t�����͂��ꂽ�ꍇ</td>
 * </tr>
 * <tr>
 * <td>typeMismatch.numberMaxRange</td>
 * <td>{���͂��ꂽ�l, �f�[�^�^�̍ő�l, �f�[�^�^}</td>
 * <td>��`���ꂽ�^�̍ő�l���傫�����l�����͂��ꂽ�ꍇ</td>
 * </tr>
 * <tr>
 * <td>typeMismatch.numberMinRange</td>
 * <td>{���͂��ꂽ�l, �f�[�^�^�̍ŏ��l, �f�[�^�^}</td>
 * <td>��`���ꂽ�^�̍ŏ��l��菬�������l�����͂��ꂽ�ꍇ</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * <b>�`���`�F�b�N�T���v��</b>
 * </p>
 * <p>
 * �X�L�[�}��`�t�@�C����XML�f�[�^�̌`�����`����
 * </p>
 * <p>
 * �y�X�L�[�}��`�t�@�C���ݒ��z<br>
 * <code><pre>
 *    &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 *    &lt;xs:schema xmlns:xs=&quot;http://www.w3.org/2001/XMLSchema&quot;&gt;
 *      &lt;xs:element name=&quot;int-param&quot; type=&quot;int-param-type&quot;/&gt;
 *      &lt;xs:complexType name=&quot;int-param-type&quot;&gt;
 *        &lt;xs:sequence&gt;
 *          &lt;xs:element name=&quot;param1&quot; type=&quot;xs:int&quot; /&gt;
 *          &lt;xs:element name=&quot;param2&quot; type=&quot;xs:int&quot; /&gt;
 *        &lt;/xs:sequence&gt;
 *      &lt;xs:attribute name=&quot;param3&quot; type=&quot;xs:int&quot;/&gt;
 *      &lt;/xs:complexType&gt;
 *    &lt;/xs:schema&gt;
 * </pre></code>
 * </p>
 * <p>
 * �v�f(param2)�Ƒ���(param3)�ɕs���Ȓl���i�[���ꂽXML�f�[�^����͂���
 * </p>
 * <p>
 * �y���͂����XML�f�[�^��z<br>
 * <code><pre>
 *   &lt;int-param param3=&quot;30b&quot;&gt;
 *   &lt;param1&gt;100&lt;/param1&gt; 
 *   &lt;param2&gt;20a&lt;/param2&gt;
 *   &lt;/int-param&gt;
 * </pre></code>
 * </p>
 * <p>
 * �`���`�F�b�N�ŃG���[���������A�G���[���b�Z�[�W���i�[���ꂽ�C���X�^���X�����������B<br>
 * �G���[���b�Z�[�W�̓K�؂ȃn���h�����O���s�����ƁB
 * </p>
 * <p>
 * �yparam2�̃G���[���b�Z�[�W���i�[���ꂽ�C���X�^���X�z<br>
 * <code><pre>
 *    �t�B�[���h���Fint-param[0].param2[0]
 *    �G���[�R�[�h�FtypeMismatch.number
 *    �u��������F{20a, integer}
 * </pre></code>
 * </p>
 * <p>
 * �yparam3�̃G���[���b�Z�[�W���i�[���ꂽ�C���X�^���X�z<br>
 * <code><pre>
 *    �t�B�[���h���Fint-param[0].param3
 *    �G���[�R�[�h�FtypeMismatch.number
 *    �u��������F{30b, integer}
 * </pre></code>
 * </p>
 * <p>
 * <b>null�l�i�󕶎��j�̋��e</b>
 * </p>
 * <p>
 * XML�X�L�[�}�̎d�l�ł́A���l�^�̗v�f�̒l��null�l�����e���邱�Ƃ͂ł��Ȃ��B<br>
 * ���l�^�̗v�f�̒l��null�l�����e����ꍇ�́A�Ǝ��̃f�[�^�^��錾���邱�ƁB<br>
 * �Ǝ��̃f�[�^�^��錾����ꍇ�́A������AllowEmpty��t���邱�ƁB�i�G���[�̃n���h�����O���s�����߂ɕK�v�j<br>
 * ��null�l�Ƃ́A�u<code>&lt;param&gt;&lt;/param&gt;</code>�v�̂悤�ɗv�f�̒l�Ƃ��ċ󕶎����ݒ肳��邱�Ƃ��w���B
 * </p>
 * <p>
 * �y���l�^�̗v�f�uparam�v��null�l�����e����ꍇ�̃X�L�[�}��`�t�@�C���ݒ��z<br>
 * <code><pre>
 *     &lt;xs:simpleType name=&quot;integerAllowEmpty&quot;&gt;
 *       &lt;xs:union&gt;
 *         &lt;xs:simpleType&gt;
 *           &lt;xs:restriction base=&quot;xs:string&quot;&gt;
 *             &lt;xs:enumeration value=&quot;&quot; /&gt;
 *           &lt;/xs:restriction&gt;
 *         &lt;/xs:simpleType&gt;
 *         &lt;xs:simpleType&gt;
 *           &lt;xs:restriction base=&quot;xs:integer&quot; /&gt;
 *         &lt;/xs:simpleType&gt;
 *       &lt;/xs:union&gt;
 *     &lt;/xs:simpleType&gt;
 *     &lt;xs:element name=&quot;param&quot; type=&quot;integerAllowEmpty&quot; /&gt;
 * </pre></code>
 * </p>
 * 
 * @see jp.terasoluna.fw.oxm.xsd.xerces.SchemaValidatorImpl
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessages
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessage
 * @see jp.terasoluna.fw.oxm.xsd.xerces.XMLSchemaValidatorEx
 * 
 */
public class XMLErrorReporterEx extends XMLErrorReporter {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(XMLErrorReporterEx.class);

    /**
     * XML�̗v�f���ƂɍŏI�C���f�b�N�X��Map�`���Ŏ��C���X�^���X�B
     * <p>
     * �C���f�b�N�X�����̗v�f���ŃC���f�b�N�X�l��ێ�����B<br>
     * �w�肷��v�f�͏�ʃ^�O�����܂݁A�ŉ��ʃ^�O�ɑΉ�����v�f�̃C���f�b�N�X���擾�ł���B
     * </p>
     */
    private Map<String, Integer> tagIndex = new HashMap<String, Integer>();

    /**
     * ��͒��̗v�f����ێ�����X�^�b�N�B
     * <p>
     * �t�B�[���h��񐶐��̍ۂɎg�p�����B
     * </p>
     */
    private Stack<String> tagStack = new Stack<String>();

    /**
     * �`���`�F�b�N�̃G���[���b�Z�[�W���i�[����C���X�^���X�B
     */
    private ErrorMessages errorMessages = null;

    /**
     * ���\�[�X�o���h�����s���t�@�C���̐ړ���
     */
    protected static final String XERCES_RESOURCE_BUNDLE_PREFIX = "org/apache/xerces/impl/msg/XMLSchemaMessages";

    /**
     * null�����e����ꍇ�ɒ�`����Ǝ��̃f�[�^�^�̖����ɕt���镶����
     */
    protected static final String ALLOW_EMPTY_SUFFIX = "AllowEmpty";

    /**
     * ���b�Z�[�W�o���h�����s�Ȃ��Ƃ��Ɏg�p����G���[�R�[�h�̃v���t�B�b�N�X
     */
    protected static final String ERROR_CODE_PREFIX = "typeMismatch";

    /**
     * �`���`�F�b�N�ő����ɕs���ȃf�[�^�^�̒l�����͂��ꂽ�ꍇ�ɁA�o�͂����G���[�R�[�h�B
     */
    protected static final String ATTRIBUTE_ERROR_CODE = "cvc-attribute.3";

    /**
     * �`���`�F�b�N�ŗv�f�ɕs���ȃf�[�^�^�̒l�����͂��ꂽ�ꍇ�ɁA�o�͂����G���[�R�[�h�B
     */
    protected static final String ELEMENT_ERROR_CODE = "cvc-type.3.1.3";

    /**
     * �`���`�F�b�N�ŕs���ȃf�[�^�^�̒l�����͂��ꂽ�ꍇ�ɁA�o�͂����G���[�R�[�h
     */
    protected static final String DATATYPE_ERROR_CODE = "cvc-datatype-valid.1.2.1";

    /**
     * �`���`�F�b�N��union��`�̃G���[�����������ꍇ�ɁA�o�͂����G���[�R�[�h
     * <p>
     * ���l�^��null�����e����Ǝ��̃f�[�^�^�̐錾�ɂ�union��`��p����̂ŁA �s���Ȓl�����͂��ꂽ�ꍇ�A���̃G���[����������
     * </p>
     */
    protected static final String UNION_ERROR_CODE = "cvc-datatype-valid.1.2.3";

    /**
     * �`���`�F�b�N�Ő��l�^�̍ő�l���傫���l�����͂��ꂽ�ꍇ�ɏo�͂����G���[�R�[�h
     */
    protected static final String MAXINCLUSIVE_ERROR_CODE = "cvc-maxInclusive-valid";

    /**
     * �`���`�F�b�N�Ő��l�^�̍ŏ��l��菬�����l�����͂��ꂽ�ꍇ�ɏo�͂����G���[�R�[�h
     */
    protected static final String MININCLUSIVE_ERROR_CODE = "cvc-minInclusive-valid";

    /**
     * �`���`�F�b�N�ŁAXML�f�[�^�̌`���ɖ�肪����ꍇ�ɏo�͂����G���[�R�[�h�̃v���t�B�b�N�X
     */
    protected static final String XML_DATA_ERROR_CODE_PREFIX = "cvc-";

    /**
     * ���l�^�̃t�B�[���h�ɕs���Ȓl�����͂��ꂽ�ꍇ�̃G���[�R�[�h
     */
    protected static final String NUMBER_ERROR_CODE = "number";

    /**
     * boolean�^�̃t�B�[���h�ɕs���Ȓl�����͂��ꂽ�ꍇ�̃G���[�R�[�h
     */
    protected static final String BOOLEAN_ERROR_CODE = "boolean";

    /**
     * date�^�̃t�B�[���h�ɕs���Ȓl�����͂��ꂽ�ꍇ�̃G���[�R�[�h
     */
    protected static final String DATE_ERROR_CODE = "date";

    /**
     * ���l�^�̃t�B�[���h�ɁA��`���ꂽ���l�^�̍ő�l���傫���l�����͂��ꂽ�ꍇ�̃G���[�R�[�h
     */
    protected static final String NUMBERMINRANGE_ERROR_CODE = "numberMinRange";

    /**
     * ���l�^�̃t�B�[���h�ɁA��`���ꂽ���l�^�̍ŏ��l��菬�����l�����͂��ꂽ�ꍇ�̃G���[�R�[�h
     */
    protected static final String NUMBERMAXRANGE_ERROR_CODE = "numberMaxRange";

    /**
     * �t�B�[���h�̃Z�p���[�^
     */
    protected static final String FIELD_SEPARATOR = ".";

    /**
     * �G���[�R�[�h�̃Z�p���[�^
     */
    protected static final String ERROR_CODE_SEPARATOR = ".";

    /**
     * boolean�^�Ƃ��ăn���h�����O����XML�X�L�[�}�̃f�[�^�^
     */
    protected static final String DATATYPE_BOOLEAN = "boolean";

    /**
     * ���t�^�Ƃ��ăn���h�����O����XML�X�L�[�}�̃f�[�^�^
     */
    protected static final List DATATYPE_DATE = Arrays.asList(new String[] {
            "date", "time", "dateTime" });

    /**
     * �f�[�^�^�̃G���[�Ő��������G���[���b�Z�[�W�̃t�B�[���h���ɁA��������ǉ����邽�߁A�C���X�^���X�̎Q�Ƃ�ۑ�����B
     */
    private ErrorMessage tmpErrorMessage = null;

    /**
     * �R���X�g���N�^
     * 
     * @param errorMessages
     *            �G���[���b�Z�[�W�̃��X�g
     */
    public XMLErrorReporterEx(ErrorMessages errorMessages) {
        super();
        this.errorMessages = errorMessages;
    }

    /**
     * �G���[���b�Z�[�W�̃��X�g��ԋp����
     * 
     * @return �G���[���b�Z�[�W�̃��X�g
     */
    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }

    /**
     * �`���`�F�b�N�Ŕ��������G���[���𗘗p���āA�G���[���b�Z�[�W�𐶐�����B
     * 
     * @param location
     *            XML�̈ʒu�����擾����I�u�W�F�N�g
     * @param domain
     *            �G���[�h���C��
     * @param key
     *            �`���`�F�b�N�̃G���[�Ŕ��������G���[�R�[�h
     * @param arguments
     *            �`���`�F�b�N�̃G���[�Ŕ��������u��������
     * @param severity
     *            �G���[���x��
     * @throws XNIException
     *             �p�[�T���Ŕ���������s����O
     */
    @Override
    public void reportError(XMLLocator location, String domain, String key,
            Object[] arguments, short severity) throws XNIException {
        super.reportError(location, domain, key, arguments, severity);

        String[] options = null;

        if (arguments == null) {
            options = new String[] {};
        } else {
            // �`���`�F�b�N�̃G���[�Ŕ��������u����������擾����
            options = new String[arguments.length];

            // �G���[���b�Z�[�W�̒u��������𐶐�����
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i] == null) {
                    options[i] = null;
                } else {
                    options[i] = arguments[i].toString();
                }
            }
        }

        // �`���`�F�b�N�Ŕ��������G���[�̏������O�ɏo�͂���
        errorLog(key, options);

        // �G���[���b�Z�[�W�𐶐�����
        addErrorMessage(key, options);

    }

    /**
     * ���͂��ꂽ�v�f���ɃC���f�b�N�X��t�����ĕԋp����B
     * <p>
     * �v�f���ɂ́A�z��̗L���Ɋւ�炸[ ]��t������B
     * </p>
     * 
     * @param element
     *            �v�f��
     * @return �C���f�b�N�X�̒l���t�����ꂽ�v�f��
     */
    protected String indexResolve(String element) {
        // �C���f�b�N�X��0����n�܂�
        Integer index = 0;
        // �C���f�b�N�X�̃L�[�𐶐�����
        StringBuilder tagIndexKey = new StringBuilder(getField());
        tagIndexKey.append(element);
        // �C���f�b�N�X�̃L�[�ɑΉ�����l���擾����
        Integer val = this.tagIndex.get(tagIndexKey.toString());
        // �l���o�^����Ă���ꍇ�́A�C���f�b�N�X�̒l���C���N�������g����
        if (val != null) {
            index = val + 1;
        }
        // �C���f�b�N�X�̃L�[�ƒl��o�^����
        this.tagIndex.put(tagIndexKey.toString(), index);

        StringBuilder retStr = new StringBuilder();
        retStr.append(element);
        retStr.append("[");
        retStr.append(index);
        retStr.append("]");
        return retStr.toString();
    }

    /**
     * �G���[�����������t�B�[���h����ԋp����
     * 
     * @return �G���[�����������t�B�[���h���
     */
    private String getField() {
        StringBuilder key = new StringBuilder();
        for (String tagstr : tagStack) {
            // �X�^�b�N�Ɋi�[����Ă���l���h�b�g�ŘA������
            key.append(tagstr);
            key.append(FIELD_SEPARATOR);
        }
        return key.toString();
    }

    /**
     * �`���`�F�b�N�Ŕ��������G���[�̏���ϊ����A�Ǝ��̃G���[���b�Z�[�W�C���X�^���X�𐶐�����B
     * <p>
     * Xerces�Ŕ��������f�[�^�^�̃G���[�Ɋւ��Ă̂݁A�Ǝ��̃G���[���b�Z�[�W�𐶐�����B<br>
     * ����ȊO�̃G���[�����������ꍇ�́A��O���X���[����B
     * <p>
     * <p>
     * Xerces�Ńf�[�^�^�̃G���[�����������ꍇ�A�v�f�E�����Ɋւ���G���[����������B<br>
     * �Ⴆ�΁A����v�f�ōő�l�G���[�����������ꍇ�A�f�[�^�^�̃G���[�Ɨv�f�G���[����������B<br>
     * ���鑮���Ńf�[�^�^�̃G���[�����������ꍇ�A�f�[�^�^�̃G���[�Ƒ����̃G���[����������B
     * </p>
     * <p>
     * �v�f�E�����̃G���[�́A�f�[�^�^�̃G���[�Ƃقړ������e�̃G���[�������̂ŁA�G���[���b�Z�[�W�͐������Ȃ��B
     * </p>
     * �f�[�^�^�̃G���[�̈ꗗ���ȉ��ɋL���B<br>
     * <table border="1" CELLPADDING="8">
     * <tr>
     * <th>Xerces�̃G���[�R�[�h</th>
     * <th>�Ǝ��̃G���[�R�[�h</th>
     * <th>�Ǝ��̒u��������</th>
     * <th>���������</th>
     * </tr>
     * <tr>
     * <td>cvc-datatype-valid.1.2.1</td>
     * <td>typeMismatch.number<br>
     * typeMismatch.boolean<br>
     * typeMismatch.date</td>
     * <td>{�s���Ȓl, �f�[�^�^}</td>
     * <td>�s���Ȓl�����͂��ꂽ�ꍇ</td>
     * </tr>
     * <tr>
     * <td>cvc-maxInclusive-valid</td>
     * <td>typeMismatch.numberMaxRange</td>
     * <td>{�s���Ȓl, �f�[�^�^�̍ő�l, �f�[�^�^}</td>
     * <td>���l�^�̍ő�l���傫���l�����͂��ꂽ�ꍇ</td>
     * </tr>
     * <tr>
     * <td>cvc-minInclusive-valid</td>
     * <td>typeMismatch.numberMinRange</td>
     * <td>{�s���Ȓl, �f�[�^�^�̍ŏ��l, �f�[�^�^}</td>
     * <td>���l�^�̍ŏ��l��菬�����l�����͂��ꂽ�ꍇ</td>
     * </tr>
     * <tr>
     * <td>cvc-datatype-valid.1.2.3</td>
     * <td>typeMismatch.numberMinRange</td>
     * <td>{�s���Ȓl, �f�[�^�^}</td>
     * <td>null�l�����e�����t�B�[���h�ɁA�s���Ȓl�����͂��ꂽ�ꍇ</td>
     * </tr>
     * </table>
     * </p>
     * <p>
     * �v�f�E�����̃G���[�̈ꗗ�����L�Ɏ����B<br>
     * <table border="1" CELLPADDING="8">
     * <tr>
     * <th>Xerces�Ŕ��������G���[�R�[�h</th>
     * <th>���������</th>
     * </tr>
     * <tr>
     * <td>cvc-type.3.1.3</td>
     * <td>�v�f�ɕs���Ȓl�����͂��ꂽ�ꍇ</td>
     * </tr>
     * <tr>
     * <td>cvc-attribute.3</td>
     * <td>�����ɕs���Ȓl�����͂��ꂽ�ꍇ</td>
     * </tr>
     * </table>
     * </p>
     * <p>
     * �����̃G���[�����������ꍇ�A�u��������Ƃ��Ċi�[����Ă��鑮�������A�G���[���b�Z�[�W�̃t�B�[���h���ɕt������B<br>
     * </p>
     * 
     * @param key
     *            �G���[�R�[�h
     * @param options
     *            �u��������
     */
    protected void addErrorMessage(String key, String[] options) {

        String messageID = null;

        // Xerces�Ŕ��������G���[���b�Z�[�W�̃n���h�����O���s��
        if (ELEMENT_ERROR_CODE.equals(key)) {
            // �v�f�̃G���[�̓n���h�����O���Ȃ�
            return;
        } else if (ATTRIBUTE_ERROR_CODE.equals(key)) {
            if (tmpErrorMessage != null) {
                // �t�B�[���h���ɑ�������t������
                StringBuilder fieldStr = new StringBuilder();
                fieldStr.append(tmpErrorMessage.getField());
                fieldStr.append(FIELD_SEPARATOR);
                fieldStr.append(options[1]);
                tmpErrorMessage.setField(fieldStr.toString());
            }
            // �����̃G���[�̓n���h�����O���Ȃ�
            return;
        } else if (DATATYPE_ERROR_CODE.equals(key)) {
            // �t�B�[���h�ɒ�`����Ă���f�[�^�^�łȂ��l�����͂��ꂽ�ꍇ�ɁA���̃G���[����������
            if (DATATYPE_BOOLEAN.equals(options[1])) {
                messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                        + BOOLEAN_ERROR_CODE;
            } else if (DATATYPE_DATE.contains(options[1])) {
                messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                        + DATE_ERROR_CODE;
            } else {
                messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                        + NUMBER_ERROR_CODE;
            }
        } else if (UNION_ERROR_CODE.equals(key)
                && options[1].endsWith(ALLOW_EMPTY_SUFFIX)) {
            // ���l�^��null�l�����e����t�B�[���h�ɁA�s���Ȓl�����͂��ꂽ�ꍇ�ɁA���̃G���[����������
            // �f�[�^�^�̕����񂩂�"AllowEmpty"���폜���āA�u��������Ɋi�[����
            options[1] = (options[1]).substring(0, (options[1])
                    .indexOf(ALLOW_EMPTY_SUFFIX));
            messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                    + NUMBER_ERROR_CODE;
            // �s�v�Ȓu����������폜����
            String[] tmpOptions = new String[2];
            System.arraycopy(options, 0, tmpOptions, 0, 2);
            options = tmpOptions;
        } else if (MAXINCLUSIVE_ERROR_CODE.equals(key)) {
            // �^�̍ő�l���傫���l�����͂��ꂽ�ꍇ�ɁA���̃G���[����������
            messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                    + NUMBERMAXRANGE_ERROR_CODE;
        } else if (MININCLUSIVE_ERROR_CODE.equals(key)) {
            // �^�̍ŏ��l��菬�����l�����͂��ꂽ�ꍇ�ɁA���̃G���[����������
            messageID = ERROR_CODE_PREFIX + ERROR_CODE_SEPARATOR
                    + NUMBERMINRANGE_ERROR_CODE;
        } else if (key.startsWith(XML_DATA_ERROR_CODE_PREFIX)) {
            // XML�f�[�^�̍\���ɃG���[������ƍl������ꍇ�A��O���X���[����
            log.error("xml data is invalid.");
            throw new UnknownXMLDataException();
        } else {
            // �X�L�[�}���̂ɖ�肪����ƍl������ꍇ�A��O���X���[����
            log.error("schema is invalid.");
            throw new IllegalSchemaDefinitionException();
        }

        ErrorMessage errorMessage = new ErrorMessage(messageID, "", options);

        // �G���[���b�Z�[�W�Ƀt�B�[���h����ݒ肷��
        errorMessage.setField(getField().substring(0, getField().length() - 1));
        // �G���[���b�Z�[�W�̃��X�g�ɒǉ�����
        errorMessages.add(errorMessage);
        // �G���[���b�Z�[�W�̎Q�Ƃ�ۑ�����
        tmpErrorMessage = errorMessage;

    }

    /**
     * �`���`�F�b�N�Ŕ��������G���[�̏������O�ɏo�͂���
     * 
     * @param key
     *            �G���[�R�[�h
     * @param options
     *            �u��������
     */
    protected void errorLog(String key, Object[] options) {

        StringBuilder buf = new StringBuilder();

        // ���s�R�[�h�̎擾
        String lineSeparator = System.getProperty("line.separator");

        buf.append("Schema error[]------------------------");
        buf.append(lineSeparator);
        // �t�B�[���h���
        if (getField().length() > 0) {
            buf.append("xpath="
                    + getField().substring(0, getField().length() - 1));
        } else {
            buf.append("xpath=" + getField());
        }
        buf.append(lineSeparator);
        buf.append("getMessage=");
        // Xerces�̃G���[���b�Z�[�W
        buf.append(getMessage(key, options));
        buf.append(lineSeparator);
        buf.append("key=");
        buf.append(key);
        // Xerces�̃G���[�R�[�h
        buf.append(lineSeparator);
        StringBuilder argNo = new StringBuilder();
        for (int i = 0; i < options.length; i++) {
            argNo.setLength(0);
            argNo.append("arg[");
            argNo.append(i);
            argNo.append("]=");
            buf.append(argNo.toString());
            // Xerces�̒u��������
            buf.append(options[i]);
            buf.append(lineSeparator);
        }
        buf.append("-----------------------------------------");
        buf.append(lineSeparator);

        log.error(buf.toString());
    }

    /**
     * Xerces�̃��\�[�X�o���h����p���Đ����������b�Z�[�W��ԋp����B
     * 
     * @param key
     *            �G���[�R�[�h
     * @param options
     *            �u��������
     * @return �G���[���b�Z�[�W
     */
    private String getMessage(String key, Object[] options) {

        String message = null;
        try {
            // ���\�[�X�o���h�����擾����
            ResourceBundle bundle = ResourceBundle
                    .getBundle(XERCES_RESOURCE_BUNDLE_PREFIX);
            // �G���[�R�[�h�ɑΉ����郁�b�Z�[�W���擾����
            message = bundle.getString(key);
        } catch (MissingResourceException e) {
            return "[[" + e.getMessage() + "]]";
        }
        // �v���[�X�t�H���_���u�����ꂽ���b�Z�[�W�𐶐�����
        return MessageFormat.format(message, options);

    }

    /**
     * ��͒��̃t�B�[���h����ێ�����X�^�b�N��ԋp����
     * 
     * @return ��͒��̃t�B�[���h����ێ�����X�^�b�N
     */
    public Stack<String> getTagStack() {
        return tagStack;
    }

}
