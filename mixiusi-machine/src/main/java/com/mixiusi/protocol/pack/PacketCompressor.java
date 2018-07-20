package com.mixiusi.protocol.pack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class PacketCompressor {

	private static final int HDR_LEN = 4;

	private static final int MAX_LEN_UNCOMPRESS = 1024 * 1024 * 1; // 1M

	public static ByteBuffer compress(byte[] src, int offset, int len) {
		int headerSize = HDR_LEN + len + len / 1000 + 12;

		byte[] output = new byte[headerSize];
		Deflater compresser = new Deflater();
		compresser.setInput(src, offset, len);
		compresser.finish();

		int compressedDataLength = compresser.deflate(output);

		ByteBuffer compressedData = ByteBuffer
				.allocate(compressedDataLength + 4);
		compressedData.order(ByteOrder.LITTLE_ENDIAN);

		compressedData.putInt(len);
		compressedData.put(output, 0, compressedDataLength);
		compressedData.flip();

		return compressedData;
	}

	public static ByteBuffer compress(ByteBuffer src) {
		return compress(src.array(), src.position(),
				src.limit() - src.position());
	}

	public static ByteBuffer uncompress(Unpack up) throws Exception {
		int len = up.popInt();
		if (len < 0 || len >= MAX_LEN_UNCOMPRESS)
			throw new UnpackException("invalid uncompress len:" + len);

		try {
			ByteBuffer uncompressedData = ByteBuffer.allocate(len);

			Inflater decompressor = new Inflater();
			ByteBuffer src = up.getBuffer();

			decompressor.setInput(src.array(), src.position(), src.limit()
					- src.position());

			int resultLength = decompressor.inflate(uncompressedData.array());
			uncompressedData.position(resultLength);
			decompressor.end();

			uncompressedData.flip();

			return uncompressedData;
		} catch (Exception ex) {
			throw new UnpackException("uncompress error");
		}
	}
}
