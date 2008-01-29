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

import org.apache.commons.validator.Validator;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.DefaultValidatorFactory;

/**
 * terasoluna-validator-spring��CommonsValidatorEx�C���X�^���X�𐶐�����t�@�N�g���N���X�B
 * 
 * <p>{@link jp.terasoluna.fw.validation.FieldChecks}�N���X�ɂ�
 * �g�p�����G���[�C���^�t�F�[�X�̎�����
 * Spring�t���[�����[�N��{@link org.springframework.validation.Errors}�N���X��
 * �������߂̃N���X�B
 * Spring�t���[�����[�N�ASpring-Modules���g�p���邱�Ƃ�O��Ƃ��Ă���A
 * Bean��`�t�@�C���Ɉȉ��̂悤�Ȑݒ肪�K�v�ƂȂ�B</p>
 * 
 * <hr>
 * 
 * <h5>Bean��`�t�@�C���ݒ��</h5>
 * <code><pre>
 * &lt;!-- Validator�t�@�N�g���ݒ� --&gt;
 * &lt;bean id="validatorFactory" 
 *   class="jp.terasoluna.fw.validation.springmodules.DefaultValidatorFactoryEx"&gt; 
 *   &lt;property name="validationConfigLocations"&gt; 
 *     &lt;list&gt; 
 *       &lt;value&gt;/WEB-INF/validation/validator-rules.xml&lt;/value&gt;
 *       &lt;value&gt;/WEB-INF/validation/validator-rules-ex.xml&lt;/value&gt;
 *       &lt;value&gt;/WEB-INF/validation/validation.xml&lt;/value&gt; 
 *     &lt;/list&gt; 
 *   &lt;/property&gt; 
 * &lt;/bean&gt; 
 *   
 * &lt;!-- Validator�ݒ� --&gt;
 * &lt;bean id="beanValidator" class="jp.terasoluna.fw.validation.springmodules.DefaultBeanValidatorEx"&gt; 
 *   &lt;property name="validatorFactory"&gt;&lt;ref local="validatorFactory"/&gt;&lt;/property&gt; 
 * &lt;/bean&gt;
 * </pre></code>
 * 
 * @see jp.terasoluna.fw.validation.ValidationErrors
 * @see jp.terasoluna.fw.validation.springmodules.SpringValidationErrors
 *
 */
public class DefaultValidatorFactoryEx extends DefaultValidatorFactory {

    /**
     * Validator�C���X�^���X�ɐݒ肷��G���[�I�u�W�F�N�g�̃L�[�B
     */
    public static final String TERASOLUNA_ERRORS_KEY = 
        "jp.terasoluna.fw.validation.ValidationErrors";
    
    /**
     * Validator�C���X�^���X���擾����B
     * 
     * @param beanName ���؂���JavaBean�̖��O�B
     * @param bean ���ؑΏۂ�JavaBean�B
     * @param errors Spring�t���[�����[�N�̃G���[���B
     * @return Validator�C���X�^���X�B
     */
    @Override
    public Validator getValidator(
            String beanName, Object bean, Errors errors) {
        Validator validator = 
            new CommonsValidatorEx(getValidatorResources(), beanName);
        
        // BindException�����b�v�����G���[�N���X��Validator�ɐݒ肷��
        SpringValidationErrors commonErrors = createSpringValidationErrors();
        commonErrors.setErrors(errors);
        validator.setParameter(TERASOLUNA_ERRORS_KEY, commonErrors);
        
        validator.setParameter(Validator.BEAN_PARAM, bean);
        return validator;
    }
    
    /**
     * ���ʓ��͒l���؃G���[�I�u�W�F�N�g�𐶐�����B
     * @return ���ʓ��͒l���؃G���[�I�u�W�F�N�g�B
     */
    protected SpringValidationErrors createSpringValidationErrors() {
        return new SpringValidationErrors();
    }
}
