package com.mixiusi.protocol.pack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.mixiusi.protocol.LinkFrame;
import com.mixiusi.protocol.PacketHeader;
import com.mixiusi.protocol.PacketHeaderConvertException;
import com.mixiusi.protocol.encrypt.RC4;
import com.mixiusi.protocol.response.Response;



/**
* 包预处理噿
 * <p>
 * 加解密之籿
 * </p>
 * User: jingege Date: 1/13/12 Time: 3:31 PM
 */
public class PackagePacker {

	private final int PACKET_COMPRESS_SIZE = 1024;
	
	byte[] rc4Key ;

	RC4 rc4Encrypt;

	RC4 rc4Decrypt;

	KeyPair rsaKeyPair = null;

	/*
	 * 序列号在部分协议釿,被用于判断是否是离线的标访.0是离线1是在线的消息, 承以正常的serialid仿2弿始比较正帿
	 */
	private volatile short serialId = 2;

	public PackagePacker() {
	
		initRsaKeyPair();
	}

	

	public void initRsaKeyPair() {
		// e=0x10001 by default
		int keySize = 1024;
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		kpg.initialize(keySize);
		rsaKeyPair = kpg.generateKeyPair();
	}

	/**
	 * unpack璇锋眰
	 * <p>
	 * 鍐欏叆涔嬪墠棰勫鐞�
	 * </p>
	 * 
	 * @param request
	 * @return
	 */
	public Pack packRequest(Response response) {
		
		LinkFrame linkFrame = response.getLinkFrame();
		
		Pack body = response.packResponse();
	
		Pack pack = new Pack();
		
		// 濉厖搴忓垪鍙�
		//linkFrame.serialId = this.getSerialId();

		int bodySize = body.size();
		ByteBuffer actBuffer = null;
		// 鍒ゆ柇鏄惁瀵瑰寘杩涜鍘嬬缉
		if (bodySize < PACKET_COMPRESS_SIZE
				|| linkFrame.isCompressed()) {
			actBuffer = body.getBuffer();
		} else {
			actBuffer = PacketCompressor.compress(body.getBuffer());
			bodySize = actBuffer.limit();
			linkFrame.setCompressed();
		}
		
		try {
		
			PacketHeader header = linkFrame.toNewStyleHeader(true, true);
		
			header.setPacketLength(header.size() + bodySize);
			pack.putMarshallable(header);
			pack.putBuffer(actBuffer);

			System.out.println("send " + header);
			
			//Pack ret = encrypt(pack);
			return pack;
		} catch (PacketHeaderConvertException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * unpck鍝嶅簲
	 * <p>
	 * 鍐欏叆涔嬪墠棰勫鐞�
	 * </p>
	 * 
	 * @param responseBuffer
	 * @return
	 */
	public Unpack unpackResponse(ByteBuffer responseBuffer) {
		Unpack unpack = new Unpack(responseBuffer);
		unpack = this.decrypt(unpack);
		return unpack;
	}

	public int readLength(byte[] bytes) {
		assert bytes.length == 4;
		if (rc4Key == null) {
			return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
					.getInt();
		} else {
			Unpack unpack = new Unpack(bytes);
			unpack = decrypt(unpack);
			return unpack.popInt();
		}
	}

	private short getSerialId() {
		return this.serialId;
	}

	public void setRc4Key(byte[] rc4Key) {
		this.rc4Key = rc4Key;
		this.rc4Decrypt = new RC4(rc4Key);// Cipher.getInstance("RC4/ECB/NoPadding");
		// this.rc4Decrypt.init(Cipher.DECRYPT_MODE, rc4Key);

		this.rc4Encrypt = new RC4(rc4Key);// Cipher.getInstance("RC4/ECB/NoPadding");
		// this.rc4Encrypt.init(Cipher.ENCRYPT_MODE, rc4Key);
	}

	public Pack encrypt(Pack pack) {
		if (rc4Key == null) {
			//LogUtil.vendor("RC4 key not inited");
			return pack;
		} else {
			//LogUtil.vendor("RC4 key inited");
			ByteBuffer buffer = pack.getBuffer();
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes);

			bytes = rc4Encrypt.rc4(bytes);

			Pack ret = new Pack();
			ret.putFetch(bytes);
			return ret;
		}

	}

	public Unpack decrypt(Unpack unpack) {
		if (rc4Key == null) {
			//LogUtil.vendor("RC4 key not inited");
			return unpack;
		} else {
			//LogUtil.vendor("RC4 key inited");
			ByteBuffer buffer = unpack.getBuffer();
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes, 0, buffer.remaining());
			bytes = rc4Decrypt.rc4(bytes);
			Unpack ret = new Unpack(ByteBuffer.wrap(bytes));
			return ret;
		}
	}

	public String getModulus() {
		KeyFactory kfactory = null;
		try {
			kfactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (rsaKeyPair == null) {
			return null;
		} else {
			RSAPublicKeySpec kpubspec = null;
			try {
				kpubspec = (RSAPublicKeySpec) kfactory.getKeySpec(
						rsaKeyPair.getPublic(), RSAPublicKeySpec.class);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
			return kpubspec.getModulus().toString(16);
		}
	}

	public byte[] decryptRc4Key(byte[] encrypted) {
		Cipher c = null;
		byte[] shortRc4Key = new byte[16];
		try {
			c = Cipher.getInstance("RSA/ECB/NoPadding");
			c.init(Cipher.DECRYPT_MODE, rsaKeyPair.getPrivate());
			byte[] rawRc4Key = c.doFinal(encrypted);
			//LogUtil.vendor("Raw RC4 key[key=" + HexDump.toHex(rawRc4Key) + "]");
			// the last 16 bytes
			int length = (rawRc4Key.length > 16 ? 16 : rawRc4Key.length);
			System.arraycopy(rawRc4Key, rawRc4Key.length - length, shortRc4Key,
					16 - length, length);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		//LogUtil.vendor("Real RC4 key[key=" + HexDump.toHex(shortRc4Key) + "]");
		return shortRc4Key;

	}
}
