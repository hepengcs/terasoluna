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

package jp.terasoluna.fw.web.rich.springmvc.servlet.handler;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.web.rich.springmvc.Constants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * ExceptionResolveDelegator�̃f�t�H���g�����N���X�B
 * <p>
 * �{�N���X�́A{@link jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx}�Ŏg�p����邱�Ƃ�O��Ƃ��Ă���B
 * </p>
 * 
 * <p>
 * �G���[��ʂ����X�|���X�w�b�_�ɁA�G���[�R�[�h��Model�C���X�^���X�ɐݒ肷��B<br>
 * �{�N���X���g�p����ꍇ�ASimpleMappingExceptionResolverEx��linkedExceptionMappings�����̒l�ɁA
 * �r���[���ƃG���[�����J���}�ŋ�؂����������ݒ�iBean��`�j����K�v������B
 * �i��Bean��`�t�@�C���̋L�q���@�Ɋւ��ẮASimpleMappingExceptionResolverEx��javadoc���Q�Ƃ��邱�Ɓj<br>
 * �J���}�ŋ�؂���������̂P�ԖڂɃr���[���A�Q�ԖڂɃG���[��ʁA�R�ԖڂɃG���[�R�[�h���L�q���邱�ƁB<br>
 * �r���[���ƃG���[��ʂ̐ݒ�͕K�{�ł���B<br>
 * �G���[�R�[�h�̐ݒ�͔C�ӂł���A�ȗ����邱�Ƃ��ł���B<br>
 * ���f���Ƀr���[���i"bindException"�j�ƃG���[�R�[�h�i"8004C002"�j�A���X�|���X�w�b�_�ɃG���[��ʁi"kind01"�j��ݒ肷��ꍇ�A�ȉ��̕������ݒ�iBean��`�j����B
 * �ubindException,kind01,8004C002�v<br>
 * ���f���Ƀr���[���i"bindException"�j�A���X�|���X�w�b�_�ɃG���[��ʁi"kind01"�j��ݒ肷��ꍇ�A�ȉ��̕������ݒ�iBean��`�j����B
 * �ubindException,kind01�v
 * </p>
 * 
 */
public class ExceptionResolveDelegatorImpl implements ExceptionResolveDelegator {
    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory
            .getLog(ExceptionResolveDelegatorImpl.class);

    /**
     * ��O�����������ꍇ�Ƀ��X�|���X�w�b�_�ɐݒ肷��L�[���̃f�t�H���g�l�B
     */
    protected static final String EXCEPTION_KEY = "exception";
    
    /**
     * �ݒ�t�@�C���Ƀ��X�|���X�w�b�_�̃G���[�^�C�v�̃L�[���̃L�[�B
     */
    protected static final String ERROR_TYPE_HEADER_NAME_KEY = "errorTypeHeaderName";

    /**
     * ��O�̌^�B
     */
    protected String mappingKey = null;

    /**
     * �r���[���ƃG���[��񂪊i�[���ꂽ������B
     */
    protected Object mappingValues = null;

    /**
     * �r���[���B
     */
    protected String viewName = null;
    
    /**
     * �G���[��ʁB
     */
    protected String errorType = null;

    /**
     * �G���[�R�[�h�B
     */
    protected String errorCode = null;
    
    /**
     * ���X�|���X�w�b�_�̃G���[�^�C�v�̃L�[��(�f�t�H���g�l�FEXCEPTION_KEY)
     */
    protected String errorTypeHeaderName = EXCEPTION_KEY;

    /**
     * ��O�̌^�ƃr���[���i���G���[���j�𑮐��Ɋi�[����B
     * �i�[����O�ɁA�r���[���i���G���[���j�̐��������`�F�b�N���A
     * ���X�|���X�w�b�_�Ƀw�b�_�̃G���[�^�C�v�̃L�[�̗L�������`�F�b�N����B
     * params�ɃL�[ERROR_TYPE_HEADER_NAME_KEY�����݂��A���̒l��null�Ƌ󕶎���ȊO�̏ꍇ�́A
     * ����errorTypeHeaderName�ɂ��̒l��ݒ肷��B
     * 
     * @param mappingKey ��O�̌^
     * @param mappingValues �r���[���ƃG���[���i�J���}��؂�̕�����j
     * @param params �w�b�_�̃G���[�^�C�v�̃L�[�ȂǏ��̃L�[�ƒl���i�[����Map
     */
    public void initMapping(String mappingKey, Object mappingValues, 
            Map<String,String> params) {

        // mappingValues��null�̏ꍇ�A��O���X���[����B
        if (mappingValues == null) {
            String message = "linkedExceptionMappings[" + mappingKey
                    + "] value is null. "
                    + "Check Spring Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }

        // mappingValues��String�^�łȂ��ꍇ�A��O���X���[����B
        if (!(mappingValues instanceof String)) {
            String message = "linkedExceptionMappings[" + mappingKey
            + "] value is not String type. "
            + "Check Spring Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }
        
        // params�ɃL�[ERROR_TYPE_HEADER_NAME_KEY�����݂��A���̒l���󕶎���ȊO�̏ꍇ
        if (params != null){
            String errorTypeName = params.get(ERROR_TYPE_HEADER_NAME_KEY);
            if(errorTypeName != null && errorTypeName.length() != 0){
                // ����errorTypeHeaderName�ɂ��̒l��ݒ肷��B
                this.errorTypeHeaderName = errorTypeName;
            }
        }
        
        String[] mappingValueArray = StringUtils
                .commaDelimitedListToStringArray((String) mappingValues);

        // mappingValues���J���}�ŕ��������l���Q�ȉ��̏ꍇ�A��O���X���[����
        if (mappingValueArray.length < 2) {
            String message = "linkedExceptionMappings[" + mappingKey
                    + "] value is insufficient. Two values are necessary. "
                    + "Check Spring Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }

        // mappingValues���J���}�ŕ��������l���󔒂̏ꍇ�A��O���X���[����
        for (int i = 0; i < mappingValueArray.length; i++) {
            mappingValueArray[i] = mappingValueArray[i].trim();
            if ("".equals(mappingValueArray[i])) {
                String message = "linkedExceptionMappings[" + mappingKey
                        + "] value[" + i + "] is empty. "
                        + "Check Spring Bean definition file.";
                log.error(message);
                throw new IllegalStateException(message);
            }
        }

        this.mappingKey = mappingKey;
        this.mappingValues = mappingValues;

        // mappingValues���J���}�ŕ��������l�𑮐��Ɋi�[����
        this.viewName = mappingValueArray[0];
        this.errorType = mappingValueArray[1];
        if (mappingValueArray.length > 2) {
            this.errorCode = mappingValueArray[2];
        }

    }

    /**
     * ���X�|���X�w�b�_�ɃG���[��ʂ�ݒ肷��B
     * 
     * @param response HTTP���X�|���X
     */
    public void setHeader(HttpServletResponse response) {
        response.setHeader(errorTypeHeaderName, this.errorType);
    }

    /**
     * ModelAndView�ɃG���[�R�[�h��ݒ肷��B
     * 
     * @param mv ModelAndView ModelAndView�I�u�W�F�N�g
     */
    public void addObjectToModel(ModelAndView mv) {
        if (this.errorCode != null) {
            mv.addObject(Constants.ERRORCODE_KEY, this.errorCode);
        }
    }

    /**
     * �r���[�����擾����B
     * @return �r���[��
     */
    public String getViewName() {
        return this.viewName;
    }

}
