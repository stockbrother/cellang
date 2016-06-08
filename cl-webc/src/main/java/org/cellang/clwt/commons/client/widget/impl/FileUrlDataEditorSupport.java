/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package org.cellang.clwt.commons.client.widget.impl;

import org.cellang.clwt.commons.client.html5.FileReaderJSO;
import org.cellang.clwt.commons.client.html5.FileRefJSO;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.core.ElementWrapper;
import org.cellang.clwt.core.client.gwtbridge.GwtChangeHandler;
import org.cellang.clwt.core.client.lang.Handler;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.lang.ObjectElementHelper;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.DOM;

/**
 * @author wuzhen
 *         <p>
 *         This implementation require html5.
 */
public class FileUrlDataEditorSupport extends EditorSupport<String> {

	private int maxSize = 10 * 1024 * 1024;// max size of file processing.

	protected ObjectElementHelper input;

	protected ElementWrapper dataRender;
	
	protected boolean functional;

	/**
	 * @param ele
	 */
	public FileUrlDataEditorSupport(Container c, String name, HasProperties<Object> pts) {
		super(c, name, DOM.createDiv(),pts);
		this.dataRender = new ElementWrapper(DOM.createDiv());
		this.elementWrapper.append(dataRender);//
		

		//
		// input = DOM.createElement("input");
		// this.input.setAttribute("type", "file");
		// DOM.appendChild(this.element, this.input);//

		// ?multiple
		// TODO add outer element.
		this.functional = FileReaderJSO.isSupport();
		
		if (this.functional) {
			this.input = this.helpers.addHelper("input", DOM.createElement("input"));
			this.getMasterHelper().append(this.input);//
			this.input.setAttribute("type", "file");
			this.input.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(),
					new GwtChangeHandler() {

						@Override
						protected void handleInternal(ChangeEvent evt) {
							FileUrlDataEditorSupport.this.onChange(evt);
							//

						}
					});
		}else{
			this.dataRender.getElement().setInnerText("Your browser not support html5 file feader!");
		}
	}

	/**
	 * see:http://www.html5rocks.com/en/tutorials/file/dndfiles/
	 */
	protected void onChange(com.google.gwt.event.dom.client.ChangeEvent evt) {

		JsArray<FileRefJSO> flist = FileRefJSO.getFileList(this.input.getElement());//

		if (flist.length() > 1) {
			throw new UiException("multiple files is not supported");
		}
		if (flist.length() == 0) {
			this.setData(null, true);//
			return;// TODO null

		}
		FileRefJSO file = flist.get(0);
		if (file.getSize() > this.maxSize) {
			throw new UiException("not supported size:" + file.getSize() + ",max size:" + this.maxSize);
		}
		String name = file.getName();//
		JsDate jsd = file.getLastModifiedDate();

		long last = jsd == null ? 0L : (long) jsd.getMilliseconds();// TODO
																	// int
																	// to
																	// long?
		final FileReaderJSO fr = FileReaderJSO.newInstance();

		fr.onLoadEnd(new Handler<JavaScriptObject>() {

			@Override
			public void handle(JavaScriptObject t) {
				FileUrlDataEditorSupport.this.onLoadEnd(fr, t);
			}
		}).readAsDataURL(file);
	}

	protected void onLoadEnd(FileReaderJSO reader, Object evt) {

		String dataUrl = reader.getResult();//

		String urlData = (dataUrl);//
		this.onDataLoad(urlData);
	}

	protected void onDataLoad(String urlData) {

		this.setData(urlData, true);//
	}
}
