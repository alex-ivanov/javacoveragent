package ru.greeneyes.codeCoverage.calculator;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ivanalx
 * @date 28.01.2009 14:35:42
 */
public class InvokeQueue {
	private Queue<InvokeEntry> invokesQueue = new ConcurrentLinkedQueue<InvokeEntry>();

	public void addCodelineInvoke(int classId, int lineNumber) {
		invokesQueue.add(new InvokeEntry(InvokeType.LINE, classId, lineNumber));
	}

	public void addMethodInvoke(int classId, int methodId) {
		invokesQueue.add(new InvokeEntry(InvokeType.METHOD, classId, methodId));
	}

	public int size() {
		return invokesQueue.size();
	}

	public InvokeEntry poll() {
		return invokesQueue.poll();
	}

	@Override
	public String toString() {
		return invokesQueue.toString();
	}
}
