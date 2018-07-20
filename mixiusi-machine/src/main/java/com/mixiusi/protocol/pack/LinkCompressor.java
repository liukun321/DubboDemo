package com.mixiusi.protocol.pack;

import java.nio.ByteBuffer;

import com.mixiusi.protocol.LinkFrame;
import com.mixiusi.protocol.marshal.Marshallable;
import com.mixiusi.protocol.marshal.Refmable;


/**
 * 压缩过的包结构如下：
 * 
 * +---------------------+ | LinkFrame | <---- LinkFrame的Tag被标记为COMPRESSED
 * +---------------------+ | Packet Body | <---- 压缩过的数据 +---------------------+
 * 
 * 即压缩过的包在最前面是一个标记为压缩的LinkFrame，后面跟睿是被压缩过包数据〿
 * 
 * @author YU Jiazi
 * 
 */
public class LinkCompressor {
	public static final int COMPRESS_SIZE = 1024;

	public static ByteBuffer compress(LinkFrame lf, Marshallable request) {
		Pack p = new Pack();
		p.putMarshallable(lf);

		// offset of PacketBody
		int offset = p.size();

		p.putMarshallable(request);

		if (p.size() < COMPRESS_SIZE || lf.isCompressed()) {
			// SET linkFrame size
			p.replaceInt(0, p.size());
			return p.getBuffer();

		} else {

			// compress the Packet body
			ByteBuffer buf = PacketCompressor.compress(ByteBuffer.wrap(p
					.getBuffer().array(), offset, p.size() - offset));

			// duplication a new LinkFrame
			lf.setCompressed();

			Pack pk = new Pack();
			pk.putMarshallable(lf);
			pk.putMarshallable(new Refmable(buf));

			lf.clearCompressed();

			// SET linkFrame size
			pk.replaceInt(0, pk.size());
			return pk.getBuffer();
		}
	}
}
