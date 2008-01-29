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

package jp.terasoluna.fw.web.rich.springmvc.controller;

import java.lang.reflect.Type;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.terasoluna.fw.service.rich.BLogic;
import jp.terasoluna.fw.util.GenericsUtil;
import jp.terasoluna.fw.util.ProxyUtil;
/**
 * BLogic�C���^�t�F�[�X�����N���X���s�p���N�G�X�g�R���g���[���B
 *
 * <p>
 * <code>DispatcherServlet</code>����N������A
 * <code>BLogic</code>�C���^�t�F�[�X�����������Ɩ����W�b�N�N���X�����s����B
 * </p>
 *
 * <p>
 * �Ɩ����W�b�N�̎��s�́A�{�N���X�����s���邪�A
 * �g�����U�N�V�����Ǘ��̐Ӗ��́ABLogic���󂯎��B
 * �Ɩ����W�b�N�N�����̈�����JavaBean�́A���N�G�X�g���o�C���h����JavaBean�i�R�}���h�j�ƂȂ�B
 * </p>
 *
 * <p>
 * �����Ƃ��āA�T�u�N���X�̍쐬�͕s�v�ł���A�{�N���X�𒼐ڗ��p����B
 * �������A�v���W�F�N�g���Ƃ̗v���ɂ���āA
 * �Ɩ��O�����A�㏈�����K�v�ȏꍇ�́A�v���W�F�N�g�P�ʂŃT�u�N���X���쐬����B
 * </p>
 * 
 * <p>
 * ���炩����TERASOLUNA���񋟂��Ă���
 * ���N�G�X�g�R���g���[���̃x�[�X��`
 * xmlRequestBLogicExecuteController�܂���
 * queryRequestBLogicExecuteController���p������Bean��`���s�����ƁB
 * �܂��A�ȉ��̃v���p�e�B��ݒ肷�邱�ƁB
 * </p>
 * 
 * <p>
 *   <table border="1" CELLPADDING="8">
 *     <th>������</th>
 *     <th>�K�{</th>
 *     <th>����</th>
 *     
 *     <tr>
 *       <td align=center><b>blogic</b></td>
 *       <td>��</td>
 *       <td>�N������BLogic��Bean��</td>
 *     </tr>
 *  
 *  </table>
 * 
 * </p>
 * 
 * �y<code>Bean��`�t�@�C��</code>�̐ݒ��z<br>
 * <code><pre>
 *   &lt;bean name="/secure/blogic/max.do"
 *       parent="xmlRequestBLogicExecuteController" scope="singleton"&gt;
 *     &lt;property name="blogic" ref="maxBLogic"/&gt;
 *   &lt;/bean&gt;
 *   �� xmlRequestBLogicExecuteController�́A
 *   BLogic���N�����郊�N�G�X�g�R���g���[���̒��ے�`�B
 *   �ڍׂ́ATerasolunaController���Q�ƁB
 * </pre></code>
 * </p>
 * 
 *�@@see jp.terasoluna.fw.service.rich.BLogic
 *
 * 
 */
public class BLogicController extends TerasolunaController<Object, Object> {
    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(BLogicController.class);
    
    /**
     * Bean��`�t�@�C���ɐݒ肳��Ă���BLogic�����N���X�B
     */
    protected BLogic<Object, Object> blogic = null;

    /**
     * BLogic�����N���X��ݒ肷��B
     * @param blogic BLogic�����N���X�B
     */
    public void setBlogic(BLogic<Object, Object> blogic) {
        this.blogic = blogic;
    }
    
    /**
     * BLogic�����N���X��ݒ肷��B
     * 
     * @param blogic
     *            BLogic�����N���X�B
     */
    public void setBusinessLogic(BLogic<Object, Object> blogic) {

        this.blogic = blogic;
    }
    
    /**
     * DI�R���e�i�ɂ���ăC���X�^���X�����ꂽ����ɌĂ΂�郁�\�b�h�B
     * �K�{������Null�`�F�b�N���s���B
     */
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        if (this.blogic == null) {
            String message = "Cannot create BLogicController "
                + "without blogic(BLogic) being set. "
                + "Check Bean definition file.";
            log.error(message);
            throw new IllegalStateException(message);
        }
    }

    /**
     * �R�}���h�I�u�W�F�N�g�̌^���擾����B
     * {@link jp.terasoluna.fw.service.rich.BLogic}�N���X��
     * execute���\�b�h�̈����̌^��Ԃ��B
     * execute���\�b�h���I�[�o�[���[�h����Ă���ꍇ�A��O��Ԃ��B
     * @return �R�}���h�I�u�W�F�N�g�̌^�B
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Type getCommandType() {
        return GenericsUtil.resolveParameterizedClass(BLogic.class, ProxyUtil
                .getTargetClass(blogic))[0];
    }
    
    /**
     *  BLogic#execute()�𒼐ڌĂяo���A�Ɩ����W�b�N�����s����B
     *
     * @param command �R�}���h�I�u�W�F�N�g
     * @return ���f���I�u�W�F�N�g
     * @throws Exception �Ɩ����W�b�N����X���[���ꂽ��O
     */
    @Override
    protected Object executeService(Object command) throws Exception {
        return this.blogic.execute(command);
    }
}
