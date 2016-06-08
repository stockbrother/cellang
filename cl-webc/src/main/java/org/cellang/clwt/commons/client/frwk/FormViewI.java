/**
 *  Feb 1, 2013
 */
package org.cellang.clwt.commons.client.frwk;

import java.util.List;
import java.util.Map;

import org.cellang.clwt.commons.client.mvc.ViewI;
import org.cellang.clwt.commons.client.mvc.widget.EditorI;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.lang.Callback;
import org.cellang.clwt.core.client.lang.Path;

/**
 * @author wuzhen
 * 
 */
public interface FormViewI extends ViewI {

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls);

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Map<String, Object> editorPts);

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass,
			final Callback<T, Object> editorCallback);

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass);

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass,
			Map<String, Object> editorPts);

	public FormModel getFormModel();

	public ObjectPropertiesData getData();

	public List<Path> getActionList();

	public <T> T getFieldData(String fname, T def);

	public <T> T getFieldData(String fname);

	public void setFieldValue(String fname, Object value);

}
