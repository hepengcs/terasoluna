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

import java.util.Iterator;
import java.util.List;

/**
 * �t�@�C���A�N�Z�X(�f�[�^�擾)�p�̃C�e���[�^�C���^�t�F�[�X�B
 * <P>
 * �e�L�X�g�t�@�C����ǂ݁A�t�@�C���̕�������t�@�C���s�I�u�W�F�N�g�Ɋi�[����B
 * FileLineIterator�̃C���X�^���X������FileQueryDAO���s���B
 * �ڍׂ�{@link jp.terasoluna.fw.file.dao.FileQueryDAO}���Q�Ƃ̂��ƁB<br>
 * 
 * <strong>�g�p��</strong><br>
 * <li>�t�@�C���s�I�u�W�F�N�g���擾�����B
 * <pre><code>
 * // �t�@�C������1���R�[�h�̃f�[�^����͂�FileColumnSample�^�̃I�u�W�F�N�g�Ɋi�[����
 * �c�c
 *     while(fileLineIterator.<strong>hasNext()</strong>){
 *        FileColumnSample fileColumnSample = fileLineIterator.<strong>next()</strong>;
 * �c�c
 * </code></pre>
 * <strong>����</strong>��FileLineIterator���񋟂��郁�\�b�h�B
 * �ڍׂ�{@link #hasNext()}�A{@link #next()}���Q�Ƃ̂��ƁB
 * </P>
 * 
 * @param <T> �t�@�C���s�I�u�W�F�N�g�B
 */
public interface FileLineIterator<T> extends Iterator {

    /**
     * �t�@�C������f�[�^���擾�ł��邩�m�F����B
     * 
     * <p>
     * �J��Ԃ������ł���ɗv�f������ꍇ��<code>true</code> ��Ԃ��B 
     * </p>
     * 
     * @return �����q������ɗv�f�����ꍇ��<code>true</code>�B
     */
    boolean hasNext();

    /**
     * �t�@�C���s�I�u�W�F�N�g��ԋp����B
     * 
     * <p>
     * <code>hasNext()</code>���\�b�h��<code>false</code>��Ԃ��܂�
     * ���̃��\�b�h�Ăяo�����ɁA�t�@�C���s�I�u�W�F�N�g��1�ԋp����B
     * </p>
     * 
     * @return ���̃t�@�C���s�I�u�W�F�N�g�B
     */
    T next();

    /**
     * �w�b�_���̕������ԋp����B
     * 
     * <p>
     * �w�b�_���̃f�[�^�𕶎���̃��X�g�Ƃ��ČĂяo�����ɕԋp����B
     * </p>
     * 
     * @return �����^�̃��X�g�B
     */
    List<String> getHeader();

    /**
     * �g���C�����̕������ԋp����B
     * 
     * <p>
     * �g���C�����̃f�[�^�𕶎���̃��X�g�Ƃ��ČĂяo�����ɕԋp����B
     * </p>
     * 
     * @return �����^�̃��X�g�B
     */
    List<String> getTrailer();

    /**
     * �X�L�b�v�����B
     * <p>
     * �t�@�C�����͋@�\�ł́A���͂��J�n����s���w��ł���B<br>
     * ��ɁA���X�^�[�g�|�C���g����t�@�C���̓Ǎ����ĊJ����Ƃ��ɗ��p����B
     * </p>
     * 
     * @param skipLines
     *            �ǂݔ�΂��s��
     */
    void skip(int skipLines);

    /**
     * �t�@�C���N���[�Y.
     * <p>
     * �t�@�C���̓��̓X�g���[�������B
     * �t�@�C�����͂����������i�K�ŕK�����s���邱�ƁB
     * </p>
     * 
     */
    void closeFile();

}
