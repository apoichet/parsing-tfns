package com.ouitech.wdi.tfn.builder.xml;

import com.ouitech.wdi.tfn.common.TfnResult;
import com.ouitech.wdi.tfn.builder.xml.input.Request;
import com.ouitech.wdi.tfn.common.TfnStateEnum;

import java.util.ArrayList;
import java.util.List;

public class TfnXmlResult extends TfnResult{

	private String fileName;
	private String projectName;
	private String testSuite;
	private String testCase;
	private String time;
	private String tfnInterface;
	private String cause;
	private String profil;
	private List<Request> requests;

	public TfnXmlResult(Builder builder) {
		this.fileName = builder.fileName;
		this.projectName = builder.projectName;
		this.testSuite = builder.testSuite;
		this.testCase = builder.testCase;
		this.time = builder.time;
		this.tfnInterface = builder.tfnInterface;
		this.requests = builder.requests;
		this.cause = builder.cause;
		this.profil = builder.profil;
		this.requests = builder.requests;
	}

	public String getTime() {
		return time;
	}

	public String getCause() {
		return cause;
	}

	public String getProfil() {
		return profil;
	}

	public String getFileName() {
		return fileName;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getTestSuite() {
		return testSuite;
	}

	public String getTestCase() {
		return testCase;
	}

	public String getTfnInterface() {
		return tfnInterface;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public static Builder builder(){
		return new Builder();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TfnXmlResult tfnXmlResult = (TfnXmlResult) o;

		return testCase.equals(tfnXmlResult.testCase);
	}

	@Override
	public String getState() {
		return status.name();
	}

	public static class Builder{
		private String fileName;
		private String projectName;
		private String testSuite;
		private String testCase;
		private String time;
		private String tfnInterface;
		private String cause;
		private String profil;
		private List<Request> requests = new ArrayList<>();

		public Builder withTime(String time) {
			this.time = time;
			return this;
		}

		public Builder withTestCase(String testCase) {
			this.testCase = testCase;
			return this;
		}

		public Builder withTestSuite(String testSuite) {
			this.testSuite = testSuite;
			return this;
		}

		public Builder withFileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public Builder withProjectName(String projectName) {
			this.projectName = projectName;
			return this;
		}

		public Builder withInterfaces(String tfnInterface) {
			this.tfnInterface = tfnInterface;
			return this;
		}

		public Builder withCause(String cause) {
			this.cause = cause;
			return this;
		}

		public Builder withProfil(String profil) {
			this.profil = profil;
			return this;
		}

		public Builder withRequest(List<Request> requests) {
			this.requests = requests;
			return this;
		}

		public TfnXmlResult build(){
			return new TfnXmlResult(this);
		}
	}
}
