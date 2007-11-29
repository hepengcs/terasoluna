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

package jp.terasoluna.fw.web.struts.actions;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.web.thin.BlockageControlFilter;
import jp.terasoluna.fw.web.thin.ServerBlockageControlFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * �t�H���[�h��̐U�蕪���������s���B
 *
 * <p>
 *  Struts���񋟂��Ă���DispatcherAction�A
 *  LookUpDispatchAction�Ƃ͈قȂ�A
 *  ���N�G�X�g�p�����[�^�̒l��p���āA�J�ڐ悪���肳���B<br>
 *  ���̋@�\�ŗ��p���郊�N�G�X�g�p�����[�^�L�[�́A���L�̗D�揇�ʂŌ��肳���B
 *  <ol>
 *   <li>event�v���p�e�B�Ŏw�肳�ꂽ�l</li>
 *   <li>event�v���p�e�B���w�肳��Ă��Ȃ��ꍇ�A
 *       &quot;event&quot;��
 *       ���N�G�X�g�p�����[�^�̃L�[�Ƃ��Ď擾�ł���l</li>
 *  </ol>
 *
 *  ���L�ɋL������`�t�@�C����
 *  ���N�G�X�g�p�����[�^�̃L�[��custom-event�ɐݒ肷���ł���B
 * </p>
 * <p>
 * <strong>Bean��`�t�@�C����event�v���p�e�B�ɂ��A
 *  �J�ڐ�ɗp�����郊�N�G�X�g�p�����[�^�L�[��custom-event�ɐݒ肷��</strong>
 * </p>
 * <p>
 * <strong>Bean��`�t�@�C���̐ݒ�</strong>
 *  <code><pre>
 *  &lt;bean name="/dispatch" scope="prototype"
 *    class="jp.terasoluna.fw.web.struts.actions.DispatchAction"&gt;
 *    <strong>&lt;property name="event"&gt;
 *      &lt;value&gt;custom-event&lt;/value&gt;
 *    &lt;/property&gt;</strong>
 *  &lt;/bean&gt;
 *  </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xml�̐ݒ�</strong>
 * <code><pre>
 *  &lt;action path="/dispatch"
 *    name="_sampleForm"
 *    scope="session"
 *    input="/prev.jsp"&gt;
 *    &lt;forward name="regist" path="/userRegist.do"/&gt;
 *    &lt;forward name="search" path="/userSearch.do"/&gt;
 *    &lt;forward name="update" path="/userUpdate.do"/&gt;    
 *    &lt;forward name="default" path="/prev.do"/&gt;
 *  &lt;/action&gt;
 * </pre></code>
 *  �����ŁAevent�p�����[�^���ȗ����ꂽ�ꍇ�A
 *  ���N�G�X�g�p�����[�^�̃L�[��&quot;event&quot;�ƂȂ�B
 * </p>
 * <p>
 *  ���ɁA��L�Ō��肳�ꂽ�L�[��p���ă��N�G�X�g�p�����[�^��������
 *  �l���擾����B���̌��ʁA�ȉ��̗D�揇�ʂőJ�ڐ敶���񂪌��肳���B
 *  <ol>
 *   <li>�������ꂽ���N�G�X�g�p�����[�^�̒l�̂����A
 *       �擪��&quot;forward_&quot;�����Ă������</li>
 *   <li>�Y�����郊�N�G�X�g�p�����[�^�̒l��������Ȃ��ꍇ�A
 *       <strong>���N�G�X�g�p�����[�^�̃L�[���̂���</strong>�̐擪��
 *       &quot;forward_&quot;���t���Ă������</li>
 *   <li>��L�Ƃ��Ɍ�����Ȃ��ꍇ�A�J�ڐ��&quot;default&quot;�ɂȂ�</li>
 *   <li>�܂��Aevent=&quot;XXXX&quot;�A&quot;forward_XXXX&quot;�Ƃ�����
 *       ���݂����Ȃ��s���ȑJ�ڐ悪�w�肳�ꂽ�ꍇ���A&quot;default&quot;��
 *       �J�ڐ�Ƃ���B</li>
 *  </ol>
 *
 *  ��L�̌��ʁA�擪��&quot;forward_&quot;��
 *  �t���Ă�����̂́A��菜���ꂽ������&quot;#input&quot;
 *  �ł������Ƃ��Astruts-config.xml��input����
 *  �i��L��ł�/logon.jsp�j���J�ڐ�ƂȂ�B<br>
 *  &quot;#input&quot;�ł͂Ȃ��Ƃ��A�擪��
 *  &quot;forward_&quot;����菜���������񂪑J�ڐ敶����ƂȂ�B
 * </p>
 * <p>
 * <strong>JSP�ł̋L�q��</strong>
 * </p>
 * <p>
 * <code><pre>
 * �E�E�E
 *  &lt;html:submit property="forward_regist" value=" �o�^ " /&gt;
 *  &lt;html:submit property="forward_search" value=" �Č��� " /&gt;
 *  &lt;html:image  property="forward_update" src="./image/update.gif" /&gt;  
 * �E�E�E
 * </pre></code>
 * �@�i�����ł̓��N�G�X�g�p�����[�^��<strong>�l��forward_...</strong>
 *  �����݂��Ȃ��ꍇ�j<br>
 *  ��ʏ�́u�o�^�v�{�^�����������ꂽ�Ƃ��A���̑J�ڐ��regist
 *  �ɂȂ�A�u�Č����v�{�^�����������ꂽ�Ƃ��A���̑J�ڐ��search
 *  �ɂȂ�A�X�V�摜�{�^�����������ꂽ�Ƃ��A���̑J�ڐ��update
 *  �ɂȂ�B
 * </p>
 *
 */
public class DispatchAction extends ActionEx {

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(DispatchAction.class);

    /**
     * ���N�G�X�g�p�����[�^�ɐU�蕪���w�����Ȃ������ꍇ�́A
     * �f�t�H���g�̑J�ڐ�̘_���t�H���[�h���B
     */
    private static final String FORWARD_DEFAULT = "default";

    /**
     * �U�蕪���w�������ʂ��邽�߂́A
     * ���N�G�X�g�p�����[�^�̃L�[�̃v���t�B�b�N�X�B
     */
    private static final String FORWARD_PREFIX = "forward_";

    /**
     * �J�ڐ��\���v���p�e�B���B
     */
    private String event = null;

    /**
     * �J�ڐ��\���v���p�e�B����ݒ肷��B
     *
     * @param value �J�ڐ�v���p�e�B��
     */
     public void setEvent(String value) {
         this.event = value;
     }

    /**
     * �t�H���[�h��̐U�蕪���������s���B
     * <p>
     *   �t�H���[�h���U�蕪������A�T�[�o�ǒʉ߃t���O��
     *   �폜����B<br>
     *   ���ۂ̑J�ڐ�́AdoDetamineForward()�����肵�Ă���B
     * </p>
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param req <code>HTTP</code>���N�G�X�g
     * @param res <code>HTTP</code>���X�|���X
     * @return �J�ڐ���
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                          ActionForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res) {
        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }
        // ���N�G�X�g�ɃL�����Z���t���O���ݒ肳��Ă��邱�Ƃ��m�F���A
        // cancelled()���\�b�h�����s���邩�ǂ��������肷��B
        if (isCancelled(req)) {
            ActionForward af = cancelled(mapping, form, req, res);
            if (af != null) {
                return af;
            }
        }

        if (event == null) {
            // event���w��Ȃ��̎��A"event"���f�t�H���g�Ƃ��Đݒ肷��B
            event = "event";
        }

        String forward = doDetermineForward(req.getParameterMap(), event);

        ActionForward actionForward = null;
        if ("#input".equalsIgnoreCase(forward)) {
            actionForward = new ActionForward(mapping.getInput());
        } else {
            actionForward = mapping.findForward(forward);
        }

        // �t�H���[�h�悪������Ȃ��ꍇ�A"default" �Ŏw�肳��Ă���
        // �A�N�V�����t�H���[�h��ԋp����B
        if (actionForward == null) {
            if (log.isWarnEnabled()) {
                log.warn("forward name[" + forward + "] is invalid by user request.");
            }
            actionForward = mapping.findForward(FORWARD_DEFAULT);
        }

        // �t�H���[�h��ŕǃ`�F�b�N����L���Ƃ��邽��
        // THRU_FILTER�t���O���폜����
        // �T�[�o��
        req.removeAttribute(ServerBlockageControlFilter
            .SERVER_BLOCKAGE_THRU_KEY);
        // �Ɩ���
        req.removeAttribute(BlockageControlFilter.BLOCKAGE_THRU_KEY);

        if (log.isDebugEnabled()) {
            log.debug("forward = " + forward + " (" + ((actionForward == null)
                ? "null" : actionForward.getPath()) + ")");
        }
        return actionForward;
    }

    /**
     * ���N�G�X�g�p�����[�^�Ɋ�Â��ăt�H���[�h����f�B�X�p�b�`����B
     * params��null�̏ꍇ�́A�f�t�H���g�̕������ԋp����B
     *
     * @param params ���N�G�X�g�p�����[�^�i�}�b�v�`���j
     * @param event �A�N�V�����}�b�s���O�Ɏw�肳�ꂽ�C�x���g��
     * @return �U�蕪����̘_���t�H���[�h��
     */
    protected String doDetermineForward(Map params, @SuppressWarnings("hiding") String event) {
        if (params != null) {
            if (exists(params, event)) {
                String[] eventValues = (String[]) params.get(event);
                for (int i = 0; i < eventValues.length; i++) {
                    if (eventValues[i].startsWith(FORWARD_PREFIX)) {
                        return eventValues[i].substring(FORWARD_PREFIX.length());
                    }
                }
            }
            Iterator iter = params.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                if (key.startsWith(FORWARD_PREFIX)) {
                    String forward = key.substring(FORWARD_PREFIX.length());
                    if(forward.endsWith(".x") || forward.endsWith(".y")){
                        forward = forward.substring(0, forward.length() - 2);
                    }
                    return forward;
                }
            }
        }
        return FORWARD_DEFAULT;
    }


    /**
     * ���N�G�X�g�p�����[�^�ɁAname�Ŏw�肵�����̂̃p�����[�^��
     * ���݂��Ă��邩�𔻒肷��B
     *
     * @param params ���N�G�X�g�p�����[�^�i�}�b�v�`���j
     * @param name ���N�G�X�g�p�����[�^��
     * @return ���N�G�X�g�p�����[�^�������݂��Ă���Ȃ�� <code>true</code>
     */
    protected boolean exists(Map params, String name) {
        return params.containsKey(name);
    }

    /**
     * ���N�G�X�g�ɃL�����Z���t���O���ݒ肳��Ă���ꍇ�̑J�ڐ��
     * ���肷��B���ӓ_�Ƃ��āAActionForward��null�Ƃ��ĕԋp����邽�߁A
     * �L�����Z�����̑J�ڐ�́A
     * ���̃N���X���p�������N���X�̃I�[�o���C�h���\�b�h�Ŏ�������K�v������B
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @return �A�N�V�����t�H���[�h
     */
    protected ActionForward cancelled(ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        return null;
    }

}
