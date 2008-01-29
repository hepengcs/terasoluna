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

package jp.terasoluna.fw.web.rich.springmvc.bind.creator;

import javax.servlet.http.HttpServletRequest;
import jp.terasoluna.fw.oxm.mapper.OXMapper;
import jp.terasoluna.fw.oxm.xsd.SchemaValidator;
import jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.ServletRequestDataBinder;

/**
 * XML�`���Œ�`���ꂽ���N�G�X�g�f�[�^���o�C���h����N���X�𐶐�������������N���X�B
 * <p>
 * Spring�ɒ�`����Ă���ServletRequestDataBinder�������N���X�Ƃ��ĕԋp����B
 * </p>
 * <p>
 * �{�N���X���g�p����ꍇ�́A�o�C���h�������s��{@link jp.terasoluna.fw.oxm.mapper.OXMapper}�̎����N���X��DI���邱�ƁB<br>
 * XML�X�L�[�}���g�p�����`���`�F�b�N���s���ꍇ�́A{@link jp.terasoluna.fw.oxm.xsd.SchemaValidator}�̎����N���X��DI���邱�ƁB
 * </p>
 * <p>
 * �yBean��`�t�@�C���̐ݒ��i�`���`�F�b�N���s���ꍇ�j�z<br>
 * <code><pre>
 * &lt;bean id=&quot;oxmapper&quot; class=&quot;jp.terasoluna.fw.oxm.mapper.castor.CastorOXMapperImpl&quot; /&gt;
 * 
 * &lt;bean id=&quot;schemaValidator&quot; class=&quot;jp.terasoluna.fw.oxm.xsdvalidator.impl.SchemaValidatorImpl&quot; /&gt;
 * 
 * &lt;bean id=&quot;xmlDataBinderCreator&quot;
 *   class=&quot;jp.terasoluna.fw.web.rich.springmvc.bind.creator.XMLServletRequestDataBinderCreator&quot;&gt;
 *   &lt;property name=&quot;oxmapper&quot;&gt;&lt;ref local=&quot;oxmapper&quot;/&gt;&lt;/property&gt;
 *   &lt;property name=&quot;schemaValidator&quot;&gt;&lt;ref local=&quot;schemaValidator&quot;/&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre></code>
 * </p>
 * <p>
 * �{�N���X�̎g�p���@�́A{@link jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController}���Q�Ƃ��邱�ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.TerasolunaController
 * @see jp.terasoluna.fw.web.rich.springmvc.bind.XMLServletRequestDataBinder
 */
public class XMLServletRequestDataBinderCreator implements
        ServletRequestDataBinderCreator, InitializingBean {
    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory
            .getLog(XMLServletRequestDataBinderCreator.class);

    /**
     * OXMapper�B
     */
    private OXMapper oxmapper = null;

    /**
     * SchemaValidator�B
     */
    private SchemaValidator schemaValidator = null;

    /**
     * XML�`���̃��N�G�X�g�f�[�^�ɑΉ�����ServletRequestDataBinder�����N���X��ԋp����B
     * 
     * @param request XML�`���̃��N�G�X�g
     * @param command �R�}���h�I�u�W�F�N�g
     * @param requestName ���N�G�X�g��
     * @return XMLServletRequestDataBinder
     */
    public ServletRequestDataBinder create(HttpServletRequest request,
            Object command, String requestName) {
        return new XMLServletRequestDataBinder(command, oxmapper,
                schemaValidator, requestName);
    }
    
    /**
     * DI�R���e�i�N�����A�{�N���X���C���X�^���X�����ꂽ����ɌĂ΂�郁�\�b�h�B
     * OXMapper�iXML�`���̃f�[�^���o�C���h����N���X�j���ݒ肳��Ă��Ȃ��ꍇ�A ��O�𓊂���B
     */
    public void afterPropertiesSet() {
        // XMLDataBinder���ݒ肳��Ă��Ȃ��ꍇ�A��O�Ƃ���
        if (oxmapper == null) {
            log.error("OXMapper isn't set in ServletRequestDataBinder. "
                    + "Check Spring Bean definition file.");
            throw new IllegalStateException(
                    "OXMapper isn't set in ServletRequestDataBinder. "
                    + "Check Spring Bean definition file.");
        }
    }

    /**
     * OXMapper���擾����B
     * 
     * @return OXMapper OXMapper
     */
    public OXMapper getOxmapper() {
        return oxmapper;
    }

    /**
     * OXMapper��ݒ肷��B
     * 
     * @param oxmapper OXMapper
     */
    public void setOxmapper(OXMapper oxmapper) {
        this.oxmapper = oxmapper;
    }

    /**
     * SchemaValidator���擾����B
     * 
     * @return SchemaValidator SchemaValidator
     */
    public SchemaValidator getSchemaValidator() {
        return schemaValidator;
    }

    /**
     * SchemaValidator��ݒ肷��
     * 
     * @param schemaValidator SchemaValidator
     */
    public void setSchemaValidator(SchemaValidator schemaValidator) {
        this.schemaValidator = schemaValidator;
    }
            
}
