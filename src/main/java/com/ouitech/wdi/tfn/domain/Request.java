package com.ouitech.wdi.tfn.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public class Request {

	private String inOut;
	private String name;
	private String id;
	private String profile;
	private Double time;
	private LocalDateTime date;
	private Optional<String> exception;

	public Request(Builder builder) {
		this.inOut = builder.inOut;
		this.id = builder.id;
		this.time = builder.time;
		this.date = builder.date;
		this.exception = builder.exception;
		this.profile = builder.profile;
		this.name = builder.name;
	}

	public String getInOut() {
		return inOut;
	}

	public void setInOut(final String inOut) {
		this.inOut = inOut;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(final Double time) {
		this.time = time;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public Optional<String> getException() {
		return exception;
	}

	public void setException(final Optional<String> exception) {
		this.exception = exception;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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
		private Optional<String> exception;
		private String name;

        public Builder withName(String name) {
            this.name = name;
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

		public Builder withException(final Optional<String> exception) {
			this.exception = exception;
			return this;
		}

		public Request build(){
			return new Request(this);
		}
	}
}
