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

package jp.terasoluna.fw.validation.springmodules;

import org.springframework.validation.Errors;

import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * ���փ`�F�b�N���s�Ȃ����ۃN���X�B
 * 
 * <p>Spring-Modules Validator�ɂ��P���ڃ`�F�b�N�ȊO�ɑ��ւɃ`�F�b�N��
 * �s�Ȃ������ꍇ�Ɏg�p����B
 * �������ADB�A�N�Z�X���K�v�ȃ`�F�b�N�͊e�Ɩ��N���X�ɂă`�F�b�N���邱�ƁB
 * �T�u�N���X��{@link #validateMultiField(Object, Errors)}���\�b�h��
 * �I�[�o�[���C�h���A�`�F�b�N���W�b�N���L�q����B</p>
 * 
 * <h5>�T�u�N���X�̎�����</h5>
 * 
 * <p>�T�u�N���X��{@link #validateMultiField(Object, Errors)}���\�b�h��
 * �I�[�o�[���C�h����B
 * ������obj�͌����Ώۂ�JavaBean�ł��邽�߁A�eJavaBean�̌^�ɃL���X�g���Ă���
 * ���������o���B
 * �G���[��ǉ�����ꍇ�A����errors��reject���\�b�h�ArejectValue���\�b�h��
 * ���s����B </p>
 * 
 * <pre>
 * protected void validateMultiField(Object obj, Errors errors) {
 *
 *     // JavaBean�̎擾
 *     SampleBean bean = (SampleBean) obj;
 *
 *     // �P�ڂ̑���
 *     String field1 = bean.getField1();
 * 
 *     // �Q�ڂ̑���
 *     String field2 = bean.getField2();
 *       
 *     // �P�ڂ̑����ƂQ�ڂ̑����������ł͂Ȃ��ꍇ�A�G���[��ǉ�����
 *     if (!field1.equals(field2) {
 *         // �G���[��ǉ�����
 *         errors.reject("errors.sample");
 *     }
 * }
 * </pre>
 * 
 * <h5>Errors�C���^�t�F�[�X�̑�\�I�ȃG���[�ǉ����\�b�h</h5>
 * 
 * <table border="1">
 * <tr>
 *  <td><center><b>���\�b�h��</b></center></td>
 *  <td><center><b>����</b></center></td>
 * </tr>
 * 
 * <tr>
 *  <td>void rejectValue(String field, String errorCode)</center></td>
 *  <td>�G���[��ǉ�����Bfield�ɂ�JavaBean�̃v���p�e�B���A
 * errorCode�ɂ̓��\�[�X�o���h���̃L�[���w�肷��B
 * �u�������񂪂Ȃ��G���[�Ɏg�p����B</td>
 * </tr>
 * <tr>
 *  <td>void rejectValue(String field, String errorCode, Object[] errorArgs, 
 *  String defaultMessage)</center></td>
 *  <td>�G���[��ǉ�����Bfield�AerrorCode�͏�L�Ɠ��l�BerrorArgs�͒u��������A
 * defaultMessage�̓f�t�H���g���b�Z�[�W���w�肷��B�iTerasoluna�ł�
 * �f�t�H���g���b�Z�[�W�͎g�p���Ȃ����߁A�C�ӂ̕������ݒ肷��j</td>
 * </tr>
 * <tr>
 *  <td>void reject(String errorCode)</center></td>
 *  <td>�G���[��ǉ�����B���\�[�X�o���h���̃L�[�̂ݎw�肷��B
 * field�����w�肵�Ȃ����փ`�F�b�N���Ɏg�p����B</td>
 * </tr>
 * <tr>
 *  <td>void reject(String errorCode, Object[] errorArgs, 
 *  String defaultMessage)</center></td>
 *  <td>�G���[��ǉ�����BerrorArgs�͒u��������AdefaultMessage��
 *  �f�t�H���g���b�Z�[�W���w�肷��B�iTerasoluna�ł�
 *  �f�t�H���g���b�Z�[�W�͎g�p���Ȃ����߁A�C�ӂ̕������ݒ肷��j
 * field�����w�肵�Ȃ����փ`�F�b�N���Ɏg�p����B</td>
 * </tr>
 * </table>
 * 
 * <h5>Bean��`�t�@�C���̋L�q��i���̓`�F�b�N�N���X�j</h5>
 * 
 * <p>Spring��ł��̓��̓`�F�b�N�@�\���g�p����ꍇ�A
 * ���̓`�F�b�N�t�@�N�g���i���̓`�F�b�N�N���X�̏��������s�Ȃ��N���X�j��
 * ���̓`�F�b�N�N���X��Bean��`�t�@�C���ɐݒ肷��K�v������B</p>
 * <pre>
 * &lt;!-- ���̓`�F�b�N�N���X�̃t�@�N�g�� --&gt;
 * &lt;bean id="validatorFactory"     
 *   class="org.springmodules.commons.validator.DefaultValidatorFactory"&gt; 
 *   &lt;property name="validationConfigLocations"&gt; 
 *     &lt;list&gt; 
 *       &lt;!-- �����ɋL�q�����o���f�[�V������`�t�@�C���ivalidation.xml�j��
 *            �o���f�[�V�������[����`�t�@�C���ivalidation-rules.xml�j��
 *            Spring�t���[�����[�N���ݒ�t�@�C���Ƃ��ĔF������ --&gt; 
 *       &lt;value&gt;<b>/WEB-INF/validation/validator-rules.xml</b>&lt;/value&gt;
 *       &lt;value&gt;<b>/WEB-INF/validation/validator-rules-ex.xml</b>&lt;/value&gt;
 *       &lt;value&gt;<b>/WEB-INF/validation/validation.xml</b>&lt;/value&gt; 
 *     &lt;/list&gt; 
 *   &lt;/property&gt; 
 * &lt;/bean&gt; 
 *   
 * &lt;!--�@���փ`�F�b�N���s�Ȃ����̓`�F�b�N�N���X 
 *   class�����ɍ쐬�������փ`�F�b�N�N���X���L�q����--&gt;
 * &lt;bean id="sampleValidator"  <b>class="jp.terasoluna.sample2.validation.SampleMultiFieldValidator"</b>&gt; 
 *   &lt;property name="validatorFactory"&gt;&lt;ref local="validatorFactory"/&gt;&lt;/property&gt; 
 * &lt;/bean&gt;
 * </pre>
 * 
 * <h5>Bean��`�t�@�C���̋L�q��i�R���g���[���j</h5>
 * 
 * <p>Bean��`�t�@�C���Őݒ肵�����̓`�F�b�N�@�\���g�p���邽�߂ɁA
 * �R���g���[����validator�����ɏ�L�Őݒ肵�����̓`�F�b�N�@�\��
 * �ݒ肷��K�v������B</p>
 * <pre>
 * &lt;!-- �T���v�����̓`�F�b�N�Ɩ� --&gt;
 * &lt;bean name="/secure/blogic/validateSample.do" 
 *   class="jp.terasoluna.sample2.web.controller.ValidateSampleController"
 *   parent="xmlRequestController"&gt;  
 *   &lt;property name="sumService" ref="sumService"/&gt;  
 *   &lt;property name=<b>"validator"</b> ref=<b>�gsampleValidator"</b>/&gt;
 *   &lt;property name="commandClass"&gt;
 *     &lt;value&gt;jp.terasoluna.sample2.dto.SampleDto&lt;/value&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * @see org.springmodules.validation.commons.DefaultBeanValidator
 * @see org.springframework.validation.BindException
 * @see org.springframework.validation.Errors
 */
public abstract class BaseMultiFieldValidator extends DefaultBeanValidator {

    /**
     * ���̓`�F�b�N���\�b�h�B
     * �ݒ�t�@�C���ɂ��P���ڃ`�F�b�N���Ăяo���A
     * �G���[���Ȃ����{@link #validateMultiField(Object, Errors)}
     * ���\�b�h���Ăяo���B
     * 
     * @param obj �����Ώۂ�JavaBean
     * @param errors �G���[
     */
    @Override
    public void validate(Object obj, Errors errors) {
        
        // �ݒ�t�@�C���ɂ��P���ڃ`�F�b�N
        super.validate(obj, errors);
        
        // �G���[���Ȃ���Α��փ`�F�b�N���s�Ȃ�
        if (!errors.hasErrors()) {
            validateMultiField(obj, errors);
        }
    }

    /**
     * ���փ`�F�b�N���s�Ȃ����\�b�h�B
     * �P���ڃ`�F�b�N�ŃG���[���������Ȃ��ꍇ�A�Ăяo�����B
     * �T�u�N���X�͂��̃��\�b�h����������
     * 
     * @param obj �����Ώۂ�JavaBean
     * @param errors �G���[
     */
    protected abstract void validateMultiField(Object obj, Errors errors);
}
