package com.mixiusi.protocol.pack;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import com.mixiusi.protocol.marshal.Marshallable;

public class AutoPackHelper {
	public Pack pack() {
		Pack p = new Pack();
    	Field[] fieldsNeedPack = getSortedFields();
 
    	for (Field field : fieldsNeedPack) {
    		if (field == null)
				continue;
    		Method getter = null;
    		try {
				getter = this.getClass().getMethod(getterMethodNameGenerator(field), (Class<?>[]) null);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
    		if (getter != null) {
    			try {
					Object member = getter.invoke(this, (Object[])null);
					packObject(p, member);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
    		}
    	}
    	return p;
	}
	
	private void packObject(Pack p, Object object) {
		if (object instanceof Integer) {
			p.putInt((Integer)object);
		} else if (object instanceof Short) {
			p.putShort((Short)object);
		} else if (object instanceof Long) {
			p.putLong((Long)object);
		} else if (object instanceof String) {
			p.putVarstr((String)object);
		} else if (object instanceof Marshallable) {
			p.putMarshallable((Marshallable)object);
		} else if (object instanceof Collection<?>) {
			Collection<?> list = (Collection<?>)object;
			p.putInt(list.size());
			for (Object o : list) {
				packObject(p, o);
			}
		} else if (object instanceof byte[]) {
			p.putVarbin32((byte[]) object);
		}
	}
	
	public void unpack(Unpack up) {
		Field[] fieldsNeedPack = getSortedFields();
		for (Field field : fieldsNeedPack) {
			if (field == null)
				continue;
    		Method setter = null;
    		try {
				setter = this.getClass().getMethod(setterMethodNameGenerator(field), field.getType());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
    		if (setter != null) {
    			try {
    				Object member  = unPackObject(up, field.getType());
    				if (member != null) {
    					setter.invoke(this, member);
    				}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
    		}
		}
	}
	
	private Object unPackObject(Unpack up, Class<?> clazz) throws InstantiationException, IllegalAccessException {
		if (clazz == int.class || clazz == Integer.class) {
			return up.popInt();
		} else if (clazz == short.class || clazz == Short.class) {
			return up.popShort();
		} else if (clazz == long.class || clazz == Long.class) {
			return up.popLong();
		} else if (clazz == String.class) {
			return up.popVarstr();
		} else if (clazz.newInstance() instanceof Marshallable) {
			Marshallable mar = (Marshallable) clazz.newInstance();
			up.popMarshallable(mar);
			return mar;
		}else if (clazz == byte[].class) {
			return up.popVarbin32();
		}/*else if (clazz.newInstance() instanceof List<?>) {
			short len = up.popShort();
			for (int i = 0; i < len; ++i) {
				
			}
		}*/
		return null;
	}
	
	private Field[] getSortedFields() {
		Field[] fields = this.getClass().getDeclaredFields();
    	Field[] fieldsNeedPack = new Field[fields.length];
    	for (Field field : fields) {
    		Annotation annotation = field.getAnnotation(PackIndex.class);
    		if (annotation != null) {
    			PackIndex indexAnnotation = (PackIndex)annotation;
    			int index = indexAnnotation.value();
    			fieldsNeedPack[index] = field;
    		}
    	}
    	
    	return fieldsNeedPack;
	}
	
	/**
	 * 鏂规硶鍚嶇敓鎴�
	 * 
	 * @param methodName
	 * @param prefix
	 * @return
	 */
	private static String methodNameGenerator(String methodName, String prefix) {
		if (methodName == null) {
			throw new IllegalArgumentException();
		}
		if (methodName.charAt(1) > 65 && methodName.charAt(1) < 90) {// 鏍规嵁Eclipse鐨勮鍒欙紝鐓ч【xXxx妯″紡鐨勫瓧娈靛悕
			return prefix + methodName;
		} else {
			return (prefix == null ? "" : prefix)
					+ methodName.substring(0, 1).toUpperCase()
					+ methodName.substring(1);
		}
	}

	/**
	 * 鐢熸垚get鏂规硶
	 * 
	 * @param field
	 * @return
	 */
	private static String getterMethodNameGenerator(Field field) {
		return methodNameGenerator(field.getName(), "get");
	}

	/**
	 * 鐢熸垚set鏂规硶
	 * 
	 * @param field
	 * @return
	 */
	private static String setterMethodNameGenerator(Field field) {
		return methodNameGenerator(field.getName(), "set");
	}
}
