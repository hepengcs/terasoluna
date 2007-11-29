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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.exception.SystemException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �P���t�H���[�h�A�N�V�����B
 *
 * <p>
 *  ActionEx�̋@�\�i�J�ڃ��O�o�́E�g�����U�N�V�����g�[�N���`�F�b�N�j���p�����A
 *  JSP�Ȃǂփt�H���[�h����A�N�V�����ł���B
 *  Struts���񋟂��Ă���ForwardAction�Ɠ��l��
 *  struts-config.xml��&lt;action&gt;�v�f��
 *  parameter�����Ɏw�肵����Ƀt�H���[�h����B
 *  parameter�������ݒ肳��Ă��Ȃ��ꍇ�A
 *  �Œ�̘_���t�H���[�h���usuccess�v�ŃA�N�V�����t�H���[�h���擾����B
 *  �t�H���[�h�悪�ݒ肳��Ă��Ȃ��ꍇ�A
 *  SC_NOT_FOUND�i404�j�G���[��Ԃ��B
 *  *.jsp�t�@�C���ւ̒��ڃA�N�Z�X���֎~����Ă���ꍇ�ɁA
 *  JSP���Ɩ��������o���P���ɕ\������ɂ�
 *  ���̃A�N�V������p����struts-config.xml
 *  �ɃG���g�����쐬����K�v������B
 *  ActionEx#execute()�ōs���Ă��鏈���́A
 *  �����ł��p�������B
 * </p>
 * <p>
 *  parameter�����ł́A���W���[�����΃p�X�̎w�肵���s�Ȃ��Ȃ��B
 *  ���W���[�����ׂ�J�ڂ⃊�_�C���N�g���s�Ȃ������ꍇ�́A
 *  &lt;forward&gt;�v�f���g�p���邱�ƁB
 * </p>
 *
 * <p>Bean��`�t�@�C���y��struts-config.xml�̋L�q����ȉ��Ɏ����B</p>
 *
 * ��:<br>
 * <p>
 * <strong>Bean��`�t�@�C���̐ݒ�</strong>
 *  <code><pre>
 *  &lt;bean name="/foo" scope="prototype"
 *      <strong>class="jp.terasoluna.fw.web.struts.actions.ForwardAction"</strong>&gt
 *  &lt;/bean&gt
 *  </pre></code>
 * </p>
 * <p>
 * <strong>struts-config.xml�̐ݒ�</strong>
 * <code><pre>
 *  &lt;action path="/foo"
 *          parameter="/foo.jsp"&gt;
 *  &lt;/action&gt;
 * </pre></code>
 * �܂���
 * <code><pre>
 *  &lt;action path="/foo"
 *          parameter="/foo.jsp"&gt;
 *    &lt;forward name="success" path="/foo.jsp" module="/sub1" redirect="true"&gt;
 *  &lt;/action&gt;
 * </pre></code>
 * </p>
 *
 */
public class ForwardAction extends ActionEx {

    /**
     * ���O�N���X�B
     */
    private static Log log
              = LogFactory.getLog(ForwardAction.class);

    /**
     * �G���[�y�[�W�i404�j�J�ڎ��s�������G���[�R�[�h�B
     */
    private static final String FORWARD_ERRORPAGE_ERROR =
        "error.forward.errorpage";

    /**
     * �Œ�̘_���t�H���[�h���B
     */
    private static final String FORWARD_SUCCESS = "success";

    /**
     * parameter�����ɐݒ肳�ꂽ�J�ڐ��
     * �A�N�V�����t�H���[�h�ɃZ�b�g���ĕԂ��B
     * parameter�������ݒ肳��Ă��Ȃ��ꍇ�A
     * �J�ڐ�_���t�H���[�h��"success"��
     * �A�N�V�����t�H���[�h���擾���ĕԂ��B
     * �ǂ�����ݒ肳��Ă��Ȃ��ꍇ�́A�i404�j�G���[��Ԃ��B
     *
     * @param mapping �A�N�V�����}�b�s���O
     * @param form �A�N�V�����t�H�[��
     * @param req HTTP���N�G�X�g
     * @param res HTTP���X�|���X
     * @return �J�ڐ�̃A�N�V�����t�H���[�h
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest req,
                                HttpServletResponse res) {
        if (log.isDebugEnabled()) {
            log.debug("doExecute() called.");
        }

        // parameter�����i�t�H���[�h��j���擾
        String path = mapping.getParameter();

        // �A�N�V�����t�H���[�h�𐶐�
        ActionForward retVal = null;

        if (path == null) {

            // ActionMapping����ActionForward���擾
            retVal = mapping.findForward(FORWARD_SUCCESS);

            // ActionFoward���ݒ肳��Ă��Ȃ��ꍇ
            if (retVal == null) {
                // parameter�����Aforward�v�f���Ƃ��ɐݒ肳��Ă��Ȃ��ꍇ�A
                // �i404�j�G���[��ԋp����
                try {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e) {
                    log.error("Error page(404) forwarding failed.");

                    throw new SystemException(e, FORWARD_ERRORPAGE_ERROR);
                }
                return null;
            }
			return retVal;
        }

        retVal = new ActionForward(path);

        return retVal;
    }
}
