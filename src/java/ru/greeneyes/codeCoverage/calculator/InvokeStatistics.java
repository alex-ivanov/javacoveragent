package ru.greeneyes.codeCoverage.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author ivanalx
 * @date 28.01.2009 14:08:25
 */
public class InvokeStatistics {
	private List<List<MethodNameStore>> methodsNames = new ArrayList<List<MethodNameStore>>();
	private List<String> classNames = new ArrayList<String>();

	private Map<Integer, Set<Integer>> visitedMethods = new ConcurrentHashMap<Integer, Set<Integer>>();
	private Map<Integer, Set<Integer>> visitedLines = new ConcurrentHashMap<Integer, Set<Integer>>();

	public InvokeStatistics() {
	}

	public synchronized int registerClass(String className) {
		methodsNames.add(new LinkedList<MethodNameStore>());
		classNames.add(className);
		int id = classNames.size() - 1;
		if (!visitedMethods.containsKey(id)) {
			visitedMethods.put(id, new ConcurrentSkipListSet<Integer>());
		}
		if (!visitedLines.containsKey(id)) {
			visitedLines.put(id, new ConcurrentSkipListSet<Integer>());
		}
		return id;
	}

	public synchronized int registerMethod(int classId, String methodName, String methodDesc) {
		List<MethodNameStore> methods = methodsNames.get(classId);
		methods.add(new MethodNameStore(methodName, methodDesc));
		return methods.size() - 1;
	}

	public void visitMethod(int classId, int methodId) {
		visitedMethods.get(classId).add(methodId);
	}

	public void visitLine(int classId, int lineId) {
		visitedLines.get(classId).add(lineId);
	}

	public Map<String, Map<MethodNameStore, Boolean>> generateMethodInvokeStatistics() {
		Map<String, Map<MethodNameStore, Boolean>> statistics = new HashMap<String, Map<MethodNameStore, Boolean>>();

		for (Map.Entry<Integer, Set<Integer>> entry : visitedMethods.entrySet()) {
			String className = classNames.get(entry.getKey());
			List<MethodNameStore> methodNames = methodsNames.get(entry.getKey());
			Set<Integer> visitedMethods = entry.getValue();
			Set<Integer> notVisitedMethods = new HashSet<Integer>();
			for (int i = 0; i < methodNames.size(); i++) {
				notVisitedMethods.add(i);
			}
			notVisitedMethods.removeAll(visitedMethods);
			Map<MethodNameStore, Boolean> methodVisitStatistics = new HashMap<MethodNameStore, Boolean>();
			statistics.put(className, methodVisitStatistics);
			for (Integer methodId : visitedMethods) {
				methodVisitStatistics.put(methodNames.get(methodId), true);
			}
			for (Integer methodId : notVisitedMethods) {
				methodVisitStatistics.put(methodNames.get(methodId), false);
			}
		}
		return statistics;
	}

	public Map<String, Map<Integer, Boolean>> generateLineInvokeStatistics() {
		Map<String, Map<Integer, Boolean>> statistics = new HashMap<String, Map<Integer, Boolean>>(); // TODO use it
		return null;
	}

}
