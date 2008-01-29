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

package jp.terasoluna.fw.file.dao;

import java.util.List;

/**
 * �t�@�C���A�N�Z�X(�f�[�^�o��)�p�̃C���^�t�F�[�X�B
 * <p>
 * �t�@�C���s�I�u�W�F�N�g����l�����o���A�e�L�X�g�t�@�C���ɏ������ށB
 * FileLineWriter�̐����́AFileUpdateDAO�������́A�W���uBean��`�t�@�C���ɃI�u�W�F�N�g�����̐ݒ���L�q����B<br>
 * FileUpdateDAO�̏ڍׂ́A{@link jp.terasoluna.fw.file.dao.FileUpdateDAO}���Q�Ƃ̂��ƁB
 * </p>
 * <p>
 * <strong>�ݒ��</strong>
 * <li>�W���uBean��`�t�@�C���̐ݒ��
 * <pre><code>
 * &lt;!-- �W���uBean��`�t�@�C���������� --&gt;
 * &lt;bean id=&quot;blogic&quot; class=&quot;testBlogic&quot;&gt;
 *   &lt;property name=&quot;writer&quot;&gt;
 *     &lt;bean class=&quot;jp.terasoluna.fw.file.dao.standard.CSVFileLineWriter&quot;
 *       destroy-method=&quot;closeFile&quot;&gt;
 *       &lt;constructor-arg index=&quot;0&quot;&gt;&lt;value&gt;�y�t�@�C�����z&lt;/value&gt;&lt;/constructor-arg&gt;
 *       &lt;constructor-arg index=&quot;1&quot;&gt;&lt;value&gt;�y�t�@�C���s�I�u�W�F�N�g�̃N���X(�t���p�X)�z&lt;/value&gt;&lt;/constructor-arg&gt;
 *       &lt;constructor-arg index=&quot;2&quot; ref=&quot;columnFormatterMap&quot; /&gt;
 *     &lt;/bean&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * &lt;!-- �W���uBean��`�t�@�C�������܂� --&gt;
 * ���R���X�g���N�^�̈����Ƀt�@�C�����A�t�@�C���s�I�u�W�F�N�g�̃N���X��n���B
 * �R���X�g���N�^�̈�����1�Ԗڂ́y�t�@�C�����z
 * �R���X�g���N�^�̈�����2�Ԗڂ́y�t�@�C���s�I�u�W�F�N�g�̃N���X(�t���p�X)�z
 * �R���X�g���N�^�̈�����3�Ԗڂ́ucolumnFormatterMap�v(�Œ�)�B
 *  �@
 * �r�W�l�X���W�b�N�ɂ�FileLineWriter�^�̃I�u�W�F�N�g�Ƃ���setter��p�ӂ���B
 * // �r�W�l�X���W�b�N�̋L�q��@��������
 * private FileLineWriter&lt;FileColumnSample&gt; fileLineWriter = null;
 * 
 * public void setFileLineWriter(FileLineWriter&lt;FileColumnSample&gt; 
 *�@�@fileLineWriter){
 *     this.fileLineWriter = fileLineWriter;
 * }
 * // �r�W�l�X���W�b�N�̋L�q��@�����܂�
 * </code></pre>
 * 
 * 
 * <strong>�g�p��</strong><br>
 * <li>�t�@�C���s�I�u�W�F�N�g�̏����o�͂���B
 * <pre><code>
 * // �r�W�l�X���W�b�N�̋L�q��@��������
 * private FileLineWriter<FileColumnSample> fileLineWriter = null;
 * �c�c
 *     // FileColumnSample�^�̃t�@�C���s�I�u�W�F�N�g����l�����o���A�e�L�X�g�t�@�C���ɏo�͂���B
 *     fileLineWriter.<strong>printDataLine</strong>(fileColumnSample);
 * �c�c
 * // �r�W�l�X���W�b�N�̋L�q��@�����܂�
 * </code></pre>
 * <strong>����</strong>��FileLineWriter���񋟂��郁�\�b�h�B
 * �ڍׂ�<code>printDataLine</code>���Q�Ƃ̂��ƁB
 * </P> 
 * 
 * @param <T> �t�@�C���s�I�u�W�F�N�g
 */
public interface FileLineWriter<T> {

    /**
     * �w�b�_���ɕ�������������ށB
     * 
     * @param headerLine �w�b�_���ɏ������ޕ�����^�̃��X�g�I�u�W�F�N�g
     */
    void printHeaderLine(List<String> headerLine);

    /**
     * �t�@�C���s�I�u�W�F�N�g�̃f�[�^���������ށB
     * 
     * @param t �t�@�C���s�I�u�W�F�N�g
     */
    void printDataLine(T t);

    /**
     * �g���C�����ɕ�������������ށB
     * 
     * @param trailerLine �g���C�����ɏ������ޕ�����^�̃��X�g�I�u�W�F�N�g
     */
    void printTrailerLine(List<String> trailerLine);
    
    /**
     * �t�@�C���Ǐ����B
     * <p>
     * �����I����ɕK�����s���邱�ƁB
     * </p>
     */
    void closeFile();
}
