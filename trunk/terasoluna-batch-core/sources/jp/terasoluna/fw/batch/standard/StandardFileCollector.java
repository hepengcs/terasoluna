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

package jp.terasoluna.fw.batch.standard;

import jp.terasoluna.fw.batch.core.AbstractCollector;
import jp.terasoluna.fw.batch.core.CollectedDataHandler;
import jp.terasoluna.fw.batch.core.CollectorResult;
import jp.terasoluna.fw.batch.core.JobStatus;
import jp.terasoluna.fw.batch.openapi.JobContext;
import jp.terasoluna.fw.batch.openapi.ReturnCode;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.dao.FileLineIterator;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �t�@�C��Collector�̕W�������N���X�B
 * <p>
 *   �t���[�����[�N�̃t�@�C���A�N�Z�X�@�\��p���ē��̓t�@�C��(CSV�`���A�Œ蒷�`
 *   ���A�ϒ��`��)����̏����Ώۃf�[�^�̎擾�@�\��񋟁B
 * </p>
 * <p>
 *  <li>�t�@�C��QueryDAO</li> <li>���̓t�@�C����</li> <li>���ʃN���X(�t�@�C���s�I�u�W�F
 *  �N�g)</li><li>FileLineException���A�㑱��������p�t���O<p>�𑮐��l�Ƃ���
 *  �ݒ肷��K�v������B</li></p>
 * </p>
 * <br>
 *<strong>�ݒ��</strong>
 * <code><pre>
 *  &lt;!-- �R���N�^�[��` --&gt;
 *   &lt;bean id="collector" parent="fileChunkCollector"&gt
 *       &lt;property name="fileQueryDao" ref="csvFileQueryDAO" /&gt
 *       &lt;property name="inputFileName" value="../INPUTFILE/SAMPLE/sampledata.csv" /&gt
 *       &lt;property name="resultClass"&gt
 *           &lt;bean class="jp.terasoluna.batch.sample.FileLine001" /&gt
 *       &lt;/property&gt
 *       &lt;property name="readNextLine" value="true" /&gt
 *   &lt;/bean&gt
 * </pre></code>
 * 
 */
public class StandardFileCollector extends AbstractCollector {
    
    /**
     * �t�@�C���A�N�Z�X�pDAO�B
     */
    private FileQueryDAO fileQueryDao = null;

    /**
     * ���ʃN���X�B
     */
    private Object resultClass = null;

    /**
     * ���̓t�@�C�����B
     */
    private String inputFileName = null;
    
    /**
     * FileLineException�X���[���A�㑱��������p�̃t���O�B
     */
    private boolean readNextLine = false;
    
    /**
     * ���O�C���X�^���X�B
     */
    private static Log log = LogFactory.getLog(StandardFileCollector.class);

    /**
     * ���̓t�@�C������f�[�^���擾����B
     *
     * @param jobContext �W���u�R���e�N�X�g
     * @param collectedDataHandler ���W�����f�[�^����������n���h��
     * @param jobStatus �W���u�X�e�[�^�X
     * @return �R���N�^�̏�������
     */
    @Override
    protected CollectorResult doCollect(JobContext jobContext,
            CollectedDataHandler collectedDataHandler, JobStatus jobStatus) {
        
        // �����ύs�̃J�E���g
        int collected = 0;
        
        // �t�@�C���s�I�u�W�F�N�g��ێ�����C�e���[�^���擾����
        FileLineIterator fileLineIterator = fileQueryDao.execute(inputFileName,
            resultClass.getClass());
        
        if (fileLineIterator == null) {
            return new CollectorResult(ReturnCode.NORMAL_END, collected);
        }
        
        try {
            while (fileLineIterator.hasNext()) {
                try {
                    // ���̓t�@�C����1�s���擾����
                    Object fileLineObject = fileLineIterator.next();
                    
                    collectedDataHandler.handle(fileLineObject, collected++);
                  
                    // �����������グ��
                    jobStatus.incrementCollected();
                } catch (FileLineException ex) {
                    if (readNextLine) {
                        // ���O�o�͌�A���̍s��ǂ�
                        writeWarnLog(ex);
                        continue;
                    } else {
                        throw ex;
                    }
                }
            }
        } finally {
            fileLineIterator.closeFile();
        }
        
        // �R���N�^�[���ʃI�u�W�F�N�g��ԋp����
        return new CollectorResult(ReturnCode.NORMAL_END, collected);
        
    }

    /**
     * �t�@�C���A�N�Z�X�pDAO��ݒ肷��B
     *
     * @param fileQueryDao �t�@�C���A�N�Z�X�pDAO
     */
    public void setFileQueryDao(FileQueryDAO fileQueryDao) {
        this.fileQueryDao = fileQueryDao;
    }

    /**
     * ���̓t�@�C������ݒ肷��B
     *
     * @param inputFileName ���̓t�@�C����
     */
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    /**
     * ���ʃN���X��ݒ肷��B
     *
     * @param resultClass ���ʃN���X
     */
    public void setResultClass(Object resultClass) {
        this.resultClass = resultClass;
    }
    
    /**
     * �t�@�C���sIterator�̏�������p�t���O��ݒ肷��B
     * 
     * @param readNextLine �t���O
     */
    public void setReadNextLine(boolean readNextLine) {
        this.readNextLine = readNextLine;
    }

    /**
     * �s��O�����������ꍇ�̌x�����O���o�͂���B
     * 
     * @param fileLineException �t�@�C���s��O
     */
    protected void writeWarnLog(FileLineException fileLineException) {
        StringBuilder builder = new StringBuilder();
        builder.append(fileLineException.toString());
        builder.append(" [FileName]:");
        builder.append(fileLineException.getFileName());
        builder.append(" [LineNo]:");
        builder.append(fileLineException.getLineNo());
        builder.append(" [ColumnName]:");
        builder.append(fileLineException.getColumnName());
        log.warn(builder.toString());
    }
    
}
