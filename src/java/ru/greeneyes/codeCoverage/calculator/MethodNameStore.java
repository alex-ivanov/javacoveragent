package ru.greeneyes.codeCoverage.calculator;

/**
 * @author ivanalx
 * @date 29.01.2009 14:13:31
 */
public class MethodNameStore {
	private final String name;
	private final String desc;

	public MethodNameStore(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return "MethodNameStore{" +
				"name='" + name + '\'' +
				", desc='" + desc + '\'' +
				'}';
	}
}
