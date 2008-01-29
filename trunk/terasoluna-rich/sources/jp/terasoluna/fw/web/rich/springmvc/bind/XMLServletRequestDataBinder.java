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

package jp.terasoluna.fw.web.rich.springmvc.bind;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletRequest;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessage;
import jp.terasoluna.fw.oxm.xsd.message.ErrorMessages;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.exception.XMLRequestIOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.w3c.dom.Document;

/**
 * XML�`���̃��N�G�X�g�f�[�^���R�}���h�I�u�W�F�N�g�Ƀo�C���h����N���X�B
 * <p>
 * �ȉ��̏��������Ԃɍs���B
 * <ol>
 * <li>XML�f�[�^�̌`���`�F�b�N�i���ȗ��\�j</li>
 * <li>XML���I�u�W�F�N�g�ւ̕ϊ�����</li>
 * </ol>
 * </p>
 * <p>
 * XML�f�[�^�̌`���`�F�b�N�ɂ�XML�X�L�[�}���g�p����B <br>
 * ���ۂ̌`���`�F�b�N������SchemaValidator�ɏ������Ϗ�����B<br>
 * �ڍׂ�{@link jp.terasoluna.fw.oxm.xsd.SchemaValidator}���Q�Ƃ��邱�ƁB
 * </p>
 * <p>
 * XML�f�[�^���I�u�W�F�N�g�ɕϊ�����@�\��Castor���g�p����B �ڍׂ�CastorOXMapperImpl�N���X���Q�Ƃ��邱�ƁB
 * ���ۂ�XML����I�u�W�F�N�g�֕ϊ����镔����OXMapper�ɏ������Ϗ�����B<br>
 * �ڍׂ�{@link jp.terasoluna.fw.oxm.mapper.OXMapper}���Q�Ƃ��邱�ƁB
 * </p>
 * <p>
 * �f�[�^�o�C���h�����Ŕ�������G���[�͂Q��ނ���A�K�؂ȃG���[�n���h�����O���s���K�v������B<br>
 * �G���[�̈ꗗ���ȉ��ɋL��<br>
 * <ol>
 * <li>�`���`�F�b�N�G���[</li>
 * <li>OXMappingException</li>
 * </ol>
 * </p>
 * <p>
 * <u>�`���`�F�b�N�G���[�̃n���h�����O</u><br>
 * �`���`�F�b�N�G���[�Ő��������G���[���b�Z�[�W���ABindException�Ɋi�[����B<br>
 * �G���[�̏ڍׂ�{@link jp.terasoluna.fw.oxm.xsd.xerces.XMLErrorReporterEx}���Q�Ƃ��邱�ƁB
 * </p>
 * <p>
 * �y�`���`�F�b�N�̃��\�[�X�o���h���ݒ��z<br>
 * <code><pre>
 *           typeMismatch.number= {0}�ɂ�{1}�l����͂��Ă�������.
 *           typeMismatch.boolean= {0}�ɂ�boolean�l����͂��Ă�������.
 *           typeMismatch.date= {0}�ɂ͐��������t����͂��Ă�������.
 *           typeMismatch.numberMinRange= {0}�ɂ�{1}�ȏ��{2}�l����͂��Ă�������.
 *           typeMismatch.numberMaxRange= {0}�ɂ�{1}�ȉ���{2}�l����͂��Ă�������.
 * </pre></code>
 * </p>
 * <p>
 * <u>OXMappingException�̃n���h�����O</u><br>
 * �f�[�^�o�C���h�����Ŕ��������O�͂��ׂĎ��s����O�ł���A ��{�I�Ƀn���h�����O����K�v�͂Ȃ��B<br>
 * �K�v�ɉ����āA��O�n���h���̒�`��OXMappingException�A �܂��͂��̃T�u�N���X���G���g�����邱�ƁB
 * OXMappingException�̏ڍׂ�{@link jp.terasoluna.fw.oxm.exception.OXMappingException}���Q�Ƃ��邱�ƁB
 * ��O�n���h�����O�̏ڍׂ�{@link org.springframework.web.servlet.handler.SimpleMappingExceptionResolver}���Q�Ƃ��邱�ƁB
 * </p>
 * <p>
 * �yOXMappingException��Bean��`��z <br>
 * <code><pre>
 *          &lt;bean id=&quot;handlerExceptionResolver&quot;
 *                class=&quot;jp.terasoluna.fw.web.rich.springmvc.servlet.handler.SimpleMappingExceptionResolverEx&quot;&gt;
 *              &lt;property name=&quot;linkedExceptionMappings&quot;&gt;
 *                  &lt;map&gt;
 *                     &lt;entry key=&quot;jp.terasoluna.fw.oxm.exception.OXMappingException&quot;&gt;
 *                          &lt;value&gt;oxmException,8004C028&lt;/value&gt;
 *                      &lt;/entry&gt;
 *                                      �E
 *                                      �E
 *                                      �E
 *                  &lt;/map&gt;
 *              &lt;/property&gt;
 *          &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessage
 * @see jp.terasoluna.fw.oxm.xsd.message.ErrorMessages
 * @see jp.terasoluna.fw.oxm.exception.OXMappingException
 * @see jp.terasoluna.fw.oxm.xsd.SchemaValidator
 * @see jp.terasoluna.fw.oxm.mapper.OXMapper
 * @see org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
 */
public class XMLServletRequestDataBinder extends ServletRequestDataBinder {
    
    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory
            .getLog(XMLServletRequestDataBinder.class);

    /**
     * OXMapper�B
     */
    private OXMapper oxmapper = null;

    /**
     * SchemaValidator�B
     */
    private SchemaValidator schemaValidator = null;

    /**
     * XMLServletRequestDataBinder�𐶐�����B
     * 
     * @param target �R�}���h�I�u�W�F�N�g
     * @param oxmapper OXMapper
     * @param schemaValidator SchemaValidator
     * @param objectName �I�u�W�F�N�g��
     */
    public XMLServletRequestDataBinder(Object target, OXMapper oxmapper,
            SchemaValidator schemaValidator,String objectName) {
        super(target, objectName);
        this.oxmapper = oxmapper;
        this.schemaValidator = schemaValidator;
    }
    
    /**
     * XML�`���Œ�`���ꂽ���N�G�X�g�f�[�^���o�C���h����B
     * <p>
     * ���ۂ̃f�[�^�o�C���h�����́AOXMapper�ɏ������Ϗ�����B
     * </p>
     * <p>
     * SchemaValidator��DI����Ă���ꍇ�A�`���`�F�b�N�����s����B
     * </p>
     * 
     * @param request XML�`���Œ�`���ꂽ���N�G�X�g�f�[�^
     */
    @Override
    public void bind(ServletRequest request) {

        // XML�`���Œ�`���ꂽ���N�G�X�g�f�[�^�̓��̓X�g���[�����擾����
        InputStream in = null;

        try {
            in = request.getInputStream();

            // SchemaValidator��DI����Ă���ꍇ�A�`���`�F�b�N�����s����B
            if (schemaValidator != null) {

                Document doc = validate(in);

                // �`���`�F�b�N�ŃG���[�����������ꍇ�A�����𒆎~����
                if (getBindingResult().hasErrors()) {
                    return;
                }
                // �`���`�F�b�N�ς݂�DOM�c���[���g�p���āA�A���}�[�V���������s����
                oxmapper.unmarshal(doc, getTarget());
            } else {
                oxmapper.unmarshal(in, request.getCharacterEncoding(),
                        getTarget());
            }
        } catch (IOException e) {
            // �X�g���[���擾�̍ۂɁA���o�͗�O�����������ꍇ
            log.error("Request stream error.", e);
            throw new XMLRequestIOException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("Failed to close request stream.", e);
            }
        }

    }

    /**
     * ���N�G�X�g�f�[�^�̌`���`�F�b�N���s���B
     * <p>
     * ���ۂ̌`���`�F�b�N�����́ASchemaValidator�ɏ������Ϗ�����B
     * </p>
     * <p>
     * �`���`�F�b�N�G���[�����������ꍇ�A BindException�ɃG���[���i�[����B
     * </p>
     * 
     * @param in XML�f�[�^
     * @return Document DOM�c���[
     */
    protected Document validate(InputStream in) {

        if (in == null) {
            log.error("InputStream is null.");
            throw new IllegalArgumentException("InputStream is null.");
        }
        
        // �X�L�[�}��`�ɂ��`���`�F�b�N
        ErrorMessages errorMessages = new ErrorMessages();

        Document doc = schemaValidator.validate(in, getTarget(), errorMessages);

        BindingResult errors = this.getBindingResult();

        // �G���[���b�Z�[�W������ꍇ�ABindingResult�ɃG���[�����l�ߑւ���
        for (ErrorMessage errorMessage : errorMessages.getErrorMessages()) {
            
            // BindingResult�Ɋi�[���邽�߂̃G���[�𐶐�����
            FieldError fe = new FieldError(getObjectName(), errorMessage
                    .getField(), null, false, errors.resolveMessageCodes(
                    errorMessage.getKey(), errorMessage.getField()),
                    createReplaceValues(errorMessage.getField(), errorMessage
                            .getReplaceValues()), null);
            
            // BindingResult�ɃG���[���Z�b�g����
            errors.addError(fe);
        }

        return doc;

    }

    /**
     * �u��������𐶐�����B
     * <p>
     * �u��������̍ŏ��Ƀt�B�[���h����ǉ����A�Ō�ɃG���[�l���i�[����B
     * </p>
     * 
     * @param field �t�B�[���h�l
     * @param replaceValues �u��������
     * @return �z��̂O�ԖڂɃt�B�[���h��񂪕t�����ꂽ�u��������
     */
    protected String[] createReplaceValues(
            String field, String[] replaceValues) {

        // �t�B�[���h�l��null�̏ꍇ�͋󕶎��ɕϊ�����
        if (field == null) {
            field = "";
        }

        // �u��������null�܂��͋�̃��X�g�̏ꍇ�A
        // �t�B�[���h�l�݂̂��i�[�����u���������ԋp����
        if (replaceValues == null || replaceValues.length == 0) {
            return new String[] { field };
        }

        String[] resultReplaceValues = new String[replaceValues.length + 1];

        // �z��̃R�s�[
        System.arraycopy(replaceValues, 0, resultReplaceValues, 0,
                replaceValues.length);

        // �t�B�[���h���͒u��������̍ŏ��Ɋi�[����
        resultReplaceValues[0] = field;

        // �G���[�l�͒u��������̍Ō�Ɋi�[����
        resultReplaceValues[resultReplaceValues.length - 1] = replaceValues[0];

        return resultReplaceValues;
    }
}
