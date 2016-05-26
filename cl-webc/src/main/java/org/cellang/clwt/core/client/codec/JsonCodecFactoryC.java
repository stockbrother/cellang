/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client.codec;

/**
 * @author wu
 * 
 */
public class JsonCodecFactoryC extends AbstractCodecFactory {

	public JsonCodecFactoryC() {
		this.add(new ObjectPropertiesJCC(this));
		this.add(new ErrorInfoJCC(this));
		this.add(new ErrorInfosJCC(this));
		this.add(new ObjectListJCC(this));
		this.add(new MessageJCC(this));
		this.add(new IntegerJCC(this));
		this.add(new StringJCC(this));
		this.add(new BooleanJCC(this));
		this.add(new DateJCC(this));
		this.add(new LongJCC(this));

	}

}
