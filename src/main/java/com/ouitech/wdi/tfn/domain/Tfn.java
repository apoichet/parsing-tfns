package com.ouitech.wdi.tfn.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import static com.ouitech.wdi.tfn.domain.TfnStateEnum.INACTIVE;
import static com.ouitech.wdi.tfn.domain.TfnStateEnum.NONE;

public class Tfn {

	private String fileName;
	private String projectName;
	private String testSuite;
	private String testCase;
	private String time;
	private String tfnInterface;
	private TfnStateEnum status;
	private List<Request> requests;

	public Tfn(Builder builder) {
		this.fileName = builder.fileName;
		this.projectName = builder.projectName;
		this.testSuite = builder.testSuite;
		this.testCase = builder.testCase;
		this.time = builder.time;
		this.tfnInterface = builder.tfnInterface;
		this.status = builder.status;
		this.requests = builder.requests;
	}

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

	public static Builder builder(){
		return new Builder();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Tfn tfn = (Tfn) o;

		return testCase.equals(tfn.testCase);
	}

	public static class Builder{
		private String fileName;
		private String projectName;
		private String testSuite;
		private String testCase;
		private String time;
		private String tfnInterface;
		private TfnStateEnum status;
		private List<Request> requests = new ArrayList<>();

		public Tfn.Builder withTime(String time) {
			this.time = time;
			return this;
		}

		public Tfn.Builder withTestCase(String testCase) {
			this.testCase = testCase;
			return this;
		}

		public Tfn.Builder withTestSuite(String testSuite) {
			this.testSuite = testSuite;
			return this;
		}

		public Tfn.Builder withFileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public Tfn.Builder withProjectName(String projectName) {
			this.projectName = projectName;
			return this;
		}


		public Tfn.Builder withInterfaces(String tfnInterface) {
			this.tfnInterface = tfnInterface;
			return this;
		}

		public Tfn.Builder withInactiveStatus(boolean inactive){
		    TfnStateEnum status = inactive ? INACTIVE : NONE;
		    this.status = status;
		    return this;
        }

		public Tfn build(){
			return new Tfn(this);
		}
	}
}
