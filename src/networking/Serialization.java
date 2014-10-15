package networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controllers.WorldController;

public class Serialization {
	/**
	 * @param obj - object to serialize to a byte array
	 * @return byte array containing the serialized obj
	 */
	public static byte[] serialize(WorldController obj) {
		byte[] result = null;
		ByteArrayOutputStream fos = null;

		try {
			fos = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(fos);
			o.writeObject(obj);
			result = fos.toByteArray();
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}


	/**
	 * @param arr - the byte array that holds the serialized object
	 * @return the deserialized object
	 */
	public static WorldController deserialize(byte[] arr) {
		InputStream fis = null;

		try {
			fis = new ByteArrayInputStream(arr);
			ObjectInputStream o = new ObjectInputStream(fis);
			return (WorldController) o.readObject();
		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}

		return null;
	}

	/**
	 * @param obj - object to be cloned
	 * @return a clone of obj
	 */
	@SuppressWarnings("unchecked")
	public static WorldController  cloneObject(WorldController obj) {
		return  deserialize(serialize(obj));
	}

}
