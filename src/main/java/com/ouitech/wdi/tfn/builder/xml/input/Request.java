package com.ouitech.wdi.tfn.builder.xml.input;

import java.time.LocalDateTime;

public class Request {

	private String inOut;
	private String service;
	private String operation;
	private String id;
	private String profile;
	private Double time;
	private LocalDateTime date;

	public String getInOut() {
		return inOut;
	}

	public String getService() {
		return service;
	}

	public String getOperation() {
		return operation;
	}

	public String getId() {
		return id;
	}

	public String getProfile() {
		return profile;
	}

	public Double getTime() {
		return time;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Request(Builder builder) {
		this.inOut = builder.inOut;
		this.id = builder.id;
		this.time = builder.time;
		this.date = builder.date;
		this.profile = builder.profile;
		this.service = builder.service;
		this.operation = builder.operation;
	}

    public static Builder builder(){
		return new Builder();
	}

	public static class Builder{

		private String inOut;
		private String id;
		private Double time;
		private LocalDateTime date;
		private String profile;
		private String service;
		private String operation;

        public Builder withService(String service) {
            this.service = service;
            return this;
        }

		public Builder withOperation(String operation) {
			this.operation = operation;
			return this;
		}

        public Builder withProfile(final String profile){
			this.profile = profile;
			return this;
		}

		public Builder withInOut(final String inOut) {
			this.inOut = inOut;
			return this;
		}

		public Builder withId(final String id) {
			this.id = id;
			return this;
		}

		public Builder withTime(final Double time) {
			this.time = time;
			return this;
		}

		public Builder withDate(final LocalDateTime date) {
			this.date = date;
			return this;
		}

		public Request build(){
			return new Request(this);
		}
	}
}
