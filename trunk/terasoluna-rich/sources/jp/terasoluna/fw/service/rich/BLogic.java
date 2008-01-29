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

package jp.terasoluna.fw.service.rich;

/**
 * TERASOLUNA����`����
 * �T�[�r�X�w�̃N���X���������邱�Ƃ��o����C���^�t�F�[�X�B
 * 
 * <p>
 * �Ɩ��J���҂́A�Ɩ����W�b�N�̃p�����[�^�ł���C�ӂ̌^��JavaBean���󂯎��A
 * �Ɩ����W�b�N���ʂ̔C�ӂ̌^��JavaBean��ԋp���鏈�����������邱�ƁB
 * </p>
 * 
 * <p>
 * �g�����U�N�V�������E�ƂȂ邽�߁A���炩���ߒ�`����
 * �g�����U�N�V�����v���L�V�̒��ے�`���p������Bean��`���s�����ƁB
 * �܂��A�񕜕s�\�ȃG���[�����������ꍇ�́A
 * �v���W�F�N�g�Œ�߂�ꂽ���[���ŔC�ӂ̗�O���X���[���邱�ƁB
 * </p>
 * 
 * <p>
 * �yBean��`��z<br>
 * <code><pre>
 *   &lt;bean id="maxBLogic" parent="baseTransactionProxy"&gt;
 *     &lt;property name="target"&gt;
 *       &lt;bean class="jp.terasoluna.sample2.service.blogic.MaxBLogic"/&gt;
 *     &lt;/property&gt;
 *   &lt;/bean&gt;
 * </pre></code>
 *   ���@baseTransactionProxy�́A�g�����U�N�V�����v���L�V�̃x�[�X��`�B
 * </p>
 * 
 * <p>
 * �ʏ�A�T�[�r�X�w�̃N���X�́A
 * DI�R���e�i�𗘗p�����ꍇ�A1�̃T�[�r�X�ɑ΂���1�̃C���^�t�F�[�X�Ƃ���A
 * �t���[�����[�N����̃C���^�t�F�[�X�Ɉˑ����Ȃ�POJO�ł��邱�Ƃ��]�܂������A
 * TERASOLUNA�ł́A1�̃C���^�t�F�[�X�ŕ����̃T�[�r�X����������d�g�݂��p�ӂ��Ă���B
 * <code>BLogic</code>�C���^�t�F�[�X�����������N���X�𗘗p����ƁA
 * �v���[���e�[�V�����w�́A�T�[�r�X�w�̃N���X�𓝈�I�Ɉ������Ƃ��o���邽�߁A
 * ���N�G�X�g���ƂɃv���[���e�[�V�����w�̌Ăяo���N���X����������K�v���Ȃ��Ȃ郁���b�g������B
 * </p>
 * 
 * <p>
 * �v���[���e�[�V�����w����̌Ăяo���ɂ��ẮA
 * <code>BLogicController</code>���Q�l�ɂ��邱�ƁB
 * </p>
 * <p>
 * {@link jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController}
 * �N���X�ł́A�����N���X��execute���\�b�h�̈������̌^�����ƂɁA�R�}���h�I�u�W�F�N�g�̌^���������肵�Ă���B
 * ����Ď����N���X�ł́A�uexecute�v�Ƃ������̂̃��\�b�h�́A�{�C���^�t�F�[�X����������1���\�b�h�̂ݒ�`���A
 * �I�[�o�[���[�h(���\�b�h��������ň����̌^�A���A���я����قȂ郁�\�b�h�𕡐���`)���Ȃ����ƁB
 * </p>
 * 
 * @see jp.terasoluna.fw.web.rich.springmvc.controller.BLogicController
 * 
 * @param <P> �Ɩ��p�����[�^
 * @param <R> �Ɩ�����
 */
public interface BLogic < P , R > {

    /**
     * �Ɩ����W�b�N�����s����B
     * 
     * <p>
     * �����N���X�ɂĖ{���\�b�h�ȊO��
     * �����̌^�A�����قȂ�execute���\�b�h���`���Ă͂Ȃ�Ȃ��B
     * </p>
     * 
     * @param params �Ɩ������p�����[�^
     * @return �Ɩ���������
     */
     R execute(P params);
}
