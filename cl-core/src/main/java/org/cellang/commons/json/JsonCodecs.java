/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

/**
 * @author wu
 * 
 */
public class JsonCodecs extends AbstractCodecs {

	public JsonCodecs() {
		this.add(new ErrorInfoJCS(this));
		this.add(new ErrorInfosJCS(this));

		this.add(new ObjectPropertiesJCS(this));
		this.add(new ObjectListJCS(this));
		this.add(new IntegerJCS(this));
		this.add(new StringJCS(this));
		this.add(new BooleanJCS(this));
		this.add(new DateJCS(this));
		this.add(new LongJCS(this));//
		this.add(new MessageJCS(this));

	}

}
