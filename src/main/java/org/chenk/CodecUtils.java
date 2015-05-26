package org.chenk;

public class CodecUtils {

	public static final char[] SIMBASE32_TABLE = { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y',
			'Z' };

	public static final char[] SIMBASE16_TABLE = { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static final char[] SIMDEC_TABLE = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9' };

	public static final String[] hexStrings;

	static {
		hexStrings = new String[256];
		for (int i = 0; i < 256; i++) {
			StringBuilder d = new StringBuilder(2);
			char ch = Character.forDigit(((byte) i >> 4) & 0x0F, 16);
			d.append(Character.toUpperCase(ch));
			ch = Character.forDigit((byte) i & 0x0F, 16);
			d.append(Character.toUpperCase(ch));
			hexStrings[i] = d.toString();
		}
	}

	public static String hexString(byte[] b) {
		StringBuilder d = new StringBuilder(b.length * 2);
		for (byte aB : b) {
			d.append(hexStrings[(int) aB & 0xFF]);
		}
		return d.toString();
	}

	/**
	 * converts a byte array to printable characters
	 * 
	 * @param b
	 *            - byte array
	 * @return String representation
	 */
	public static String dumpString(byte[] b) {
		StringBuilder d = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			char c = (char) b[i];
			if (Character.isISOControl(c)) {
				// TODO: complete list of control characters,
				// use a String[] instead of this weird switch
				switch (c) {
				case '\r':
					d.append("{CR}");
					break;
				case '\n':
					d.append("{LF}");
					break;
				case '\000':
					d.append("{NULL}");
					break;
				case '\001':
					d.append("{SOH}");
					break;
				case '\002':
					d.append("{STX}");
					break;
				case '\003':
					d.append("{ETX}");
					break;
				case '\004':
					d.append("{EOT}");
					break;
				case '\005':
					d.append("{ENQ}");
					break;
				case '\006':
					d.append("{ACK}");
					break;
				case '\007':
					d.append("{BEL}");
					break;
				case '\020':
					d.append("{DLE}");
					break;
				case '\025':
					d.append("{NAK}");
					break;
				case '\026':
					d.append("{SYN}");
					break;
				case '\034':
					d.append("{FS}");
					break;
				case '\036':
					d.append("{RS}");
					break;
				default:
					d.append('[');
					d.append(hexStrings[(int) b[i] & 0xFF]);
					d.append(']');
					break;
				}
			} else
				d.append(c);

		}
		return d.toString();
	}

	/**
	 * converts a byte array to hex string (suitable for dumps and ASCII
	 * packaging of Binary fields
	 * 
	 * @param b
	 *            - byte array
	 * @param offset
	 *            - starting position
	 * @param len
	 *            the length
	 * @return String representation
	 */
	public static String hexString(byte[] b, int offset, int len) {
		StringBuilder d = new StringBuilder(len * 2);
		len += offset;
		for (int i = offset; i < len; i++) {
			d.append(hexStrings[(int) b[i] & 0xFF]);
		}
		return d.toString();
	}

	/**
	 * @param b
	 *            source byte array
	 * @param offset
	 *            starting offset
	 * @param len
	 *            number of bytes in destination (processes len*2)
	 * @return byte[len]
	 */
	public static byte[] hex2byte(byte[] b, int offset, int len) {
		byte[] d = new byte[len];
		for (int i = 0; i < len * 2; i++) {
			int shift = i % 2 == 1 ? 0 : 4;
			d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
		}
		return d;
	}

	/**
	 * @param s
	 *            source string (with Hex representation)
	 * @return byte array
	 */
	public static byte[] hex2byte(String s) {
		if (s.length() % 2 == 0) {
			return hex2byte(s.getBytes(), 0, s.length() >> 1);
		} else {
			// Padding left zero to make it even size #Bug raised by tommy
			return hex2byte("0" + s);
		}
	}
}
