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

package jp.terasoluna.fw.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import jp.terasoluna.fw.beans.IndexedBeanWrapper;
import jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl;
import jp.terasoluna.fw.util.BeanUtil;
import jp.terasoluna.fw.util.ClassLoadException;
import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.util.PropertyAccessException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.util.ValidatorUtils;

/**
 * TERASOLUNA�̓��̓`�F�b�N�@�\�ŋ��ʂɎg�p����錟�؃��[���N���X�B
 *
 * ���̃N���X���񋟂��錟�؃��[���Ƃ��ẮA�ȉ��̂��̂�����B</p>
 *
 * <table border="1">
 * <tr>
 *  <td><center><b>���ؖ�</b></center></td>
 *  <td><center><b>���\�b�h��</b></center></td>
 *  <td><center><b>�o���f�[�V������`�t�@�C���ivalidation.xml�j��
 *  �L�q���郋�[����</b></center></td>
 * </tr>
 * <tr>
 *  <td>�K�{�`�F�b�N</td>
 *  <td><code>{@link #validateRequired(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>requierd</code></td>
 * </tr>
 * <tr>
 *  <td>���K�\���`�F�b�N</td>
 *  <td><code>{@link #validateMask(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>mask</code></td>
 * </tr>
 * <tr>
 *  <td><code>byte</code>�^�`�F�b�N</td>
 *  <td><code>{@link #validateByte(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>byte</code></td>
 * </tr>
 * <tr>
 *  <td><code>short</code>�^�`�F�b�N</td>
 *  <td><code>{@link #validateShort(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>short</code></td>
 * </tr>
 * <tr>
 *  <td><code>integer</code>�^�`�F�b�N</td>
 *  <td><code>{@link #validateInteger(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>integer</code></td>
 * </tr>
 * <tr>
 *  <td><code>long</code>�^�`�F�b�N</td>
 *  <td><code>{@link #validateLong(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>long</code></td>
 * </tr>
 * <tr>
 *  <td><code>float</code>�^�`�F�b�N</td>
 *  <td><code>{@link #validateFloat(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>float</code></td>
 * </tr>
 * <tr>
 *  <td><code>double</code>�^�`�F�b�N</td>
 *  <td><code>{@link #validateDouble(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>double</code></td>
 * </tr>
 * <tr>
 *  <td>���t�^�`�F�b�N</td>
 *  <td><code>{@link #validateDate(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>date</code></td>
 * </tr>
 * <tr>
 *  <td>�����w��͈̓`�F�b�N</td>
 *  <td><code>{@link #validateIntRange(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>intRange</code></td>
 * </tr>
 * <tr>
 *  <td>�����w��͈̓`�F�b�N</td>
 *  <td><code>{@link #validateDoubleRange(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>doubleRange</code></td>
 * </tr>
 * <tr>
 *  <td>���������_���w��͈̓`�F�b�N</td>
 *  <td><code>{@link #validateFloatRange(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>floatRange</code></td>
 * </tr>
 * <tr>
 *  <td>�ő啶�����`�F�b�N</td>
 *  <td><code>{@link #validateMaxLength(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>maxLength</code></td>
 * </tr>
 * <tr>
 *  <td>�ŏ��������`�F�b�N</td>
 *  <td><code>{@link #validateMinLength(
 *  Object, ValidatorAction, Field, ValidationErrors)}</code></td>
 *  <td><code>minLength</code></td>
 * </tr>
 * <tr>
 *  <td>�p�����`�F�b�N</td>
 *  <td><code>{@link #validateAlphaNumericString(Object, ValidatorAction,
 *  Field, ValidationErrors)}</code></td>
 *  <td><code>alphaNumericString</code></td>
 * </tr>
 * <tr>
 *  <td>�啶���p�����`�F�b�N</td>
 *  <td><code>{@link #validateCapAlphaNumericString(Object, ValidatorAction,
 *  Field, ValidationErrors)}</code></td>
 *  <td><code>capAlphaNumericString</code></td>
 * </tr>
 * <tr>
 *  <td>���l�`�F�b�N</td>
 *  <td><code>{@link #validateNumber(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>number</code></td>
 * </tr>
 * <tr>
 *  <td>���p�J�i�����`�F�b�N</td>
 *  <td><code>{@link #validateHankakuKanaString(Object, ValidatorAction,
 *  Field, ValidationErrors)}</code></td>
 *  <td><code>hankakuKanaString</code></td>
 * </tr>
 * <tr>
 *  <td>���p�����`�F�b�N</td>
 *  <td><code>{@link #validateHankakuString(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>hankakuString</code></td>
 * </tr>
 * <tr>
 *  <td>�S�p�J�i�����`�F�b�N</td>
 *  <td><code>{@link #validateZenkakuKanaString(Object, ValidatorAction,
 *  Field, ValidationErrors)}</code></td>
 *  <td><code>zenkakuKanaString</code></td>
 * </tr>
 * <tr>
 *  <td>�S�p�����`�F�b�N</td>
 *  <td><code>{@link #validateZenkakuString(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>zenkakuString</code></td>
 * </tr>
 * <tr>
 *  <td>���͋֎~�����`�F�b�N</td>
 *  <td><code>{@link #validateProhibited(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>prohibited</code></td>
 * </tr>
 * <tr>
 *  <td>�����񒷈�v�`�F�b�N</td>
 *  <td><code>{@link #validateStringLength(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>stringLength</code></td>
 * </tr>
 * <tr>
 *  <td>����������`�F�b�N</td>
 *  <td><code>{@link #validateNumericString(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>numericString</code></td>
 * </tr>
 * <tr>
 *  <td>URL�`�F�b�N</td>
 *  <td><code>{@link #validateUrl(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>url</code></td>
 * </tr>
 * <tr>
 *  <td>�z�񒷈�v�`�F�b�N</td>
 *  <td><code>{@link #validateArrayRange(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>arrayRange</code></td>
 * </tr>
 * <tr>
 *  <td>�o�C�g���w��͈͓��`�F�b�N</td>
 *  <td><code>{@link #validateByteRange(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>byteRange</code></td>
 * </tr>
 * <tr>
 *  <td>���t������w��͈͓��`�F�b�N</td>
 *  <td><code>{@link #validateDateRange(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td><code>dateRange</code></td>
 * </tr>
 * <tr>
 *  <td>�z��E�R���N�V�����^�t�B�[���h�S�v�f�`�F�b�N</td>
 *  <td><code>{@link #validateArraysIndex(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td>���̒P�̃t�B�[���h���؃��[�����{"Array"(�z�񒷈�v�`�F�b�N�͏���)</td>
 * </tr>
 * <tr>
 *  <td>���փ`�F�b�N</td>
 *  <td><code>{@link #validateMultiField(Object, ValidatorAction, Field,
 *  ValidationErrors)}</code></td>
 *  <td>multiField</td>
 * </tr>
 * </table>
 *
 * <p>���̃N���X�ł�Struts��ValidWhen�𗘗p�������̓`�F�b�N���\�b�h��
 * �T�|�[�g���Ă��Ȃ��B���փ`�F�b�N���s�Ȃ��ꍇ�A�e�t���[�����[�N��
 * �ʓr�A���փ`�F�b�N���s���d�g�݂�p�ӂ��邱�ƁB</p>
 *
 * <p>���̌��؃��[���N���X�𗘗p���邽�߂ɂ́A�A�N�V�������ƂɌ��ؓ��e���L�q����
 * �o���f�[�V������`�t�@�C��(validation.xml) ���쐬����K�v������B</p>
 *
 * <h5>validation.xml�̋L�q��i�P�̃t�B�[���h���؁j</h5>
 * <code><pre>
 *  &lt;formset&gt;
 *    �E�E�E
 *    &lt;!-- �P�̂̃t�B�[���h���� --&gt;
 *    &lt;form name="testBean"&gt;
 *      &lt;field property="field"
 *          depends="required,alphaNumericString,maxlength"&gt;
 *        &lt;arg key="testBean.field" position="0"/&gt;
 *        &lt;arg key="${var:maxlength}" position="1"/&gt;
 *        &lt;var&gt;
 *          &lt;var-name&gt;maxlength&lt;/var-name&gt;
 *          &lt;var-value&gt;10&lt;/var-value&gt;
 *        &lt;/var&gt;
 *      &lt;/field&gt;
 *    &lt;/form&gt;
 *    �E�E�E
 *  &lt;/formset&gt;
 * </pre></code>
 *
 * <h5>�z��E�R���N�V�����^�t�B�[���h�̓��̓`�F�b�N</h5>
 *
 * <p>���̃N���X��
 * {@link #validateArraysIndex(
 * Object, ValidatorAction, Field, ValidationErrors)}
 * ���\�b�h���g�p���邱�Ƃɂ��A�z��E�R���N�V�����^�̓��̓`�F�b�N��
 * �\�ɂȂ�B</p>
 *
 * <p>�Ⴆ�΁Afields�Ƃ����z��̃v���p�e�B������bean�C���X�^���X�ɑ΂��āA
 * fields�v�f�ɑ΂���K�{�`�F�b�N���s�Ȃ��ꍇ�Avalidation.xml�̃v���p�e�B����
 * fields�ƋL�q����B
 * ���s���ɂ́A�V�X�e������fields�v���p�e�B�̔z����O�Ԗڂ��珇�ɑ������āA
 * �S�v�f�ɑ΂��ă`�F�b�N���\�b�h�����s����B
 * ���s���郋�[���ivalidation.xml��depends�w��j�́A���[�����ɁhArray�h��
 * ���������O���w�肷��B</p>
 *
 * �z��E�R���N�V�����^�̈ꗗ���؂ɑΉ����郋�[���͈ȉ��̒ʂ�ł���B
 * <ul>
 *   <li><code>requiredArray</code></li>
 *   <li><code>minLengthArray</code></li>
 *   <li><code>maxLengthArray</code></li>
 *   <li><code>maskArray</code></li>
 *   <li><code>byteArray</code></li>
 *   <li><code>shortArray</code></li>
 *   <li><code>integerArray</code></li>
 *   <li><code>longArray</code></li>
 *   <li><code>floatArray</code></li>
 *   <li><code>doubleArray</code></li>
 *   <li><code>dateArray</code></li>
 *   <li><code>intRangeArray</code></li>
 *   <li><code>doubleRangeArray</code></li>
 *   <li><code>floatRangeArray</code></li>
 *   <li><code>creditCardArray</code></li>
 *   <li><code>emailArray</code></li>
 *   <li><code>urlArray</code></li>
 *   <li><code>alphaNumericStringArray</code></li>
 *   <li><code>hankakuKanaStringArray</code></li>
 *   <li><code>hankakuStringArray</code></li>
 *   <li><code>zenkakuStringArray</code></li>
 *   <li><code>zenkakuKanaStringArray</code></li>
 *   <li><code>capAlphaNumericStringArray</code></li>
 *   <li><code>numberArray</code></li>
 *   <li><code>numericStringArray</code></li>
 *   <li><code>prohibitedArray</code></li>
 *   <li><code>stringLengthArray</code></li>
 *   <li><code>dateRangeArray</code></li>
 *   <li><code>byteRangeArray</code></li>
 * </ul>
 *
 * <p>���[����ǉ������ꍇ�A�z��E�R���N�V�����p�̃��\�b�h��ʓr�쐬����
 * �K�v������B</p>
 *
 * <p>���̓`�F�b�N�ŃG���[�����o���ꂽ�ꍇ�A�ȉ��̏���Ԃ��B
 * <ul>
 *   <li>�G���[�R�[�h</li>
 *   <li>�G���[�����������v���p�e�B��</li>
 *   <li>�u��������</li>
 * </ul>
 * </p>
 *
 * @see jp.terasoluna.fw.beans.JXPathIndexedBeanWrapperImpl
 * @see jp.terasoluna.fw.beans.IndexedBeanWrapper
 */
public class FieldChecks {

    /**
     * �{�N���X�ŗ��p���郍�O�B
     */
    private static Log log = LogFactory.getLog(FieldChecks.class);

    /**
     * ���͒l��Null���؂ƁA�X�y�[�X�����������͒l�̕����񒷂�0���傫����
     * ���؂���B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     */
    public boolean validateRequired(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);

        // ����
        if (GenericValidator.isBlankOrNull(value)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l���w�肳�ꂽ���K�\���ɓK�����邩���؂���B
     *
     * <p>�ȉ��́A�����񂪔��p�p�����ł���Ƃ��̂�true��ԋp����
     * ���؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;maskField&quot;
     *      depends=&quot;mask&quot;&gt;
     *    &lt;arg key=&quot;sample.escape&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;mask&lt;/var-name&gt;
     *      &lt;var-value&gt;^([0-9]|[a-z]|[A-Z])*$&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     *
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> mask </td>
     *   <td>���K�\��</td>
     *   <td>true</td>
     *   <td>���͕����񂪎w�肳�ꂽ���K�\���ɍ��v����Ƃ��� <code>true</code>
     *       ���ԋp�����B�w�肳��Ȃ��ꍇ��ValidatorException
     *       ���X���[�����B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     * @exception ValidatorException �ݒ�t�@�C������mask(���K�\��)�̒l��
     * �擾�ł��Ȃ��ꍇ�ɃX���[�����B
     */
    public boolean validateMask(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors)
            throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ���K�\��
        String mask = field.getVarValue("mask");

        // var����mask���擾�ł��Ȃ��ꍇ��ValidatorException
        if (StringUtils.isEmpty(mask)) {
            log.error("var[mask] must be specified.");
            throw new ValidatorException("var[mask] must be specified.");
        }

        // ����
        if (!GenericValidator.matchRegexp(value, mask)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l��byte�^�ɕϊ��\�����؂���B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     */
    public boolean validateByte(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (GenericTypeValidator.formatByte(value) == null) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l��short�^�ɕϊ��\�����؂���B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     */
    public boolean validateShort(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (GenericTypeValidator.formatShort(value) == null) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l��int�^�ɕϊ��\�����؂���B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     */
    public boolean validateInteger(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) {
        // ����
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (GenericTypeValidator.formatInt(value) == null) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l��long�^�ɕϊ��\�����؂���B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     */
    public boolean validateLong(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (GenericTypeValidator.formatLong(value) == null) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l��float�^�ɕϊ��\�����؂���B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     */
    public boolean validateFloat(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (GenericTypeValidator.formatFloat(value) == null) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l��double�^�ɕϊ��\�����؂���B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     */
    public boolean validateDouble(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (GenericTypeValidator.formatDouble(value) == null) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l���L���ȓ��t�����؂���B
     *
     * <p>field�ɁudatePattern�v�ϐ�����`����Ă���ꍇ�A
     * <code>java.text.SimpleDateFormat</code>�N���X��
     * ���p�����t�H�[�}�b�g�̌��؂��s����B</p>
     *
     * <p>field�ɁudatePatternStrict�v�ϐ�����`����Ă���ꍇ�A
     * <code>java.text.SimpleDateFormat</code>�N���X�𗘗p�����t�H�[�}�b�g��
     * �����̌��؂��s����B
     * �Ⴆ�΁A'2/12/1999'��'MM/dd/yyyy'�`���̃t�H�[�}�b�g�ɂ����
     * ���iMonth)���Q���łȂ����߁A���؃G���[�ƂȂ�B</p>
     *
     * <p>�udatePattern�v�ϐ��ƁA�udatePatternStrict�v�ϐ��̗������w�肳�ꂽ
     * �ꍇ�A�udatePattern�v�ϐ����D�悵�Ďg�p�����B
     *  �udatePattern�v�ϐ��ƁA�udatePatternStrict�v�ϐ��̗������w�肳��Ă��Ȃ�
     *  �ꍇ�A��O����������B�܂��A���t�p�^�[���ɖ����ȕ����񂪊܂܂�Ă���
     *  �ꍇ����O����������</p>
     *
     * <p>�t�H�[�}�b�g���ɂ͕K���usetLenient=false�v���ݒ肳��邽�߁A
     * 2000/02/31�̂悤�ȑ��݂��Ȃ����t�́A������̏ꍇ�����e����Ȃ��B</p>
     *
     * �ȉ��́A������yyyy/MM/dd�^�̓��t�p�^�[���ɍ��v���邱�Ƃ�����
     * ����ꍇ�̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;dateField&quot;
     *      depends=&quot;date&quot;&gt;
     *    &lt;arg key=&quot;sample.dateField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;datePattern&lt;/var-name&gt;
     *      &lt;var-value&gt;yyyy/MM/dd&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     *
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> datePattern </td>
     *   <td>���t�p�^�[��</td>
     *   <td>false</td>
     *   <td>���t�p�^�[�����w�肷��B���͒l�̌����`�F�b�N�͍s��Ȃ��B
     *   ���Ƃ��΁A���t�p�^�[����yyyy/MM/dd�̏ꍇ�A2001/1/1�̓G���[�ɂȂ�Ȃ��B
     *   datePattern��datePatternStrict�������w�肳��Ă���ꍇ�́A
     *   datePattern���D�悵�Ďg�p�����B
     *   </td>
     *  </tr>
     *  <tr>
     *   <td> datePatternStrict </td>
     *   <td>���t�p�^�[��</td>
     *   <td>false</td>
     *   <td>���t�p�^�[�����w�肷��B���͒l�̌������A
     *   �w�肳�ꂽ���t�p�^�[���̌����ɍ��v���邩�̃`�F�b�N���s���B
     *   ���Ƃ��΁A���t�p�^�[����yyyy/MM/dd�̏ꍇ�A2001/1/1�̓G���[�ɂȂ�B
     *   datePattern��datePatternStrict�������w�肳��Ă���ꍇ�́A
     *   datePattern���D�悵�Ďg�p�����B
     *   </td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateDate(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // �t�H�[�}�b�g�p�̓��t�p�^�[��
        String datePattern = field.getVarValue("datePattern");
        String datePatternStrict = field.getVarValue("datePatternStrict");

        // ����
        Date result = null;
        try {
            result =
                ValidationUtil.toDate(value, datePattern, datePatternStrict);
        } catch (IllegalArgumentException e) {
            // ���t�p�^�[�����s���ȏꍇ
            String message = "Mistake on validation definition file. "
                + "- datePattern or datePatternStrict is invalid. "
                + "You'll have to check it over. ";
            log.error(message, e);
            throw new ValidatorException(message);
        }
        if (result == null) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l���w�肳�ꂽint�^�ɕϊ��\�ł���A
     * ���w�肳�ꂽ�͈͓������؂���B
     *
     * <p>�ȉ��́A������10����100�܂ł͈͓̔��̐��l�ł���Ƃ��̂�
     * true��ԋp���錟�؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;intField&quot;
     *      depends=&quot;intRange&quot;&gt;
     *    &lt;arg key=&quot;sample.intField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;intRangeMin&lt;/var-name&gt;
     *      &lt;var-value&gt;10&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;intRangeMax&lt;/var-name&gt;
     *      &lt;var-value&gt;100&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     *
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> intRangeMin </td>
     *   <td>�ŏ��l</td>
     *   <td>false</td>
     *   <td>�͈͎w��̍ŏ��l��ݒ肷��B�ݒ肵�Ȃ��ꍇ�AInteger�̍ŏ��l��
     *   �w�肳���B
     *   ���l�ȊO�̕����񂪓��͂��ꂽ�ꍇ�A��O���X���[�����B</td>
     *  </tr>
     *  <tr>
     *   <td> intRangeMax </td>
     *   <td>�ő�l</td>
     *   <td>false</td>
     *   <td>�͈͎w��̍ő�l��ݒ肷��B�ݒ肵�Ȃ��ꍇ�AInteger�̍ő�l��
     *   �w�肳���B
     *   ���l�ȊO�̕����񂪓��͂��ꂽ�ꍇ�A��O���X���[�����B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateIntRange(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ���ؒl��int�ɕϊ� --- Integer�^�ł͂Ȃ��ꍇ�A���؃G���[�B
        int intValue = 0;
        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            rejectValue(errors, field, va, bean);
            return false;
        }

        // �͈͎w��l --- �ݒ�l��Integer�^�ł͂Ȃ��ꍇ�A��O�B
        //                �ݒ�Ȃ��̓f�t�H���g�l���g�p����B
        String strMin = field.getVarValue("intRangeMin");
        int min = Integer.MIN_VALUE;
        if (!GenericValidator.isBlankOrNull(strMin)) {
            try {
                min = Integer.parseInt(strMin);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- intRangeMin is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }
        String strMax = field.getVarValue("intRangeMax");
        int max = Integer.MAX_VALUE;
        if (!GenericValidator.isBlankOrNull(strMax)) {
            try {
                max = Integer.parseInt(strMax);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- intRangeMax is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }

        // ����
        if (!GenericValidator.isInRange(intValue, min, max)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l���w�肳�ꂽdouble�^�ɕϊ��\�ł���A
     * ���w�肳�ꂽ�͈͓������؂���B
     *
     * <p>�ȉ��́A������10����100�܂ł͈͓̔��̐��l�ł���Ƃ��̂�
     * true��ԋp���錟�؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;doubleField&quot;
     *      depends=&quot;doubleRange&quot;&gt;
     *    &lt;arg key=&quot;sample.doubleField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;doubleRangeMin&lt;/var-name&gt;
     *      &lt;var-value&gt;10.0&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;doubleRangeMax&lt;/var-name&gt;
     *      &lt;var-value&gt;100.0&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     *
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> doubleRangeMin </td>
     *   <td>�ŏ��l</td>
     *   <td>false</td>
     *   <td>�͈͎w��̍ŏ��l��ݒ肷��B�ݒ肵�Ȃ��ꍇ�ADouble�̍ŏ��l��
     *   �w�肳���B
     *   ���l�ȊO�̕����񂪓��͂��ꂽ�ꍇ�A��O���X���[�����B</td>
     *  </tr>
     *  <tr>
     *   <td> doubleRangeMax </td>
     *   <td>�ő�l</td>
     *   <td>false</td>
     *   <td>�͈͎w��̍ő�l��ݒ肷��B�ݒ肵�Ȃ��ꍇ�ADouble�̍ő�l��
     *   �w�肳���B
     *   ���l�ȊO�̕����񂪓��͂��ꂽ�ꍇ�A��O���X���[�����B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateDoubleRange(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ���ؒl��double�ɕϊ� --- Double�^�ł͂Ȃ��ꍇ�A���؃G���[�B
        double dblValue = 0;
        try {
            dblValue = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            rejectValue(errors, field, va, bean);
            return false;
        }

        // �͈͎w��l --- �ݒ�l��Double�^�ł͂Ȃ��ꍇ�A��O�B
        //                �ݒ�Ȃ��̓f�t�H���g�l���g�p����B
        String strMin = field.getVarValue("doubleRangeMin");
        double min = Double.MIN_VALUE;
        if (!GenericValidator.isBlankOrNull(strMin)) {
            try {
                min = Double.parseDouble(strMin);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- doubleRangeMin is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }
        String strMax = field.getVarValue("doubleRangeMax");
        double max = Double.MAX_VALUE;
        if (!GenericValidator.isBlankOrNull(strMax)) {
            try {
                max = Double.parseDouble(strMax);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- doubleRangeMax is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }

        // ����
        if (!GenericValidator.isInRange(dblValue, min, max)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l���w�肳�ꂽfloat�^�ɕϊ��\�ł���A
     * ���w�肳�ꂽ�͈͓������؂���B
     *
     * <p>�ȉ��́A������10����100�܂ł͈͓̔��̐��l�ł���Ƃ��̂�
     * true��ԋp���錟�؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;floatField&quot;
     *      depends=&quot;floatRange&quot;&gt;
     *    &lt;arg key=&quot;sample.floatField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;floatRangeMin&lt;/var-name&gt;
     *      &lt;var-value&gt;10&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;floatRangeMax&lt;/var-name&gt;
     *      &lt;var-value&gt;100&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     *
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> floatRangeMin </td>
     *   <td>�ŏ��l</td>
     *   <td>false</td>
     *   <td>�͈͎w��̍ŏ��l��ݒ肷��B�ݒ肵�Ȃ��ꍇ�AFloat�̍ŏ��l��
     *   �w�肳���B
     *   ���l�ȊO�̕����񂪓��͂��ꂽ�ꍇ�A��O���X���[�����B</td>
     *  </tr>
     *  <tr>
     *   <td> floatRangeMax </td>
     *   <td>�ő�l</td>
     *   <td>false</td>
     *   <td>�͈͎w��̍ő�l��ݒ肷��B�ݒ肵�Ȃ��ꍇ�AFloat�̍ő�l��
     *   �w�肳���B
     *   ���l�ȊO�̕����񂪓��͂��ꂽ�ꍇ�A��O���X���[�����B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateFloatRange(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ���ؒl��float�ɕϊ� --- Float�^�ł͂Ȃ��ꍇ�A���؃G���[�B
        float floatValue = 0;
        try {
            floatValue = Float.parseFloat(value);
        } catch (NumberFormatException e) {
            rejectValue(errors, field, va, bean);
            return false;
        }

        // �͈͎w��l --- �ݒ�l��Float�^�ł͂Ȃ��ꍇ�A��O�B
        //                �ݒ�Ȃ��̓f�t�H���g�l���g�p����B
        String strMin = field.getVarValue("floatRangeMin");
        float min = Float.MIN_VALUE;
        if (!GenericValidator.isBlankOrNull(strMin)) {
            try {
                min = Float.parseFloat(strMin);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- floatRangeMin is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }
        String strMax = field.getVarValue("floatRangeMax");
        float max = Float.MAX_VALUE;
        if (!GenericValidator.isBlankOrNull(strMax)) {
            try {
                max = Float.parseFloat(strMax);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- floatRangeMax is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }

        // ����
        if (!GenericValidator.isInRange(floatValue, min, max)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l�̕��������w�肳�ꂽ�ő啶�����ȉ������؂���B
     *
     * <p>�ȉ��́A������10�����ȉ��ł���Ƃ��̂�
     * true��ԋp���錟�؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;stringField&quot;
     *      depends=&quot;maxLength&quot;&gt;
     *    &lt;arg key=&quot;sample.stringField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;maxlength&lt;/var-name&gt;
     *      &lt;var-value&gt;10&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     *
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> maxlength </td>
     *   <td>�ő啶����</td>
     *   <td>true</td>
     *   <td>������̍ő啶������ݒ肷��B
     *   ���l�ȊO�̕����񂪓��͂��ꂽ�ꍇ�A��O���X���[�����B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateMaxLength(
            Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // �ő包��
        int max = 0;
        try {
            max = Integer.parseInt(field.getVarValue("maxlength"));
        } catch (NumberFormatException e) {
            String message = "Mistake on validation definition file. "
                + "- maxlength is not number. "
                + "You'll have to check it over. ";
            log.error(message, e);
            throw new ValidatorException(message);
        }

        // ����
        if (!GenericValidator.maxLength(value, max)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * ���͒l�̕��������w�肳�ꂽ�ŏ��������ȏォ���؂���B
     *
     * <p>�ȉ��́A������10�����ȏ�ł���Ƃ��̂�
     * true��ԋp���錟�؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;stringField&quot;
     *      depends=&quot;minLength&quot;&gt;
     *    &lt;arg key=&quot;sample.stringField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;minlength&lt;/var-name&gt;
     *      &lt;var-value&gt;10&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     *
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> minlength </td>
     *   <td>�ŏ�������</td>
     *   <td>true</td>
     *   <td>������̍ŏ���������ݒ肷��B
     *   ���l�ȊO�̕����񂪓��͂��ꂽ�ꍇ�A��O���X���[�����B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va ���ؒ���<code>ValidatorAction</code>�C���X�^���X�B
     * @param field ���ؒ���<code>Field</code>�C���X�^���X�B
     * @param errors ���؃G���[�����������ꍇ�A
     * �G���[�����i�[����I�u�W�F�N�g�B
     * @return ���؂ɐ��������ꍇ��<code>true</code>��Ԃ��B
     * ���؃G���[������ꍇ�A<code>false</code>��Ԃ��B
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateMinLength(
        Object bean, ValidatorAction va, Field field, ValidationErrors errors)
        throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // �ŏ�����
        int min = 0;
        try {
            min = Integer.parseInt(field.getVarValue("minlength"));
        } catch (NumberFormatException e) {
            String message = "Mistake on validation definition file. "
                + "- minlength is not number. "
                + "You'll have to check it over. ";
            log.error(message, e);
            throw new ValidatorException(message);
        }

        // ����
        if (!GenericValidator.minLength(value, min)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h���p�����ł��邱�Ƃ��`�F�b�N����B
     * ���K�\��<code>^([0-9]|[a-z]|[A-Z])*$</code>���g�p����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     */
    public boolean validateAlphaNumericString(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (!ValidationUtil.isAlphaNumericString(value)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h���啶���p�����ł��邱�Ƃ��`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     */
    public boolean validateCapAlphaNumericString(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (!ValidationUtil.isUpperAlphaNumericString(value)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�����l�ł��邱�Ƃ��`�F�b�N����B
     * �S�p�������g�p����ƌ��؂Ɏ��s����B
     * �`�F�b�N�Ɉ������������ꍇ�́A
     * �G���[����errors�ɒǉ����Afalse��ԋp����B
     *
     * <p>�܂��A���͂��ꂽ�������p���ABigDecimal �^�𐶐�����
     * �����Ő����s�\�Ȃ�΃G���[�������s�Ȃ��B
     *
     * ���ɐ������̌������w�肳��Ă���ꍇ�ɁA�����̊m�F���s���B
     * validation.xml �� isAccordedInteger()
     * �� "true" �w�肳��Ă���ꍇ�̂�
     * ���������̓���`�F�b�N���s����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[�������s�Ȃ��B
     *
     * �Ō�ɏ������̌������w�肳��Ă���ꍇ�ɁA�����̊m�F���s���B
     * validation.xml��isAccordedScale��"true"�ł���ꍇ�̂�
     * ���������̓���`�F�b�N���s����B
     *
     * <p>
     * ���L�́A������3�A������2�ł��鐔�l�����؂����ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;numberField&quot;
     *      depends=&quot;number&quot;&gt;
     *    &lt;arg key=&quot;sample.numberField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;integerLength&lt;/var-name&gt;
     *      &lt;var-value&gt;3&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;scale&lt;/var-name&gt;
     *      &lt;var-value&gt;2&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;isAccordedInteger&lt;/var-name&gt;
     *      &lt;var-value&gt;true&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;isAccordedScale&lt;/var-name&gt;
     *      &lt;var-value&gt;true&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> <code>integerLength</code> </td>
     *   <td> ���������� </td>
     *   <td> <code>false</code> </td>
     *   <td>�����̌�����ݒ肷��B<code>isAccordedInteger</code>�w�肪
     *       �����Ƃ��́A�w�茅���ȓ��̌��؂��s���B
     *       �ȗ����́A<code>int</code>�^�̍ő�l���w�肳���B
     *       �񐔒l��ݒ肵���ꍇ�A��O���X���[�����B</td>
     *  </tr>
     *  <tr>
     *   <td> <code>scale</code> </td>
     *   <td> ���������� </td>
     *   <td> <code>false</code> </td>
     *   <td>�����l�̌�����ݒ肷��A<code>isAccordedScale</code>�w�肪
     *       �����Ƃ��́A�w�茅���ȓ��̌��؂��s���B
     *       �ȗ����́A<code>int</code>�^�̍ő�l���w�肳���B
     *       �񐔒l��ݒ肵���ꍇ�A��O���X���[�����B</td>
     *  </tr>
     *  <tr>
     *   <td> <code>isAccordedInteger</code> </td>
     *   <td> ����������v�`�F�b�N </td>
     *   <td> <code>false</code> </td>
     *   <td> <code>true</code>���w�肳�ꂽ�Ƃ��A���������̈�v�`�F�b�N��
     *        �s�Ȃ���B�ȗ����A<code>true</code>�ȊO�̕����񂪐ݒ肳�ꂽ����
     *        �����ȓ��`�F�b�N�ƂȂ�B</td>
     *  </tr>
     *  <tr>
     *   <td> <code>isAccordedScale</code> </td>
     *   <td> ����������v�`�F�b�N </td>
     *   <td> <code>false</code> </td>
     *   <td> <code>true</code>���w�肳�ꂽ�Ƃ��A���������̈�v�`�F�b�N��
     *        �s�Ȃ���B�ȗ����A<code>true</code>�ȊO�̕����񂪐ݒ肳�ꂽ����
     *        �����ȓ��`�F�b�N�ƂȂ�B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateNumber(Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ���ؒl�����p�ł͂Ȃ��ꍇ�G���[�B
        if (!ValidationUtil.isHankakuString(value)) {
            rejectValue(errors, field, va, bean);
            return false;
        }

        // ���ؒl��BigDecimal�ɕϊ�
        BigDecimal number = null;
        try {
            number = new BigDecimal(value);
        } catch (NumberFormatException e) {
            rejectValue(errors, field, va, bean);
            return false;
        }

        // �����������擾
        int integerLength = Integer.MAX_VALUE;
        String integerLengthStr = field.getVarValue("integerLength");
        if (!GenericValidator.isBlankOrNull(integerLengthStr)) {
            try {
                integerLength = Integer.parseInt(integerLengthStr);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- integerLength is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }

        // �����������擾
        int scaleLength = Integer.MAX_VALUE;
        String scaleStr = field.getVarValue("scale");
        if (!GenericValidator.isBlankOrNull(scaleStr)) {
            try {
                scaleLength = Integer.parseInt(scaleStr);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- scale is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }

        // ����������v�`�F�b�N��
        boolean isAccordedInteger =
            "true".equals(field.getVarValue("isAccordedInteger"));
        // ����������v�`�F�b�N��
        boolean isAccordedScale =
            "true".equals(field.getVarValue("isAccordedScale"));

        // ����
        if (!ValidationUtil.isNumber(
                number, integerLength, isAccordedInteger,
                scaleLength, isAccordedScale)) {
            rejectValue(errors, field, va, bean);
            return false;
        }

        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�����p�J�i������ł��邱�Ƃ��`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     */
    public boolean validateHankakuKanaString(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (!ValidationUtil.isHankakuKanaString(value)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�����p������ł��邱�Ƃ��`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     */
    public boolean validateHankakuString(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (!ValidationUtil.isHankakuString(value)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h���S�p������ł��邱�Ƃ��`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     */
    public boolean validateZenkakuString(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (!ValidationUtil.isZenkakuString(value)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h���S�p�J�^�J�i������� ���邱�Ƃ��`�F�b�N����B<br>
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     */
    public boolean validateZenkakuKanaString(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (!ValidationUtil.isZenkakuKanaString(value)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�ɓ��͋֎~�����񂪍������Ă��邩
     * �ǂ������`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * <p>�ȉ��́A�֎~������`�F�b�N�̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;stringField&quot;
     *      depends=&quot;prohibited&quot;&gt;
     *    &lt;arg key=&quot;sample.stringField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;chars&lt;/var-name&gt;
     *      &lt;var-value&gt;!&quot;#$%&amp;'()&lt;&gt;&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> chars </td>
     *   <td>���͋֎~�L�����N�^</td>
     *   <td> true </td>
     *   <td>���͂��֎~���镶���B�ݒ肳��Ȃ��ꍇ�́AValidatorException
     *   ���X���[�����B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     * @exception ValidatorException �ݒ�t�@�C������chars�̒l���擾�ł��Ȃ�
     * �ꍇ�ɃX���[�����B
     */
    public boolean validateProhibited(Object bean, ValidatorAction va,
            Field field, ValidationErrors errors)
            throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ���͋֎~������
        String prohibitedStr = field.getVarValue("chars");

        // chars���擾�ł��Ȃ��ꍇ��ValidatorException���X���[
        if (StringUtils.isEmpty(prohibitedStr)) {
            log.error("var[chars] must be specified.");
            throw new ValidatorException("var[chars] must be specified.");
        }

        // ����
        if (!ValidationUtil.hasNotProhibitedChar(value, prohibitedStr)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�����p�����ł��邱�Ƃ��`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     */
    public boolean validateNumericString(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ����
        if (!ValidationUtil.isNumericString(value)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�̕����񒷂���v���Ă��邱�Ƃ��`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * <p>�ȉ��́A�����񒷂�5�ł���Ƃ��̂�true��ԋp����
     * ���؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;stringField&quot;
     *      depends=&quot;stringLength&quot;&gt;
     *    &lt;arg key=&quot;sample.stringField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;stringLength&lt;/var-name&gt;
     *      &lt;var-value&gt;5&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> stringLength </td>
     *   <td>���͕�����</td>
     *   <td> false </td>
     *   <td>���͕����񒷂��w�肷��B
     *        �ȗ����́A<code>int</code>�^�̍ő�l���w�肳���B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateStringLength(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors)
            throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ������
        int length = Integer.MAX_VALUE;
        String lengthStr = field.getVarValue("stringLength");

        try {
            length = Integer.valueOf(lengthStr).intValue();
        } catch (NumberFormatException e) {
            String message = "Mistake on validation definition file. "
                + "- stringLength is not number. "
                + "You'll have to check it over. ";
            log.error(message, e);
            throw new ValidatorException(message);
        }

        // ����
        if (value.length() != length) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�̔z��E�R���N�V�����̒������A
     * �w�萔�͈͓̔��ł��邱�Ƃ��`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * <p>�ȉ��́A�z��E�R���N�V�����̒������S�`�V�ł���Ƃ��̂�true��ԋp����
     * ���؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;arrayField&quot;
     *      depends=&quot;arrayRange&quot;&gt;
     *    &lt;arg key=&quot;sample.arrayField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;minArrayLength&lt;/var-name&gt;
     *      &lt;var-value&gt;4&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;maxArrayLength&lt;/var-name&gt;
     *      &lt;var-value&gt;7&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> minArrayLength </td>
     *   <td>�ŏ��z��</td>
     *   <td>false</td>
     *   <td>�z��̍ŏ��z�񐔂��w�肷��B
     *        �ŏ��z�񐔂̎w�肪�Ȃ��ꍇ�A�O���w�肳���B</td>
     *  </tr>
     *  <tr>
     *   <td> maxArrayLength </td>
     *   <td>�ő�z��</td>
     *   <td>false</td>
     *   <td>�z��̍ő�z�񐔂��w�肷��B
     *        �ő�z�񐔂̎w�肪�Ȃ��ꍇ�A
     *        <code>int</code>�^�̍ő�l���w�肳���B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateArrayRange(Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) throws ValidatorException {

        // �`�F�b�N�Ώۂ�bean��null�̏ꍇ��ValidatorException���X���[
        if (bean == null) {
            String message = "target of validateArrayRange must be not null.";
            log.error(message);
            throw new ValidatorException(message);
        }

        try {
            Class type =
                BeanUtil.getBeanPropertyType(bean, field.getProperty());
            if (type == null) {
                String message = "Cannot get property type[" +
                    bean.getClass().getName() + "." +
                    field.getProperty() + "]";
                log.error(message);
                throw new ValidatorException(message);
            } else if (!type.isArray()
                    && !Collection.class.isAssignableFrom(type)) {
                String message = "property [" + bean.getClass().getName() +
                    "." + field.getProperty() +
                    "] must be instance of Array or Collection.";
                log.error(message);
                throw new ValidatorException(message);
            }
        } catch (PropertyAccessException e) {
            String message = "Cannot get property type[" +
                bean.getClass().getName() + "." +
                field.getProperty() + "]";
            log.error(message, e);
            throw new ValidatorException(message);
        }

        // ���ؒl
        Object obj = null;
        try {
            obj = BeanUtil.getBeanProperty(bean, field.getProperty());
        } catch (PropertyAccessException e) {
            String message = "Cannot get property [" +
                bean.getClass().getName() +
                "." + field.getProperty() + "]";
            log.error(message, e);
            throw new ValidatorException(message);
        }

        // �w�肷��z��̍ŏ��T�C�Y
        int min = 0;
        String minStr = field.getVarValue("minArrayLength");
        if (!GenericValidator.isBlankOrNull(minStr)) {
            try {
                min = Integer.parseInt(minStr);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- minArrayLength is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }

        // �w�肷��z��̍ő�T�C�Y
        int max = Integer.MAX_VALUE;
        String maxStr = field.getVarValue("maxArrayLength");
        if (!GenericValidator.isBlankOrNull(maxStr)) {
            try {
                max = Integer.parseInt(maxStr);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- maxArrayLength is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }

        // ����
        try {
            if (!ValidationUtil.isArrayInRange(obj, min, max)) {
                rejectValue(errors, field, va, bean);
                return false;
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new ValidatorException(e.getMessage());
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h��URL�`�����ǂ����`�F�b�N����B<br>
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * <p>�ȉ��́AHTTP�AFTP�v���g�R���w��\�A�_�u���X���b�V���w��\�A
     * URL�̕����\�ł��錟�؂̐ݒ��ł���B
     * </p>
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;date&quot;
     *      depends=&quot;url&quot;&gt;
     *    &lt;arg key=&quot;label.date&quot; position=&quot;0&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;allowallschemes&lt;/var-name&gt;
     *      &lt;var-value&gt;false&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;allow2slashes&lt;/var-name&gt;
     *      &lt;var-value&gt;true&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;nofragments&lt;/var-name&gt;
     *      &lt;var-value&gt;false&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;schemes&lt;/var-name&gt;
     *      &lt;var-value&gt;http,ftp&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> allowallschemes </td>
     *   <td>true�ior false�j</td>
     *   <td>false</td>
     *   <td>�S�ẴX�L�[���������邩���f����t���O�B�f�t�H���gfalse�B</td>
     *  </tr>
     *  <tr>
     *   <td> allow2slashes </td>
     *   <td>true�ior false�j</td>
     *   <td>false</td>
     *   <td>�_�u���X���b�V���������邩���f����t���O�B�f�t�H���gfalse�B</td>
     *  </tr>
     *  <tr>
     *   <td> nofragments </td>
     *   <td>true�ior false�j</td>
     *   <td>false</td>
     *   <td>URL�����֎~�����f����t���O�B�f�t�H���gfalse�B</td>
     *  </tr>
     *  <tr>
     *   <td> schemes </td>
     *   <td>�v���g�R��</td>
     *   <td>false</td>
     *   <td>������X�L�[���B��������ꍇ�̓J���}��؂�Ŏw��B
     *       �f�t�H���g��http, https, ftp�B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return ���͒l����������� true
     */
    public boolean validateUrl(Object bean, ValidatorAction va,
            Field field, ValidationErrors errors) {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // �I�v�V�����̕ϐ����擾����
        boolean allowallschemes =
            "true".equals(field.getVarValue("allowallschemes"));
        boolean allow2slashes =
            "true".equals(field.getVarValue("allow2slashes"));
        boolean nofragments =
            "true".equals(field.getVarValue("nofragments"));
        String schemesVar = allowallschemes ? null : field
                .getVarValue("schemes");

        // ����
        if (!ValidationUtil.isUrl(
                value, allowallschemes, allow2slashes,
                nofragments, schemesVar)) {
            rejectValue(errors, field, va, bean);
            return false;
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�̃o�C�g�񒷂��w�肵���͈͓��ł��邱�Ƃ�
     * �`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * <p>�ȉ��́A�o�C�g�񒷂�5�ȏ�A10�ȉ��ł���Ƃ��̂� true
     * ��ԋp���錟�؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;stringField&quot;
     *      depends=&quot;byteRange&quot;&gt;
     *    &lt;arg key=&quot;sample.stringField&quot; position="0"/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;maxByteLength&lt;/var-name&gt;
     *      &lt;var-value&gt;10&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;minByteLength&lt;/var-name&gt;
     *      &lt;var-value&gt;5&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;encoding&lt;/var-name&gt;
     *      &lt;var-value&gt;Windows-31J&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> maxByteLength </td>
     *   <td>�ő�o�C�g��</td>
     *   <td>false</td>
     *   <td>���͕�����o�C�g�������؂��邽�߂̍ő�o�C�g���B
     *        �ȗ����́A<code>int</code>�^�̍ő�l���w�肳���B</td>
     *  </tr>
     *  <tr>
     *   <td> minByteLength </td>
     *   <td>�ŏ��o�C�g��</td>
     *   <td>false</td>
     *   <td>���͕�����o�C�g�������؂��邽�߂̍ŏ��o�C�g���B
     *        �ȗ����́A0���w�肳���B</td>
     *  </tr>
     *  <tr>
     *   <td> encoding </td>
     *   <td>�����G���R�[�f�B���O</td>
     *   <td>false</td>
     *   <td>���͕������o�C�g��ɕϊ�����ۂɎg�p���镶���G���R�[�f�B���O�B
     *   �ȗ����ꂽ�ꍇ��VM�̃f�t�H���g�G���R�[�f�B���O���g�p�����B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ̃I�u�W�F�N�g
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�I�u�W�F�N�g
     * @param errors �G���[
     * @return ���͒l����������� true
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �X���[������O�B
     */
    public boolean validateByteRange(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors)
            throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // �G���R�[�f�B���O
        String encoding = field.getVarValue("encoding");

        // �ŏ��o�C�g��
        int min = 0;
        String minStr = field.getVarValue("minByteLength");
        if (!GenericValidator.isBlankOrNull(minStr)) {
            try {
                min = Integer.parseInt(minStr);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- minByteLength is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }

        // �ő�o�C�g��
        int max = Integer.MAX_VALUE;
        String maxStr = field.getVarValue("maxByteLength");
        if (!GenericValidator.isBlankOrNull(maxStr)) {
            try {
                max = Integer.parseInt(maxStr);
            } catch (NumberFormatException e) {
                String message = "Mistake on validation definition file. "
                    + "- maxByteLength is not number. "
                    + "You'll have to check it over. ";
                log.error(message, e);
                throw new ValidatorException(message);
            }
        }

        // ����
        try {
            if (!ValidationUtil.isByteInRange(value, encoding, min, max)) {
                rejectValue(errors, field, va, bean);
                return false;
            }
        } catch (IllegalArgumentException e) {
            log.error("encoding[" + encoding + "] is not supported.");
            throw new ValidatorException("encoding[" + encoding +
                    "] is not supported.");
        }
        return true;
    }

    /**
     * ���t���w�肵���͈͓��ł��邩�ǂ����`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * <p>�ȉ��́A���t��2000/01/01����2010/12/31�͈͓̔��ł��邩�ǂ�����
     * ���؂̐ݒ��ł���B
     *
     * <h5>validation.xml�̋L�q��</h5>
     * <code><pre>
     * &lt;form name=&quot;sample&quot;&gt;
     *  �E�E�E
     *  &lt;field property=&quot;date&quot;
     *      depends=&quot;dateRange&quot;&gt;
     *    &lt;arg key=&quot;date&quot; position=&quot;0&quot;/&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;startDate&lt;/var-name&gt;
     *      &lt;var-value&gt;2000/01/01&lt;/var-value&gt;
     *    &lt;/var&gt;
     *    &lt;var&gt;
     *      &lt;var-name&gt;endDate&lt;/var-name&gt;
     *      &lt;var-value&gt;2010/12/31&lt;/var-value&gt;
     *    &lt;/var&gt;
     *  &lt;/field&gt;
     *  �E�E�E
     * &lt;/form&gt;
     * </pre></code>
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> startDate </td>
     *   <td>�J�n���t</td>
     *   <td>false</td>
     *   <td>���t�͈͂̊J�n��臒l�ƂȂ���t�B
     *   ���t�p�^�[���Ŏw�肵���`���Őݒ肷�邱�ƁB</td>
     *  </tr>
     *  <tr>
     *   <td> endDate </td>
     *   <td>�I�����t</td>
     *   <td>false</td>
     *   <td>���t�͈͂̏I����臒l�ƂȂ���t�B
     *   ���t�p�^�[���Ŏw�肵���`���Őݒ肷�邱�ƁB</td>
     *  </tr>
     *  <tr>
     *   <td> datePattern </td>
     *   <td>���t�p�^�[��</td>
     *   <td>false</td>
     *   <td>���t�p�^�[�����w�肷��B���͒l�̌����`�F�b�N�͍s��Ȃ��B
     *   ���Ƃ��΁A���t�p�^�[����yyyy/MM/dd�̏ꍇ�A2001/1/1�̓G���[�ɂȂ�Ȃ��B
     *   datePattern��datePatternStrict�������w�肳��Ă���ꍇ�́A
     *   datePattern���D�悵�Ďg�p�����B
     *   </td>
     *  </tr>
     *  <tr>
     *   <td> datePatternStrict </td>
     *   <td>���t�p�^�[��</td>
     *   <td>false</td>
     *   <td>���t�p�^�[�����w�肷��B���͒l�̌������A
     *   �w�肳�ꂽ���t�p�^�[���̌����ɍ��v���邩�̃`�F�b�N���s���B
     *   ���Ƃ��΁A���t�p�^�[����yyyy/MM/dd�̏ꍇ�A2001/1/1�̓G���[�ɂȂ�B
     *   datePattern��datePatternStrict�������w�肳��Ă���ꍇ�́A
     *   datePattern���D�悵�Ďg�p�����B
     *   </td>
     *  </tr>

     * </table>
     *
     * @param bean �����Ώۂ̃I�u�W�F�N�g
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�I�u�W�F�N�g
     * @param errors �G���[
     * @return ���͒l����������� true
     * @throws ValidatorException datePattern�܂���datePatternStrict�ɕs����
     * �p�^�[���������܂܂��ꍇ�AstartDate�܂���endDate�����t�ɕϊ��ł��Ȃ�
     * �ꍇ�ɃX���[�����
     */
    public boolean validateDateRange(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors)
            throws ValidatorException {
        // ���ؒl
        String value = extractValue(bean, field);
        if (StringUtils.isEmpty(value)) {
            return true;
        }

        // ���t���̓p�^�[��
        String datePattern = field.getVarValue("datePattern");
        String datePatternStrict = field.getVarValue("datePatternStrict");

        // �͈͎w�肷����t
        String startDateStr = field.getVarValue("startDate");
        String endDateStr = field.getVarValue("endDate");

        // ����
        try {
            if (!ValidationUtil.isDateInRange(value, startDateStr, endDateStr,
                    datePattern, datePatternStrict)) {
                rejectValue(errors, field, va, bean);
                return false;
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new ValidatorException(e.getMessage());
        }
        return true;
    }

    /**
     * �w�肳�ꂽ�t�B�[���h�Ɉ�v����S�Ẵv���p�e�B�l���`�F�b�N����B
     * �`�F�b�N�Ɉ������������ꍇ�́A�G���[����errors�ɒǉ����A
     * false��ԋp����B
     *
     * <p>�z��E�R���N�V�����^�ɑ΂��Ẵt�B�[���h�`�F�b�N���\�ł���B
     * �z��E�R���N�V�����̑S�Ă̗v�f�ɑ΂��āAvalidation.xml��
     * ��`����depends������hArray�h��������`�F�b�N���[�����Ăяo���B
     * depends="requiredArray"�@�� "required" �i�K�{�`�F�b�N�j
     *
     * @param bean �����Ώۂ�JavaBean�C���X�^���X
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param errors �G���[
     * @return �v�f���ׂĂ̓��͒l����������� true
     * @throws ValidatorException validation��`�t�@�C���̐ݒ�~�X���������ꍇ
     * �A�`�F�b�N�Ώۂ�bean��null�ł���ꍇ�ɃX���[������O�B
     */
    public boolean validateArraysIndex(Object bean,
            ValidatorAction va, Field field, ValidationErrors errors)
            throws ValidatorException {
        if (bean == null) {
            log.error("validation target bean is null.");
            throw new ValidatorException("validation target bean is null.");
        }

        Class[] paramClass = null;  // ���؃��\�b�h�̈����̌^
        Method method = null;       // ���؃��\�b�h
        try {
            paramClass = getParamClass(va);
            if (paramClass == null || paramClass.length == 0) {
                String message = "Mistake on validation rule file. "
                    + "- Can not get argument class. "
                    + "You'll have to check it over. ";
                log.error(message);
                throw new ValidatorException(message);
            }
            
            method = getMethod(va, paramClass);
            if (method == null) {
                String message = "Mistake on validation rule file. "
                    + "- Can not get validateMethod. "
                    + "You'll have to check it over. ";
                log.error(message);
                throw new ValidatorException(message);
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw new ValidatorException(e.getMessage());
        }

        try {
            // �z��̃C���f�b�N�X���ς���Ă��A�l���ω����Ȃ��p�����[�^
            Object[] argParams = new Object[paramClass.length];
            argParams[0] = bean;
            argParams[1] = va;
            argParams[3] = errors;

            // ���ؒl�i�z��v�f�j�̎��o��
            IndexedBeanWrapper bw = getIndexedBeanWrapper(bean);
            Map<String, Object> propertyMap =
                bw.getIndexedPropertyValues(field.getKey());

            boolean isValid = true; // ���؃t���O

            for (String key : propertyMap.keySet()) {
                // �C���f�b�N�X�t���̃v���p�e�B���Ńt�B�[���h������������
                Field indexedField = (Field) field.clone();
                indexedField.setKey(key);
                indexedField.setProperty(key);

                argParams[2] = indexedField; // �t�B�[���h

                // ���̓`�F�b�N���\�b�h�̌Ăяo��
                boolean bool = (Boolean) method.invoke(
                        this, argParams);
                if (!bool) {
                    isValid = false;
                }
            }
            return isValid;
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t instanceof ValidatorException) {
                throw (ValidatorException) t;
            } 
            log.error(t.getMessage(), t);
            throw new ValidatorException(t.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ValidatorException(e.getMessage());
        }
    }

    /**
     * IndexedBeanWrapper�����N���X���擾����B
     * @param bean �^�[�Q�b�g��Bean
     * @return IndexedBeanWrapper�̎���
     */
    protected IndexedBeanWrapper getIndexedBeanWrapper(Object bean) {
        return new JXPathIndexedBeanWrapperImpl(bean);
    }

    /**
     * ���؃��[���ɓn���������N���X�z����擾����B
     *
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @return �����N���X�z��
     */
    protected Class[] getParamClass(ValidatorAction va) {

        StringTokenizer st = new StringTokenizer(va.getMethodParams(), ",");
        Class[] paramClass = new Class[st.countTokens()];

        for (int i = 0; st.hasMoreTokens(); i++) {
            try {
                String key = st.nextToken().trim();
                paramClass[i] = ClassUtils.getClass(key);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
        return paramClass;
    }

    /**
     * �z��E�R���N�V�����̗v�f�����؂��郁�\�b�h���擾����B
     *
     * @param va ValidatorAction
     * @param paramClass �����N���X�z��
     * @return ���؃��\�b�h
     */
    protected Method getMethod(
            ValidatorAction va, Class[] paramClass) {

        String methodNameSource = va.getName();
        if (methodNameSource == null || "".equals(methodNameSource)) {
            // ���\�b�h���w�肪null�܂��͋󕶎��̂Ƃ�null�ԋp�B
            return null;
        }

        // name��������"Array"�������������\�b�h���𐶐�����
        // xxxxArray��validateXxxx
        char[] chars = methodNameSource.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        String validate = "validate" + new String(chars);
        String methodName = validate.substring(0, validate.length()
                - "Array".length());

        Method method = null;
        try {
            method = FieldChecks.class.getMethod(methodName, paramClass);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return method;
    }

    /**
     * �����t�B�[���h�̑��փ`�F�b�N���s���B
     *
     * ���̌��؃��[���̎��s�ɂ�{@link MultiFieldValidator} �̎����N���X���K�v�B<br>
     * ���������N���X�� <code>validation.xml</code> �ɐݒ���s���B<br>
     * �G���[�ƂȂ����ꍇ�ɂ́A�G���[���𐶐����A
     * �w�肳�ꂽ�G���[��񃊃X�g�ɒǉ�����B
     * ���̌��؃��[���ɂ̓f�t�H���g�̃G���[���b�Z�[�W���Ȃ����߁A
     * ���b�Z�[�W�� <code>validation.xml</code> �ɕK���L�q���邱�ƁB<br>
     * value�t�B�[���h�̒l���Avalue1�t�B�[���h�̒l�ȏ�A
     * value2�t�B�[���h�̒l�ȉ��ł��邱�Ƃ����؂���ꍇ�A�ȉ��̂悤�Ɏ����A
     * �ݒ���s���B
     * <h5>{@link MultiFieldValidator} �̎�����</h5>
     * <code><pre>
     * public boolean validate(Object value, Object[] depends) {
     *     int value0 = Integer.parseInt(value);
     *     int value1 = Integer.parseInt(depends[0]);
     *     int value2 = Integer.parseInt(depends[1]);
     *     return (value1 <= value0 && value2 >= value0);
     * }
     * </pre></code>
     * <h5>validation.xml�̐ݒ��</h5>
     * <code><pre>
     * &lt;form name=&quot;/validateMultiField&quot;&gt;
     *   &lt;field property=&quot;value&quot; depends=&quot;multiField&quot;&gt;
     *     &lt;msg key=&quot;errors.multiField&quot;
     *             name=&quot;multiField&quot;/&gt;
     *     &lt;arg key=&quot;label.value&quot; position=&quot;0&quot; /&gt;
     *     &lt;arg key=&quot;label.value1&quot; position=&quot;1&quot; /&gt;
     *     &lt;arg key=&quot;label.value2&quot; position=&quot;2&quot; /&gt;
     *     &lt;var&gt;
     *       &lt;var-name&gt;fields&lt;/var-name&gt;
     *       &lt;var-value&gt;value1,value2&lt;/var-value&gt;
     *     &lt;/var&gt;
     *     &lt;var&gt;
     *       &lt;var-name&gt;multiFieldValidator&lt;/var-name&gt;
     *       &lt;var-value&gt;sample.SampleMultiFieldValidator&lt;/var-value&gt;
     *     &lt;/var&gt;
     *   &lt;/field&gt;
     * &lt;/form&gt;
     * </pre></code>
     * <h5>���b�Z�[�W���\�[�X�t�@�C���̐ݒ��</h5>
     * <code>
     * errors.multiField={0}��{1}����{2}�̊Ԃ̒l����͂��Ă��������B
     * </code>
     *
     * <h5>validation.xml�ɐݒ��v����&lt;var&gt;�v�f</h5>
     * <table border="1">
     *  <tr>
     *   <td><center><b><code>var-name</code></b></center></td>
     *   <td><center><b><code>var-value</code></b></center></td>
     *   <td><center><b>�K�{��</b></center></td>
     *   <td><center><b>�T�v</b></center></td>
     *  </tr>
     *  <tr>
     *   <td> fields </td>
     *   <td>���؂ɕK�v�ȑ��̃t�B�[���h��</td>
     *   <td>false</td>
     *   <td>�����̃t�B�[���h���w�肷��ꍇ�̓t�B�[���h�����J���}��؂��
     *   �w�肷��B</td>
     *  </tr>
     *  <tr>
     *   <td> multiFieldValidator </td>
     *   <td>{@link MultiFieldValidator} �����N���X��</td>
     *   <td>true</td>
     *   <td>�����t�B�[���h�̑��փ`�F�b�N���s�� {@link MultiFieldValidator}
     *   �����N���X���B</td>
     *  </tr>
     * </table>
     *
     * @param bean �����Ώۂ̃I�u�W�F�N�g
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�I�u�W�F�N�g
     * @param errors �G���[
     * @return ���͒l����������� <code>true</code>
     */
    public boolean validateMultiField(Object bean,
                                        ValidatorAction va,
                                        Field field,
                                        ValidationErrors errors) {

        // bean��null�̎��A�G���[���O���o�͂��Atrue��ԋp����B
        if (bean == null) {
            log.error("bean is null.");
            return true;
        }

        // ���ؑΏۂ̒l���擾
        Object value = null;
        if (bean instanceof String) {
            value = bean;
        } else {
            try {
                value = PropertyUtils.getProperty(bean, field.getProperty());
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                log.error(e.getMessage(), e);
            } catch (NoSuchMethodException e) {
                log.error(e.getMessage(), e);
            }
        }
        // ���̃t�B�[���h�Ɉˑ������K�{���̓`�F�b�N���l�����A
        // ���ؒl��null�܂��͋󕶎��̏ꍇ�ł������t�B�[���h�`�F�b�N�͎��s����B
        
        // MultiFieldValidator�����N���X�����擾
        String multiFieldValidatorClass
            = field.getVarValue("multiFieldValidator");

        if (multiFieldValidatorClass == null
                || "".equals(multiFieldValidatorClass)) {
            log.error("var value[multiFieldValidator] is required.");
            throw new IllegalArgumentException(
                    "var value[multiFieldValidator] is required.");
        }

        MultiFieldValidator mfv = null;
        try {
            mfv = (MultiFieldValidator) ClassUtil.create(
                    multiFieldValidatorClass);
        } catch (ClassLoadException e) {
            log.error("var value[multiFieldValidator] is invalid.", e);
            throw new IllegalArgumentException(
                    "var value[multiFieldValidator] is invalid.", e);
        } catch (ClassCastException e) {
            log.error("var value[multiFieldValidator] is invalid.", e);
            throw new IllegalArgumentException(
                    "var value[multiFieldValidator] is invalid.", e);
        }
        
        // ���؂̈ˑ��t�B�[���h�̒l���擾
        String fields = field.getVarValue("fields");
        List<Object> valueList = new ArrayList<Object>();
        if (fields != null) {
            StringTokenizer st = new StringTokenizer(fields, ",");
            while (st.hasMoreTokens()) {
                String f = st.nextToken();
                f = f.trim();
                try {
                    valueList.add(PropertyUtils.getProperty(bean, f));
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                } catch (InvocationTargetException e) {
                    log.error(e.getMessage(), e);
                } catch (NoSuchMethodException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug("dependent fields:" + valueList);
        }

        Object[] values = new Object[valueList.size()];

        valueList.toArray(values);

        boolean result = mfv.validate(value, values);

        if (!result) {
            rejectValue(errors, field, va, bean);
            return false;
        }

        return true;
    }

    /**
     * �I�u�W�F�N�g���猟�ؒl�����o���Bbean��Null�̏ꍇ��Null��Ԃ��B
     * bean��String�^�̏ꍇ�Abean��Ԃ��B
     * bean��Number�^��Boolean�^��Character�^�̏ꍇ�Abean.toString()��Ԃ��B
     * ����ȊO�̏ꍇ�Abean��<code>Field</code>�I�u�W�F�N�g�̒l����A
     * <code>ValidatorUtils</code>�N���X�̃��\�b�h�𗘗p���Ēl���擾����B
     *
     * @param bean ���ؑΏۂ̃I�u�W�F�N�g�B
     * @param field <code>Field</code>�I�u�W�F�N�g�B
     * @return ���ؒl�B
     * @see ValidatorUtils#getValueAsString(Object, String)
     */
    protected String extractValue(Object bean, Field field) {
        String value = null;

        if (bean == null) {
            return null;
        } else if (bean instanceof String) {
            value = (String) bean;
        } else if (bean instanceof Number
            ||  bean instanceof Boolean
            ||  bean instanceof Character) {
            value = bean.toString();
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        return value;
    }

    /**
     * ���̓`�F�b�N�G���[���������ꍇ�ɁA�G���[�����A
     * TERASOLUNA���ʂ̃G���[�C���^�t�F�[�X�Ɉ����n���B
     *
     * @param errors �G���[
     * @param va Validator�ɂ��p�ӂ��ꂽValidatorAction
     * @param field �t�B�[���h�C���X�^���X
     * @param bean ���̓G���[��������JavaBean�C���X�^���X
     */
    protected void rejectValue(ValidationErrors errors, Field field,
            ValidatorAction va, Object bean) {
        errors.addError(bean, field, va);
    }
}