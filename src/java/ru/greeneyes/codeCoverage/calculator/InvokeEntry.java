package ru.greeneyes.codeCoverage.calculator;

/**
 * @author ivanalx
 * @date 28.01.2009 10:59:10
 */
public class InvokeEntry {
	private final InvokeType type;
	private final int classId;
	private final int codeId;

	public InvokeEntry(InvokeType type, int classId, int codeId) {
		this.type = type;
		this.classId = classId;
		this.codeId = codeId;
	}

	public InvokeType getType() {
		return type;
	}

	public int getClassId() {
		return classId;
	}

	public int getCodeId() {
		return codeId;
	}

	@Override
	public String toString() {
		return "InvokeEntry{" +
				"type=" + type +
				", classId=" + classId +
				", codeId=" + codeId +
				'}';
	}
}
