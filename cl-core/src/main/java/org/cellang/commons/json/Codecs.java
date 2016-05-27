package org.cellang.commons.json;

public interface Codecs {
	public Codec getCodec(Class<?> dataCls);

	public Codec getCodec(Class<?> dataCls, boolean force);

	public Codec getCodec(String type);

}