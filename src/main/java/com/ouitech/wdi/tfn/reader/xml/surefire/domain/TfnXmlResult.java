package com.ouitech.wdi.tfn.reader.xml.surefire.domain;

import com.ouitech.wdi.tfn.common.TfnResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnStateEnum.INACTIVE;
import static com.ouitech.wdi.tfn.reader.xml.surefire.domain.TfnStateEnum.NONE;

public class TfnXmlResult extends TfnResult{

	private String fileName;
	private String projectName;
	private String testSuite;
	private String testCase;
	private String time;
	private String tfnInterface;
	private String cause;
	private String profil;
	private TfnStateEnum status;
	private List<Request> requests;

	public TfnXmlResult(Builder builder) {
		this.fileName = builder.fileName;
		this.projectName = builder.projectName;
		this.testSuite = builder.testSuite;
		this.testCase = builder.testCase;
		this.time = builder.time;
		this.tfnInterface = builder.tfnInterface;
		this.status = builder.status;
		this.requests = builder.requests;
		this.cause = builder.cause;
		this.profil = builder.profil;
	}

	public String print(){
	    return "";
    };

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(final String projectName) {
		this.projectName = projectName;
	}

	public String getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(final String testSuite) {
		this.testSuite = testSuite;
	}

	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(final String testCase) {
		this.testCase = testCase;
	}

	public String getTime() {
		return time;
	}

	public void setTime(final String time) {
		this.time = time;
	}

	public String getTfnInterface() {
		return tfnInterface;
	}

	public void setTfnInterface(final String tfnInterface) {
		this.tfnInterface = tfnInterface;
	}

	public TfnStateEnum getStatus() {
		return status;
	}

	public void setStatus(final TfnStateEnum status) {
		this.status = status;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(final List<Request> requests) {
		this.requests = requests;
	}

	public String getCause() {
		return cause;
	}

	public String getProfil() {
		return profil;
	}

	public static Comparator<TfnXmlResult> compare() {
		return Comparator.comparing(TfnXmlResult::getTfnInterface)
				.thenComparing(TfnXmlResult::getFileName)
				.thenComparing(TfnXmlResult::getTestSuite)
				.thenComparing(TfnXmlResult::getStatus);
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
		private TfnStateEnum status;
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

		public Builder withStatus(TfnStateEnum status) {
			this.status = status;
			return this;
		}

		public TfnXmlResult build(){
			return new TfnXmlResult(this);
		}
	}
}
