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

package jp.terasoluna.fw.batch.core;

/**
 * ������Ԃ̃T�}���C�U�B<BR>
 * �q���̏�����ԃ��X�g���m�F���A�T�}���C�U����B<BR>
 * �W���u����<code>bean</code>��`��<code>id="JobStatusSummarizer"</code>��
 * ��`���邱�ƂŎ�����ύX���邱�Ƃ��ł���B<BR>
 * �W���u���̐ݒ肪�����ꍇ�̓t���[�����[�N�̃f�t�H���g�ݒ�ɏ]���������s���B
 * <BR>�t���[�����[�N�̃f�t�H���g�ݒ��<code>id="JobStatusSummarizer"</code>��
 * �Q�Ƃ̂��ƁB
 *
 */
public interface JobStatusSummarizer {

    /**
     * �W���u������Ԃ̌��ʃn���h�����O�B
     * 
     * @param jobStatus
     *            �����Ώۂ̏�����
     */
    void summarize(JobStatus jobStatus);
}
