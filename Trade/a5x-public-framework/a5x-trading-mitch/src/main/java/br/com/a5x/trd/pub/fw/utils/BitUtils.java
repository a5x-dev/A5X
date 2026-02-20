package br.com.a5x.trd.pub.fw.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * Utility class for bit and byte manipulation operations.
 * Provides methods for converting between different numeric representations,
 * byte order handling, and base62 encoding/decoding.
 */
public class BitUtils {
	
	// Pre-computed base62 character lookup for encoding
	private static final byte[] BASE62_CHARS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	
	// Pre-computed base62 character to value lookup table
	private static final int[] BASE62_DECODE = new int[128];
	static {
		for (int i = 0; i < BASE62_DECODE.length; i++) {
			BASE62_DECODE[i] = -1;
		}
		for (int i = 0; i < 10; i++) {
			BASE62_DECODE['0' + i] = i;
		}
		for (int i = 0; i < 26; i++) {
			BASE62_DECODE['A' + i] = 10 + i;
			BASE62_DECODE['a' + i] = 36 + i;
		}
	} 
	
	/**
	 * Extracts individual bits from a byte into an integer array.
	 * 
	 * @param b the byte to extract bits from
	 * @return an array of 8 integers (0 or 1) with MSB at index 0
	 */
	public static int[] getBitsFromByte(byte b) {
		int[] bits = new int[8];
		
		for (int i = 0; i < 8; i++) {
			bits[7 - i] = (b >> i) & 1; // MSB at index 0
		}
		
		return bits;
	}
	
	/**
	 * Converts a byte to an unsigned 8-bit integer from ByteBuffer.
	 * 
	 * @param bb the ByteBuffer to read from
	 * @return the unsigned 8-bit integer value (0-255)
	 */
	public static int byteToUInt8(ByteBuffer bb) {
		return byteToUInt8(bb.get());
	}
	
	/**
	 * Converts a byte to an unsigned 8-bit integer.
	 * 
	 * @param b the byte to convert
	 * @return the unsigned 8-bit integer value (0-255)
	 */
	public static int byteToUInt8(byte b) {
		return b & 0xFF;
	}
	
	/**
	 * Converts two bytes to an unsigned 16-bit integer (big-endian).
	 * 
	 * @param b0 the high byte
	 * @param b1 the low byte
	 * @return the unsigned 16-bit integer value
	 */
	public static int byteArrayToUInt16(byte b0, byte b1) {
	    return ((int) (b0 & 0xFF) << 8)  |
	           ((int) (b1 & 0xFF));
	}
	
	/**
	 * Converts a 2-byte array to an unsigned 16-bit integer (big-endian).
	 * 
	 * @param bytes the byte array (must be exactly 2 bytes)
	 * @return the unsigned 16-bit integer value
	 * @throws IllegalArgumentException if array length is not 2
	 */
	public static int byteArrayToUInt16(byte[] bytes) {
	    if (bytes.length != 2) {
	        throw new IllegalArgumentException("Byte array must be exactly 2 bytes long");
	    }

	    return byteArrayToUInt16(bytes[0], bytes[1]);
	}
	
	/**
	 * Converts bytes from ByteBuffer to an unsigned 16-bit integer.
	 * 
	 * @param pBytes the ByteBuffer to read from
	 * @param pIsBigEndian true for big-endian, false for little-endian
	 * @return the unsigned 16-bit integer value
	 * @throws IllegalArgumentException if insufficient bytes available
	 */
	public static int byteArrayToUInt16(ByteBuffer pBytes, boolean pIsBigEndian) {
	    if ((pBytes.capacity() - pBytes.position()) < 2) {
	        throw new IllegalArgumentException("Byte array must be exactly 2 bytes long");
	    }
	    
	    byte b1 = pBytes.get();
	    byte b2 = pBytes.get();
	    
	    if (pIsBigEndian) {
	    	return byteArrayToUInt16(b1, b2);
	    } else {
	    	return byteArrayToUInt16(b2, b1);
	    }
	}
	
	/**
	 * Converts a 4-byte array to an unsigned 32-bit integer (big-endian).
	 * 
	 * @param bytes the byte array (must be exactly 4 bytes)
	 * @return the unsigned 32-bit integer value as long
	 * @throws IllegalArgumentException if array length is not 4
	 */
	public static long byteArrayToUInt32(byte[] bytes) {
	    if (bytes.length != 4) {
	        throw new IllegalArgumentException("Byte array must be exactly 4 bytes long");
	    }

	    return ((long) (bytes[0] & 0xFF) << 24) |
	           ((long) (bytes[1] & 0xFF) << 16) |
	           ((long) (bytes[2] & 0xFF) << 8)  |
	           ((long) (bytes[3] & 0xFF));
	}

	/**
	 * Converts bytes from ByteBuffer to an unsigned 32-bit integer.
	 * 
	 * @param bytes the ByteBuffer to read from
	 * @param pIsBigEndian true for big-endian, false for little-endian
	 * @return the unsigned 32-bit integer value as long
	 * @throws IllegalArgumentException if insufficient bytes available
	 */
	public static long byteArrayToUInt32(ByteBuffer bytes, boolean pIsBigEndian) {
	    if ((bytes.capacity() - bytes.position()) < 4) {
	        throw new IllegalArgumentException("Byte array must be exactly 4 bytes long");
	    }
	    
	    if (pIsBigEndian) {
	    	byte[] temp = new byte[4];
	    	bytes.get(temp);
	    	return byteArrayToUInt32(temp);
	    } else {
	    	byte b0 = bytes.get();
	    	byte b1 = bytes.get();
	    	byte b2 = bytes.get();
	    	byte b3 = bytes.get();
	    	return ((long) (b3 & 0xFF) << 24) |
	    	       ((long) (b2 & 0xFF) << 16) |
	    	       ((long) (b1 & 0xFF) << 8)  |
	    	       ((long) (b0 & 0xFF));
	    }
	}
	
	/**
	 * Converts an 8-byte array to an unsigned 64-bit integer (big-endian).
	 * 
	 * @param bytes the byte array (must be exactly 8 bytes)
	 * @return the unsigned 64-bit integer value as BigInteger
	 * @throws IllegalArgumentException if array length is not 8
	 */
	public static BigInteger byteArrayToUInt64(byte[] bytes) {
	    if (bytes.length != 8) {
	        throw new IllegalArgumentException("Byte array must be exactly 8 bytes long");
	    }

	    return new BigInteger(1, bytes);
	}

	/**
	 * Converts bytes from ByteBuffer to an unsigned 64-bit integer.
	 * 
	 * @param bytes the ByteBuffer to read from
	 * @param pIsBigEndian true for big-endian, false for little-endian
	 * @return the unsigned 64-bit integer value as BigInteger
	 * @throws IllegalArgumentException if insufficient bytes available
	 */
	public static BigInteger byteArrayToUInt64(ByteBuffer bytes, boolean pIsBigEndian) {
	    if ((bytes.capacity() - bytes.position()) < 8) {
	        throw new IllegalArgumentException("Byte array must be exactly 8 bytes long");
	    }
	    
	    byte[] temp = new byte[8];
	    bytes.get(temp);
	    
	    if (!pIsBigEndian) {
	    	for(int i = 0; i < 4; i++) {
	    		byte swap = temp[i];
	    		temp[i] = temp[7-i];
	    		temp[7-i] = swap;
	    	}
	    }
	    
	    return new BigInteger(1, temp);
	}

	/**
	 * Converts an unsigned 8-bit integer to a byte.
	 * 
	 * @param value the unsigned integer value (0-255)
	 * @return the byte representation
	 */
	public static byte uInt8ToByte(int value) {
		return (byte) value;
	}
	
	/**
	 * Writes an unsigned 8-bit integer to ByteBuffer.
	 * 
	 * @param value the unsigned integer value (0-255)
	 * @param pData the ByteBuffer to write to
	 */
	public static void uint8ToByte(int value, ByteBuffer pData) {
		pData.put((byte) value);
	}
	
	/**
	 * Writes an unsigned 16-bit integer to ByteBuffer.
	 * 
	 * @param value the unsigned 16-bit integer value
	 * @param pData the ByteBuffer to write to
	 * @param pIsBigEndian true for big-endian, false for little-endian
	 */
	public static void uint16ToByteArray(int value, ByteBuffer pData, boolean pIsBigEndian) {
		if (pIsBigEndian) {
			pData.put((byte) ((value >> 8) & 0xFF));
			pData.put((byte) (value & 0xFF));
		} else {
			pData.put((byte) (value & 0xFF));
			pData.put((byte) ((value >> 8) & 0xFF));
		}
	}
	
	/**
	 * Writes an unsigned 32-bit integer to ByteBuffer.
	 * 
	 * @param value the unsigned 32-bit integer value
	 * @param pData the ByteBuffer to write to
	 * @param pIsBigEndian true for big-endian, false for little-endian
	 */
	public static void uint32ToByteArray(long value, ByteBuffer pData, boolean pIsBigEndian) {
		if (pIsBigEndian) {
			pData.put((byte) ((value >> 24) & 0xFF));
			pData.put((byte) ((value >> 16) & 0xFF));
			pData.put((byte) ((value >> 8) & 0xFF));
			pData.put((byte) (value & 0xFF));
		} else {
			pData.put((byte) (value & 0xFF));
			pData.put((byte) ((value >> 8) & 0xFF));
			pData.put((byte) ((value >> 16) & 0xFF));
			pData.put((byte) ((value >> 24) & 0xFF));
		}
	}
	
	/**
	 * Writes an unsigned 64-bit integer to ByteBuffer.
	 * 
	 * @param value the unsigned 64-bit integer value
	 * @param pData the ByteBuffer to write to
	 * @param pIsBigEndian true for big-endian, false for little-endian
	 */
	public static void uint64ToByteArray(BigInteger value, ByteBuffer pData, boolean pIsBigEndian) {
		byte[] bytes = value.toByteArray();
		byte[] result = new byte[8];
		
		// Handle sign byte if present
		int srcPos = bytes.length > 8 ? bytes.length - 8 : 0;
		int destPos = bytes.length < 8 ? 8 - bytes.length : 0;
		int length = Math.min(bytes.length, 8);
		
		System.arraycopy(bytes, srcPos, result, destPos, length);
		
		if (!pIsBigEndian) {
			for (int i = 0; i < 4; i++) {
				byte swap = result[i];
				result[i] = result[7 - i];
				result[7 - i] = swap;
			}
		}
		
		pData.put(result);
	}
	
	/**
	 * Converts a long value to ASCII byte array using base62 encoding.
	 * Base62 uses characters: 0-9, A-Z, a-z
	 * 
	 * @param value the long value to encode
	 * @param length the desired output length
	 * @return the ASCII byte array representation
	 * @throws IllegalArgumentException if length is not positive
	 */
	public static byte[] base62ToAscii(long value, int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("Length must be positive");
		}
		
		byte[] result = new byte[length];
		
		for (int i = length - 1; i >= 0; i--) {
			result[i] = BASE62_CHARS[(int) (value % 62)];
			value /= 62;
		}
		
		return result;
	}
	
	/**
	 * Converts an ASCII byte array from base62 encoding to a long value.
	 * Base62 uses characters: 0-9, A-Z, a-z
	 * 
	 * @param ascii the ASCII byte array to decode
	 * @return the decoded long value
	 * @throws IllegalArgumentException if invalid base62 character is encountered
	 */
	public static long asciiToBase62(byte[] ascii) {
		long result = 0;
		
		for (byte b : ascii) {
			if (b < 0 || b >= 128) {
				throw new IllegalArgumentException("Invalid base62 character: " + (char) b);
			}
			int index = BASE62_DECODE[b];
			if (index == -1) {
				throw new IllegalArgumentException("Invalid base62 character: " + (char) b);
			}
			result = result * 62 + index;
		}
		
		return result;
	}
}
