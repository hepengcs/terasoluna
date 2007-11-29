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

package jp.terasoluna.fw.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class JspWriterStub extends JspWriter {

	@Override
	public void newLine() throws IOException {
		// TODO Auto-generated method stub

	}
	
	public JspWriterStub(int arg0, boolean arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void print(boolean arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(char arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(int arg0) throws IOException {
		// TODO Auto-generated method stub
		throw new IOException("発生したIOExceptionのメッセージ");
	}

	@Override
	public void print(long arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(float arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(double arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(char[] arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(String arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void print(Object arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(boolean arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(char arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(long arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(float arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(double arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(char[] arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(String arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void println(Object arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearBuffer() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getRemaining() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void write(char[] arg0, int arg1, int arg2) throws IOException {
		// TODO Auto-generated method stub

	}

}
