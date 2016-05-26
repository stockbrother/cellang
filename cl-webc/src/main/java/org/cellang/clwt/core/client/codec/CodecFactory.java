package org.cellang.clwt.core.client.codec;

public interface CodecFactory {
	public <T> Codec<T> getCodec(Class<T> dataCls);

	public <T> Codec<T> getCodec(String type);

}