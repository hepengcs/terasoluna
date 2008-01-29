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

package jp.terasoluna.fw.batch.openapi;

/**
 * �r�W�l�X���W�b�N�����s����C���^�t�F�[�X�B
 * 
 * <p>
 * �Ɩ��J���҂́ACollector�Ŏ擾���ꂽ�����Ώۃf�[�^�N���X��
 * <code>JobContext</code>���ۃN���X�����������W���u�R���e�L�X�g���󂯎��A
 * �r�W�l�X���W�b�N�������ʂ�<code>BLogicResult</code>��ԋp���鏈������������
 * ���ƁB
 * </p>
 * 
 * <strong>�ݒ��</strong><br>
 * �J���҂��쐬�����r�W�l�X���W�b�N���W���uBean��`�t�@�C���ɐݒ肷��B<br>
 * <pre>
 *     &lt;bean id=&quot;blogic&quot; class=&quot;jp.terasoluna.batch.sample.checksample.SampleBLogic&quot; /&gt;
 * </pre>
 * 
 * <p>
 * 1�̃W���u�ɑ΂��A1�̃r�W�l�X���W�b�N���`����B
 * </p>
 * @param <T> �����Ώۃf�[�^
 * @param <S> �W���u�R���e�L�X�g
 */
public interface BLogic<T, S extends JobContext> {

    /**
     * <code>BLogic</code>�����s����B
     *
     * @param param �����Ώۃf�[�^
     * @param jobContext �W���u�R���e�L�X�g
     * @return �r�W�l�X���W�b�N��������
     */
    BLogicResult execute(T param, S jobContext);
}
