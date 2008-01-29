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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import jp.terasoluna.fw.file.annotation.FileFormat;
import jp.terasoluna.fw.file.annotation.InputFileColumn;
import jp.terasoluna.fw.file.annotation.StringConverter;
import jp.terasoluna.fw.file.dao.FileException;
import jp.terasoluna.fw.file.dao.FileLineException;
import jp.terasoluna.fw.file.dao.FileLineIterator;

import org.apache.commons.lang.StringUtils;

/**
 * �t�@�C���A�N�Z�X(�f�[�^�擾)�p�̋��ʃN���X�B
 * 
 * <p>
 * �t�@�C���A�N�Z�X(�f�[�^�擾)���s��3�̃N���X(CSV�A�Œ蒷�A�ϒ�) �ɋ��ʂ��鏈�����܂Ƃ߂����ۃN���X�B
 * �t�@�C���̎�ނɑΉ�����T�u�N���X���������s���B<br>
 * �g�p���{@link jp.terasoluna.fw.file.dao.FileLineIterator}���Q�Ƃ̂��ƁB
 * </p>
 * 
 * �t�@�C���擾�����͉��L�̎菇�ŌĂяo�����悤�Ɏ������邱�ƁB<br>
 * <ul>
 * <li>�w�b�_���擾(getHeader())</li>
 * <li>�X�L�b�v����(skip())</li>
 * <li>�f�[�^���擾����(readLine())</li>
 * <li>�g���C�����擾(getTrailer())</li>
 * </ul>
 * ��L�̏��Ԃł̂ݐ��m�Ƀf�[�^�擾�ł���B��L�̏��ʊO�ŏ��������s�����<code>IllegalStateException<code>����������B<br>
 * �W���u�Ď��s�܂łɃt�@�C�����X�V����ƃ��X�^�[�g�@�\������ɓ��삵�Ȃ��B
 * 
 * @see jp.terasoluna.fw.file.dao.FileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.CSVFileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.FixedFileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.VariableFileLineIterator
 * @see jp.terasoluna.fw.file.dao.standard.PlainFileLineIterator
 * 
 * @param <T> �t�@�C���s�I�u�W�F�N�g�B
 */
public abstract class AbstractFileLineIterator<T> implements
        FileLineIterator<T> {

    /**
     * �t�@�C�����B
     */
    private String fileName = null;

    /**
     * ���ʃN���X�B
     */
    private Class<T> clazz = null;

    /**
     * �s��؂蕶���B
     */
    private String lineFeedChar = System.getProperty("line.separator");

    /**
     * �t�@�C���G���R�[�f�B���O�B
     */
    private String fileEncoding = System.getProperty("file.encoding");

    /**
     * �w�b�_�s���B
     */
    private int headerLineCount = 0;

    /**
     * �g���C���s���B
     */
    private int trailerLineCount = 0;

    /**
     * ���݂̃f�[�^����1�s���̕�����B
     */
    private String currentLineString = null;

    /**
     * �t�@�C�����͏����ς݂̃f�[�^���̍s���B
     */
    private int currentLineCount = 0;

    /**
     * �t�@�C���A�N�Z�X�p�̕����X�g���[���B
     */
    private Reader reader = null;

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ��B
     */
    private Field[] fields = null;

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field�ɑΉ�����setter���\�b�h���i�[����B
     */
    private Method[] methods = null;

    /**
     * �J�����p�[�U�[���i�[����}�b�v�B
     */
    private Map<String, ColumnParser> columnParserMap = null;

    /**
     * �w�b�_���̕����񃊃X�g�B
     */
    private List<String> header = new ArrayList<String>();

    /**
     * �g���C�����̕����񃊃X�g�B
     */
    private List<String> trailer = new ArrayList<String>();

    /**
     * �w�b�_�������m�F�p�t���O�B
     */
    private boolean readHeader = false;

    /**
     * �f�[�^�������m�F�p�t���O�B
     */
    private boolean readData = false;

    /**
     * �g���C���������m�F�p�t���O�B
     */
    private boolean readTrailer = false;

    /**
     * �g���C�����̈ꎞ�i�[�p�̃L���[�B
     */
    private Queue<String> trailerQueue = null;

    /**
     * 1�s���̕������ǂݍ��ރI�u�W�F�N�g
     */
    private LineReader lineReader = null;

    /**
     * �t�@�C���s�I�u�W�F�N�g�Œ�`����Ă���J�������i@InputFileColumn���t���Ă��鑮���̐��j���i�[����B
     */
    private int columnCount = 0;

    /**
     * �R���X�g���N�^�B
     * 
     * @param fileName �t�@�C����
     * @param clazz ���ʃN���X
     * @param columnParserMap �t�H�[�}�b�g�������X�g
     */
    public AbstractFileLineIterator(String fileName, Class<T> clazz,
            Map<String, ColumnParser> columnParserMap) {
        if (fileName == null || "".equals(fileName)) {
            throw new FileException("File is not found.", 
                    new IllegalStateException(), fileName);
        }
        FileFormat fileFormat = clazz.getAnnotation(FileFormat.class);
        if (fileFormat == null) {
            throw new FileException("FileFormat annotation is not found.", 
                    new IllegalStateException(), fileName);
        }
        this.fileName = fileName;
        this.clazz = clazz;
        if (fileFormat.lineFeedChar() != null
                && !"".equals(fileFormat.lineFeedChar())) {
            this.lineFeedChar = fileFormat.lineFeedChar();
        }
        if (fileFormat.fileEncoding() != null
                && !"".equals(fileFormat.fileEncoding())) {
            this.fileEncoding = fileFormat.fileEncoding();
        }
        this.headerLineCount = fileFormat.headerLineCount();
        this.trailerLineCount = fileFormat.trailerLineCount();
        this.columnParserMap = columnParserMap;
    }

    /**
     * ���̍s�̃��R�[�h�����邩�ǂ����m�F���邽�߂̃��\�b�h�B<br>
     * �J��Ԃ������ł���ɗv�f������ꍇ�� true ��Ԃ��܂��B
     * 
     * @return �J��Ԃ������ł���ɗv�f������ꍇ�� <code>true</code>
     */
    public boolean hasNext() {
        try {
            if (reader.ready()) {
                return true;
            }
        } catch (IOException e) {
            throw new FileException(e, fileName);
        }
        return false;
    }

    /**
     * �J��Ԃ������Ńt�@�C���s�I�u�W�F�N�g��ԋp����B
     * 
     * <p>
     * ���̍s�̃��R�[�h�̏����t�@�C���s�I�u�W�F�N�g�Ɋi�[���ĕԋp���܂��B<br>
     * �J��Ԃ������Ŏ��̗v�f��Ԃ��܂��B
     * </p>
     * 
     * @return �t�@�C���s�I�u�W�F�N�g
     */
    public T next() {
        if (readTrailer) {
            throw new FileLineException(
                    "Data part should be called before trailer part.", 
                    new IllegalStateException(),
                    fileName, currentLineCount, null, -1);
        }
        if (!hasNext()) {
            throw new FileLineException(new NoSuchElementException(), 
                fileName, currentLineCount, null, -1);
        }

        T fileLineObject = null;

        String currentString = readLine();

        // �t�@�C���s�I�u�W�F�N�g��V���ɐ������鏈���B
        try {
            fileLineObject = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1);
        } catch (IllegalAccessException e) {
            throw new FileLineException(e, fileName, currentLineCount + 1);
        }

        // CSV�̋�؂蕶���ɂ��������ē��̓f�[�^�𕪉�����B
        // ��؂蕶���̓A�m�e�[�V��������擾����B
        String[] columns = separateColumns(currentString);

        // �t�@�C������ǂݎ�����J�������ƃt�@�C���s�I�u�W�F�N�g�̃J���������r����B
        if (columnCount != columns.length) {
            throw new FileLineException("Column Count is not different from "
                    + "FileLineObject's column counts",
                    new IllegalStateException(),
                    fileName,
                    currentLineCount,
                    null,
                    -1);
        }

        for (int i = 0; i < fields.length; i++) {
            // JavaBean�̓��͗p�̃A�m�e�[�V������ݒ肷��B
            InputFileColumn inputFileColumn = null;

            if (fields[i] != null) {
                inputFileColumn = fields[i]
                        .getAnnotation(InputFileColumn.class);
            }

            // �t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V������null�łȂ���Ώ������p���B
            if (inputFileColumn != null) {

                // 1�J�����̕�������Z�b�g����B
                String columnString = columns[inputFileColumn.columnIndex()];

                // �����T�C�Y�̊m�F�B
                if (0 < inputFileColumn.bytes()) {
                    try {
                        if (columnString.getBytes(fileEncoding).length
                                != inputFileColumn.bytes()) {
                            throw new FileLineException(
                                    "Data size is different from a set point "
                                    + "of a column.", 
                                    new IllegalStateException(),
                                    fileName, currentLineCount + 1,
                                    fields[i].getName(),
                                    inputFileColumn.columnIndex());
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new FileException(e, fileName);
                    }
                }

                // �g��������
                columnString = FileDAOUtility.trim(columnString, fileEncoding,
                        inputFileColumn.trimChar(), inputFileColumn.trimType());

                // �p�f�B���O����
                columnString = FileDAOUtility.padding(columnString,
                        fileEncoding, inputFileColumn.bytes(), inputFileColumn
                                .paddingChar(), inputFileColumn.paddingType());

                // �啶���ϊ��A�������ϊ��B
                // inputFileColumn.stringConverter()�̓��e�ɂ�菈����U�蕪����B
                try {
                    StringConverter stringConverter = inputFileColumn
                            .stringConverter().newInstance();
                    columnString = stringConverter.convert(columnString);
                } catch (InstantiationException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                } catch (IllegalAccessException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                }

                // �l���i�[���鏈���B
                // JavaBean�̑����̌^�̖��O�ɂ���ď�����U�蕪����B
                ColumnParser textSetter = columnParserMap.get(fields[i]
                        .getType().getName());
                try {
                    textSetter.parse(columnString, fileLineObject, methods[i],
                            inputFileColumn.columnFormat());
                } catch (IllegalArgumentException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                } catch (IllegalAccessException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                } catch (InvocationTargetException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                } catch (ParseException e) {
                    throw new FileLineException(e, fileName,  
                            currentLineCount + 1, fields[i].getName(),
                            inputFileColumn.columnIndex());
                }

            }
        }
        readData = true;
        currentLineCount++;
        return fileLineObject;
    }

    /**
     * Iterator�Œ�`����Ă��郁�\�b�h�B
     * FileQueryDAO�ł͎������Ȃ��̂ŁA���̃N���X����Ăяo�����ꍇ�A
     * UnsupportedOperationException���X���[����B
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * �����������B <br>
     * �����������ōs���͈̂ȉ��̂R�B
     * <ul>
     * <li>�t�@�C���s�I�u�W�F�N�g�̑���(Field)�̎擾</li>
     * <li>�g���C���L���[�̏�����</li>
     * <li>�t�@�C���I�[�v������</li>
     * </ul>
     */
    protected void init() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(
                    (new FileInputStream(fileName)), fileEncoding));
        } catch (UnsupportedEncodingException e) {
            throw new FileException(e, fileName);
        } catch (FileNotFoundException e) {
            throw new FileException(e, fileName);
        }

        if (1 <= trailerLineCount) {
            trailerQueue = new ArrayBlockingQueue<String>(trailerLineCount);
        }

        buildFields();
        buildMethods();

        // �t�@�C������1�������ǂ�ŁA�s��؂蕶���ƈ�v������1�s�ƃJ�E���g����B
        if (getEncloseChar() == Character.MIN_VALUE) {
            // �͂ݕ��������A�s��؂蕶����2����
            if (lineFeedChar.length() == 2) {
                lineReader = new LineFeed2LineReader(reader, lineFeedChar);
            } else if (lineFeedChar.length() == 1) {
                // �͂ݕ��������A�s��؂蕶����1����
                lineReader = new LineFeed1LineReader(reader, lineFeedChar);
            } else {
                throw new FileException(
                        "lineFeedChar length must be 1 or 2. but: "
                        + lineFeedChar.length(), new IllegalStateException(),
                        fileName);
            }
        } else {
            // �͂ݕ�������A�s��؂蕶����1����
            if (lineFeedChar.length() == 1) {
                lineReader = new EncloseCharLineFeed1LineReader(getDelimiter(),
                       getEncloseChar(), reader, lineFeedChar);
                    // �͂ݕ�������A�s��؂蕶��2����
            } else if (lineFeedChar.length() == 2) {
                lineReader = new EncloseCharLineFeed2LineReader(getDelimiter(),
                        getEncloseChar(), reader, lineFeedChar);
            } else {
                throw new FileException(
                        "lineFeedChar length must be 1 or 2. but: "
                        + lineFeedChar.length(), new IllegalStateException(),
                        fileName);
            }
        }
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̑����̃t�B�[���h�I�u�W�F�N�g�̔z��𐶐�����B<br>
     */
    private void buildFields() {
        // �t�B�[���h�I�u�W�F�N�g�𐶐�
        fields = clazz.getDeclaredFields();

        Field[] fieldArray = new Field[fields.length];

        for (Field field : getFields()) {
            InputFileColumn inputFileColumn = field
                    .getAnnotation(InputFileColumn.class);
            if (inputFileColumn != null) {
                if (fieldArray[inputFileColumn.columnIndex()] == null) {
                    fieldArray[inputFileColumn.columnIndex()] = field;
                } else {
                    throw new FileException("Column Index: "
                            + inputFileColumn.columnIndex()
                            + " is duplicated.", fileName);
                }
                columnCount++;
            }
        }
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g�̑�����setter���\�b�h�̃��\�b�h�I�u�W�F�N�g�̔z��𐶐�����B
     */
    private void buildMethods() {
        List<Method> methodList = new ArrayList<Method>();
        StringBuilder setterName = new StringBuilder();

        for (Field field : fields) {
            if (field.getAnnotation(InputFileColumn.class) != null) {
                // JavaBean���珈���̑ΏۂƂȂ鑮���̑��������擾����B
                String fieldName = field.getName();

                // �����������ɁAsetter���\�b�h�̖��O�𐶐�����B
                setterName.setLength(0);
                setterName.append("set");
                setterName.append(StringUtils.upperCase(fieldName.substring(0,
                        1)));
                setterName.append(fieldName.substring(1, fieldName.length()));

                // setter�̃��t���N�V�����I�u�W�F�N�g���擾����B
                // fields[i].getType()�ň����̌^���w�肵�Ă���B
                try {
                    methodList.add(clazz.getMethod(setterName.toString(),
                            new Class[] { field.getType() }));
                } catch (NoSuchMethodException e) {
                    throw new FileException(e, fileName);
                }
            } else {
                methodList.add(null);
            }
        }
        methods = methodList.toArray(new Method[methodList.size()]);
    }

    /**
     * �t�@�C���Ǐ����B
     */
    public void closeFile() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new FileException(e, fileName);
        }
    }

    /**
     * �w�b�_���̃f�[�^���擾���郁�\�b�h�B
     * 
     * @return header �w�b�_���̕����񃊃X�g
     */
    public List<String> getHeader() {
        if (readTrailer || readData) {
            throw new FileException(new IllegalStateException(), fileName);
        }
        if (!readHeader) {
            readHeader = true;
            if (0 < headerLineCount) {
                for (int i = 0; i < headerLineCount; i++) {
                    if (!hasNext()) {
                        throw new FileException(new NoSuchElementException(),
                                fileName);
                    }
                    try {
                        header.add(lineReader.readLine());
                    } catch (FileException e) {
                        throw new FileException(e, fileName);
                    }
                }
            }
        }
        return header;
    }

    /**
     * �g���C�����̃f�[�^���擾���郁�\�b�h.
     * 
     * @return �g���C�����̕����񃊃X�g
     */
    public List<String> getTrailer() {
        if (0 < trailerLineCount) {
            while (hasNext()) {
                if (!readHeader) {
                    getHeader();
                }
                if (trailerLineCount <= trailerQueue.size()) {
                    currentLineString = trailerQueue.poll();
                }
                try {
                    trailerQueue.add(lineReader.readLine());
                } catch (FileException e) {
                    throw new FileException(e, fileName);
                }
            }
            for (String fileLineBuilder : trailerQueue) {
                trailer.add(fileLineBuilder);
            }
        }
        readTrailer = true;
        return trailer;
    }

    /**
     * �t�@�C������f�[�^���̃f�[�^��1�s���ǂݎ��A������Ƃ��Čďo���ɕԋp����.
     * 
     * @return �f�[�^���̂P�s���̕�����
     */
    protected String readLine() {
        if (!hasNext()) {
            throw new FileException(new NoSuchElementException(), fileName);
        }

        if (!readHeader) {
            getHeader();
        }

        if (1 <= trailerLineCount) {
            if (trailerQueue.size() < trailerLineCount) {
                int loopCount = trailerLineCount - trailerQueue.size();
                for (int i = 0; i < loopCount; i++) {
                    if (!hasNext()) {
                        throw new FileException(new NoSuchElementException(),
                                fileName);
                    }
                    try {
                        trailerQueue.add(lineReader.readLine());
                    } catch (FileException e) {
                        throw new FileException(e, fileName);
                    }
                }
                if (!hasNext()) {
                    return null;
                }
            }

            currentLineString = trailerQueue.poll();
            try {
                trailerQueue.add(lineReader.readLine());
            } catch (FileException e) {
                throw new FileException(e, fileName);
            }
        } else {
            try {
                currentLineString = lineReader.readLine();
            } catch (FileException e) {
                throw new FileException(e, fileName);
            }
        }

        return currentLineString;
    }

    /**
     * ���X�^�[�g���ɏ����ς̃f�[�^���̃f�[�^��ǂݔ�΂����������s����B
     * 
     * @param skipLines �ǂݔ�΂��s���B
     */
    public void skip(int skipLines) {
        for (int i = 0; i < skipLines; i++) {
            readLine();
        }
    }

    /**
     * ��؂蕶�����擾����B
     * 
     * @return �s��؂蕶���B
     */
    protected abstract char getDelimiter();

    /**
     * �͂ݕ������擾����B
     * 
     * @return �͂ݕ����B
     */
    protected abstract char getEncloseChar();

    /**
     * �����񕪊�����.
     * <p>
     * �f�[�^���̃f�[�^�P�s�����t�@�C���s�I�u�W�F�N�g�̃A�m�e�[�V�����̋L�q��
     * �]���J�����ɕ�������B
     * </p>
     * 
     * @param fileLineString �f�[�^���̃f�[�^�P�s��
     * @return �f�[�^���P�s�̕�����𕪉����������z��
     */
    protected abstract String[] separateColumns(String fileLineString);

    /**
     * �s��؂蕶�����擾����B
     * 
     * @return �s��؂蕶��
     */
    protected String getLineFeedChar() {
        return lineFeedChar;
    }

    /**
     * �t�@�C���G���R�[�f�B���O�擾����B
     * 
     * @return �t�@�C���G���R�[�f�B���O
     */
    protected String getFileEncoding() {
        return fileEncoding;
    }

    /**
     * �w�b�_�s�����擾����B
     * 
     * @return �w�b�_�s��
     */
    protected int getHeaderLineCount() {
        return headerLineCount;
    }

    /**
     * �g���C���s�����擾����B
     * 
     * @return �g���C���s��
     */
    protected int getTrailerLineCount() {
        return trailerLineCount;
    }

    /**
     * �t�@�C�����͏����ς݂̃f�[�^���̍s�����擾����B
     * 
     * @return �t�@�C�����͏����ς݂̃f�[�^���̍s���B
     */
    public int getCurrentLineCount() {
        return currentLineCount;
    }

    /**
     * �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ����擾����B
     * 
     * @return �t�@�C���s�I�u�W�F�N�g��Field���iAnnotation�j���i�[����ϐ�
     */
    protected Field[] getFields() {
        return fields;
    }

    /**
     * �t�@�C�������擾����B
     * @return fileName �t�@�C����
     */
    protected String getFileName() {
        return fileName;
    }
}
