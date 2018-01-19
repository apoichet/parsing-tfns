package com.ouitech.wdi.tfn.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public class Request {

	private String input;
	private String output;
	private Double time;
	private LocalDateTime date;
	private Optional<String> exception;

	public Request(Builder builder) {
		this.input = builder.input;
		this.output = builder.output;
		this.time = builder.time;
		this.date = builder.date;
		this.exception = builder.exception;
	}

	public String getInput() {
		return input;
	}

	public void setInput(final String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(final String output) {
		this.output = output;
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

	public static class Builder{

		private String input;
		private String output;
		private Double time;
		private LocalDateTime date;
		private Optional<String> exception;

		public Builder withInput(final String input) {
			this.input = input;
			return this;
		}

		public Builder withOutput(final String output) {
			this.output = output;
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
