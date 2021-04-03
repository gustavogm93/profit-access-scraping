package ar.com.pa.model;

import java.sql.Date;
import java.util.Set;

public class FetchLog<T> {

	private final Date startFetch;
	private final Date endFetch;
	private final long timePerFetch;
	private final Set<T> objectsLog;

	private FetchLog(Builder<T> builder) {
		startFetch = builder.startFetch;
		endFetch = builder.endFetch;
		timePerFetch = builder.timePerFetch;
		objectsLog = builder.objectsLog;

	}

	public static class Builder<T> {

		private Date startFetch;
		private Date endFetch;
		private long timePerFetch;
		private Set<T> objectsLog;

		public Builder<T> start(Date val) {
			startFetch = val;
			return this;
		}

		public Builder<T> end(Date val) {
			endFetch = val;
			return this;
		}

		public Builder<T> perFetch(long val) {
			timePerFetch = val;
			return this;
		}

		public Builder<T> to(Set<T> val) {
			objectsLog = val;
			return this;
		}

		public FetchLog<T> build() {
			return new FetchLog<T>(this);
		}

	}
	


}
