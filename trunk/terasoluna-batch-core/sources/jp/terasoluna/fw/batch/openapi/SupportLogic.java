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
 * �W���u�O�E��A�e�W���u�O�E��A�擪�`�����N�O�A�ŏI�`�����N�㏈���p�C���^�t�F�[�X�B<br>
 * <br>
 * <strong>�ݒ��</strong><br>
 * �J���҂��쐬�����O�E�㏈�����W���uBean��`�t�@�C���ɐݒ肷��B<br>
 * <br>
 * <p>
 * �y�W���u�㏈���̐ݒ��z<br>
 * �Ώۃf�[�^�̏������I��������A���ʏ�W���u����юq�W���u���I������O�Ɏ��s
 * �����B</p>
 * <pre>
 * &lt;bean id=&quot;jobPostLogicList&quot;
 *    class=&quot;org.springframework.beans.factory.config.ListFactoryBean&quot;&gt;
 *    &lt;property name=&quot;sourceList&quot;&gt;
 *        &lt;list&gt;
 *            &lt;bean
 *                class=&quot;jp.terasoluna.batch.sample.checksample.Sample01PostLogic&quot;&gt;
 *                &lt;property name=&quot;queryDAO&quot; ref=&quot;queryDAO&quot; /&gt;
 *                &lt;property name=&quot;updateDAO&quot; ref=&quot;updateDAO&quot; /&gt;
 *            &lt;/bean&gt;
 *            &lt;bean
 *                class=&quot;jp.terasoluna.batch.sample.checksample.Sample02PostLogic&quot;&gt;
 *                &lt;property name=&quot;queryDAO&quot; ref=&quot;queryDAO&quot; /&gt;
 *                &lt;property name=&quot;updateDAO&quot; ref=&quot;updateDAO&quot; /&gt;
 *            &lt;/bean&gt;
 *        &lt;/list&gt;
 *    &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 * 
 * <p>
 * �y�e�W���u�㏈���̐ݒ��z<br>
 * �S�Ă̕����L�[�̏������I����i�S�Ă̎q�W���u���I����j�ɍs����B
 * </p>
 * <pre>
 * &lt;bean name=&quot;parentjobPostLogicList&quot; class=&quot;org.springframework.beans.factory.config.ListFactoryBean&quot;&gt;
 *    &lt;property name=&quot;sourceList&quot;&gt;
 *        &lt;list&gt;
 *            &lt;bean
 *                class=&quot;jp.terasoluna.batch.sample.checksample.dummy.DummySample01PostLogic&quot;&gt;
 *            &lt;/bean&gt;
 *        &lt;/list&gt;
 *    &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * <p>
 * �y�ŏI�`�����N�㏈���̐ݒ��z<br>
 * �ŏI�`�����N�̏������I����A�W���u�㏈�����N�������O
 * �i�W���u�㏈�����ݒ肳��Ă����ꍇ�j�ɋN�������B
 * </p>
 * <pre>
 * &lt;bean id=&quot;lastchunkPostLogicList&quot;
 *    class=&quot;org.springframework.beans.factory.config.ListFactoryBean&quot;&gt;
 *    &lt;property name=&quot;sourceList&quot;&gt;
 *        &lt;list&gt;
 *            &lt;bean
 *                class=&quot;jp.terasoluna.batch.sample.checksample.dummy.DummySample01PostLogic&quot;&gt;
 *            &lt;/bean&gt;
 *        &lt;/list&gt;
 *    &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * @param <T> �W���u�R���e�L�X�g�̃p�����[�^�N���X
 */
public interface SupportLogic<T extends JobContext> {

    /**
     * �W���u�㏈�������s����B
     *
     * @param jobContext �W���u�R���e�L�X�g
     * @return �W���u�㏈������
     */
    BLogicResult execute(T jobContext);
}
