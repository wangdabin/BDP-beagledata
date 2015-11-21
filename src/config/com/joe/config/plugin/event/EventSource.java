package com.joe.config.plugin.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author qiaolong
 *
 */
public class EventSource {
	private Collection listeners;

	private Collection errorListeners;

	private int detailEvents;

	public EventSource() {
		initListeners();
	}

	public void addConfigListener(ConfigListener l) {
		doAddListener(listeners, l);
	}

	public boolean removeConfigListener(ConfigListener l) {
		return doRemoveListener(listeners, l);
	}

	public Collection getConfigListeners() {
		return doGetListeners(listeners);
	}

	public void clearConfigListeners() {
		doClearListeners(listeners);
	}

	public boolean isDetailEvents() {
		synchronized (listeners) {
			return detailEvents > 0;
		}
	}

	public void setDetailEvents(boolean enable) {
		synchronized (listeners) {
			if (enable) {
				detailEvents++;
			} else {
				detailEvents--;
			}
		}
	}

	public void addErrorListener(ConfigErrorListener l) {
		doAddListener(errorListeners, l);
	}

	public boolean removeErrorListener(ConfigErrorListener l) {
		return doRemoveListener(errorListeners, l);
	}

	public void clearErrorListeners() {
		doClearListeners(errorListeners);
	}

	public Collection getErrorListeners() {
		return doGetListeners(errorListeners);
	}

	protected void fireEvent(int type, String propName, Object propValue,
			boolean before) {
		Collection listenersToCall = null;

		synchronized (listeners) {
			if (detailEvents >= 0 && listeners.size() > 0) {
				// Copy listeners to another collection so that manipulating
				// the listener list during event delivery won't cause problems
				listenersToCall = new ArrayList(listeners);
			}
		}

		if (listenersToCall != null) {
			ConfigEvent event = createEvent(type, propName, propValue, before);
			for (Iterator it = listenersToCall.iterator(); it.hasNext();) {
				((ConfigListener) it.next()).configChanged(event);
			}
		}
	}

	protected ConfigEvent createEvent(int type, String propName,
			Object propValue, boolean before) {
		return new ConfigEvent(this, type, propName, propValue, before);
	}

	protected void fireError(int type, String propName, Object propValue,
			Throwable ex) {
		Collection listenersToCall = null;

		synchronized (errorListeners) {
			if (errorListeners.size() > 0) {
				// Copy listeners to another collection so that manipulating
				// the listener list during event delivery won't cause problems
				listenersToCall = new ArrayList(errorListeners);
			}
		}

		if (listenersToCall != null) {
			ConfigErrorEvent event = createErrorEvent(type, propName,
					propValue, ex);
			for (Iterator it = listenersToCall.iterator(); it.hasNext();) {
				((ConfigErrorListener) it.next()).configError(event);
			}
		}
	}

	protected ConfigErrorEvent createErrorEvent(int type, String propName,
			Object propValue, Throwable ex) {
		return new ConfigErrorEvent(this, type, propName, propValue, ex);
	}

	protected Object clone() throws CloneNotSupportedException {
		EventSource copy = (EventSource) super.clone();
		copy.initListeners();
		return copy;
	}

	private static void doAddListener(Collection listeners, Object l) {
		if (l == null) {
			throw new IllegalArgumentException("Listener must not be null!");
		}
		synchronized (listeners) {
			listeners.add(l);
		}
	}

	private static boolean doRemoveListener(Collection listeners, Object l) {
		synchronized (listeners) {
			return listeners.remove(l);
		}
	}

	private static void doClearListeners(Collection listeners) {
		synchronized (listeners) {
			listeners.clear();
		}
	}

	private static Collection doGetListeners(Collection listeners) {
		synchronized (listeners) {
			return Collections.unmodifiableCollection(new ArrayList(listeners));
		}
	}

	private void initListeners() {
		listeners = new LinkedList();
		errorListeners = new LinkedList();
	}
}
