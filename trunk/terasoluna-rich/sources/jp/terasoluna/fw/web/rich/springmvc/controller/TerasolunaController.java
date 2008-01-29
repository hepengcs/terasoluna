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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.terasoluna.fw.util.ClassUtil;
import jp.terasoluna.fw.web.rich.context.support.RequestContextSupport;
import jp.terasoluna.fw.web.rich.springmvc.Constants;
import jp.terasoluna.fw.web.rich.springmvc.bind.creator.ServletRequestDataBinderCreator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

/**
 * �T�[�r�X�w�̃N���X�����s���郊�N�G�X�g�R���g���[�����ۃN���X�B
 *
 * <p>�{�R���g���[���́A<code>DispachServlet</code>����N������A
 * �ȉ��̋@�\��񋟂���B
 * </p>
 * <ul>
 * <li>HTTP���N�G�X�g��JavaBean�i�R�}���h�j�Ƀo�C���h</li>
 * <li>���̓`�F�b�N���s</li>
 * <li>POJO�̋Ɩ����W�b�N�N���X���s</li>
 * <li>JavaBan(���f��)��View��ԋp</li>
 * </ul>
 * 
 * <p>
 * �{�N���X�́A���ۃN���X�ł���B
 * �Ɩ��J���҂��A���N�G�X�g���Ƃɖ{�N���X�̎����N���X���쐬���邱�ƁB
 * �{�N���X�́A�^�p�����[�^�𗘗p���Đ錾����Ă���B
 * �^�p�����[�^P�́AHTTP���N�G�X�g���o�C���h����JavaBean�i�R�}���h)�A
 * �^�p�����[�^R�́AView�ɔ��f���������ێ�����JavaBean(���f��)�̌^������킵�Ă���B
 * �����N���X�錾���ɁA�^�p�����[�^�Ɏ��ۂ̌^���w�肵�A
 * ���ۃ��\�b�h�̌^�ϐ����w�肵���^�ɂ��킹�Ď������邱�Ƃ�
 * �����N���X�̌^�̈��S����ۏႳ���Ă���B
 * �^�p�����[�^��P,R�ɂ́A�K���������Ȃ��R���X�g���N�^������JavaBean���w�肷�邱�ƁB
 * �C���^�t�F�[�X�A���ۃN���X�A�������Ȃ��R���X�g���N�^�������Ȃ��N���X�̎w��͏o���Ȃ��B
 * </p>
 * 
 * <p>
 * <u>�T�[�r�X�w�̃N���X�̎��s</u><br>
 * �T�[�r�X�w�̃N���X�́ADI�R���e�i�𗘗p���Ė{�R���g���[���ɐݒ肳��邱�Ƃ�O��Ƃ���̂ŁA
 * �Ɩ��J���҂̓T�[�r�X�w�̃N���X�𑮐��Ƃ��ėp�ӂ��Asetter/getter���\�b�h��݂��邱�ƁB
 * �܂��A�Ăяo�������́A���ۃ��\�b�h�ł���executeService���\�b�h�Ɏ������邱�ƁB
 * </p>
 * 
 * <p> 
 * �Ɩ������ŗ�O�����������ꍇ�A
 * Spring MVC�̗�O�����@�\�Ńn���h�����O�����B
 * </p>
 *
 * <p>
 * <u>�o�C���h����</u><br>
 * ServletRequestDataBinder�p���N���X���s���B
 * ServletRequestDataBinder�𐶐����邽�߂̃N���X�ł���DataBinderCreator��
 * DI�R���e�i�𗘗p���Ė{�R���g���[���ɐݒ肷�邱�ƁB
 * DataBinderCreator�́A���N�G�X�g�̌`��(XML or Query)�ɂ��g���킯��B
 * �o�C���h�����ŁA�o�C���h�G���[�����������ꍇ�ABindException���X���[����A
 * Spring MVC�̗�O�����@�\�Ńn���h�����O�����B
 * </p>
 * 
 * <p>
 * <u>���̓`�F�b�N����</u><br>
 * Validator�C���^�t�F�[�X�����N���X���s���B
 * Validator�C���^�t�F�[�X�����N���X��
 * DI�R���e�i�𗘗p���Ė{�R���g���[���ɐݒ肷�邱�ƁB
 * ���̓`�F�b�N�����ŁA���̓`�F�b�N�G���[�����������ꍇ�A
 * BindException���X���[����A
 * Spring MVC�̗�O�����@�\�Ńn���h�����O�����B
 * </p>
 * 
 * <p>
 * <u>�r���[���̐ݒ�</u><br>
 * <code>DispachServlet</code>�ł̃r���[�����Ɏg�p�����r���[����
 * {@link #handle(HttpServletRequest, HttpServletResponse, Object, BindException)}
 * ���\�b�h���Őݒ肵�Ă���B
 * �r���[���́A�ȉ��̏��ԂŌ��肳���B
 * <ul>
 * <li>�{�R���g���[����{@link #viewName}���������͂���Ă���΁A���̑����l</li>
 * <li>�{�R���g���[����{@link #useRequestNameView}������true�Ȃ�΁A
 *     �h/�h�{�u���N�G�X�g���v�@</li>
 * <li>��L�ɂ��Ă͂܂�Ȃ��ꍇ�A�󕶎��@��</li>
 * </ul>
 * �����TERASOLUNA�̏����ݒ�ł́A�r���[���ɋ󕶎��������Ă����
 * Castor�r���[���g�p����d�l�ɂȂ��Ă���B
 * </p>
 * 
 * <p>
 * �g�����U�N�V�����Ǘ��̐Ӗ��́A�T�[�r�X�w���󂯎��B
 * �i�������AAOP�ɂ��錾�I�g�����U�N�V�����𗘗p����̂ŁA
 * �T�[�r�X�w�̃N���X���ӎ�����K�v�͂Ȃ��B�j
 * </p>
 *
 * <p>
 * �쐬���������N���X�𗘗p����ɂ́ABean�̒�`���s�����ƁB
 * </p>
 *
 * <p>
 * �y<code>xxx-servlet.xml</code>�̒�`��z<br>
 * <code><pre>
 *   &lt;bean name="/secure/blogic/sum.do"
 *       class="jp.terasoluna.sample2.web.controller.SumController"
 *       parent="xmlRequestController" singleton ="false"&gt;
 *     &lt;property name="sumService" ref="sumService"/&gt;
 *     &lt;property name="ctxSupport" ref="ctxSupport"/&gt;  
 *     &lt;property name="dataBinderCreator" ref="xmlDataBinderCreator"/&gt;
 *     &lt;property name="validator" ref="sumValidator"/&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 * </p>
 * 
 * <p>
 * �ȉ��̃v���p�e�B��ݒ肷�邱�ƁB
 *   <table border="1" CELLPADDING="8">
 *     <th>������</th>
 *     <th>�K�{</th>
 *     <th>����</th>
 *  
 *     <tr>
 *       <td align=center><b>ctxSupport</b></td>
 *       <td>��</td>
 *       <td>������T�|�[�g�N���X�B</td>
 *     </tr>
 *     
 *     <tr>
 *       <td align=center><b>dataBinderCreator</b></td>
 *       <td>��</td>
 *       <td>���N�G�X�g�f�[�^�o�C���_�����N���X�B</td>
 *     </tr>
 *   
 *     <tr>
 *       <td align=center><b>validator</b></td>
 *       <td>�~</td>
 *       <td>���̓`�F�b�N�N���X�B</td>
 *     </tr> 
 *     
 *     <tr>
 *       <td align=center><b>viewName</b></td>
 *       <td>�~</td>
 *       <td>�r���[���B
 *           �f�t�H���g�ȊO��View�Z�p(Velocity,�o�C�i���f�[�^, PDF, Excel��)��
 *           ���p����ꍇ�ɐݒ肷��B</td>
 *     </tr>
 *     <tr>
 *       <td align=center><b>useRequestNameView</b></td>
 *       <td>�~</td>
 *       <td>�r���[���Ƀ��N�G�X�g�����g�p���邩���f����t���O�B
 *           �f�t�H���g��false�B���N�G�X�g�����g�p�������ꍇ�Atrue��ݒ肷��B
 *           true���ݒ肳��Ă��Ă��AviewName�����͂���Ă����viewName��
 *           �Ή�����r���[���g�p�����B
 *       </td>
 *     </tr> 
 *  </table>
 *  ���̂ق��Ɏ��s����T�[�r�X�w�̃N���X���v���p�e�B�ɐݒ肷�邱�ƁB
 *  �R�}���h�N���X�����w�肷��Acommandclass�����̎w��͕s�v�ł���B
 *  �R�}���h�N���X�̌^�́A�{�N���X�̌^�p�����[�^���A�����I�ɔ��f���邽�߂ł���B
 * </p>
 * 
 * 
 * <p>
 * �܂��A������T�|�[�g�N���X�A���N�G�X�g�f�[�^�o�C���_�����N���X�̐ݒ�́A
 * �����̃R���g���[����`�œ���ɂȂ�B
 * ����āA����Bean��`�����炩���ߐݒ肵�Ă����A
 * �����N���X��Bean��`�́A����Bean��`���p�����čs����
 * �ݒ�t�@�C���̋L�q���V���v���ɂȂ�B
 * </p>
 * 
 * <p>
 * �y����Bean��`�𗘗p����<code>xxx-servlet.xml</code>�̒�`��z<br>
 * <code><pre>
 *   &lt;!-- �R���g���[���̒���Bean��` --&gt;
 *   &lt;bean id="xmlRequestController" abstract="true"&gt;
 *     &lt;property name="cxtSupport" ref="ctxSupport"/&gt;  
 *     &lt;property name="dataBinderCreator" ref="xmlDataBinderCreator"/&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;!-- ����Bean��`���p�������R���g���[���̒�` --&gt;
 *   &lt;bean name="/secure/blogic/sum.do"
 *       class="jp.terasoluna.sample2.web.controller.SumController"
 *       parent="xmlRequestController" scope="singleton"&gt;
 *     &lt;property name="sumService" ref="sumService"/&gt;
 *     &lt;property name="validator" ref="sumValidator"/&gt;
 *   &lt;/bean&gt;
 * </code></pre>
 * </p>
 * 
 * <p>
 * TERASOLUNA�ł́A
 * ���炩���߂������̒���Bean��`���p�ӂ���Ă���B�K�v�ɉ����ė��p���邱�ƁB
 *   <table border="1" CELLPADDING="8">
 *     <th>����Bean��</th>
 *     <th>��M���N�G�X�g</th>
 *     <th>�N���T�[�r�X</th>
 *  
 *     <tr>
 *       <td align=center><b>xmlRequestController</b></td>
 *       <td>XML�`��</td>
 *       <td>POJO</td>
 *     </tr>
 *     
 *     <tr>
 *       <td align=center><b>queryRequestController</b></td>
 *       <td>�N�G���`��</td>
 *       <td>POJO</td>
 *     </tr>
 *   
 *     <tr>
 *       <td align=center><b>xmlRequestBLogicExecuteController</b></td>
 *       <td>XML�`��</td>
 *       <td>BLogic</td>
 *     </tr> 
 *     
 *     <tr>
 *       <td align=center><b>queryRequestBLogicExecuteController</b></td>
 *       <td>�N�G���`��</td>
 *       <td>BLogic</td>
 *     </tr>   
 *  </table>
 * 
 * </p>
 *   
 * </pre></code>
 * </p>
 *
 * <p>
 * �K�p��V�X�e���ɓ��������Ɩ��O�����A�㏈����ǉ��������ꍇ
 * �i�Ⴆ�΋Ɩ������p�����[�^��Ɩ��������ʂ�
 * �Z�b�V�����̏��𔽉f�������ꍇ���j�A
 * preService�ApostService���\�b�h���I�[�o�[���C�h���������N���X���쐬���A
 * ���p���邱�ƁB<br>
 * �T�u�N���X�Œ��ۃN���X���g�p����ꍇ�A{@link #getCommandType()}���\�b�h��
 * �I�[�o�[���C�h����K�v������B
 * </p>
 * 
 * <p>
 * �N������T�[�r�X�w�̃N���X�Ƃ���POJO�ł͂Ȃ��A
 * BLogic�C���^�t�F�[�X�����N���X�𗘗p���邱�Ƃ��\�ł���B
 * �ڍׂ�BLogicController���Q�Ƃ̂��ƁB
 * </p>
 * 
 * 
 * @param <P> �R�}���h�N���X�B�T�[�r�X�w�̃N���X�֓n���N���X�B
 * @param <R> ���f���N���X�B�T�[�r�X�w�̃N���X����Ԃ����N���X�B
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController
 * 
 */
public abstract class TerasolunaController<P, R>
        extends AbstractCommandController implements InitializingBean {
    
    /**
     * �^�p�����[�^<P, R>���`���Ă���R���g���[���N���X�B
     */
    protected Class parameterizedControllerClass = TerasolunaController.class;

    /**
     * ���O�N���X�B
     */
    private static Log log = LogFactory.getLog(TerasolunaController.class);
    
    /**
     * ������������T�|�[�g���W�b�N�N���X�B
     */
    protected RequestContextSupport ctxSupport = null;
    
    /**
     * ServletRequestDataBinder�p���N���X�𐶐�����N���X�B
     */
    protected ServletRequestDataBinderCreator dataBinderCreator = null;
    
    /**
     * �f�t�H���g�ȊO�̃r���[�Z�p(Velocity,�o�C�i���f�[�^, PDF, Excel)��
     * ���p����ꍇ�ɐݒ肷��r���[���B
     */
    protected String viewName = null;
    
    /**
     * �r���[���Ƀ��N�G�X�g�����g�p���邩���f����t���O�B
     * <p>true��ݒ肵���ꍇ�A�r���[���Ƀ��N�G�X�g����ݒ肷��B
     */
    protected boolean useRequestNameView = false;

    /**
     * �R���e�L�X�g�������T�|�[�g���W�b�N�N���X��ݒ肷��B
     *
     * @param ctxSupport ������������T�|�[�g���W�b�N�N���X
     */
    public void setCtxSupport(RequestContextSupport ctxSupport) {
        this.ctxSupport = ctxSupport;
    }
    
    /**
     * DataBinder��������N���X��ݒ肷��B
     * 
     * @param dataBinderCreator DataBinder��������N���X
     */
    public void setDataBinderCreator(
            ServletRequestDataBinderCreator dataBinderCreator) {
        this.dataBinderCreator = dataBinderCreator;
    }

    /**
     * �r���[����ݒ肷��B
     * 
     * @param viewName �f�t�H���g�ȊO��View�Z�p�𗘗p����ꍇ��
     * �ݒ肷��View��
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
    
    /**
     * �r���[���Ƀ��N�G�X�g�����g�p���邩���f����t���O��ݒ肷��B
     *
     * @param useRequestNameView �r���[���Ƀ��N�G�X�g�����g�p���邩���f����t���O�B
     */
    public void setUseRequestNameView(boolean useRequestNameView) {
        this.useRequestNameView = useRequestNameView;
    }
    
    /**
     * DI�R���e�i�ɂ���ăC���X�^���X�����ꂽ����ɌĂ΂�郁�\�b�h�B
     * �K�{������Null�`�F�b�N���s���B
     */
    public void afterPropertiesSet() {
        if (this.dataBinderCreator == null) {
            log.error("DataBinderCreator is Null.");
            throw new IllegalStateException("DataBinderCreator is Null.");
        }
        
        if (this.ctxSupport == null) {
            log.error("ContextSupport is Null.");
            throw new IllegalStateException("ContextSupport is Null.");
        }
    }

    /**
     * ���N�G�X�g�̏����i�[���邽�߂�JavaBean(�R�}���h)���擾����B
     * �^�p�����[�^��������R���g���[���ɑΉ�����JavaBean(�R�}���h)�̌^�𔻒肵�A
     * �C���X�^���X������B
     * 
     * @param request HTTP���N�G�X�g
     * @return object ��̃R�}���h�I�u�W�F�N�g
     * @throws Exception ��O
     */
    @Override
    protected Object getCommand(
            HttpServletRequest request) throws Exception {
        // �R�}���h�N���X�̌^�p�����[�^���擾����
        Type commandType = getCommandType();
            
        if (logger.isDebugEnabled()) {
            logger.debug(
                "Creating new command of class ["
                    + ((Class) commandType).getName() + "]");
        }
        
        // �^�p�����[�^��Object�^�i�w�肳��Ă��Ȃ��j
        if (commandType == Object.class) { 
            String message = "Cannot get Command type. "
                + "Controller cannot specify the Object type "
                + "for parameterized type P.";
            log.error(message);
            throw new IllegalStateException(message);
        }
        
        try {
            // �R�}���h�N���X���C���X�^���X������
            return ClassUtil.create(((Class) commandType).getName());
        } catch (Exception e) {
            log.error("Invalid Command type.", e);
            throw new IllegalStateException("Invalid Command type.");
        }
    }

    /**
     * �{�N���X�̃T�u�N���X��`���ꂽ�A�R�}���h�N���X�̎��^�C�v���擾����B
     * 
     * @return �R�}���h�N���X�̃^�C�v�B
     */
    protected Type getCommandType() {
        Class childClass = this.getClass();
        
        // �Q����ȏ�̌p�������Ă���ꍇ�A
        // TerasolunaController�̎q�ɂ�����N���X���擾����
        while (childClass.getSuperclass() != parameterizedControllerClass) {
            childClass = childClass.getSuperclass();
        }
        
        // TerasolunaController�̌^���i�^�p�����[�^�̏��t���j
        Type terasolunaControllerType = childClass.getGenericSuperclass();
        if (!(terasolunaControllerType instanceof ParameterizedType)) {
            log.error("Controller class must be set ParameterizedType");
            throw new IllegalStateException(
                    "Controller class must be set ParameterizedType");
        }
        ParameterizedType pt = (ParameterizedType) terasolunaControllerType;
        
        // �^�p�����[�^
        return pt.getActualTypeArguments()[0];
    } 
   
    /**
     *  ���N�G�X�g�̏���JavaBean(�R�}���h)�Ɋi�[���邽�߂�
     *  �f�[�^�o�C���_�𐶐�����B
     *  <code>bindAndValidate</code>���\�b�h���Ăяo�����B
     *  
     * @param request HTTP���N�G�X�g
     * @param command �o�C���h�����R�}���h�I�u�W�F�N�g
     * @return �������ꂽ�f�[�^�o�C���_
     * @throws Exception ��O
     * 
     */
    @Override
    protected ServletRequestDataBinder createBinder(
            HttpServletRequest request, Object command) throws Exception {
        // �f�[�^�o�C���_�̐���
        ServletRequestDataBinder binder = dataBinderCreator.create(
                      request, command, ctxSupport.getRequestName());
        
        if (binder == null) {
            log.error("DataBinder is Null.");
            throw new IllegalStateException("DataBinder is Null.");
        }
        
        if (this.getMessageCodesResolver() != null) {
            binder.setMessageCodesResolver(this.getMessageCodesResolver());
        }
        if (getBindingErrorProcessor() != null) {
            binder.setBindingErrorProcessor(getBindingErrorProcessor());
        }
        if (getPropertyEditorRegistrars() != null) {
            for (int i = 0; i < getPropertyEditorRegistrars().length; i++) {
                getPropertyEditorRegistrars()[i].registerCustomEditors(binder);
            }
        }
        initBinder(request, binder);
        return binder;
    }
    
    /**
     * ���N�G�X�g�̏���JavaBean(�R�}���h)�Ɋi�[������Ɏ��s����鏈���B
     * �o�C���h��A���̓`�F�b�N�����O�̃^�C�~���O�ŌĂяo�����B
     * 
     * �o�C���h�����ŃG���[��񂪊i�[���ꂽ�ꍇ�A
     * BindException���X���[����B
     * 
     * @param request HTTP���N�G�X�g
     * @param command �o�C���h�ς݂�JavaBean�i�R�}���h�j
     * @param errors �o�C���h�E���̓`�F�b�N�G���[�ێ��N���X
     * @throws Exception ��O
     */
    @Override
    protected void onBind(
            HttpServletRequest request,
            Object command,
            BindException errors) throws Exception {
        if (errors.hasErrors()) {
            throw errors;
        }
    }
    
    /**
     * ���̓`�F�b�N�̌㏈���B
     * ���̓`�F�b�N������A�Ɩ����W�b�N���s�O�̃^�C�~���O�ŌĂяo�����B
     * 
     * ���̓`�F�b�N�����ŃG���[��񂪊i�[���ꂽ�ꍇ�A
     * BindException���X���[����B
     * 
     * @param request HTTP���N�G�X�g
     * @param command �o�C���h�ς݂̃R�}���h�I�u�W�F�N�g
     * @param errors �o�C���h�E���̓`�F�b�N�G���[�ێ��N���X
     * @throws Exception ��O
     */
    @Override
    protected void onBindAndValidate(
            HttpServletRequest request,
            Object command,
            BindException errors) throws Exception {
        if (errors.hasErrors()) {
            throw errors;
        }
    }

    /**
     * �Ɩ����W�b�N���s���\�b�h���Ăяo���A���f���ƃr���[��ԋp����B
     * 
     * 
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param command �R�}���h�I�u�W�F�N�g
     * @param errors �o�C���h�E���̓`�F�b�N�G���[�ێ��N���X
     * @return ���f���ƃr���[
     * @throws Exception ��O
     */
    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors) throws Exception {
        R model = executeService(request, response, (P) command);

        if (this.viewName != null) {
            // �r���[���𒼐ڎw�肷��ꍇ
            return new ModelAndView(viewName, Constants.RESULT_KEY, model);
        } else if (this.useRequestNameView) {
            // Velocity�r���[�𗘗p����ꍇ
            return new ModelAndView(
                "/" + ctxSupport.getRequestName(), Constants.RESULT_KEY, model);
        } else {
            // �r���[���Ȃ���Castor�r���[�𗘗p����
            return new ModelAndView("", Constants.RESULT_KEY, model);
        }
    }
    
    /**
     * �Ɩ����W�b�N�����s����B
     * 
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param command �R�}���h�I�u�W�F�N�g
     * @return ���f���I�u�W�F�N�g
     * @throws Exception ��O
     */
    protected R executeService(
            HttpServletRequest request,
            HttpServletResponse response,
            P command) throws Exception {
        // �O����
        preService(request, response, command);

        // �Ɩ����W�b�N���s����
        R model = executeService(command);
        
        // �㏈��
        postService(request, response, command, model);
        return model;
    }

    /**
     * �Ɩ����W�b�N���s�㏈���B
     * 
     * �Z�b�V�����X�R�[�v�̃��N�G�X�g�ɑΉ����邽�߂̊g���_�B
     * �T�u�N���X�ɂĕK�v�ɉ����ăI�[�o�[���C�h���邱�ƁB
     * 
     * �Ɩ����W�b�N�����ɂė�O�����������ꍇ�͎��s����Ȃ��B
     * 
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param command �R�}���h�I�u�W�F�N�g
     * @param modelAndView ���f���ƃr���[
     * @throws Exception ��O
     */
    protected void postService(
            HttpServletRequest request,
            HttpServletResponse response,
            P command,
            R modelAndView) throws Exception {
    }

    /**
     * �Ɩ����W�b�N���s�O�����B
     * 
     * �Z�b�V�����X�R�[�v�̃��N�G�X�g�ɑΉ����邽�߂̊g���_�B
     * �T�u�N���X�ɂĕK�v�ɉ����ăI�[�o�[���C�h���邱�ƁB
     * 
     * @param request HTTP���N�G�X�g
     * @param response HTTP���X�|���X
     * @param command �R�}���h�I�u�W�F�N�g
     * @throws Exception ��O
     */
    protected void preService(
            HttpServletRequest request,
            HttpServletResponse response,
            P command) throws Exception {
    }

    /**
     * �Ɩ��J���҂��������ׂ��A�Ɩ����W�b�N�̎��s�����B
     * @param command �R�}���h�i�Ɩ��p�����[�^�j
     * @return ���f��
     * @throws Exception ��O
     */
    protected abstract R executeService (
            P command) throws Exception;

}
