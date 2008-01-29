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

package jp.terasoluna.fw.file.dao.standard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.OutputFileColumn;
import jp.terasoluna.fw.file.annotation.StringConverter;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.dao.FileLineWriter;

/**
 * �t�@�C���A�N�Z�X(�f�[�^����)�p�̋��ʃN���X�B
 * 
 * <p>
 * �t�@�C���A�N�Z�X(�f�[�^����)���s��3�̃N���X(CSV�A�Œ蒷�A�ϒ�) �ɋ��ʂ��鏈�����܂Ƃ߂����ۃN���X�B
 * �t�@�C���̎�ނɑΉ�����T�u�N���X���������s���B<br>
 * �g�p���{@link jp.terasoluna.fw.file.dao.FileLineWriter}���Q�Ƃ̂��ƁB
 * </p>
 * <p>
 * �t�@�C���擾�����͉��L�̎菇�ŌĂяo�����悤�Ɏ������邱�ƁB
 * <ul>
 * <li>����������(init()�A�C���X�^���X�������K���P��s�Ȃ�)</li>
 * <li>�w�b�_���擾(printHeaderLine())</li>
 * <li>�f�[�^���擾����(printDataLine())</li>
 * <li>�g���C�����擾(printTrailerLine())</li>
 * </ul>
 * ��L�̏��Ԃł̂ݐ��m�ɏo�͂ł���B<br>
 * 
 * </p>
 * 
 * @see jp.terasoluna.fw.file.dao.FileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileLineWriter
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileLineWriter
 * @param <T> �t�@�C���s�I�u�W�F�N�g�B
 */
public abstract class AbstractFileLineWriter<T>
        implements FileLineWriter<T> {

    /**
     * �t�@�C���A�N�Z�X�i�o�́j�p�̕����X�g���[���B
     */
    private Writer writer = null;

    /**
     * �t�@�C���A�N�Z�X���s���t�@�C�����B
     */
    private String fileName = null;

    /**
     * �t�@�C���G���R�[�f�B���O�B
     */
    private String fileEncoding = System.getProperty("file.encoding");

    /**
     * �p�����[�^�N���X�̃N���X�B
     */
    private Class<T> clazz = null;

    /**
     * �s��؂蕶���B
     */
    private String lineFeedChar = System.getProperty("line.separator");

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ��B
     */
    private Field[] fields = null;

    /**
     * ���\�b�h�I�u�W�F�N�g
     */
    private Method[] methods = null;

    /**
     * �J�����t�H�[�}�b�g(�t�@�C�������j���i�[����}�b�v�B
     */
    private Map<String, ColumnFormatter> columnFormatterMap = null;

    /**
     * �f�[�^���o�͊m�F�p�t���O�B
     */
    private boolean writeData = false;

    /**
     * �g���C�����o�͊m�F�p�t���O�B
     */
    private boolean writeTrailer = false;

    /**
     * �������ݏ����ς݃f�[�^���̍s���B
     */
    private int currentLineCount = 0;

    /**
     * �R���X�g���N�^�B
     * 
     * @param fileName �t�@�C����
     * @param clazz �p�����[�^�N���X
     * @param columnFormatterMap �e�L�X�g�擾���[��
     */
    public AbstractFileLineWriter(String fileName, Class<T> clazz,
            Map<String, ColumnFormatter> columnFormatterMap) {

        this.fileName = fileName;
        this.clazz = clazz;
        this.columnFormatterMap = columnFormatterMap;
    }

    /**
     * �����������B
     */
    protected void init() {
        // �t�@�C���t�H�[�}�b�g���擾����B
        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);

        // @FileFormat�������ꍇ�A��O���X���[����B
        if (fileFormat == null) {
            throw new FileException("FileFormat annotation is not found.",
                    new IllegalStateException(), fileName);
        }

        // �����R�[�h������������B
        if (fileFormat.fileEncoding() != null
                && !"".equals(fileFormat.fileEncoding())) {
            this.fileEncoding = fileFormat.fileEncoding();
        }

        // �s��؂蕶��������������B
        if (fileFormat.lineFeedChar() != null
                && !"".equals(fileFormat.lineFeedChar())) {
            this.lineFeedChar = fileFormat.lineFeedChar();
        }

        // �s��؂蕶����3�����ȏ�̏ꍇ�A��O���X���[����B
        if (lineFeedChar.length() > 2) {
            throw new FileException(new IllegalStateException(
                    "lineFeedChar length must be 1 or 2. but: "
                            + lineFeedChar.length()), fileName);
        }

        buildFields();

        // �㏑���t���O���m�F
        if (fileFormat.overWriteFlg()) {
            File file = new File(fileName);
            file.delete();
        }

        // �t�@�C���I�[�v��
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    (new FileOutputStream(fileName, true)), fileEncoding));
        } catch (UnsupportedEncodingException e) {
            throw new FileException(e, fileName);
        } catch (FileNotFoundException e) {
            throw new FileException(e, fileName);
        }
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̑����̃t�B�[���h�I�u�W�F�N�g�̔z��𐶐�����B
     */
    private void buildFields() {
        // �t�B�[���h�I�u�W�F�N�g�𐶐�
        fields = clazz.getDeclaredFields();

        Field[] fieldArray = new Field[fields.length];

        for (Field field : getFields()) {
            OutputFileColumn outputFileColumn = field
                    .getAnnotation(OutputFileColumn.class);
            if (outputFileColumn != null) {
                if (fieldArray[outputFileColumn.columnIndex()] == null) {
                    fieldArray[outputFileColumn.columnIndex()] = field;
                } else {
                    throw new FileException("Column Index is duplicate : "
                            + outputFileColumn.columnIndex(), fileName);
                }
            }
        }
        buildMethods();
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̑�����getter���\�b�h�̃��\�b�h�I�u�W�F�N�g�̔z��𐶐�����B
     */
    private void buildMethods() {
        // ���\�b�h�I�u�W�F�N�g�𐶐�
        List<Method> methodList = new ArrayList<Method>();
        List<Field> fieldList = new ArrayList<Field>();
        StringBuilder getterName = new StringBuilder();
        for (Field field : fields) {
            if (field != null
                    && field.getAnnotation(OutputFileColumn.class) != null) {

                // JavaBean���珈���̑ΏۂƂȂ鑮���̑��������擾����B
                String fieldName = field.getName();

                // �����������ɁAsetter���\�b�h�̖��O�𐶐�����B
                getterName.setLength(0);
                getterName.append("get");
                getterName.append(StringUtils.upperCase(fieldName.substring(0,
                        1)));
                getterName.append(fieldName.substring(1, fieldName.length()));

                // setter�̃��t���N�V�����I�u�W�F�N�g���擾����B
                // fields[i].getType()�ň����̌^���w�肵�Ă���B
                try {
                    methodList.add(clazz.getMethod(getterName.toString()));
                    fieldList.add(field);
                } catch (NoSuchMethodException e) {
                    throw new FileException(e, fileName);
                }
            }
        }
        methods = methodList.toArray(new Method[methodList.size()]);
        fields = fieldList.toArray(new Field[fieldList.size()]);
    }

    /**
     * �w�b�_���ւ̏����ݏ����B
     * 
     * @param headerLine �w�b�_���֏������ޕ�����̃��X�g
     */
    public void printHeaderLine(List<String> headerLine) {
        if (writeData || writeTrailer) {
            throw new FileException("Header part should be called before "
                    + "data part or trailer part.",
                    new IllegalStateException(), fileName);
        }
        printList(headerLine);
    }

    /**
     * �f�[�^���ւ̏������ݏ����B
     * 
     * @param t �f�[�^���֏������ރt�@�C���s�I�u�W�F�N�g
     */
    public void printDataLine(T t) {
        checkWriteTrailer();
        // �t�@�C���������݂̏�����
        StringBuilder fileLineBuilder = new StringBuilder();
        
        // �Œ蒷�t�@�C���̏ꍇ
        // (��؂蕶���A�͂ݕ������Ȃ��ꍇ�͌Œ蒷�t�@�C���Ɣ��f����B)
        if (getDelimiter() == Character.MIN_VALUE
                && getEncloseChar() == Character.MIN_VALUE) {
            for (int i = 0; i < getFields().length; i++) {
                fileLineBuilder.append(getColumn(t, i));
            }
        } else {
            for (int i = 0; i < getFields().length; i++) {
                // �͂ݕ����A��؂蕶���̒ǉ������B
                if (getEncloseChar() != Character.MIN_VALUE) {
                    fileLineBuilder.append(getEncloseChar());
                    fileLineBuilder.append(getColumn(t, i));
                    fileLineBuilder.append(getEncloseChar());
                } else {
                    fileLineBuilder.append(getColumn(t, i));
                }
                fileLineBuilder.append(getDelimiter());
            }
            // ��ԍŌ�̋�؂蕶�����폜����B
            if (fileLineBuilder.length() > 0) {
                fileLineBuilder.deleteCharAt(fileLineBuilder.length() - 1);
            }
        }

        // �s��؂蕶����ǉ�����B
        fileLineBuilder.append(getLineFeedChar());

        // �t�@�C���ւ̏������ݏ����B
        try {
            getWriter().write(fileLineBuilder.toString());
        } catch (IOException e) {
            throw new FileException(e, fileName);
        }
        currentLineCount++;
        setWriteData(true);
    }

    /**
     * �g���C�����ւ̏����ݏ����B
     * 
     * @param trailerLine �g���C�����֏������ޕ�����̃��X�g
     */
    public void printTrailerLine(List<String> trailerLine) {
        printList(trailerLine);
        writeTrailer = true;
    }

    /**
     * �w�b�_���A�g���C�����̏������ݗp�̋��ʃ��\�b�h�B
     * 
     * @param stringList ������̃��X�g
     */
    private void printList(List<String> stringList) {
        for (String header : stringList) {
            try {
                writer.write(header);
                writer.write(lineFeedChar);
            } catch (IOException e) {
                throw new FileException(e, fileName);
            }
        }
    }

    /**
     * �t�@�C���N���[�Y�����B
     */
    public void closeFile() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new FileException(e, fileName);
        }
    }

    /**
     * <p>
     * �t�@�C���s�I�u�W�F�N�g����J�����C���f�b�N�X�ƈ�v���鑮���̒l���擾����B
     * </p>
     * 
     * <p>
     * �������擾����ہA�t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����̋L�q�ɂ��<br>
     * <li>�p�f�B���O<br>
     * <li>�g��������<br>
     * <li>�����ϊ��������s���B<br>
     * <br>
     * �t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����ŃJ�����̃o�C�g�����w�肳��Ă���ꍇ�A<br>
     * �ԋp���镶���񂪃o�C�g���ƈ�v���Ă��邩�m�F����B
     * </p>
     * 
     * @param t �t�@�C���s�I�u�W�F�N�g
     * @param index �J�����̃C���f�b�N�X
     * @return �J�����̕�����
     */
    protected String getColumn(T t, int index) {
        // �t�@�C���ɏ������ރJ�����̕�����B
        String columnString = null;

        OutputFileColumn outputFileColumn = fields[index]
                .getAnnotation(OutputFileColumn.class);

        // �t�@�C���s�I�u�W�F�N�g(t)����J�����C���f�b�N�X�ƈ�v���鑮���̒l���擾����B
        ColumnFormatter columnFormatter = columnFormatterMap.get(methods[index]
                .getReturnType().getName());
        try {
            columnString = columnFormatter.format(t, methods[index],
                    outputFileColumn.columnFormat());
        } catch (IllegalArgumentException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1,
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        } catch (IllegalAccessException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1,
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        } catch (InvocationTargetException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1,
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        }

        if (columnString == null) {
            columnString = "";
        }

        // �g��������
        columnString = FileDAOUtility.trim(columnString, fileEncoding,
                outputFileColumn.trimChar(), outputFileColumn.trimType());

        // �p�f�B���O����
        columnString = FileDAOUtility.padding(columnString, fileEncoding,
                outputFileColumn.bytes(), outputFileColumn.paddingChar(),
                outputFileColumn.paddingType());

        // �啶���ϊ��A�������ϊ��B
        // outputFileColumn.textTransform()�̓��e�ɂ�菈����U�蕪����B
        try {
            StringConverter stringConverter = outputFileColumn
                    .stringConverter().newInstance();
            columnString = stringConverter.convert(columnString);
        } catch (InstantiationException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1, 
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        } catch (IllegalAccessException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1, 
                    fields[index].getName(),
                    outputFileColumn.columnIndex());
        }

        // �J�����̃o�C�g���`�F�b�N�B
        if (isCheckByte(outputFileColumn)) {
            try {
                //�Œ蒷�o�͎��ABytes�l�̐ݒ肪���̗�O
                if (0 >= outputFileColumn.bytes()) {
                    throw new FileLineException("bytes is not set "
                            + "or a number equal to or less than 0 is set.",
                            new IllegalStateException(), getFileName(),
                            currentLineCount + 1, fields[index].getName(),
                            outputFileColumn.columnIndex());
                }
                //�ݒ肳�ꂽBytes�l�ƃf�[�^�̃T�C�Y���Ⴄ�ꍇ�͗�O����
                if (columnString.getBytes(fileEncoding).length
                        != outputFileColumn.bytes()) {
                    throw new FileLineException(
                            "The data size is different from bytes value of "
                            + "the set value of the column .",
                            new IllegalStateException(), fileName,
                            currentLineCount + 1, fields[index].getName(),
                            outputFileColumn.columnIndex());
                }
            } catch (UnsupportedEncodingException e) {
                throw new FileException(e, fileName);
            }
        }
        return columnString;
    }
    
    /**
     * �t�@�C�������擾����B
     * 
     * @return fileName �t�@�C����
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * �s��؂蕶����ݒ肷��B
     * 
     * @return lineFeedChar �s��؂蕶��
     */
    protected String getLineFeedChar() {
        return lineFeedChar;
    }

    /**
     * �J�����t�H�[�}�b�g(�t�@�C�������j�������i�[����}�b�v���擾����B
     * 
     * @param columnFormatterMap
     *            �J�����t�H�[�}�b�g(�t�@�C�������j���i�[����}�b�v
     */
    public void setColumnFormatterMap(
            Map<String, ColumnFormatter> columnFormatterMap) {
        this.columnFormatterMap = columnFormatterMap;
    }

    /**
     * �t�@�C���A�N�Z�X�i�o�́j�p�̕����X�g���[�����擾����B
     * 
     * @return bufferedWriter �t�@�C���A�N�Z�X�i�o�́j�p�̕����X�g���[��
     */
    protected Writer getWriter() {
        return writer;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ����擾����B
     * 
     * @return fields �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ�
     */
    protected Field[] getFields() {
        return fields;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���ɑΉ�����getter���\�b�h���i�[����ϐ����擾����B
     * 
     * @return methods �t�@�C���s�I�u�W�F�N�g��Field���ɑΉ�����getter���\�b�h���i�[����ϐ�
     */
    protected Method[] getMethods() {
        return methods;
    }

    /**
     * �f�[�^���̏o�͂��J�n����Ă��邩�ǂ����𔻒肷��t���O�B
     * 
     * @param writeData �t���O
     */
    protected void setWriteData(boolean writeData) {
        this.writeData = writeData;
    }

    /**
     * �g���C�����̏������I����Ă��邩�ǂ����𔻒肷��B<br>
     * �������������Ă���ꍇ�A��O���X���[����B
     */
    protected void checkWriteTrailer() {
        if (writeTrailer) {
            throw new FileException("Header part or data part should be "
                    + "called before TrailerPart",
                    new IllegalStateException(), fileName);
        }
    }

    /**
     * ��؂蕶�����擾����B
     * 
     * @return ��؂蕶��
     */
    public abstract char getDelimiter();

    /**
     * �͂ݕ������擾����B
     * 
     * @return �͂ݕ���
     */
    public abstract char getEncloseChar();


    /**
     * �o�C�g���`�F�b�N���s�����ǂ����B
     * @param outputFileColumn �o�̓J����
     * @return �o�C�g�����ݒ肳��Ă���(1�o�C�g�ȏ�)�ꍇ��true�B
     */
    protected boolean isCheckByte(OutputFileColumn outputFileColumn) {

        if (0 < outputFileColumn.bytes()) {
            return true;
        }

        return false;
    }
}
