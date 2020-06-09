package com.sutong.bjstjh.hcp.tools;

public class Base64 {

	private static final Base64Codec CODEC = new Base64Codec();

	private Base64() {
	}

	public static String encodeAsString(byte... bytes) {
		return bytes == null ? null : (bytes.length == 0 ? "" : CodecUtils.toStringDirect(CODEC.encode(bytes)));
	}
}
